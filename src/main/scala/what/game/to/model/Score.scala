package what.game.to.controller

import scalafx.scene.control.Label
import what.game.to.MainApp
import what.game.to.model.Player

class Score(private val scoreLabel: Label) {
  private var score: Int = 0
  val player: Player = MainApp.currentPlayer
  def getScore: Int = score

  def incrementScore(): Unit = {
    score += 1
    player.recordZombiesKilled(score)
    player.save()
    updateScoreLabel()
  }

  private def updateScoreLabel(): Unit = {
    scoreLabel.text = s"Score: $score"
  }
}
