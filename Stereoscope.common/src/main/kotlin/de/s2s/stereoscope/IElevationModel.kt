package de.s2s.stereoscope

interface IElevationModel {

    var width: Double
    var height: Double

    fun elevation(x: Double, y: Double): Double

}