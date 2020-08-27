package com.sezzle.sezzlemerchantsdkandroid

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.content.res.ResourcesCompat

class SezzlePromotionButton: FrameLayout {
    private var message: String? = null
    private var promotionButton: PromotionButton? = null
    private var sezzleLogoType: SezzleLogoType? = null

    constructor(context: Context) : this(context, null)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : this(context, attrs, 0)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ): super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.SezzlePromotionButton)
        val sezzleLogoTypeOrdinal = typedArray.getInt(
            R.styleable.SezzlePromotionButton_sezzleLogoType,
            SezzleLogoType.SEZZLE_LOGO_COLOR_TEXT_BLACK.ordinal
        )
        val sezzleTextSize = typedArray.getDimensionPixelSize(
            R.styleable.SezzlePromotionButton_sezzleTextFont,
            resources.getDimensionPixelSize(R.dimen.sezzle_promotion_size)
        ).toFloat()
        val sezzleTextColor = typedArray.getResourceId(
            R.styleable.SezzlePromotionButton_sezzleTextColor,
            android.R.color.black
        )
        val sezzleTextFont = typedArray.getResourceId(
            R.styleable.SezzlePromotionButton_sezzleTextFont,
            0
        )
        sezzleLogoType = SezzleLogoType.getSezzleLogoType(sezzleLogoTypeOrdinal)
        typedArray.recycle()

        promotionButton = PromotionButton(context)
        promotionButton?.setSezzleTextSize(sezzleTextSize)
        promotionButton?.setSezzleTextColor(sezzleTextColor)
        promotionButton?.typeface = if (sezzleTextFont > 0) ResourcesCompat.getFont(
            getContext(),
            sezzleTextFont
        ) else Typeface.DEFAULT
        promotionButton?.setSezzleLogoType(sezzleLogoType)
    }

    fun setLabel(text: String) {
        message = text
        removeAllViews()
        addView(promotionButton)
        promotionButton?.text = promotionButton?.updateSpan(text)
    }

    fun configWithLocalStyling(
        sezzleLogoType: SezzleLogoType
    ) {
        this.configWithLocalStyling(sezzleLogoType, null)
    }

    fun configWithLocalStyling(
        sezzleLogoType: SezzleLogoType,
        typeface: Typeface?
    ) {
        this.configWithLocalStyling(
            sezzleLogoType, typeface,
            android.R.color.black, R.dimen.sezzle_promotion_size
        )
    }

    fun configWithLocalStyling(
        sezzleLogoType: SezzleLogoType,
        typeface: Typeface,
        sezzleTextColor: Int
    ) {
        this.configWithLocalStyling(
            sezzleLogoType, typeface,
            sezzleTextColor, R.dimen.sezzle_promotion_size
        )
    }

    fun configWithLocalStyling(
        sezzleLogoType: SezzleLogoType,
        typeface: Typeface?,
        sezzleTextColor: Int,
        sezzleTextSize: Int
    ) {
        this.sezzleLogoType = sezzleLogoType
        promotionButton?.setSezzleTextColor(sezzleTextColor)
        promotionButton?.typeface = typeface
        promotionButton?.setSezzleLogoType(sezzleLogoType)
        promotionButton?.setSezzleTextSize(
            getResources().getDimensionPixelSize(sezzleTextSize).toFloat()
        )
    }
}