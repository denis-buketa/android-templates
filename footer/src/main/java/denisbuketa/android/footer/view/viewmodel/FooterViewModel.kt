/*
 * Copyright © 2019 Rosetta Stone. All rights reserved.
 */

package denisbuketa.android.footer.view.viewmodel

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 * View model for the [FooterView]
 */
sealed class FooterViewModel

data class ImageButtonViewModel(
    @DrawableRes val imageResId: Int,
    val onButtonClickListener: () -> Unit
) : FooterViewModel()

data class TextButtonViewModel(
    val text: String,
    val onButtonClickListener: () -> Unit
) : FooterViewModel()

data class TextButtonWithBannerViewModel(
    val textButtonViewModel: TextButtonViewModel,
    val bannerViewModel: BannerViewModel
) : FooterViewModel()

data class ImageWithBannerViewModel(
    @DrawableRes val imageResId: Int,
    val bannerViewModel: BannerViewModel
) : FooterViewModel()

data class BannerViewModel(
    val bannerText: String,
    @ColorRes val bannerColorResId: Int
)