package ru.serget.mydrivecar.presenter

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.serget.mydrivecar.view.IView
import java.util.concurrent.TimeUnit

class MainPresenterImpl<V : IView>(
    private val ds: CompositeDisposable = CompositeDisposable(),
) : IPresenter<V> {

    private var currentView: IView? = null
    private val compensator: Float = 60f
    private val xyList: ArrayList<Pair<Float, Float>> = ArrayList()
    private val observerXYList: Observable<Pair<Float, Float>> = Observable.fromIterable(xyList)

    private fun correctionPosition(point: Pair<Float, Float>): Pair<Float, Float> =
        Pair(point.first - compensator, point.second - compensator)

    override fun attachView(view: V) {
        if (currentView != view)
            currentView = view
    }

    override fun detachView(view: V) {
        ds.clear()
        if (currentView == view)
            currentView = null
    }

    override fun clearPosition() {
        xyList.clear()
    }

    override fun setPosition(point: Pair<Float, Float>) {
        xyList.add(correctionPosition(point))
    }

    override fun getPosition() {
        ds.add(observerXYList
            .subscribeOn(Schedulers.io())
            .concatMap {
                Observable.just(it).delay(80L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
            }
            .subscribe {
                currentView?.renderPosition(it)
            }
        )
    }

}
