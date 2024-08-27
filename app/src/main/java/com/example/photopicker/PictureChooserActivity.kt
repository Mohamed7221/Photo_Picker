package com.example.photopickerbm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.photopicker.ImageEffectActivity
import com.example.photopicker.ui.theme.PhotoPickerTheme


class PictureChooserActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoPickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PhotoPicker(modifier = Modifier.padding((innerPadding)))

                }
            }
        }
    }
}

@Composable
fun PhotoPicker(modifier: Modifier = Modifier) {
    var picUri by remember { mutableStateOf<Uri?>(null) }
    val resultLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            picUri = it
        }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Button(onClick = { resultLauncher.launch("image/*") }) {
            Text(text = "Pick a photo")
        }
        AsyncImage(
            model = picUri,
            contentDescription = "Photo",
            modifier = Modifier.padding(250.dp)
        )

        EffectButton(btnName = "Black and White", effect = "b&w", pic = picUri)
        EffectButton(btnName = "Brightness and Contrast Effect", effect = "b&c", pic = picUri)

    }
}

@Composable
fun EffectButton(btnName: String, effect: String, pic: Uri?, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Button(onClick = {
        val i = Intent(context, ImageEffectActivity::class.java)
        i.putExtra("effect", effect)
        i.putExtra("picture", pic)
        context.startActivity(i)
    }) {
        Text(text = btnName)

    }
}

@Preview
@Composable
private fun PhotoPicker() {

}