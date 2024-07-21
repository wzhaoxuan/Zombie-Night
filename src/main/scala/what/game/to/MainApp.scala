package what.game.to

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafxml.core.{FXMLLoader, NoDependencyResolver}

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
    val gameSceneResource = getClass.getResource("GameScene.fxml")
    val gameSceneloader = new FXMLLoader(gameSceneResource, NoDependencyResolver)
    gameSceneloader.load()
    val gameSceneLayout: scalafx.scene.layout.AnchorPane = new scalafx.scene.layout.AnchorPane(gameSceneloader.getRoot[javafx.scene.layout.AnchorPane]) // Convert to ScalaFX AnchorPane
    rootLayout.center = gameSceneLayout
  }

  showGameScene()
}
