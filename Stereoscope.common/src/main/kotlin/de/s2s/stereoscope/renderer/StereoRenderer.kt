package de.s2s.stereoscope.renderer

import de.s2s.stereoscope.basepattern.IBasePattern
import de.s2s.stereoscope.basepattern.RandomBasePattern
import de.s2s.stereoscope.elevation.IElevationModel
import de.s2s.stereoscope.platform.ICanvas
import kotlin.properties.Delegates

class StereoRenderer(canvas: ICanvas,
                     val basePattern: IBasePattern = RandomBasePattern(),
                     elevationModel: IElevationModel? = null) {

    var canvas: ICanvas by Delegates.observable(canvas) { _, _, _ ->
        updateElevationModelSize()
        updateParallaxFactor()
    }

    // In mm
    var eyeDistance: Double by Delegates.observable(70.0) { _, _, _ ->
        updateParallaxFactor()
    }

    // In mm
    var screenDistance: Double by Delegates.observable(500.0) { _, _, _ ->
        updateParallaxFactor()
    }

    var elevationModel: IElevationModel? by Delegates.observable(elevationModel) { _, _, _ ->
        updateElevationModelSize()
    }

    private var parallaxFactor = calcParallaxFactor()


    init {
        updateElevationModelSize()
    }


    fun render() {
        with(canvas) {
            for (y in 0 until height) {
                val dY = y.toDouble()
                for (x in 0 until width) {
                    val dX = x.toDouble()
                    val parallax = parallax(dX, dY)
                    if (x < parallax) {
                        //println("Parallax: $parallax, for pixel $x")
                        val color = basePattern.patternPixel(x, y)
                        canvas.setPixel(x, y, color)
                    } else {
                        //println("Parallax: $parallax, getting color from ${x - parallax} for pixel $x from ")
                        copyPixel(x - parallax, y, x, y)
                    }
                }
            }
            commit()
        }
    }

    private fun updateElevationModelSize() {
        val canvasWidth = canvas.width.toDouble()
        val canvasHeight = canvas.height.toDouble()
        val physicalWidth = canvasWidth / canvas.density.xPPMM
        val physicalHeight = canvasHeight / canvas.density.yPPMM
        println("xPPMM: ${canvas.density.xPPMM}px, yPPMM: ${canvas.density.yPPMM}px")
        println("Width: ${canvasWidth}px, Height: ${canvasHeight}px")
        println("Width: ${physicalWidth}mm, Height: ${physicalHeight}mm")
        elevationModel?.width = physicalWidth
        elevationModel?.height = physicalHeight
    }

    private fun updateParallaxFactor() {
        parallaxFactor = calcParallaxFactor()
    }

    private fun calcParallaxFactor() = eyeDistance * screenDistance * canvas.density.xPPMM

    private fun parallax(x: Double, y: Double): Int {

        val elevation = elevationModel?.elevation(x / canvas.density.xPPMM, y / canvas.density.yPPMM) ?: 0.0
        val parallax = parallaxFactor / elevation
        return parallax.toInt()
    }

}
