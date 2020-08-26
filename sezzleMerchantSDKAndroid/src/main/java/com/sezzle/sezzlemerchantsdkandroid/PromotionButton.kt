package com.sezzle.sezzlemerchantsdkandroid

import android.content.Context
import android.text.SpannableString
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat

class PromotionButton: AppCompatButton {

    private var sezzleLogoType: SezzleLogoType? = null

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr);
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0);
    constructor(context: Context): this(context, null);

    fun setSezzleLogoType(sezzleLogoType: SezzleLogoType?) {
        this.sezzleLogoType = sezzleLogoType
    }

    fun setSezzleTextSize(sezzleTextSize: Float) {
        setTextSize(TypedValue.COMPLEX_UNIT_PX, sezzleTextSize)
    }

    fun setSezzleTextColor(colorRes: Int) {
        setTextColor(ContextCompat.getColor(context, colorRes))
    }

    fun updateSpan(template: String): SpannableString? {
        val textSize = textSize
        return SezzleUtils.createSpannableForText(
            template, textSize,
            this.sezzleLogoType!!, context
        )
    }
}