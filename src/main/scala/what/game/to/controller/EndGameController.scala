package what.game.to.controller

import scalafx.scene.control.{Button, Label}
import scalafxml.core.macros.sfxml
import what.game.to.MainApp

@sfxml
class EndGameController(
                         private val statusLabel: Label,
                         private val healthStatus: Label,
                         private val zombieKilled: Label,
                         private val playAgain: Button,
                         private val exit: Button
                       ){

  def showVictory(healthProgress: Double, score: Int): Unit = {
    val healthPercentage = (healthProgress * 100).toInt
    statusLabel.text = "Victory!!"
    healthStatus.text = s"Health Remaining: $healthPercentage"
    zombieKilled.text = s"Zombies Killed: $score"
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




}