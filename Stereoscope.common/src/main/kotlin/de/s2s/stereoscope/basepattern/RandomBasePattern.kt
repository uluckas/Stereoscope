package de.s2s.stereoscope.basepattern

import de.s2s.stereoscope.renderer.Color

class RandomBasePattern : IBasePattern {

    override fun patternPixel(x: Int, y: Int) = Color.randomColor()

}