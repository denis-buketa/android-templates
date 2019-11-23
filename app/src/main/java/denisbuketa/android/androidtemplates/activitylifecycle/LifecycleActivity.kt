package denisbuketa.android.androidtemplates.activitylifecycle

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import denisbuketa.android.androidtemplates.R
import kotlinx.android.synthetic.main.activity_lifecycle.*

class LifecycleActivity : AppCompatActivity() {

    companion object {
        var lastActivityId = 0
    }

    private var activityId = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        lastActivityId++
        activityId = lastActivityId
        Log.d("debug_log", "onCreate() - $activityId")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)

        startNewActivityButton.setOnClickListener {
            startActivity(Intent(this, LifecycleActivity::class.java))
        }

        openAlertDialogButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.apply {
                setTitle("Test Alert Dialog")
                setPositiveButton("OK") { dialog, id -> dialog.dismiss() }
                setNegativeButton("Cancel") { dialog, id -> dialog.dismiss() }
            }
            builder.create().show()
        }
    }

    override fun onStart() {
        Log.d("debug_log", "onStart() - $activityId")
        super.onStart()
    }

    override fun onResume() {
        Log.d("debug_log", "onResume() - $activityId")
        super.onResume()
    }

    override fun onPause() {
        Log.d("debug_log", "onPause() - $activityId")
        super.onPause()
    }

    override fun onStop() {
        Log.d("debug_log", "onStop() - $activityId")
        super.onStop()
    }

    override fun onRestart() {
        Log.d("debug_log", "onRestart() - $activityId")
        super.onRestart()
    }

    override fun onDestroy() {
        Log.d("debug_log", "onDestroy() - isFinishing: $isFinishing - $activityId")
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        Log.d("debug_log", "onSaveInstanceState() - $activityId")
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d("debug_log", "onRestoreInstanceState() - $activityId")
        super.onRestoreInstanceState(savedInstanceState)
    }
}
