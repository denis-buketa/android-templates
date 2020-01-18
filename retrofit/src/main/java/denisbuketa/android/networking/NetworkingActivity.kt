package denisbuketa.android.networking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_networking.*

class NetworkingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_networking)

        fetchPopularMoviesRawButton.setOnClickListener {
            Client.fetchPopularMoviesRaw()
        }

        fetchPopularMoviesButton.setOnClickListener {
            Client.fetchPopularMovies()
        }

        fetchPopularMoviesObservableButton.setOnClickListener {
            Client.fetchPopularMoviesObservable()
        }
    }
}
