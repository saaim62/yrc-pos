package com.yrc.pos.core.views

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import androidx.annotation.AttrRes
import com.yrc.pos.core.providers.TypefaceProvider

class YrcEditText : EditText , TypefaceProvider {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        this.typeface = getTypefaceFromXml(context, attrs)
    }
}