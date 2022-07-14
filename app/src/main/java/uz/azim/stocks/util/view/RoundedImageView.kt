package uz.azim.stocks.util.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.google.android.material.card.MaterialCardView
import uz.azim.stocks.R
import uz.azim.stocks.util.setStockLogo

class RoundedImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : MaterialCardView(context, attrs, defStyle) {

    private val img = ImageView(context)

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RoundedImageView,
            0, 0
        ).apply {
            try {
                val imageRadius = getDimension(R.styleable.RoundedImageView_radius, 4f)
                this@RoundedImageView.radius = imageRadius
            } finally {
                recycle()
            }
        }
        this.cardElevation = 0f
        addView(img)
    }

    fun setImageResource(@DrawableRes res:Int){
        img.setImageResource(res)
    }

    fun setStockImg(symbol: String, errorImg: Drawable?){
        img.setStockLogo(symbol,errorImg)
    }

}