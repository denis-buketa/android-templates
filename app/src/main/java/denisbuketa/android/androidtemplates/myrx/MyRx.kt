package denisbuketa.android.androidtemplates.myrx

import android.os.Handler
import android.os.Looper

class MyRx {

    val handler = Handler()
    val dispatcher = Handler(Looper.getMainLooper())

    /**
     * Execute work function async, get data
     * Return data T on main thread to callback
     */
    fun <T> schedule(work: () -> T, callback: (T) -> Unit) {
        handler.post {
            val result = work()
            dispatcher.post { callback(result) }
        }
    }
}