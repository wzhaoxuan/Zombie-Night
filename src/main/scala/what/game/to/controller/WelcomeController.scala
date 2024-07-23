package what.game.to.controller

import scalafxml.core.macros.sfxml
import what.game.to.MainApp

@sfxml
class WelcomeController{

  def getStart(): Unit = {
    MainApp.showGameScene()
  }
}