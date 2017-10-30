package de.s2s.stereoscope.platform

import de.s2s.stereoscope.renderer.Color
import de.s2s.stereoscope.renderer.Density

interface ICanvas {
    val width: Int
    val height: Int
    val density: Density
    fun setPixel(x: Int, y: Int, c: Color)
    fun getPixel(x: Int, y: Int): Color
    fun copyPixel(srcX: Int, srcY: Int, dstX: Int, dstY: Int) {
        val color = getPixel(srcX, srcY)
        setPixel(dstX, dstY, color)
    }
    fun commit()
}