package denisbuketa.android.logger

import android.util.Log

private const val TAG = "debug_log"

class Logger {

    companion object {

        fun log(message: String) {
            Log.d(TAG, message)
        }
    }
}