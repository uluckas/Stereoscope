package de.s2s.stereoscope

import de.s2s.stereoscope.basepattern.RandomBasePattern
import de.s2s.stereoscope.elevation.CircleElevationModel
import de.s2s.stereoscope.platform.JSCanvas
import de.s2s.stereoscope.renderer.Density
import de.s2s.stereoscope.renderer.StereoRenderer
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import kotlin.browser.document
import kotlin.browser.window

class Stereoscope {

    private val canvas: JSCanvas
    private val stereoRenderer: StereoRenderer

    init {
        canvas = JSCanvas(newCanvasElement(document.body!!), measureDensity())

        stereoRenderer = StereoRenderer(canvas, RandomBasePattern(), CircleElevationModel())

    }

    fun run() {
        stereoRenderer.render()
        window.setInterval(stereoRenderer::render, 20)
    }

    private fun measureDensity(): Density {
        val parent = document.body!!
        val canvas = document.createElement("div") as HTMLDivElement

        parent.appendChild(canvas)
        canvas.style.width = "10cm"
        canvas.style.height = "10cm"
        val width = canvas.clientWidth
        val height = canvas.clientHeight
        parent.removeChild(canvas)
        val density = Density(width / 100.0, height / 100.0)
        println("10cm x 10xm: $width x $height")

        return density
    }

    private fun newCanvasElement(parent: HTMLElement): HTMLCanvasElement {
        val canvas = document.createElement("canvas") as HTMLCanvasElement

        parent.appendChild(canvas)
        canvas.width = window.innerWidth
        canvas.height = window.innerHeight
        println("Canvas: ${canvas.width} x ${canvas.height}")

        return canvas
    }

}

fun main(args: Array<String>) {
    Stereoscope().run()
}
