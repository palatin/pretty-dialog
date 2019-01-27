package com.palatin.prettydialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputLayout
import com.palatin.prettydialog.ext.DialogButtonCallback
import kotlinx.android.synthetic.main.pretty_dialog.*



open class PrettyDialog(context: Context, viewDelegate: ViewDelegate = PrettyDialogViewDelegate()) : Dialog(context, R.style.PrettyDialog) {

    var viewDelegate: ViewDelegate = viewDelegate
    set(value) {
        field = value
        render()
    }
    private var title: TextView? = null
    var callback: DialogButtonCallback? = null


    init {
        setContentView(LayoutInflater.from(context).inflate(R.layout.pretty_dialog, null))
        render()
    }

    private fun render() {
        viewDelegate.theme()?.let {
            context.setTheme(it)
        }
        setBackground()
        setHeader()
    }

    private fun setBackground() {
        this.viewDelegate.background(this)?.let {
            window?.setBackgroundDrawable(it)
        }

    }

    private fun setHeader() {
        viewDelegate.header(this, header)
    }

    private fun setTag(view: View, tag: Any?) {
        tag?.let {
            view.tag = tag
        }
    }

    fun input(hint: String? = null, t: TextWatcher, tag: Any? = null) : PrettyDialog {

        val et = viewDelegate.input(this, content, hint)
        et.editText?.addTextChangedListener(t)
        setTag(et, tag)
        return this
    }


    fun title(titleString: String) : PrettyDialog {

        if (this.title == null)
            this.title = viewDelegate.title(this, header)
        this.title!!.text = titleString


        return this
    }



    fun message(message: String, tag: Any? = null) : PrettyDialog {

        val tv = viewDelegate.message(this, content, message)
        setTag(tv, tag)


        return this
    }



    fun image(drawable: Drawable, width: Int? = null, height: Int? = null, tag: Any? = null) : PrettyDialog {
        val image = viewDelegate.image(this, content, drawable, width, height)
        setTag(image, tag)
        return this
    }

    fun addView(view: View) : PrettyDialog {
        content.addView(view)
        return this
    }

    fun getContentView() : View = content

    fun getRootView() : View = root

    fun getViewsByTag(tag: Any): Array<View> {
        val views = ArrayList<View>()
        for(i in 0 until content.childCount) {
            val v = content.getChildAt(i)
            if(v.tag == tag)
                views.add(v)
        }
        return views.toTypedArray()
    }

    fun canBeCanceled(flag: Boolean): PrettyDialog {
        setCancelable(flag)
        return this
    }

    fun cancelOnTOuch(flag: Boolean): PrettyDialog {
        setCanceledOnTouchOutside(flag)
        return this
    }

    fun apply(f: PrettyDialog.() -> Unit): PrettyDialog {
        this.f()
        return this
    }

    fun clearButtons(): PrettyDialog {
        lButtons.removeAllViews()
        return this
    }

    fun clear() {
        title = null
        header.removeAllViews()
        content.removeAllViews()
        lButtons.removeAllViews()
    }

    fun header(): FrameLayout = header



    interface ViewDelegate {

        fun theme(): Int?
        fun header(dialog: PrettyDialog, header: FrameLayout)
        fun background(dialog: PrettyDialog): Drawable?
        fun title(dialog: PrettyDialog, container: FrameLayout): TextView
        fun message(dialog: PrettyDialog, container: LinearLayout, message: String): View
        fun button(dialog: PrettyDialog, container: LinearLayout, title: String, type: ButtonType): Button
        fun image(dialog: PrettyDialog, container: LinearLayout, drawable: Drawable, width: Int?, height: Int?): View
        fun input(dialog: PrettyDialog, container: LinearLayout, hint: String?): TextInputLayout
        fun singleChoice(dialog: PrettyDialog, container: LinearLayout, titles: Array<String>, checked: Int): RadioGroup

    }

    enum class ButtonType {
        NEUTRAL,
        POSITIVE,
        NEGATIVE
    }
}