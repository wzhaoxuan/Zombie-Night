package what.game.to.model

import scalafx.animation.{KeyFrame, Timeline}
import scalafx.scene.control.Label
import scalafx.util.Duration



class Timer(totalTime: Int, timerLabel: Label, spawnZombieTime: Int, createZombies: () => Unit) {
  private var remainingTime = totalTime

  def start(): Unit = {
    val timeline = new Timeline {
      cycleCount = Timeline.Indefinite
      keyFrames = Seq(
        KeyFrame(Duration(700), onFinished = _ => {
          remainingTime -= 1
          timerLabel.text = remainingTime.toString
        })
      )
    }

    val spawnTimeLine = new Timeline{
      cycleCount = Timeline.Indefinite
      keyFrames = Seq(
        KeyFrame(Duration(spawnZombieTime * 700), onFinished = _ => {
          createZombies()
        })
      )
      println(keyFrames)
    }
    timeline.play()
    spawnTimeLine.play()
  }
}
