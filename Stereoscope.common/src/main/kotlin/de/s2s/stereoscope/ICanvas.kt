package de.s2s.stereoscope

interface ICanvas {
    val width: Int
    val height: Int
    val density: Density
    fun setPixel(x: Int, y: Int, c: Color)
    fun getPixel(x: Int, y: Int): Color
    fun commit()
}