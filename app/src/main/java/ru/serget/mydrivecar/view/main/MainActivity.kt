package ru.serget.mydrivecar.view.main

import android.os.Bundle
import android.widget.ImageView
import ru.serget.mydrivecar.databinding.ActivityMainBinding
import ru.serget.mydrivecar.presenter.IPresenter
import ru.serget.mydrivecar.presenter.MainPresenterImpl
import ru.serget.mydrivecar.view.IView
import ru.serget.mydrivecar.view.base.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taxiView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taxiView = binding.imageTaxi

        taxiView.setOnClickListener {
            presenter.getPosition()
        }
    }

    override fun createPresenter(): IPresenter<IView> {
        return MainPresenterImpl()
    }

    override fun setPosition(point: Pair<Float, Float>) {
        presenter.setPosition(point)
    }

    override fun clearPosition() {
        presenter.clearPosition()
    }

    override fun renderPosition(point: Pair<Float, Float>) {
        taxiView.translationX = point.first
        taxiView.translationY = point.second
    }
}
