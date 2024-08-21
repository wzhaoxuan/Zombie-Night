package what.game.to.controller
import what.game.to.MainApp
import scalafx.scene.control.Button
import scalafxml.core.macros.sfxml
import what.game.to.model.Player

@sfxml
class ModeSceneController(
                           private val easyMode: Button,
                           private val normalMode: Button,
                           private val hardMode: Button,
                           private val back: Button
                         ) {
  val player: Player = MainApp.currentPlayer

  // Handle button actions
  def handleEasyButton(): Unit = {
    MainApp.setDifficulty("Easy")
    player.recordDifficulty("Easy")
    MainApp.showRule()
  }

  def handleNormalButton(): Unit = {
    MainApp.setDifficulty("Normal")
    player.recordDifficulty("Normal")
    MainApp.showRule()
  }

  def handleHardButton(): Unit = {
    MainApp.setDifficulty("Hard")
    player.recordDifficulty("Hard")
    MainApp.showRule()
  }

  def backMainPage(): Unit = {
    MainApp.showWelcome()
  }
}
