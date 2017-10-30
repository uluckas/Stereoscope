package de.s2s.stereoscope.basepattern

import de.s2s.stereoscope.renderer.Color

class ColorBasePattern : IBasePattern {

    override fun patternPixel(x: Int, y: Int) = Color(x % 255, (x * y) % 255, y % 255, 255)

}