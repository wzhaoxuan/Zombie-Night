package what.game.to.controller
import what.game.to.MainApp
import scalafx.scene.control.Button
import scalafx.scene.input.MouseEvent
import scalafxml.core.macros.sfxml

@sfxml
class ModeSceneController(
                           private val easyMode: Button,
                           private val normalMode: Button,
                           private val hardMode: Button
                         ) {

  // Initialize button actions using ScalaFX's onAction with lambda
  easyMode.onAction = (e: javafx.event.ActionEvent) => handleEasyButton()
  normalMode.onAction = (e: javafx.event.ActionEvent) => handleNormalButton()
  hardMode.onAction = (e: javafx.event.ActionEvent) => handleHardButton()

  // Handle button actions
  def handleEasyButton(): Unit = {
    MainApp.setDifficulty("Easy")
    MainApp.showGameScene()
  }

  def handleNormalButton(): Unit = {
    MainApp.setDifficulty("Normal")
    MainApp.showGameScene()
  }

  def handleHardButton(): Unit = {
    MainApp.setDifficulty("Hard")
    MainApp.showGameScene()
  }
}
