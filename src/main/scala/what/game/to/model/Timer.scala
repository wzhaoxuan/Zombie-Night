package what.game.to.model

import scalafx.animation.{KeyFrame, Timeline}
import scalafx.scene.control.Label
import scalafx.util.Duration
import scalafx.Includes._


class Timer(totalTime: Int, timerLabel: Label, createZombies: () => Unit) {
  private var remainingTime = totalTime
  private val spawnZombieTime = 5

  def start(): Unit = {
    val timeline = new Timeline {
      cycleCount = totalTime
      keyFrames = Seq(
        KeyFrame(Duration(700), onFinished = _ => {
          remainingTime -= 1
          timerLabel.text = remainingTime.toString
          if(remainingTime % spawnZombieTime == 0){
            createZombies()
          }
        })
      )
    }
    timeline.play()
  }
}
