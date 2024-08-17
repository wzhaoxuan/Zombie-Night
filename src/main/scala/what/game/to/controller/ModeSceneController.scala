package what.game.to.controller
import what.game.to.MainApp
import scalafx.scene.control.Button
import scalafxml.core.macros.sfxml

@sfxml
class ModeSceneController(
                           private val easyMode: Button,
                           private val normalMode: Button,
                           private val hardMode: Button
                         ) {


  // Handle button actions
  def handleEasyButton(): Unit = {
    MainApp.setDifficulty("Easy")
    MainApp.showRule()
  }

  def handleNormalButton(): Unit = {
    MainApp.setDifficulty("Normal")
    MainApp.showRule()
  }

  def handleHardButton(): Unit = {
    MainApp.setDifficulty("Hard")
    MainApp.showRule()
  }
}
