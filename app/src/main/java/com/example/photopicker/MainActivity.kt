package com.example.photopicker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.photopicker.ui.theme.PhotoPickerTheme
import com.example.photopickerbm.PictureChooserActivity
import java.security.PrivateKey

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PhotoPickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginPage(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun LoginPage(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val reader = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    val savedEmail = reader.getString("email","")!!
    val savedPassword = reader.getString("password","")!!


    var email by remember { mutableStateOf(savedEmail) }
    var password by remember { mutableStateOf(savedPassword) }
    var checkBox by remember { mutableStateOf(true) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 16.dp)
        ) {
            Text(text = "Remember me next time")
            Checkbox(
                checked = checkBox,
                onCheckedChange = { checkBox = it }
            )
        }

        Button(
            onClick = {
                saveCredentials(email,password,context,checkBox)
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 16.dp)
        ) {
            Text(text = "Login")
        }
    }
}

fun saveCredentials(email :String , pass :String , context : Context , cbState : Boolean){

    val writer = context.getSharedPreferences("user_Data", Context.MODE_PRIVATE).edit()
    if(cbState){
        writer.putString("email",email)
        writer.putString("password",pass)
    }
    else{
        writer.putString("email","")
        writer.putString("password","")
    }
    writer.apply()
    val i = Intent(context, PictureChooserActivity::class.java)
    context.startActivity(i)

}

@Preview
@Composable
private fun LoginPagePreview() {
    LoginPage()
}