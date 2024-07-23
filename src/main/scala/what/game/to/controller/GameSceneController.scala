package what.game.to.controller
import  what.game.to.model.Zombie
import scalafx.scene.control.Label
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.util.Duration

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
    startTimer()
    createZombies(zombieNumber)
  }

  private def createZombies(zombieNum: Int): Unit = {
    val zombie = new Zombie(gameArea, handleZombieClick)
      zombie.createZombies(zombieNum)
    }

  private def handleZombieClick(): Unit = {
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