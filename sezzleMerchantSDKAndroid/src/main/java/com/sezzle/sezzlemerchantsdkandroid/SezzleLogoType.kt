package com.sezzle.sezzlemerchantsdkandroid
import androidx.annotation.DrawableRes

enum class SezzleLogoType {
    SEZZLE_LOGO_COLOR_TEXT_BLACK,
    SEZZLE_LOGO_BLACK_TEXT_BLACK,
    SEZZLE_LOGO_WHITE_TEXT_WHITE,
    SEZZLE_LOGO_WHITE_TEXT_WHITE_FLAT,
    SEZZLE_LOGO_BLACK_TEXT_BLACK_FLAT,
    SEZZLE_LOGO_COLOR_TEXT_WHITE;

    companion object {
        open fun getSezzleLogoType(ordinal: Int): SezzleLogoType {
            val types: Array<SezzleLogoType> = values()
            for (type in types) {
                if (type.ordinal == ordinal) {
                    return type
                }
            }
            return SEZZLE_LOGO_COLOR_TEXT_BLACK
        }
    }

    @DrawableRes
    open fun getDrawableRes(): Int {
        return when {
            this == SEZZLE_LOGO_BLACK_TEXT_BLACK -> {
                R.drawable.sezzle_logo_black
            }
            this == SEZZLE_LOGO_WHITE_TEXT_WHITE -> {
                R.drawable.sezzle_logo_white
            }
            this == SEZZLE_LOGO_WHITE_TEXT_WHITE_FLAT -> {
                R.drawable.sezzle_logo_white_alt
            }
            this == SEZZLE_LOGO_BLACK_TEXT_BLACK_FLAT -> {
                R.drawable.sezzle_logo_black_alt
            }
            this == SEZZLE_LOGO_COLOR_TEXT_WHITE -> {
                R.drawable.sezzle_logo_full_color_white_wm
            }
            else -> {
                R.drawable.sezzle_logo_full_color
            }
        }
    }
}