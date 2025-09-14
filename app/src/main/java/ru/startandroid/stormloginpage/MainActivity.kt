package ru.startandroid.stormloginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.startandroid.stormloginpage.ui.theme.StormLoginPageTheme
import ru.startandroid.stormloginpage.ui.theme.textFieldColors
import androidx.compose.ui.res.dimensionResource
import androidx.activity.viewModels


class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StormLoginPageTheme {
                var isLoading by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(3000)
                    isLoading = false
                }
                if (isLoading) {
                    SplashScreen()
                } else {
                    LoginScreen(loginViewModel)
                }
            }
        }
    }
}


@Composable
private fun SplashScreen() = Box(
    Modifier
        .fillMaxSize(),
    Alignment.Center
) {
    Image(
        painter = painterResource(id = R.mipmap.frame_19219),
        contentDescription = "",
        alignment = Alignment.Center, modifier = Modifier
            .size(dimensionResource(id = R.dimen.splash_image_size))
    )

    Text(
        text = "1.0.41",
        textAlign = TextAlign.Center,
        fontSize = 14.sp,
        color = Color(0xFF676767),
        modifier = Modifier.align(Alignment.BottomCenter).padding(dimensionResource(id = R.dimen.splash_text_padding))
    )
}

@Composable
private fun LoginScreen(loginViewModel: LoginViewModel) {
    val image = painterResource(R.drawable.typography)

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.splash_text_padding)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(
                width = dimensionResource(id = R.dimen.logo_size_width),
                height = dimensionResource(id = R.dimen.logo_size_height)
            )
        )
        LoginForm(loginViewModel)
        LoginButton(loginViewModel.isButtonEnabled())
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
    Column() {
        TextField(
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Unspecified),
            modifier = Modifier.width(dimensionResource(id = R.dimen.text_field_width)),
            colors = textFieldColors(isError = isLoginError),
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
            onValueChange = {
                if (it.length <= 12) {
                    onValueChange(it)
                }
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.width(dimensionResource(id = R.dimen.text_field_width)),
            label = { Text(label) },
            colors = textFieldColors(isError = isPasswordError),
            supportingText = {
                if (errorPasswordText != null) {
                    Text(errorPasswordText)
                }
            },
            trailingIcon = {
                IconButton (onClick = { showPassword = !showPassword }) {
                    Icon(
                        painter = painterResource(
                            if (showPassword) R.drawable.hide_1 else R.drawable.show_1
                        ),
                        contentDescription = "Toggle password visibility",
                        modifier = Modifier.size(dimensionResource(id = R.dimen.login_button_height)).padding(0.dp),
                        tint = Color.White
                    )
                }
            }
        )
    }
}

@Composable
private fun LoginForm(loginViewModel: LoginViewModel) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.48f)
            .padding(
                top = dimensionResource(id = R.dimen.login_form_padding_top),
                start = dimensionResource(id = R.dimen.login_form_padding_start),
                end = dimensionResource(id = R.dimen.login_form_padding_start)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginTextField(
            value = loginViewModel.login,
            onValueChange = {
                loginViewModel.onLoginChange(it)
            },
            errorLoginText = loginViewModel.validateLogin()
        )

        PasswordTextField(
            value = loginViewModel.password,
            onValueChange = {
                loginViewModel.onPasswordChange(it)
            },
            errorPasswordText = loginViewModel.validatePassword()
        )
    }
}

@Composable
private fun LoginButton(isButtonEnabled: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxSize().padding(
                end = dimensionResource(id = R.dimen.login_form_padding_start)
            ),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            onClick = {},
            shape = RoundedCornerShape(dimensionResource(id = R.dimen.login_button_padding_bottom)),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isButtonEnabled) Color.White else Color.Gray,
                contentColor = Color.DarkGray
            ),
            contentPadding = PaddingValues(
                dimensionResource(id = R.dimen.login_form_padding_start),
                dimensionResource(id = R.dimen.login_button_padding_bottom)
            ),
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.login_button_width))
                .height(dimensionResource(id = R.dimen.login_button_height))
        ) {
            Text(text = "ВОЙТИ")
        }
    }
}

