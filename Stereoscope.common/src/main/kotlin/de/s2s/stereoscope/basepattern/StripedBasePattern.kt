package de.s2s.stereoscope.basepattern

import de.s2s.stereoscope.renderer.Color

class StripedBasePattern : IBasePattern {

    override fun patternPixel(x: Int, y: Int) = if ((x / 3) % 2 == 0) {
        Color.black
    } else {
        Color.white
    }

}