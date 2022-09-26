package com.pi.ativas.firstLogin

import java.util.regex.Matcher
import java.util.regex.Pattern

fun isValidPassword(password: String): Boolean {
    val pattern: Pattern
    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"
    pattern = Pattern.compile(PASSWORD_PATTERN)
    val matcher: Matcher = pattern.matcher(password)
    return matcher.matches()
}