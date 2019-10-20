package com.yrc.pos.core.providers

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.yrc.pos.R
import com.yrc.pos.core.Fonts

interface TypefaceProvider {

    fun getTypefaceFromXml(context: Context, attrs: AttributeSet?): Typeface? {

        var fontTypeface: Typeface? = null
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.YrcFontAttributes, 0, 0)
        val fontType = typedArray.getInteger(R.styleable.YrcFontAttributes_fontType, 2)

        when(fontType){
            0 -> fontTypeface = Typeface.createFromAsset(context.assets, Fonts.titillium_web_REGULAR)
            1 -> fontTypeface = Typeface.createFromAsset(context.assets, Fonts.titillium_web_BOLD)
            2 -> fontTypeface = Typeface.createFromAsset(context.assets, Fonts.titillium_web_LIGHT)
        }

        typedArray.recycle()
        return fontTypeface
    }
}