package com.palatin.prettydialog.ext

import android.view.View
import com.palatin.prettydialog.PrettyDialog
import com.palatin.prettydialog.R
import kotlinx.android.synthetic.main.pretty_dialog.*
import java.lang.Exception

typealias DialogButtonCallback = (PrettyDialog, View, PrettyDialog.ButtonType) -> Unit

fun PrettyDialog.button(title: String, callback: DialogButtonCallback? = null,
                        type: PrettyDialog.ButtonType = PrettyDialog.ButtonType.NEUTRAL, tag: Any? = null, dismiss: Boolean = false) : PrettyDialog {

    val button = viewDelegate.button(this, lButtons, title, type)
    button.setOnClickListener {
        callback?.invoke(this, button, button.getTag(R.id.buttonTypeTag) as? PrettyDialog.ButtonType
                ?: PrettyDialog.ButtonType.NEUTRAL)
        this.callback?.invoke(this, button, button.getTag(R.id.buttonTypeTag) as? PrettyDialog.ButtonType
                ?: PrettyDialog.ButtonType.NEUTRAL)
        if (dismiss)
            try {
                this.dismiss()
            } catch (ex: Exception) {
            }
    }

    button.setTag(R.id.buttonTypeTag, type)
    button.tag = tag

    return this
}

fun PrettyDialog.setOnButtonClickListener(callback: DialogButtonCallback?): PrettyDialog {
    this.callback = callback
    return this
}

