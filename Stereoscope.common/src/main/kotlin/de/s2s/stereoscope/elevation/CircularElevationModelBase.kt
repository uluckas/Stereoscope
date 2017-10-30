package de.s2s.stereoscope.elevation

import de.s2s.stereoscope.IElevationModel
import kotlin.properties.Delegates

// stdlib min is broken :-(
private fun min(a: Double, b: Double) = if (a < b) a else b

abstract class CircularElevationModelBase : IElevationModel {
    override var width: Double by Delegates.observable(0.0) { _, _, _ ->
        onDimensionChange()
    }
    override var height: Double by Delegates.observable(0.0) { _, _, _ ->
        onDimensionChange()
    }

    var maxRadius = calcMaxRadius()

    internal open fun onDimensionChange() {
        maxRadius = calcMaxRadius()
    }

    private fun calcMaxRadius() = min(height, width) / 3.0

}