package denisbuketa.android.androidtemplates.footer.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import denisbuketa.android.androidtemplates.R
import denisbuketa.android.androidtemplates.footer.view.viewmodel.*
import denisbuketa.android.androidtemplates.updateshapecolor.ShapeColorUtils
import kotlinx.android.synthetic.main.view_footer.view.*

/**
 * FooterView is view that supports different types of buttons and banners.
 */
class FooterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_footer, this)
    }

    fun render(viewModel: FooterViewModel) {
        resetView()
        when (viewModel) {
            is ImageButtonViewModel -> renderImageButtonViewModel(viewModel)
            is TextButtonViewModel -> renderTextButtonViewModel(viewModel)
            is TextButtonWithBannerViewModel -> renderTextButtonWithBannerViewModel(viewModel)
            is ImageWithBannerViewModel -> renderImageWithBannerViewModel(viewModel)
        }
    }

    /**
     * Resets footer's views visibilities.
     *
     * This might not be necessary when we introduce animations between different view models.
     *
     * In that case, we should check differences between previous and new view model and
     * animate necessary views.
     */
    private fun resetView() {
        banner.visibility = View.INVISIBLE
        bannerText.visibility = View.INVISIBLE
        buttonText.visibility = View.INVISIBLE
        buttonImage.visibility = View.INVISIBLE
        imageBadge.visibility = View.INVISIBLE
    }

    private fun renderImageButtonViewModel(viewModel: ImageButtonViewModel) {
        buttonImage.apply {
            buttonImage.setImageResource(viewModel.imageResId)
            setOnClickListener { viewModel.onButtonClickListener.invoke() }
            visibility = View.VISIBLE
        }
    }

    private fun renderTextButtonViewModel(viewModel: TextButtonViewModel) {
        buttonText.apply {
            text = viewModel.text
            setOnClickListener { viewModel.onButtonClickListener.invoke() }
            visibility = View.VISIBLE
        }
    }

    private fun renderTextButtonWithBannerViewModel(viewModel: TextButtonWithBannerViewModel) {
        renderTextButtonViewModel(viewModel.textButtonViewModel)
        renderBanner(viewModel.bannerViewModel)
    }

    private fun renderImageWithBannerViewModel(viewModel: ImageWithBannerViewModel) {
        imageBadge.apply {
            setImageResource(viewModel.imageResId)
            visibility = View.VISIBLE
        }
        renderBanner(viewModel.bannerViewModel)
    }

    private fun renderBanner(bannerViewModel: BannerViewModel) {
        bannerText.text = bannerViewModel.bannerText
        ShapeColorUtils.setColor(
            context,
            bannerViewModel.bannerColorResId,
            banner.background
        )
        banner.visibility = View.VISIBLE
        bannerText.visibility = View.VISIBLE
    }
}