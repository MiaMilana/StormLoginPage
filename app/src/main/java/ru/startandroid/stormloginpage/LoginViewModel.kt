package ru.startandroid.stormloginpage

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class LoginViewModel : ViewModel() {
    var login by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun onLoginChange(newLogin: String) {
        login = newLogin
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun validateLogin(): String? {
        return when {
            login.isEmpty() -> null
            !login.any { it in '\u0400'..'\u04FF' } -> "Логин пользователя должен быть на кириллице"
            login != "Логин_Юзера" -> "Неверный логин"
            else -> null
        }
    }

    fun validatePassword(): String? {
        return when {
            password.isEmpty() -> null
            password.length < 6 -> "Пароль должен содержать не менее 6 символов"
            !password.any { it in 'a'..'z' || it in 'A'..'Z' } -> "Пароль должен содержать хотя бы одну латинскую букву"
            !password.any { it.isDigit() } -> "Пароль должен содержать хотя бы одну цифру"
            else -> null
        }
    }

    fun isButtonEnabled(): Boolean {
        val loginErrorText = validateLogin()
        val passwordErrorText = validatePassword()
        return login.isNotEmpty() && password.isNotEmpty() && loginErrorText == null && passwordErrorText == null
    }
}