package ru.startandroid.stormloginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isLoading by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                delay(3000)
                isLoading = false
            }
            if (isLoading) {
                SplashScreen()
            } else {
                LoginScreen()
            }
        }
    }
}


@Composable
private fun SplashScreen() = Box(
    Modifier
        .fillMaxSize()
        .background(color = Color.Black),
    Alignment.Center
) {
    Image(
        painter = painterResource(id = R.mipmap.frame_19219),
        contentDescription = "",
        alignment = Alignment.Center, modifier = Modifier
            .padding(115.dp,355.dp).size(149.dp, 110.dp)
    )

    Text(
        text = "1.0.41",
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        color = Color(0xFF676767),
        modifier = Modifier.align(Alignment.BottomCenter).padding(64.dp)
    )
}

@Composable
@Preview
private fun LoginScreen() {
    val image = painterResource(R.drawable.typography)
    var isButtonEnabled = false
    Column (
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(top = 65.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(210.dp, 80.dp)
        )
        Column(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxWidth()
                .fillMaxHeight(0.47f)
                .padding(top = 188.dp, start = 11.dp, end = 11.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            var login by remember { mutableStateOf("") }
            var errorLoginText: String? = null
            var isLoginError = false
            when (login) {
                "", "Логин_Юзера" -> isLoginError = false
                else -> isLoginError = true
            }

            if (isLoginError) {
                if (!login.any { it in '\u0400'..'\u04FF' }) {
                    errorLoginText = "Логин пользователя должен быть на кириллице"
                } else {
                    errorLoginText = "Неверный логин"
                }
            }
            LoginTextField(
                value = login,
                onValueChange = { login = it },
                label = "Логин",
                isLoginError = isLoginError,
                errorLoginText = errorLoginText
            )


            var password by remember { mutableStateOf("") }
            var errorPasswordText: String? = null
            var isPasswordError = false

            if (password.length < 6 && password.isNotEmpty()) {
                errorPasswordText = "Пароль должен содержать не менее 6 символов"
                isPasswordError = true
            } else if (password.any { !password.any { it in 'a'..'z' || it in 'A'..'Z' } }) {
                errorPasswordText = "Пароль должен содержать хотя бы одну латинскую букву"
                isPasswordError = true
            } else if (password.any { !password.any {  it.isDigit() }}) {
                errorPasswordText = "Пароль должен содержать хотя бы одну цифру"
                isPasswordError = true
            } else {
                errorPasswordText = null
                isPasswordError = false
            }

            PasswordTextField(
                value = password,
                onValueChange = {
                    if (it.length <= 12) {
                    password = it
                } },
                label = "Пароль",
                isPasswordError = isPasswordError,
                errorPasswordText = errorPasswordText
            )
            if (!isPasswordError && !isLoginError && password != "" && login != "") {
                isButtonEnabled = true
            }
        }

        Row (
            modifier = Modifier
                .fillMaxSize().padding(end = 11.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {},
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isButtonEnabled) Color.White else Color.Gray,
                    contentColor = Color.DarkGray
                ),
                contentPadding = PaddingValues(12.dp, 4.dp),
                modifier = Modifier
                    .width(100.dp)
                    .height(47.dp)
            ) {
                Text(text = "ВОЙТИ")
            }
        }
    }
}

@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Логин",
    isLoginError: Boolean = false,
    errorLoginText: String? = ""
) {
   // var showPassword by remember { mutableStateOf(false) }
    Column() {
        TextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize =  18.sp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Unspecified),
            modifier = Modifier.width(367.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFF000000),
                unfocusedTextColor = Color.Gray,
                unfocusedLabelColor = Color.DarkGray,
                unfocusedSupportingTextColor = Color.Red,
                focusedContainerColor = Color.Black,
                focusedTextColor = Color.White,
                focusedIndicatorColor = if (!isLoginError) Color.DarkGray else Color.Red,
                focusedLabelColor = Color.DarkGray,
                focusedSupportingTextColor = Color.Red
            ),
            supportingText = {
                if (errorLoginText != null) {
                    Text(errorLoginText)
                }
            },
            label = { Text(label) }
        )
    }
}


@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Пароль",
    isPasswordError: Boolean = false,
    errorPasswordText: String? = ""
) {
    var showPassword by remember { mutableStateOf(false) }

    Column() {
        TextField(
            value = value,
            onValueChange = onValueChange,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.width(367.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = Color.White),
            label = { Text(label) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFF000000),
                unfocusedTextColor = Color.Gray,
                unfocusedLabelColor = Color.DarkGray,
                unfocusedIndicatorColor = Color.DarkGray,
                unfocusedSupportingTextColor = Color.Red,
                focusedContainerColor = Color.Black,
                focusedTextColor = Color.White,
                focusedIndicatorColor = if (isPasswordError) Color.Red else Color.DarkGray,
                focusedLabelColor = Color.DarkGray,
                focusedSupportingTextColor = Color.Red
            ),
            supportingText = {
                if (errorPasswordText != null) {
                    Text(errorPasswordText)
                }
            },
            trailingIcon = {
                IconButton (onClick = { showPassword = !showPassword }) {
                    Icon(
                        painter = painterResource(
                            if (showPassword) R.drawable.property_1_hide else R.drawable.property_1_show
                        ),
                        contentDescription = "Toggle password visibility",
                        modifier = Modifier.size(50.dp).padding(0.dp),
                        tint = Color.White
                    )
                }
            }
        )
    }
}
