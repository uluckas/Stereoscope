package de.s2s.stereoscope.platform

import de.s2s.stereoscope.renderer.Color
import de.s2s.stereoscope.renderer.Density

interface ICanvas {
    val width: Int
    val height: Int
    val density: Density
    fun setPixel(x: Int, y: Int, c: Color)
    fun getPixel(x: Int, y: Int): Color
    fun commit()
}