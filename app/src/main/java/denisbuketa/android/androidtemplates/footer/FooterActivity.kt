package denisbuketa.android.androidtemplates.footer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import denisbuketa.android.androidtemplates.R
import denisbuketa.android.androidtemplates.footer.view.viewmodel.*
import kotlinx.android.synthetic.main.activity_footer.*
import kotlinx.android.synthetic.main.view_footer.*

/**
 * This is a container activity for testing the Footer view.
 */
class FooterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_footer)

        enableButton.setOnClickListener {
            buttonText.isEnabled = true
            buttonImage.isEnabled = true
        }

        disableButton.setOnClickListener {
            buttonText.isEnabled = false
            buttonImage.isEnabled = false
        }

        imageButtonViewModel.setOnClickListener {
            footer.render(imageButtonViewModel())
        }

        textButtonViewModel.setOnClickListener {
            footer.render(textButtonViewModel())
        }

        textButtonWithBannerViewModel.setOnClickListener {
            footer.render(textButtonWithBannerViewModel())
        }

        imageWithBannerViewModel.setOnClickListener {
            footer.render(imageWithBannerViewModel())
        }
    }

    private fun imageButtonViewModel() =
        ImageButtonViewModel(
            R.drawable.ic_microphone
        ) { Toast.makeText(this, "Image Button Click", Toast.LENGTH_SHORT).show() }

    private fun textButtonViewModel() =
        TextButtonViewModel(
            "Test"
        ) { Toast.makeText(this, "Image Button Click", Toast.LENGTH_SHORT).show() }

    private fun textButtonWithBannerViewModel() =
        TextButtonWithBannerViewModel(
            textButtonViewModel(),
            bannerViewModel()
        )

    private fun imageWithBannerViewModel() =
        ImageWithBannerViewModel(
            R.drawable.ic_checkmark_badge,
            bannerViewModel()
        )

    private fun bannerViewModel() =
        BannerViewModel(
            "Test Banner",
            R.color.colorBlue
        )
}
