package de.s2s.stereoscope.platform

import de.s2s.stereoscope.renderer.Density
import javafx.scene.canvas.Canvas
import javafx.scene.image.WritableImage
import javafx.scene.paint.Color

class FXCanvas(private val fxCanvas: Canvas, override var density: Density) : ICanvas {

    override val width = fxCanvas.width.toInt()
    override val height = fxCanvas.height.toInt()
    private val writableImage = WritableImage(width, height)
    private val pixelWriter = writableImage.pixelWriter
    private val pixelReader = writableImage.pixelReader

    override fun setPixel(x: Int, y: Int, c: de.s2s.stereoscope.renderer.Color) {
        val color = Color(c.r / 255.0, c.g / 255.0, c.b / 255.0, c.a / 255.0)
        pixelWriter.setColor(x, y, color)
    }

    override fun getPixel(x: Int, y: Int): de.s2s.stereoscope.renderer.Color {
        val color = pixelReader.getColor(x, y)
        val r = (color.red * 255.0).toInt()
        val g = (color.green * 255.0).toInt()
        val b = (color.blue * 255.0).toInt()
        val a = (color.opacity * 255.0).toInt()
        return de.s2s.stereoscope.renderer.Color(r, g, b, a)
    }

    override fun copyPixel(srcX: Int, srcY: Int, dstX: Int, dstY: Int) {
        val color = pixelReader.getColor(srcX, srcY)
        pixelWriter.setColor(dstX, dstY, color)
    }

    override fun commit() {
        fxCanvas.graphicsContext2D.drawImage(writableImage, 0.0, 0.0)
    }

}