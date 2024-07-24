package what.game.to.controller
import what.game.to.model.{NormalZombie, SpeedZombie, Timer}
import what.game.to.MainApp
import scalafx.scene.control.{Label, ProgressBar}
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml
import scalafx.scene.image.ImageView
import scalafx.scene.input.MouseEvent
import scalafx.Includes._


import scala.util.Random

@sfxml
class GameSceneController(
                           private val scoreLabel: Label,
                           private val gameArea: AnchorPane,
                           private val timerLabel: Label,
                           private val targetImage: ImageView,
                           private var healthPoint: ProgressBar,
                           private val zombieLabel: Label
                         ) {

  private val totalTime = 120
  private var score = 0
  private val initialZombieNum = 10
  private val spawnZombieNum = 3
  private val spawnZombieTime = 5
  private val maxZombies = 100
  private var currentZombieCount = 0

  def initialize(): Unit = {
    println("Initializing GameSceneController")
    startTimer()
    createZombies(initialZombieNum)
    updateZombieLabel()
  }

  private def createZombies(zombieNum: Int): Unit = {
    val zombiesLeft = maxZombies - currentZombieCount

    if (zombiesLeft > 0) {
      val maxNormalZombies = zombiesLeft / 2
      val maxSpeedZombies = zombiesLeft - maxNormalZombies

      val normalZombieCount = math.min(zombieNum, maxNormalZombies)
      val speedZombieCount = math.min(zombieNum, maxSpeedZombies)

      val normalZombie = new NormalZombie(gameArea, handleZombieClick, targetImage, healthPoint)
      val speedZombie = new SpeedZombie(gameArea, handleZombieClick, targetImage, healthPoint)

      println("Normal Zombie")
      normalZombie.createZombies(normalZombieCount)
      println("SpeedZombie")
      speedZombie.createZombies(speedZombieCount)

      currentZombieCount += (normalZombieCount + speedZombieCount)
      updateZombieLabel()
    }
    }

  private def updateZombieLabel(): Unit = {
    zombieLabel.text = s"$currentZombieCount/$maxZombies"
  }

  private def handleZombieClick(): Unit = {
    // Update score
    score += 1
    scoreLabel.text = score.toString
  }

  // Timer count down function
  private def startTimer(): Unit = {
    val time = new Timer(totalTime, timerLabel, spawnZombieTime, () => createZombies(spawnZombieNum))
    time.start()
  }

  def exit(): Unit = {
    System.exit(0)
//    MainApp.showWelcome()
  }
}