package de.s2s.stereoscope.basepattern

import de.s2s.stereoscope.renderer.Color

class BWBasePattern : IBasePattern {

    override fun patternPixel(x: Int, y: Int) = if (((x * y) / 11) % 2 == 0) {
        Color.black
    } else {
        Color.white
    }

}