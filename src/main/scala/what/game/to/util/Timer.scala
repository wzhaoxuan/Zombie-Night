package what.game.to.util

import scalafx.animation.{KeyFrame, Timeline}
import scalafx.scene.control.Label
import scalafx.util.Duration

class Timer(totalTime: Int, timerLabel: Label, spawnZombieTime: Int) {
  private var remainingTime = totalTime
  private var gameRunning = true
  private var timeLine: Timeline = _
  private var spawnTimeLine: Timeline = _

  def start(createZombies: () => Unit, checkGameOver: () => Unit): Unit = {
    timeLine = new Timeline {
      cycleCount = Timeline.Indefinite
      keyFrames = Seq(
        KeyFrame(Duration(700), onFinished = _ => {
          if (gameRunning) {
            remainingTime -= 1
            timerLabel.text = remainingTime.toString
            if (remainingTime <= 0) {
              timeUp(checkGameOver)
            }
          }
        })
      )
    }

    spawnTimeLine = new Timeline {
      cycleCount = Timeline.Indefinite
      keyFrames = Seq(
        KeyFrame(Duration(spawnZombieTime * 700), onFinished = _ => {
          if (remainingTime > 0) {
            createZombies()
          }
        })
      )
    }

    timeLine.play()
    spawnTimeLine.play()
  }

  private def timeUp(checkGameOver: () => Unit): Unit = {
    gameRunning = false
    checkGameOver()
    stop() // Stop both timelines when time is up
  }

  def stop(): Unit = {
    gameRunning = false
    if (timeLine != null) timeLine.stop()
    if (spawnTimeLine != null) spawnTimeLine.stop()
  }
}
