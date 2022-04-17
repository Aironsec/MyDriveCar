package ru.serget.mydrivecar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.CompletableSubject
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

    private fun View.gogoTrail(xx: Float, yy: Float, duration: Long = 50L): Completable {
        val animationSubject = CompletableSubject.create()
        return animationSubject.doOnSubscribe {
            ViewCompat.animate(this)
                .setDuration(duration)
                .translationX(xx)
                .translationY(yy)
                .withEndAction {
                    animationSubject.onComplete()
                }
        }

    }
}