package what.game.to.model

import scalafx.animation.{KeyFrame, Timeline}
import scalafx.scene.control.Label
import scalafx.util.Duration

class Timer(totalTime: Int, timerLabel: Label, spawnZombieTime: Int, createZombies: () => Unit) {
  private var remainingTime = totalTime
  private var gameRunning = true // Flag to check if the game is running
  private var timeLine: Timeline = _
  private var spawnTimeLine: Timeline = _

  def start(): Unit = {
    timeLine= new Timeline {
      cycleCount = Timeline.Indefinite
      keyFrames = Seq(
        KeyFrame(Duration(700), onFinished = _ => {
          if (gameRunning) {
            remainingTime -= 1
            timerLabel.text = remainingTime.toString
            if (remainingTime <= 0) {
              timeUp()
            }
          }
        })
      )
}
      spawnTimeLine = new Timeline {
        cycleCount = Timeline.Indefinite
        keyFrames = Seq(
          KeyFrame(Duration(spawnZombieTime * 700), onFinished = _ => {
            if (gameRunning) {
              createZombies()
            }
          })
        )
      }

      timeLine.play()
      spawnTimeLine.play()
    }

      private def timeUp() = {
        gameRunning = false
        // Optional: You can stop the timelines here if needed
        timeLine.stop()
        spawnTimeLine.stop()

  }
}