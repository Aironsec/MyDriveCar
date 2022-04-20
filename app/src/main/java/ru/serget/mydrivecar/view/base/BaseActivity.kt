package ru.serget.mydrivecar.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.serget.mydrivecar.presenter.IPresenter
import ru.serget.mydrivecar.view.IView

abstract class BaseActivity: AppCompatActivity(), IView {

    protected lateinit var presenter: IPresenter<IView>

    protected abstract fun createPresenter(): IPresenter<IView>

    abstract override fun renderPosition(point: Pair<Float, Float>)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }
}