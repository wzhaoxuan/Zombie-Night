package what.game.to.controller
import what.game.to.model.{DefenseZombie, NormalZombie, SpeedZombie, Timer, Zombie}
import what.game.to.MainApp
import scalafx.scene.control.{Label, ProgressBar}
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml
import scalafx.scene.image.ImageView
import scalafx.Includes._
import what.game.to.MainApp.showEndGameScene

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
  private var difficulty: String = _
  private val totalTime = 120
  private var score = 0
  private val initialZombieNum = 10
  private var spawnZombieNum = 0
  private var spawnZombieTime = 0
  private var maxZombies = 0
  private var currentZombieCount = 0
  var gameRunning = true


  def initialize(): Unit = {
    println("Initializing GameSceneController")
    startTimer()
    createZombies(initialZombieNum)
    updateZombieLabel()
  }

  // Need to modify
  def setDifficulty(diff: String): Unit = {
    difficulty = diff
    difficultySettings()
  }

  private def difficultySettings(): Unit = {
    difficulty match {
      case "Easy" =>
        spawnZombieNum = 3
        spawnZombieTime = 5
        maxZombies = 50

      case "Normal" =>
        spawnZombieNum = 5
        spawnZombieTime = 10
        maxZombies = 150

      case "Hard" =>
        spawnZombieNum = 3
        spawnZombieTime = 15
        maxZombies = 200
    }
  }

  private def createZombies(zombieNum: Int): Unit = {
    if(!gameRunning) return

    val zombiesLeft = maxZombies - currentZombieCount

    if (zombiesLeft > 0) {
      // Adjust zombie counts based on difficulty
      val (normalZombieCount, speedZombieCount, defenseZombieCount): (Int, Int, Int) = {
        difficulty match {
          case "Easy" =>
            val normalZombies = (math.min(zombieNum, zombiesLeft))
            (normalZombies, 0, 0)
          case "Normal" =>
            val maxNormalZombies = zombiesLeft / 2
            val maxSpeedZombies = zombiesLeft
            // return the number of zombie
            (math.min(zombieNum, maxNormalZombies), math.min(zombieNum, maxSpeedZombies), 0)
          case "Hard" =>
            val maxNormalZombies = zombiesLeft / 2
            val maxSpeedZombies = zombiesLeft
            val maxDefenseZombies = zombiesLeft / 2
            // return the number of zombie
            (math.min(zombieNum, maxNormalZombies), math.min(zombieNum, maxSpeedZombies), math.min(zombieNum, maxDefenseZombies))
        }
      }

      val normalZombie = new NormalZombie(gameArea, handleZombieClick, targetImage, healthPoint, checkGameOver, gameRunning)
      val speedZombie = new SpeedZombie(gameArea, handleZombieClick, targetImage, healthPoint, checkGameOver, gameRunning)
      val defenseZombie = new DefenseZombie(gameArea, handleZombieClick, targetImage, healthPoint, checkGameOver, gameRunning)

      println("NormalZombie")
      normalZombie.createZombies(normalZombieCount)
      println("SpeedZombie")
      speedZombie.createZombies(speedZombieCount)
      println("DefenseZombie")
      defenseZombie.createZombies(defenseZombieCount)

      currentZombieCount += (normalZombieCount + speedZombieCount + defenseZombieCount)
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
    checkGameOver()
  }
  private def checkGameOver(): Unit = {
    if (healthPoint.progress.value <= 0) {
      gameRunning = false
      println(gameRunning)
      stopAllZombies()
      // Game over due to health point depletion
      MainApp.showEndGameScene(healthPoint.progress.value, score) // Pass relevant info
    } else if (score >= maxZombies){
      gameRunning = false
//      println(gameRunning)
      // Victory condition: All zombies are killed
      stopAllZombies()
      MainApp.showEndGameScene(healthPoint.progress.value, score)
    }
  }

  // Timer count down function
  private def startTimer(): Unit = {
    val time = new Timer(totalTime, timerLabel, spawnZombieTime, () => createZombies(spawnZombieNum), () =>checkGameOver())
    time.start()
  }

  private def stopAllZombies(): Unit = {
    // Ensure all zombies stop when the game ends
    gameArea.children.clear() // Clear any remaining UI elements
  }


  def exit(): Unit = {
    System.exit(0)
//    MainApp.showWelcome()
  }
}