package de.s2s.stereoscope

import de.s2s.stereoscope.basepattern.RandomBasePattern
import de.s2s.stereoscope.elevation.CircleElevationModel
import de.s2s.stereoscope.platform.FXCanvas
import de.s2s.stereoscope.renderer.Density
import de.s2s.stereoscope.renderer.StereoRenderer
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.StackPane
import javafx.stage.Screen
import javafx.stage.Stage
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.javafx.JavaFx
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit.MILLISECONDS


class Stereoscope() : Application() {


    private lateinit var stereoRenderer: StereoRenderer

    private var shouldExit = false

    override fun start(primaryStage: Stage?) {
        val screen = Screen.getPrimary()
        val PPMM = screen.dpi / 25.4 // inch to mm
        val primaryScreenBounds = screen.visualBounds
        val width = primaryScreenBounds.width
        val height = primaryScreenBounds.height

        val canvas = Canvas(width, height)
        val root = StackPane(canvas)

        primaryStage?.apply {
            setTitle("Stereoscope");
            setX(primaryScreenBounds.getMinX())
            setY(primaryScreenBounds.getMinY())
            setWidth(width)
            setHeight(height)
            setScene(Scene(root, width, height))
            setOnCloseRequest {
                Platform.exit()
                shouldExit = true
            }
            show()
        }

        val density = Density(PPMM, PPMM)
        val fxCanvas = FXCanvas(canvas, density)
        stereoRenderer = StereoRenderer(fxCanvas, RandomBasePattern(), CircleElevationModel())

        launch(JavaFx, CoroutineStart.UNDISPATCHED) {
            while (true) {
                stereoRenderer.render()
                delay(20, MILLISECONDS)
            }
        }

    }

}

fun main(args: Array<String>) {
    Application.launch(Stereoscope::class.java, *args)
}

