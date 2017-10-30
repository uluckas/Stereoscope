package de.s2s.stereoscope.renderer

import de.s2s.stereoscope.platform.expected.random

data class Color(val r: Int, val g: Int, val b: Int, val a: Int) {

    companion object {
        val black = Color(0, 0, 0, 255)
        val white = Color(255, 255, 255, 255)
        fun randomColor() = Color(randomByte(), randomByte(), randomByte(), 255)

        private fun randomByte() = (random() * 255.0).toInt()
    }

}

