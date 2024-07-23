package what.game.to

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.AnchorPane
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import what.game.to.controller.GameSceneController
import scalafx.Includes._
import scalafx.stage.{Modality, Stage}

object MainApp extends JFXApp {

  // Load RootLayout.fxml
  val rootResource = getClass.getResource("RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load()
  val rootLayout: BorderPane = new BorderPane(loader.getRoot[javafx.scene.layout.BorderPane]) // Convert to ScalaFX BorderPane

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
    val gameSceneLayout = gameSceneLoader.load[javafx.scene.layout.AnchorPane] // Convert to ScalaFX AnchorPane
    rootLayout.center = gameSceneLayout

    val control = gameSceneLoader.getController[GameSceneController#Controller]
    control.initialize()

  }
  def showWelcome(): Unit = {
    val welcomeSceneResource = getClass.getResource("WelcomeScene.fxml")
    val welcomeSceneLoader = new FXMLLoader(welcomeSceneResource, NoDependencyResolver)
    welcomeSceneLoader.load()
    val welcomeSceneLayout = new AnchorPane(welcomeSceneLoader.getRoot[javafx.scene.layout.AnchorPane]) // Convert to ScalaFX AnchorPane
    rootLayout.center = welcomeSceneLayout
    print("welcome")
  }

  showWelcome()
}