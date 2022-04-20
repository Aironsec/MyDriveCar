package ru.serget.mydrivecar.presenter

import ru.serget.mydrivecar.view.IView

interface IPresenter<V : IView> {
    fun attachView(view: V)
    fun detachView(view: V)
    fun getPosition()
    fun setPosition(point: Pair<Float, Float>)
    fun clearPosition()
}