package what.game.to.controller
import what.game.to.model.{NormalZombie, SpeedZombie, Timer}
import what.game.to.MainApp
import scalafx.scene.control.Label
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.util.Duration

import scala.util.Random

@sfxml
class GameSceneController(
                           private val scoreLabel: Label,
                           private val gameArea: AnchorPane,
                           private val timerLabel: Label
                         ) {

  private val totalTime = 120
  private var score = 0
  private val zombieNumber = 10
  private val spawnZombieNum = 7

  def initialize(): Unit = {
    println("Initializing GameSceneController")
    startTimer()
    createZombies(zombieNumber)
  }

  private def createZombies(zombieNum: Int): Unit = {
    val normalZombie = new NormalZombie(gameArea, handleZombieClick)
    val speedZombie = new SpeedZombie(gameArea, handleZombieClick)

    println("Normal Zombie")
    normalZombie.createZombies(zombieNum)
    println("SpeedZombie")
    speedZombie.createZombies(zombieNum)
    }

  private def handleZombieClick(): Unit = {
    // Update score
    score += 1
    scoreLabel.text = score.toString
  }

  // Timer count down function
  private def startTimer(): Unit = {
    val time = new Timer(totalTime, timerLabel, () => createZombies(spawnZombieNum))
    time.start()
  }

  def exit(): Unit = {
    System.exit(0)
//    MainApp.showWelcome()
  }
}