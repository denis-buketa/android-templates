package denisbuketa.android.androidtemplates.intentfilters

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import denisbuketa.android.androidtemplates.R
import kotlinx.android.synthetic.main.activity_start_send.*

/**
 * An Activity that allows you to trigger SEND intent to test the [SendActivity]
 */
class StartSendActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_send)

        startSendIntentButton.setOnClickListener {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Test Message")
            }
            startActivity(sendIntent)
        }
    }
}
