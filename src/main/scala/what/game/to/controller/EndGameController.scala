package what.game.to.controller

import scalafx.scene.control.{Button, Label}
import scalafxml.core.macros.sfxml
import what.game.to.MainApp
import what.game.to.model.Player

@sfxml
class EndGameController(
                         private val statusLabel: Label,
                         private val healthStatus: Label,
                         private val zombieKilled: Label,
                         private val playAgain: Button,
                         private val exit: Button,
                         private val leaderBoard: Button
                       ){
  val player: Player = MainApp.currentPlayer

  def showVictory(healthProgress: Double, score: Int): Unit = {
    val healthPercentage = (healthProgress * 100).toInt
    statusLabel.text = "Victory!!"
    healthStatus.text = s"Health Remaining: $healthPercentage%"
    zombieKilled.text = s"Zombies Killed: $score"
    player.recordZombiesKilled(score)
    player.save()
  }

  def gameOver(healthProgress: Double, score: Int): Unit = {
    val healthPercentage = (healthProgress * 100).toInt
    statusLabel.text = "Death"
    healthStatus.text = s"Health Remaining: $healthPercentage%"
    zombieKilled.text = s"Zombies Killed: $score"
  }


  def handlePlayAgain(): Unit = {
    MainApp.showModeScene()
  }

  def handleExit(): Unit = {
    System.exit(0)
  }

  def handleLeaderBoard(): Unit = {
    MainApp.showLeaderBoardScene()
  }
}