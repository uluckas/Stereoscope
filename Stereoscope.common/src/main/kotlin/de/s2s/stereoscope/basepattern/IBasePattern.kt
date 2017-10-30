package de.s2s.stereoscope.basepattern

import de.s2s.stereoscope.renderer.Color

interface IBasePattern {
    fun patternPixel(x: Int, y: Int): Color
}