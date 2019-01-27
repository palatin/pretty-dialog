package com.palatin.dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat

import com.palatin.prettydialog.PrettyDialog
import com.palatin.prettydialog.PrettyDialogViewDelegate
import com.palatin.prettydialog.ext.button
import com.palatin.prettydialog.ext.setOnButtonClickListener
import com.palatin.prettydialog.ext.singleChoice

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PrettyDialog(this)
                .title("Hello!")
                .message("Do you like this window?", tag = "msg")
                .button("No", type = PrettyDialog.ButtonType.NEGATIVE)
                .button("Learn more", type = PrettyDialog.ButtonType.NEUTRAL)
                .button("Yes", type = PrettyDialog.ButtonType.POSITIVE)
                .setOnButtonClickListener { prettyDialog, view, buttonType ->
                    when(buttonType) {
                        PrettyDialog.ButtonType.POSITIVE -> prettyDialog.apply {
                            title("Thank you!")
                            (getViewsByTag("msg")[0] as TextView).text = "Thank you so much, I'm glad you like it. I will continue to develop the library"
                            clearButtons()
                            button("Ok", dismiss = true)
                        }
                        PrettyDialog.ButtonType.NEUTRAL -> themeDialog(prettyDialog, PrettyDialogViewDelegate.Theme.GREY, 1)

                    }
                }
                .show()
    }

    private fun themeDialog(dialog: PrettyDialog, theme: PrettyDialogViewDelegate.Theme, selected: Int) {
        dialog.apply {
            viewDelegate = PrettyDialogViewDelegate(theme, separateButtons = false)
            clear()
            title("Themes")
            message("With library, you can easily set up different themes")
            singleChoice(arrayOf("Light theme", "Grey theme", "Dark theme"), selected) { dialog, radiogroup, index ->
                when(index) {
                    0 -> themeDialog(dialog, PrettyDialogViewDelegate.Theme.LIGHT, index)
                    1 -> themeDialog(dialog, PrettyDialogViewDelegate.Theme.GREY, index)
                    2 -> themeDialog(dialog, PrettyDialogViewDelegate.Theme.DARK, index)
                }
            }
            button("Next")
            setOnButtonClickListener { prettyDialog, view, buttonType ->

            }
        }
    }


}
