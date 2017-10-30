package de.s2s.stereoscope.platform

import de.s2s.stereoscope.renderer.Color
import de.s2s.stereoscope.renderer.Density
import org.khronos.webgl.Uint8ClampedArray
import org.khronos.webgl.get
import org.khronos.webgl.set
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.ImageData

class JSCanvas(htmlCanvas: HTMLCanvasElement, override val density: Density) : ICanvas {

    override val width = htmlCanvas.width
    override val height = htmlCanvas.height

    override fun setPixel(x: Int, y: Int, c: Color) {
        var pixelIndex = pixelIndex(x, y)
        val data = imageData.data
        // data holds Uint8 values but kotlin's getter is declared as Byte
        // This leads to wrong conversion of values above 127
        data[pixelIndex++] = c.r.asDynamic()
        data[pixelIndex++] = c.g.asDynamic()
        data[pixelIndex++] = c.b.asDynamic()
        data[pixelIndex++] = c.a.asDynamic()
    }

    override fun getPixel(x: Int, y: Int): Color {
        var pixelIndex = pixelIndex(x, y)
        val data: Uint8ClampedArray = imageData.data
        val r = data[pixelIndex++].asDynamic()
        val g = data[pixelIndex++].asDynamic()
        val b = data[pixelIndex++].asDynamic()
        val a = data[pixelIndex++].asDynamic()
        return Color(r, g, b, a)
    }

    override fun copyPixel(srcX: Int, srcY: Int, dstX: Int, dstY: Int) {
        var srcPixelIndex = pixelIndex(srcX, srcY)
        var dstPixelIndex = pixelIndex(dstX, dstY)
        val data: Uint8ClampedArray = imageData.data
        data[dstPixelIndex++] = data[srcPixelIndex++]
        data[dstPixelIndex++] = data[srcPixelIndex++]
        data[dstPixelIndex++] = data[srcPixelIndex++]
        data[dstPixelIndex++] = data[srcPixelIndex++]
    }

    override fun commit() {
        context.putImageData(imageData, 0.0, 0.0)
    }

    private val context = htmlCanvas.getContext("2d") as CanvasRenderingContext2D
    private val imageData: ImageData = context.createImageData(width.toDouble(), height.toDouble())

    private fun pixelIndex(x: Int, y: Int): Int = (y * imageData.width + x) * BYTES_PER_PIXEL

    private fun Any?.asDynamic(): dynamic = this

    companion object {
        const val BYTES_PER_PIXEL = 4
    }

}