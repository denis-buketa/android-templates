package denisbuketa.android.androidtemplates

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import denisbuketa.android.androidtemplates.intentfilters.StartSendActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Activity that you can use to start various tests
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendIntentFilterTestButton.setOnClickListener {
            startActivity(Intent(this, StartSendActivity::class.java))
        }
    }
}
