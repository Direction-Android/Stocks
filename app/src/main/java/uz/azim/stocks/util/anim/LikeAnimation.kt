package uz.azim.stocks.util.anim

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import uz.azim.stocks.R
import uz.azim.stocks.util.getDrawable
import uz.azim.stocks.util.simple.SimpleAnimationListener

class LikeAnimation(private val context: Context) {
    private val animation by lazy { AnimationUtils.loadAnimation(context, R.anim.scale_up) }
    val likeImg = getDrawable(context, R.drawable.ic_star_active)
    val unlikeImg = getDrawable(context, R.drawable.ic_star_inactive)

    fun like(img: ImageView) {
        animation.setAnimationListener(object : SimpleAnimationListener() {
            override fun onAnimationEnd(animation: Animation?) {
                super.onAnimationEnd(animation)
                img.setImageDrawable(likeImg)
            }
        })
        img.startAnimation(animation)
    }

    fun unlike(img:ImageView) {
        animation.setAnimationListener(object : SimpleAnimationListener() {
            override fun onAnimationEnd(animation: Animation?) {
                super.onAnimationEnd(animation)
                img.setImageDrawable(unlikeImg)
            }
        })
        img.startAnimation(animation)
    }
}