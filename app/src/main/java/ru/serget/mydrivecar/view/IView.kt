package ru.serget.mydrivecar.view

interface IView {

    fun renderPosition(point: Pair<Float, Float>)
    fun setPosition(point: Pair<Float, Float>)
    fun clearPosition()
}