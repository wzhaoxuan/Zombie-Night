package what.game.to.controller
import what.game.to.MainApp
import scalafx.scene.control.Button
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml
import scalafx.Includes._

@sfxml
class ModeSceneController(
                           private val easyMode: Button,
                           private val normalMode: Button,
                           private val hardMode: Button
                         ) {

  // Initialize button actions using ScalaFX's onAction with lambda
  easyMode.onAction = (e: ActionEvent) => handleEasyButton()
  normalMode.onAction = (e: ActionEvent) => handleNormalButton()
  hardMode.onAction = (e: ActionEvent) => handleHardButton()

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
