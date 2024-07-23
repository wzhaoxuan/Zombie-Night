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
  private val zombieNumber = 10
  private val spawnZombieTime = 5
  private val spawnZombieNum = 10

  def initialize(): Unit = {
    println("Initializing GameSceneController")
    //    scoreLabel.text = "Score: 0"
    startTimer()
    createZombies(zombieNumber)
  }

  private def createZombies(zombieNum: Int): Unit = {
    println("Creating zombies")
    val zombieWidth = 300
    val zombieHeight = 300

    for (_ <- 1 to zombieNum){
      val zombie = new ImageView()
      zombie.getStyleClass.add("ImageView") // Add a style class to the ImageView
      zombie.image = new Image("/Images/zombie.gif")
      

      // Random position within the GameScene
      val randomX = Math.abs(Random.nextDouble() * (gameArea.width.value - zombieWidth))
      val randomY = Math.abs(Random.nextDouble() * (gameArea.height.value - zombieHeight))

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

  // Timer count down function
  private def startTimer(): Unit = {
    val timeline = new Timeline{
      cycleCount = totalTime
      keyFrames = Seq(
        KeyFrame(Duration(100), onFinished = _ => {
          remainingTime -= 1
          timerLabel.text = remainingTime.toString
          if(remainingTime % spawnZombieTime == 0){
            createZombies(spawnZombieNum)
          }
        })
      )
    }
    timeline.play()
  }
}