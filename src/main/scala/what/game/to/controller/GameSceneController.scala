package what.game.to.controller

import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml

@sfxml
class GameSceneController(var zombieImageView: ImageView ){

  val zombie = new Image(getClass.getResourceAsStream("/Images/z.jpeg"))


  def initialize(): Unit = {
    zombieImageView.setImage(zombie)
    println("Where is image")

  }
}