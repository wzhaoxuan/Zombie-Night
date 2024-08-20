package what.game.to.controller
import what.game.to.model.{DefenseZombie, NormalZombie, Person, SpeedZombie}
import what.game.to.util.{Timer, Difficulty}
import what.game.to.MainApp
import scalafx.scene.control.{Button, Label, ProgressBar}
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml

@sfxml
class GameSceneController(
                           private val scoreLabel: Label,
                           private val gameArea: AnchorPane,
                           private val timerLabel: Label,
                           private var healthPoint: ProgressBar,
                           private val zombieLabel: Label,
                           private val pauseButton: Button
                         ) {

  var difficulty: Difficulty = _
  private val totalTime = 120
  private var currentZombieCount = 0
  private var gameRunning = true
  private var gamePaused = false
  private var zombieController: Option[ZombieController] = None
  private var timer: Option[Timer] = None
  private val victim = new Person(gameArea)
  private val scoreManager = new Score(scoreLabel)

  def initialize(): Unit = {
    println("Initializing GameSceneController")
    victim.setupImage("/Images/Victim/yang-gang-mini-yang.gif", 1189.0, 297.0, 255.0, 346.0)
    startTimer()
    createZombies(10) // Using a fixed number here for initial setup
    updateZombieLabel()
    pauseButton.onAction = _ => pauseGame()
  }

  def setDifficulty(diff: Difficulty): Unit = {
    difficulty = diff
    difficultySettings()
  }

  private def difficultySettings(): Unit = {
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
    if (!gameRunning|| gamePaused) return

    val zombiesLeft = difficulty.maxZombies - currentZombieCount

    if (zombiesLeft > 0) {
      val (normalZombieCount, speedZombieCount, defenseZombieCount) = difficulty.level match {
        case "Easy" =>
          val normalZombies = math.min(zombieNum, zombiesLeft)
          (normalZombies, 0, 0)
        case "Normal" =>
          val maxNormalZombies = zombiesLeft / 2
          val maxSpeedZombies = zombiesLeft
          (math.min(zombieNum, maxNormalZombies), math.min(zombieNum, maxSpeedZombies), 0)
        case "Hard" =>
          val maxNormalZombies = zombiesLeft / 2
          val maxSpeedZombies = zombiesLeft
          val maxDefenseZombies = zombiesLeft / 2
          (math.min(zombieNum, maxNormalZombies), math.min(zombieNum, maxSpeedZombies), math.min(zombieNum, maxDefenseZombies))
      }

      println("Creating zombies...")
      val normalZombies = List.fill(normalZombieCount)(
        new NormalZombie(300, 300, 350)
      )
      val speedZombies = List.fill(speedZombieCount)(
        new SpeedZombie(250, 200, 450)
      )
      val defenseZombies = List.fill(defenseZombieCount)(
        new DefenseZombie(300, 400, 270)
      )

      val allZombies = normalZombies ++ speedZombies ++ defenseZombies

      zombieController.foreach(_.createZombies(allZombies))

      currentZombieCount += (normalZombieCount + speedZombieCount + defenseZombieCount)
      updateZombieLabel()
    }
  }

  private def updateZombieLabel(): Unit = {
    val maxZombies = difficulty.maxZombies
    zombieLabel.text = s"Zombie: $currentZombieCount/$maxZombies"
  }

  private def handleZombieClick(): Unit = {
    if (!gamePaused) {
      scoreManager.incrementScore()
      checkGameOver()
    }
  }

  private def checkGameOver(timeRanOut: Boolean = false): Unit = {
    if (healthPoint.progress.value <= 0) {
      gameRunning = false
      stopAllZombies()
      MainApp.showEndGameScene(healthPoint.progress.value, scoreManager.getScore)
    } else if (scoreManager.getScore >= difficulty.maxZombies || timeRanOut) {
      gameRunning = false
      stopAllZombies()
      MainApp.showEndGameScene(healthPoint.progress.value, scoreManager.getScore)
    }
  }

  private def startTimer(): Unit = {
    val time = new Timer(totalTime, timerLabel, difficulty.spawnZombieTime)
    timer = Some(time)
    time.start(() => createZombies(difficulty.spawnZombieNum), () => checkGameOver(true))
    }


  private def stopTimer(): Unit = {
    timer.foreach(_.stop())
  }

  private def stopAllZombies(): Unit = {
    zombieController.foreach(_.stopAllZombies())
    gameArea.children.clear()
  }

  def pauseGame(): Unit = {
    if (gameRunning && !gamePaused) {
      gamePaused = true
      stopTimer()
      zombieController.foreach(_.pauseZombies())
      MainApp.showPauseScene()
    }
  }

  def resume(): Unit = {
    if (gameRunning && gamePaused) {
      gamePaused = false
      startTimer()
      zombieController.foreach(_.resumeZombies())
    }
  }

  def exit(): Unit = {
//    MainApp.showModeScene()
    System.exit(0)
  }
}
