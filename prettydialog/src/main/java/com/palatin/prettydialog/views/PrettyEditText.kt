package com.palatin.prettydialog.views

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import java.util.*
import kotlin.collections.ArrayList

class PrettyEditText @kotlin.jvm.JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : EditText(context, attrs, defStyleAttr) {

    private var textWatchers: TextWatcher? = null

    fun addTextWatcher(t: TextWatcher) {
        textWatchers = t
        addTextChangedListener(t)
    }

    fun release() {
        textWatchers?.let {
            removeTextChangedListener(it)
        }
    }


}
