package ru.serget.mydrivecar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.serget.mydrivecar.PaintView.Companion.observerXYList
import ru.serget.mydrivecar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val taxiView = binding.imageTaxi

        taxiView.setOnClickListener {
            observerXYList
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    taxiView.translationX = it.first
                    taxiView.translationY = it.second
                }
        }
    }
}