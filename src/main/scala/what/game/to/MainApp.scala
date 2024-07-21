package what.game.to

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.scene.layout.{BorderPane => JFXBorderPane, Pane => JFXPane}

object MainApp extends JFXApp {

  // Load RootLayout.fxml
  val rootResource = getClass.getResource("RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  val rootLayoutJFX = loader.load[JFXBorderPane]()
  val rootLayout = new BorderPane(rootLayoutJFX) // Convert to ScalaFX BorderPane

  // Set up the primary stage
  stage = new PrimaryStage {
    title = "ZombieStrike"
    scene = new Scene(rootLayout) // Use ScalaFX BorderPane
  }

}
