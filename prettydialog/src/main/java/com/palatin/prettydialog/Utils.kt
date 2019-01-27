package com.palatin.prettydialog

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes

fun dpToPx(dp: Float, context: Context): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
}

fun spToPx(sp: Float, context: Context): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics)
}

fun getColor(context: Context, @AttrRes attr: Int): Int? {

    val typedArray = context.theme.obtainStyledAttributes(intArrayOf(attr))
    val color = if(typedArray.hasValue(0)) typedArray.getColor(0, 0) else null
    typedArray.recycle()
    return color
}