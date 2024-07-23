package what.game.to.controller

import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml
import scala.util.Random
import scalafx.Includes._
import what.game.to.MainApp

@sfxml
class GameSceneController(
                           private val scoreLabel: Label,
                           private val gameArea: AnchorPane
                         ) {


  private var score = 0
  private val zombieNumber = 10

  def initialize(): Unit = {
    println("Initializing GameSceneController")
    //    scoreLabel.text = "Score: 0"
    createZombies()
  }

  private def createZombies(): Unit = {
    println("Creating zombies")
    val zombieWidth = 300 // Adjust this value as needed
    val zombieHeight = 300 // Adjust this value as needed

    for (_ <- 1 to zombieNumber){
      val zombie = new ImageView()
      zombie.getStyleClass.add("ImageView") // Add a style class to the ImageView
      zombie.image = new Image("/Images/zombie.gif")

      val randomX = Random.nextDouble() * (gameArea.width.value + zombieWidth)
      val randomY = Random.nextDouble() * (gameArea.height.value + zombieHeight)

      zombie.layoutX = randomX
      zombie.layoutY = randomY

      println(s"Coordinate: [$randomX, $randomY]")
      // Add zombie image in the Anchor Pane
      gameArea.children.add(zombie)
    }


  }
}