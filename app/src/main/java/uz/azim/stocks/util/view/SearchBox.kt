package uz.azim.stocks.util.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import uz.azim.stocks.R
import uz.azim.stocks.util.getColor
import uz.azim.stocks.util.getDrawable
import uz.azim.stocks.util.simple.SimpleTextWatcher
import uz.azim.stocks.util.toDp

class SearchBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {
    private val isSearchActive: Boolean
    private val startImg = ImageView(context)
    private val endImg = ImageView(context)
    private val et = AppCompatEditText(context)

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SearchBox,
            0, 0
        ).apply {
            try {
                isSearchActive = getBoolean(R.styleable.SearchBox_isSearchActive, true)
            } finally {
                recycle()
            }
        }
        setUpView()
        setUpStartImg(context)
        setUpEndImg(context)
        addChildren(context)
    }

    private fun setUpView() {
        this.orientation = HORIZONTAL
        val viewParams =
            LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        viewParams.setMargins(
            20,
            10,
            20,
            10
        )
        this.gravity = Gravity.CENTER
        this.background = getDrawable(context, R.drawable.search_box)
        this.layoutParams = viewParams
    }

    private fun setUpStartImg(context: Context) {
        val src = if (isSearchActive) R.drawable.ic_back else R.drawable.ic_search
        startImg.setImageResource(src)
        startImg.setPadding(16.toDp(context), 16.toDp(context), 16.toDp(context), 16.toDp(context))
    }

    private fun setUpEndImg(context: Context) {
        endImg.setImageResource(R.drawable.ic_clear)
        endImg.setPadding(16.toDp(context), 16.toDp(context), 16.toDp(context), 16.toDp(context))
        endImg.isVisible = false
        endImg.setOnClickListener {
            et.setText("")
        }
    }

    private fun addChildren(context: Context) {
        val middleView = if (isSearchActive) getEditText(context) else getTextView(context)
        addView(startImg)
        addView(middleView)
        addView(endImg)
    }

    private fun getEditText(context: Context): AppCompatEditText {
        val lp = LayoutParams(
            0,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        lp.weight = 1f
        et.apply {
            layoutParams = lp
            background = null
            textSize = 16f
            isSingleLine = true
            typeface = ResourcesCompat.getFont(context, R.font.montserrat_bold)
            imeOptions = EditorInfo.IME_ACTION_DONE
            requestFocus()
        }
        et.doOnTextChanged { text, start, end, count ->
            endImg.isVisible = count > 0
        }
        val imgr: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        return et
    }

    private fun getTextView(context: Context): AppCompatTextView {
        val tv = AppCompatTextView(context)
        val lp = LayoutParams(
            0.toDp(context),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.weight = 1f
        tv.layoutParams = lp
        tv.textSize = 16f
        tv.typeface = ResourcesCompat.getFont(context, R.font.montserrat_medium)
        tv.setText(R.string.find_company)
        tv.setTextColor(getColor(context, R.color.black))
        return tv
    }

    fun onStartImgClicked(func: (view: View) -> Unit) {
        startImg.setOnClickListener {
            func.invoke(it)
        }
    }

    fun onTextChanged(func: (text: String) -> Unit) {
        et.doOnTextChanged { text, start, before, count ->
            func.invoke(text.toString())
        }
    }

    fun getText() = et.text.toString()

    fun setText(text: String) = et.setText(text)
}