package de.s2s.stereoscope.elevation

import kotlin.math.sqrt

class CircleElevationModel : CircularElevationModelBase() {

    override fun elevation(x: Double, y: Double): Double {
        val xFromCenter = width / 2.0 - x
        val yFromCenter = height / 2.0 - y
        val radius = sqrt(xFromCenter * xFromCenter + yFromCenter * yFromCenter)
        return if (radius < maxRadius) {
            circleElevation
        } else {
            baseElevation
        }
    }

    companion object {
        const val baseElevation = 700.0
        const val circleElevation = 800.0
    }

}