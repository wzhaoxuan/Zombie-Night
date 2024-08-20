package what.game.to.controller

import scalafx.scene.control.Label

class Score(private val scoreLabel: Label) {
  private var score: Int = 0

  def getScore: Int = score

  def incrementScore(): Unit = {
    score += 1
    updateScoreLabel()
  }

  private def updateScoreLabel(): Unit = {
    scoreLabel.text = s"Score: $score"
  }
}
