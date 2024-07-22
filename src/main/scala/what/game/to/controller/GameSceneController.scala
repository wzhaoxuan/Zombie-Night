package what.game.to.controller

import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import what.game.to.MainApp

@sfxml
class GameSceneController(
                           private val scoreLabel: Label,
                           private val gameArea: AnchorPane
                         ) {


  var score = 0

  def initialize(): Unit = {
    println("Initializing GameSceneController")
    //    scoreLabel.text = "Score: 0"
    createZombies()
  }

  private def createZombies(): Unit = {
    println("Creating zombies")
    val zombieWidth = 1000 // Adjust this value as needed
    val zombieHeight = 400 // Adjust this value as needed


    val zombie = new ImageView()
    zombie.getStyleClass.add("ImageView") // Add a style class to the ImageView
    zombie.image = new Image("/Images/zombie.gif") // Ensure the image path is correct
    // Add zombie image in the Anchor Pane
    gameArea.children.add(zombie)

}
}
