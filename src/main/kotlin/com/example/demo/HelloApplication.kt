package com.example.demo

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.stage.Stage
import kotlinx.coroutines.DelicateCoroutinesApi
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import java.util.*
import kotlin.io.path.Path

@DelicateCoroutinesApi
class HelloApplication : Application() {
    private lateinit var fxmlLoader: FXMLLoader
    private lateinit var scene: Scene
    private lateinit var helloController: HelloController

    private val configPath = Paths.home + File.separator + ".screen+maker"
    private lateinit var  config: File
    private val appProperties = Properties()

    private val saveCombination = KeyCodeCombination(
        KeyCode.S,
        KeyCodeCombination.CONTROL_DOWN
    )

    private val saveAsCombination = KeyCodeCombination(
        KeyCode.S,
        KeyCodeCombination.CONTROL_DOWN,
        KeyCodeCombination.SHIFT_DOWN
    )

    private val openCombination = KeyCodeCombination(
        KeyCode.O,
        KeyCodeCombination.CONTROL_DOWN
    )

    private val closeCombination = KeyCodeCombination(
        KeyCode.E,
        KeyCodeCombination.CONTROL_DOWN
    )

    private val screenshotCombination = KeyCodeCombination(
        KeyCode.P,
        KeyCodeCombination.CONTROL_DOWN
    )

    private  fun bindShortCuts(stage: Stage) {
        stage.scene.accelerators[saveCombination] = Runnable {
            helloController.save()
        }
        stage.scene.accelerators[saveAsCombination] = Runnable {
            helloController.saveAs()
        }
        stage.scene.accelerators[openCombination] = Runnable {
            helloController.open()
        }
        stage.scene.accelerators[closeCombination] = Runnable {
            helloController.close()
        }
        stage.scene.accelerators[screenshotCombination] = Runnable {
            helloController.screenshot()
        }
    }

    private fun setupProperties() {
        if (Files.notExists(Path(configPath))) {
            Files.createDirectory(Path(configPath))
        }

        config = File(configPath + File.separator + ".config")

        if(!config.exists()) {
            config.createNewFile()
        }

        FileInputStream(config).use {
            appProperties.load(it)
        }
        helloController.appProperties = appProperties
    }

    override fun start(stage: Stage) {
        fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("hello-view.fxml"))
        scene = Scene(fxmlLoader.load())
        stage.title = "Screenshot"
        stage.scene = scene
        helloController = fxmlLoader.getController()
        helloController.stage = stage
        bindShortCuts(stage)
        setupProperties()
        stage.show()
    }

    override fun stop() {
        FileOutputStream(config).use {
            appProperties.store(it, "")
        }
        super.stop()
    }

}

class Paths {
    companion object {
        val home: String = System.getProperty("user.home")
        val pictures = home + File.separator + "Pictures"
    }
}

