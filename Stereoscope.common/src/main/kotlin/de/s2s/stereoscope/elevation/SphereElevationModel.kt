package de.s2s.stereoscope.elevation

import de.s2s.stereoscope.debugger
import kotlin.math.sqrt

class SphereElevationModel : CircularElevationModelBase() {

    private var cache = seedCache()

    override fun elevation(x: Double, y: Double): Double = cache[x.toInt()][y.toInt()]

    private fun calcElevation(x: Double, y: Double): Double {
        val xFromCenter = width / 2.0 - x
        val yFromCenter = height / 2.0 - y
        val radius = sqrt(xFromCenter * xFromCenter + yFromCenter * yFromCenter)
        val elevation = if (radius < maxRadius) {
            baseElevation + sqrt(maxRadius * maxRadius - radius * radius)
        } else {
            baseElevation
        }

        if (elevation < 0) {
            println("Negative elevation")
            debugger()
        }
        return elevation
    }

    override fun onDimensionChange() {
        super.onDimensionChange()
        cache = seedCache()
    }

    private fun seedCache() =
            Array(width.toInt() + 1) { x ->
                val xD = x.toDouble()
                DoubleArray(height.toInt() + 1) { y ->
                    calcElevation(xD, y.toDouble())
                }
            }

    companion object {
        const val baseElevation = 700.0
    }

}