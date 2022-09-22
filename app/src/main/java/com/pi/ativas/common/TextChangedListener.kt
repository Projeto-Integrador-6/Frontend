package com.pi.ativas.common

import android.text.Editable
import android.text.TextWatcher

abstract class TextChangedListener<T>(private val target: T): TextWatcher{
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun afterTextChanged(p0: Editable?) {
        this.onTextChanged(target, p0)
    }

    abstract fun onTextChanged(target: T,p0: Editable?)
}