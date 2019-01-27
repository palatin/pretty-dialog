package com.palatin.prettydialog

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import com.google.android.material.textfield.TextInputLayout



open class PrettyDialogViewDelegate(private val theme: Theme = Theme.GREY, private val separateButtons: Boolean = true,
                                    private val cornerRadius: Float = 50f) : PrettyDialog.ViewDelegate {



    private val themes = mapOf(Theme.LIGHT to R.style.PrettyDialogLight, Theme.GREY to R.style.PrettyDialogGrey, Theme.DARK to  R.style.PrettyDialogDark)
    private val buttonThemes = mapOf(PrettyDialog.ButtonType.NEUTRAL to R.attr.buttonNeutralTitleColor,
                                     PrettyDialog.ButtonType.POSITIVE to R.attr.buttonPositiveTitleColor,
                                     PrettyDialog.ButtonType.NEGATIVE to R.attr.buttonNegativeTitleColor)

    override fun theme(): Int? = themes[theme]

    override fun input(dialog: PrettyDialog, container: LinearLayout, hint: String?): TextInputLayout {
        val et = LayoutInflater.from(dialog.context).inflate(R.layout.pretty_edittext, container, false) as TextInputLayout
        et.editText?.hint = hint
        container.addView(et)
        return et
    }


    override fun message(dialog: PrettyDialog, container: LinearLayout, message: String): View {
        val tv: TextView = LayoutInflater.from(dialog.context).inflate(R.layout.pretty_textview, container, false) as TextView
        tv.text = message
        tv.setTextColor(getColor(dialog.context, android.R.attr.textColor) ?: Color.BLACK)
        container.addView(tv)
        return tv
    }


    override fun image(dialog: PrettyDialog, container: LinearLayout, drawable: Drawable, width: Int?, height: Int?): View {

        val iv: ImageView = LayoutInflater.from(dialog.context).inflate(R.layout.pretty_imageview, container, false) as ImageView
        val lp = LinearLayout.LayoutParams(width ?: LinearLayout.LayoutParams.WRAP_CONTENT, height ?: LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.bottomMargin = Math.round(dpToPx(12f, dialog.context))
        lp.gravity = Gravity.CENTER_HORIZONTAL
        iv.layoutParams = lp
        iv.setImageDrawable(drawable)
        container.addView(iv)
        return iv
    }


    override fun header(dialog: PrettyDialog, header: FrameLayout) {

    }

    override fun title(dialog: PrettyDialog, container: FrameLayout): TextView {
        val tv = TextView(dialog.context)
        tv.setTextColor(getColor(dialog.context, R.attr.titleColor) ?: Color.BLACK)
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22f)
        val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL)
        lp.topMargin = Math.round(dpToPx(16f, dialog.context))
        lp.leftMargin = Math.round(dpToPx(16f, dialog.context))
        lp.rightMargin = Math.round(dpToPx(16f, dialog.context))
        container.addView(tv, lp)
        return tv
    }

    override fun button(dialog: PrettyDialog, container: LinearLayout,
                        title: String, type: PrettyDialog.ButtonType): Button {
        val button: Button = LayoutInflater.from(dialog.context).inflate(R.layout.pretty_button, container, false) as Button
        button.text = title
        button.setTextColor(getColor(dialog.context, buttonThemes[type] ?: R.attr.buttonNegativeTitleColor) ?:
                            ContextCompat.getColor(dialog.context, R.color.defaultButtonTextColor))
        val p = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Math.round(dpToPx(50f, dialog.context)))
        p.weight = 1f
        container.addView(button, p)
        if(!separateButtons)
            return button
        ViewCompat.setBackground(button, ContextCompat.getDrawable(dialog.context, R.drawable.top_separator_bg))
        if(container.childCount > 1)
            container.addView(separator(dialog.context, true), container.childCount - 1)
        return button
    }

    protected fun separator(context: Context, isVertical: Boolean): View {
        val view = View(context)
        if(isVertical)
            view.layoutParams = LinearLayout.LayoutParams(dpToPx(0.5f, context).toInt(), LinearLayout.LayoutParams.MATCH_PARENT)
        else
            view.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(0.5f, context).toInt())
        view.setBackgroundColor((getColor(context, R.attr.separatorColor) ?: ContextCompat.getColor(context, R.color.buttonSeparator)))
        return view
    }

    override fun background(dialog: PrettyDialog): Drawable {
        val drawable = GradientDrawable()
        drawable.cornerRadius = cornerRadius
        DrawableCompat.setTint(drawable, getColor(dialog.context, R.attr.backgroundColor) ?:
                               ContextCompat.getColor(dialog.context, R.color.defaultDialogColor))

        return drawable
    }

    override fun singleChoice(dialog: PrettyDialog, container: LinearLayout, titles: Array<String>, checked: Int): RadioGroup {
        val rg = RadioGroup(dialog.context)
        val inflater = LayoutInflater.from(dialog.context)
        for(i in 0 until titles.size) {
            val rb = inflater.inflate(R.layout.pretty_radiobutton, rg, false) as RadioButton
            rb.text = titles[i]
            rb.setTextColor(getColor(dialog.context, android.R.attr.textColor) ?: Color.BLACK)
            rb.id = i
            rb.isChecked = i == checked
            ViewCompat.setLayoutDirection(rb, ViewCompat.LAYOUT_DIRECTION_RTL)
            rg.addView(rb)
        }
        rg.dividerDrawable = ContextCompat.getDrawable(dialog.context, R.drawable.radiobutton_separator)
        rg.showDividers = LinearLayout.SHOW_DIVIDER_BEGINNING or LinearLayout.SHOW_DIVIDER_MIDDLE
        ViewCompat.setBackground(rg, ContextCompat.getDrawable(dialog.context, R.drawable.bottom_separator_bg))
        container.addView(rg)
        return rg
    }


    enum class Theme {
        LIGHT,
        GREY,
        DARK
    }
}