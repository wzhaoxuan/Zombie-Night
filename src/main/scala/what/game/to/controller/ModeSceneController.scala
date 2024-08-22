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
    MainApp.showRuleScene()
  }

  def handleNormalButton(): Unit = {
    MainApp.setDifficulty("Normal")
    player.recordDifficulty("Normal")
    MainApp.showRuleScene()
  }

  def handleHardButton(): Unit = {
    MainApp.setDifficulty("Hard")
    player.recordDifficulty("Hard")
    MainApp.showRuleScene()
  }

  def backMainPage(): Unit = {
    MainApp.showWelcome()
  }
}
