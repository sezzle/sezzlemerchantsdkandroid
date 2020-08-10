package com.sezzle.sezzlemerchantsdkandroid

import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.util.AttributeSet
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

class SezzleProgressBar @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) :
    ProgressBar(context, attrs, defStyle) {
    init {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val drawableProgress =
                DrawableCompat.wrap(indeterminateDrawable)
            DrawableCompat.setTint(
                drawableProgress,
                ContextCompat.getColor(getContext(), R.color.sezzle_purple)
            )
            indeterminateDrawable = DrawableCompat.unwrap(
                drawableProgress
            )
        } else {
            indeterminateDrawable.setColorFilter(
                ContextCompat.getColor(getContext(), R.color.sezzle_purple),
                PorterDuff.Mode.SRC_IN
            )
        }
    }
}