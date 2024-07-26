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


  def showVictory(healthProgress: Double, zombiesKilled: Int): Unit = {
    val healthPercentage = (healthProgress * 100).toInt
    statusLabel.text = "Victory!!"
    healthStatus.text = s"Health Remaining: $healthPercentage"
    zombieKilled.text = s"Zombies Killed: $zombiesKilled"

    def handlePlayAgain(): Unit = {
      MainApp.showModeScene()
    }

    def handleExit(): Unit = {
      System.exit(0)
    }
  }

  def gameOver(healthProgress: Double, zombiesKilled: Int): Unit = {
    val healthPercentage = (healthProgress * 100).toInt
    statusLabel.text = "Game Over"
    healthStatus.text = s"Health Remaining: $healthPercentage%"
    zombieKilled.text = s"Zombies Killed: $zombiesKilled"

    def handlePlayAgain(): Unit = {
      MainApp.showModeScene()
    }

    def handleExit(): Unit = {
      System.exit(0)
    }
  }








}