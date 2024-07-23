package what.game.to.controller

import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml

import scala.util.Random
import scalafx.Includes._
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.util.Duration
import what.game.to.MainApp

@sfxml
class GameSceneController(
                           private val scoreLabel: Label,
                           private val gameArea: AnchorPane,
                           private val timerLabel: Label
                         ) {


  private val totalTime = 120
  private var remainingTime = totalTime
  private var score = 0
  private val zombieNumber = 30

  def initialize(): Unit = {
    println("Initializing GameSceneController")
    //    scoreLabel.text = "Score: 0"
    startTimer()
    createZombies()
  }

  private def createZombies(): Unit = {
    println("Creating zombies")
    val zombieWidth = 300
    val zombieHeight = 300

    for (_ <- 1 to zombieNumber){
      val zombie = new ImageView()
      zombie.getStyleClass.add("ImageView") // Add a style class to the ImageView
      zombie.image = new Image("/Images/zombie.gif")

      // Random position
      val randomX = Random.nextDouble() * (gameArea.width.value + zombieWidth)
      val randomY = Random.nextDouble() * (gameArea.height.value + zombieHeight)

      zombie.layoutX = randomX
      zombie.layoutY = randomY

      println(s"Coordinate: [$randomX, $randomY]")
      // Event Handler to count Zombie Click
      zombie.onMouseClicked = (e: MouseEvent) => handleZombieClick(zombie)
      // Add zombie image in the Anchor Pane
      gameArea.children.add(zombie)
    }


  }

  private def handleZombieClick(zombie: ImageView): Unit = {
    // Remove the clicked zombie
    gameArea.children.remove(zombie)

    // Update score
    score += 1
    scoreLabel.text = score.toString
  }

  // Timer count down
  private def startTimer(): Unit = {
    val timeline = new Timeline {
      cycleCount = totalTime
      keyFrames = Seq(
        KeyFrame(Duration(1000), onFinished = _ => {
          remainingTime -= 1
          timerLabel.text = remainingTime.toString
        })
      )
    }
    timeline.play()
  }
}