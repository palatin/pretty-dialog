package com.palatin.prettydialog.ext

import android.view.View
import android.widget.RadioGroup
import androidx.annotation.Size
import com.palatin.prettydialog.PrettyDialog
import kotlinx.android.synthetic.main.pretty_dialog.*

typealias SingleChoiceListener = (PrettyDialog, RadioGroup, Int) -> Unit

fun PrettyDialog.singleChoice(@Size(min = 1) titles: Array<String>, checked: Int = 0, tag: String? = null, callback: SingleChoiceListener? = null) {
    val rg = viewDelegate.singleChoice(this, content, titles, checked)
    rg.tag = tag
    rg.setOnCheckedChangeListener { radioGroup, i ->
        callback?.invoke(this, radioGroup, i)
    }
}

fun PrettyDialog.checkSingleChoice(tag: String, index: Int) {
    val views = getViewsByTag(tag).takeIf { it.isNotEmpty() && it[0] is RadioGroup } ?: return
    val rg = views[0] as RadioGroup
    rg.check(index)
}

fun PrettyDialog.uncheckSingleChoice(tag: String) {
    checkSingleChoice(tag, -1)
}


