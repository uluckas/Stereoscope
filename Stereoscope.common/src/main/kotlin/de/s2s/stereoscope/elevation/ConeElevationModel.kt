package de.s2s.stereoscope.elevation

import kotlin.math.sqrt

class ConeElevationModel : CircularElevationModelBase() {

    override fun elevation(x: Double, y: Double): Double {
        val xFromCenter = width / 2.0 - x
        val yFromCenter = height / 2.0 - y
        val radius = sqrt(xFromCenter * xFromCenter + yFromCenter * yFromCenter)
        return if (radius < maxRadius) {
            baseElevation - (baseElevation - centerElevation) * (1.0 - radius / maxRadius)
        } else {
            baseElevation
        }
    }

    companion object {
        const val baseElevation = 1000.0
        const val centerElevation = 1500.0
    }

}