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
                         ) {

  var difficulty: Difficulty = _
  private val totalTime = 120
  private var currentZombieCount = 0
  private var gameRunning = true
  private var zombieController: Option[ZombieController] = None
  private val victim = new Person(gameArea)
  private val scoreManager = new Score(scoreLabel)
  private var timer: Option[Timer] = None

  def initialize(): Unit = {
    println("Initializing GameSceneController")
    victim.setupImage("/Images/Victim/yang-gang-mini-yang.gif", 1189.0, 297.0, 255.0, 346.0)
    startTimer()
    createZombies(10) // Using a fixed number here for initial setup
    updateZombieLabel()
  }


  def difficultySettings(): Unit = {
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
    if (!gameRunning) return

    val zombiesLeft = difficulty.maxZombies - currentZombieCount

    if (zombiesLeft > 0) {
      val (normalZombieCount, speedZombieCount, defenseZombieCount) = difficulty.level match {
        case "Easy" =>
          val normalZombies = math.min(zombieNum, zombiesLeft)
          (normalZombies, 0, 0)
        case "Normal" =>
          val normalZombies = math.min(zombieNum / 2, zombiesLeft / 2)
          val speedZombies = math.min(zombieNum - normalZombies, zombiesLeft - normalZombies)
          (normalZombies, speedZombies, 0)
        case "Hard" =>
          val normalZombies = math.min(zombieNum / 3, zombiesLeft / 3)
          val speedZombies = math.min(zombieNum / 3, zombiesLeft / 3)
          val defenseZombies = math.min(zombieNum - normalZombies - speedZombies, zombiesLeft - normalZombies - speedZombies)
          (normalZombies, speedZombies, defenseZombies)
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
      scoreManager.incrementScore()
      checkGameOver()
  }

  private def checkGameOver(timeRanOut: Boolean = false): Unit = {
    if (healthPoint.progress.value <= 0 && gameRunning) {
      gameRunning = false
      timer.foreach(_.stop())
      stopAllZombies()
      MainApp.showEndGameScene(healthPoint.progress.value, scoreManager.getScore)
    } else if ((scoreManager.getScore >= difficulty.maxZombies || timeRanOut) && gameRunning) {
      gameRunning = false
      timer.foreach(_.stop())
      stopAllZombies()
      MainApp.showEndGameScene(healthPoint.progress.value, scoreManager.getScore)
    }
  }

  private def startTimer(): Unit = {
    val time = new Timer(totalTime, timerLabel, difficulty.spawnZombieTime)
    this.timer = Some(time)
    time.start(() => createZombies(difficulty.spawnZombieNum), () => checkGameOver(true))
    }

  private def stopAllZombies(): Unit = {
    zombieController.foreach(_.stopAllZombies())
    gameArea.children.clear()
  }

}
