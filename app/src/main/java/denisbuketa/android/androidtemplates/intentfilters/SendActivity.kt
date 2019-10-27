package denisbuketa.android.androidtemplates.intentfilters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import denisbuketa.android.androidtemplates.R

/**
 * Test activity that should be triggered from SEND intent.
 *
 * Check intent filter in manifest for this activity.
 */
class SendActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
    }
}
