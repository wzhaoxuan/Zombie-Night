package what.game.to.controller

import scalafx.beans.property.StringProperty
import scalafx.scene.control.TextField
import scalafxml.core.macros.sfxml
import what.game.to.MainApp


@sfxml
class PlayerInfoController(_name: TextField){
  val player = MainApp.currentPlayer

  def createPlayer(): Unit = { //create player based on name input
    if (StringProperty(_name.text.value)().nonEmpty) { //validation checking for empty name
      player.name = StringProperty(_name.text.value)
      println("User created with name" + player.name)
      MainApp.showModeScene()
    }
  }
}