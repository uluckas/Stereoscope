package de.s2s.stereoscope.elevation

interface IElevationModel {

    var width: Double
    var height: Double

    fun elevation(x: Double, y: Double): Double

}