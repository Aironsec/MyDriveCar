package ru.serget.mydrivecar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.serget.mydrivecar.PaintView.Companion.observerXYList
import ru.serget.mydrivecar.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var disposableSubscriber: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val taxiView = binding.imageTaxi

        taxiView.setOnClickListener {
            disposableSubscriber = observerXYList
                .subscribeOn(Schedulers.computation())
                .concatMap {
                    Observable.just(it).delay(80L, TimeUnit.MILLISECONDS)
                }
                .subscribe {
                    taxiView.translationX = it.first
                    taxiView.translationY = it.second
                }
        }
    }

    override fun onStop() {
        super.onStop()
        disposableSubscriber.dispose()
    }
}