package com.example.photopicker

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.photopicker.ui.theme.PhotoPickerTheme

class ImageEffectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoPickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val effect = intent.getStringExtra("effect")

                    val photo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        intent.getParcelableExtra("picture", Uri::class.java)!!
                    else
                        intent.getParcelableExtra("picture")!!

                    if (effect == "b&w") {
                        val filter =
                            ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                        ImageEffect(
                            photo = photo,
                            colorFilter = filter,
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else {
                        val contrast = 2f // 0f..10f (1 should be default)
                        val brightness = -180f // -255f..255f (0 should be default)
                        val colorMatrix = floatArrayOf(
                            contrast, 0f, 0f, 0f, brightness,
                            0f, contrast, 0f, 0f, brightness,
                            0f, 0f, contrast, 0f, brightness,
                            0f, 0f, 0f, 1f, 0f
                        )
                        val filter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))
                        ImageEffect(
                            photo = photo,
                            colorFilter = filter,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ImageEffect(
    photo: Uri,
    colorFilter: ColorFilter,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Image(
        painter = rememberAsyncImagePainter(
            model = photo,
            imageLoader = ImageLoader(context)
        ),
        contentDescription = "Photo",
        colorFilter = colorFilter,
        modifier = modifier
            .fillMaxSize()
    )
}
