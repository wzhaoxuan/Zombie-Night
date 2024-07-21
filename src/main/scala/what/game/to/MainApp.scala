package what.game.to

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.Includes._

object MainApp extends JFXApp {

  // Load RootLayout.fxml
  val rootResource = getClass.getResource("RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load()
  val rootLayout = loader.getRoot[jfxs.layout.BorderPane]// Convert to ScalaFX BorderPane

  // Set up the primary stage
  stage = new PrimaryStage {
    title = "ZombieStrike"
    scene = new Scene {
      root = rootLayout

    }
  }

}