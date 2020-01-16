package denisbuketa.android.androidtemplates

import agency.five.imagebutton.ImageButtonActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import denisbuketa.android.androidtemplates.activitylifecycle.LifecycleActivity
import denisbuketa.android.androidtemplates.intentfilters.StartSendActivity
import denisbuketa.android.encryption.EncryptionActivity
import denisbuketa.android.footer.FooterActivity
import denisbuketa.android.networking.NetworkingActivity
import denisbuketa.android.recyclerview.RecyclerViewListActivity
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

        startLifecycleActivityButton.setOnClickListener {
            startActivity(Intent(this, LifecycleActivity::class.java))
        }

        startFooterActivityButton.setOnClickListener {
            startActivity(Intent(this, FooterActivity::class.java))
        }

        startEncryptionActivityButton.setOnClickListener {
            startActivity(Intent(this, EncryptionActivity::class.java))
        }

        startImageButtonActivityButton.setOnClickListener {
            startActivity(Intent(this, ImageButtonActivity::class.java))
        }

        recyclerViewListActivityButton.setOnClickListener {
            startActivity(Intent(this, RecyclerViewListActivity::class.java))
        }

        networkingActivityButton.setOnClickListener {
            startActivity(Intent(this, NetworkingActivity::class.java))
        }
    }
}
