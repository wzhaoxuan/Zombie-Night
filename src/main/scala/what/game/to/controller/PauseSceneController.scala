package what.game.to.controller

import scalafx.scene.control.Button
import scalafxml.core.macros.sfxml
import what.game.to.MainApp

@sfxml
class PauseSceneController(
                            private val resumeButton: Button,
                            private val exitButton: Button
                          ) {

  def initialize(): Unit = {
    // Set actions for buttons
    resumeButton.onAction = _ => resumeGame()
    exitButton.onAction = _ => exitGame()
  }

  private def resumeGame(): Unit = {
    MainApp.gameSceneController.foreach(_.resume())
    MainApp.showGameScene()
  }

  private def exitGame(): Unit = {
    MainApp.gameSceneController.foreach(_.exit())
  }
}
