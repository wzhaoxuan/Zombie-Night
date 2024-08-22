package what.game.to.controller

import scalafxml.core.macros.sfxml
import what.game.to.MainApp

@sfxml
class WelcomeController{

  def getStart(): Unit = {
    MainApp.showPlayerScene()
  }

  def handleLeaderBoard(): Unit = {
    MainApp.showLeaderBoardScene()
  }

  def exit(): Unit = {
    System.exit(0)
  }
}