package what.game.to.controller
import what.game.to.model.{DefenseZombie, NormalZombie, Person, SpeedZombie, Zombie}
import what.game.to.util.Timer
import what.game.to.MainApp
import scalafx.scene.control.{Label, ProgressBar}
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml


@sfxml
class GameSceneController(
                           private val scoreLabel: Label,
                           private val gameArea: AnchorPane,
                           private val timerLabel: Label,
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
  private val victim = new Person(gameArea)
  private var zombieController: Option[ZombieController] = None
  var gameRunning = true




  def initialize(): Unit = {
    println("Initializing GameSceneController")
    victim.setupImage("/Images/yang-gang-mini-yang.gif", 1189.0, 297.0, 255.0, 346.0)
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

    // Create a new ZombieController instance with updated settings
    zombieController = Some(new ZombieController(
      gameArea,
      handleZombieClick,
      victim,
      healthPoint,
      () => checkGameOver(),
      gameRunning
    ))
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

      println("Creating zombies...")
      val normalZombies = List.fill(normalZombieCount)(
        new NormalZombie("/Images/Zombie/NormalZombie.gif", 300, 300, 4, 3, 2, 350)
      )
      val speedZombies = List.fill(speedZombieCount)(
        new SpeedZombie("/Images/Zombie/SpeedZombie.gif", 250, 200, 8, 2, 1, 450)
      )
      val defenseZombies = List.fill(defenseZombieCount)(
        new DefenseZombie("/Images/Zombie/DefenseZombie.gif", 300, 400, 2, 5, 3, 300)
      )

      val allZombies = normalZombies ++ speedZombies ++ defenseZombies

      zombieController.foreach(_.createZombies(allZombies))

      currentZombieCount += (normalZombieCount + speedZombieCount + defenseZombieCount)
      updateZombieLabel()
    }
    }

  private def updateZombieLabel(): Unit = {
    zombieLabel.text = s"Zombie: $currentZombieCount/$maxZombies"
  }

  private def handleZombieClick(): Unit = {
    // Update score
    score += 1
    scoreLabel.text = s" Score: ${score.toString}"
    checkGameOver()
  }
  private def checkGameOver(timeRanOut: Boolean = false): Unit = {
    if (healthPoint.progress.value <= 0) {
      gameRunning = false
      stopAllZombies()
      // Game over due to health point depletion
      MainApp.showEndGameScene(healthPoint.progress.value, score) // Pass relevant info
    } else if (score >= maxZombies || timeRanOut){
      gameRunning = false
//      println(gameRunning)
      // Victory condition: All zombies are killed
      stopAllZombies()
      MainApp.showEndGameScene(healthPoint.progress.value, score)
    }
  }

  // Timer count down function
  private def startTimer(): Unit = {
    val time = new Timer(totalTime, timerLabel, spawnZombieTime, () => createZombies(spawnZombieNum), () =>checkGameOver(true))
    time.start()
  }

  private def stopAllZombies(): Unit = {
    zombieController.foreach(_.stopAllZombies())
    gameArea.children.clear()
  }


  def exit(): Unit = {
    System.exit(0)
//    MainApp.showWelcome()
  }
}