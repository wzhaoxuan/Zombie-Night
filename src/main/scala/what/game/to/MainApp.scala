package what.game.to

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.AnchorPane
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import what.game.to.controller.{GameSceneController, WelcomeController}
import scalafx.Includes._
import scalafx.stage.{Modality, Stage}

object MainApp extends JFXApp {

  // Load RootLayout.fxml
  val rootResource = getClass.getResource("WelcomeScene.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load()
  var rootLayout: AnchorPane = loader.getRoot[javafx.scene.layout.AnchorPane] // Convert to ScalaFX AnchorPane

  // Set up the primary stage
  stage = new PrimaryStage {
    title = "ZombieStrike"
    scene = new Scene {
      root = rootLayout
    }
  }

  def showGameScene(): Unit = {
    val gameSceneResource = getClass.getResource("/what/game/to/GameScene.fxml")
    if (gameSceneResource == null) {
      throw new RuntimeException("GameScene.fxml not found.")
    }
    val gameSceneLoader = new FXMLLoader(gameSceneResource, NoDependencyResolver)
    gameSceneLoader.load()
    val gameSceneLayout: AnchorPane = gameSceneLoader.getRoot[javafx.scene.layout.AnchorPane] // Convert to ScalaFX AnchorPane
    stage.scene = new Scene {
      root = gameSceneLayout
    }
    val control = gameSceneLoader.getController[GameSceneController#Controller]
    control.initialize()
  }

  // Show the welcome scene on start
  showWelcome()

  def showWelcome(): Unit = {
    val welcomeSceneResource = getClass.getResource("WelcomeScene.fxml")
    if (welcomeSceneResource == null) {
      throw new RuntimeException("WelcomeScene.fxml not found.")
    }
    val welcomeSceneLoader = new FXMLLoader(welcomeSceneResource, NoDependencyResolver)
    welcomeSceneLoader.load()
    val welcomeSceneLayout: AnchorPane = welcomeSceneLoader.getRoot[javafx.scene.layout.AnchorPane] // Convert to ScalaFX AnchorPane
    stage.scene = new Scene {
      root = welcomeSceneLayout
    }
    val control = welcomeSceneLoader.getController[WelcomeController#Controller]
  }

  // Entry point
  showWelcome()
}