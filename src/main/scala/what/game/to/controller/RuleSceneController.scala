package what.game.to.controller

import scalafx.event.ActionEvent
import what.game.to.MainApp
import scalafx.scene.control.{Button, Label}
import scalafxml.core.macros.sfxml

@sfxml
class RuleSceneController(
                         private val description: Label,
                         private val startButton: Button
                         ){

  def difficultyDescription(difficulty: String): Unit = {
    val descriptionText: String = {
      difficulty match {
        case "Easy" => "EasyMode: Given a 120-second duration, your goal is to rescue the person from being " +
          "killed by zombies by shooting them with clicks. There are total of 50 zombies appears in this mode" +
          "and 3 zombie spawning in every 5 seconds. Understanding the characteristics of these zombie " +
          "types is essential for effective gameplay."


        case "Normal" => "NormalMode: Given a 120-second duration, your goal is to rescue the person from being " +
          "killed by zombies by shooting them with clicks. There are total of 150 zombies appears in this mode " +
          "and 5 zombie spawning in every 10 seconds. There are two types of zombies, each with its own attributes: " +
          "Normal Zombies and Speed Zombies. Understanding the characteristics of these zombie types is " +
          "essential for effective gameplay."

        case "Hard" => "HardMode: Given a 120-second duration, your goal is to rescue the person from being " +
          "killed by zombies by shooting them with clicks. There are total of 200 zombies appears in this mode " +
          "and 8 zombie spawning in every 15 seconds. There are three types of zombies, each with its own attributes: " +
          "Normal Zombies, Speed Zombies and Defense Zombies. Understanding the characteristics of these zombie types is " +
          "essential for effective gameplay."
      }
    }

    description.text = descriptionText
  }


  // Define a method to handle the button click event
  def handleStartButton(e: ActionEvent): Unit = {
    MainApp.showGameScene()
  }


}