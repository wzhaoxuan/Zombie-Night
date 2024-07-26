package what.game.to.controller

import scalafx.event.ActionEvent
import what.game.to.MainApp
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml
import what.game.to.model.{DefenseZombie, NormalZombie, SpeedZombie}

@sfxml
class RuleSceneController(
                         private val description: Label,
                         private val startButton: Button,
                         private val backButton: Button,
                         private val normalZombieImage: ImageView,
                         private val speedZombieImage: ImageView,
                         private val defenseZombieImage: ImageView,
                         private val normalZombieInfo: Label,
                         private val speedZombieInfo: Label,
                         private val defenseZombieInfo: Label
                         ){


  def difficultyDescription(difficulty: String): Unit = {
    val descriptionText: String = {
      difficulty match {
        case "Easy" =>
          normalZombieImage.setImage(new Image(NormalZombie.imagePath))
          speedZombieImage.setImage(null)
          defenseZombieImage.setImage(null)
          normalZombieInfo.text = s"${NormalZombie.zombieName} \n Speed: ${NormalZombie.zombieSpeed} " +
            s"\n Clicks: ${NormalZombie.zombieRequiredClicks} \n Damage: ${NormalZombie.zombieDamage}"
          speedZombieInfo.text = null
          defenseZombieInfo.text = null
          "EasyMode: Given a 120-second duration, your goal is to rescue the person from being " +
          "killed by zombies by shooting them with clicks. There are total of 50 zombies appears in this mode" +
          "and 3 zombie spawning in every 5 seconds. Understanding the characteristics of these zombie " +
          "types is essential for effective gameplay."


        case "Normal" =>
          normalZombieImage.setImage(new Image(NormalZombie.imagePath))
          speedZombieImage.setImage(new Image(SpeedZombie.imagePath))
          defenseZombieImage.setImage(null)
          normalZombieInfo.text = s"${NormalZombie.zombieName} \n Speed: ${NormalZombie.zombieSpeed} " +
            s"\n Clicks: ${NormalZombie.zombieRequiredClicks} \n Damage: ${NormalZombie.zombieDamage}"
          speedZombieInfo.text = s"${SpeedZombie.zombieName} \n Speed: ${SpeedZombie.zombieSpeed} " +
            s"\n Clicks: ${SpeedZombie.zombieRequiredClicks} \n Damage: ${SpeedZombie.zombieDamage}"
          defenseZombieInfo.text = null
          "NormalMode: Given a 120-second duration, your goal is to rescue the person from being " +
          "killed by zombies by shooting them with clicks. There are total of 150 zombies appears in this mode " +
          "and 5 zombie spawning in every 10 seconds. There are two types of zombies, each with its own attributes: " +
          "Normal Zombies and Speed Zombies. Understanding the characteristics of these zombie types is " +
          "essential for effective gameplay."

        case "Hard" =>
          normalZombieImage.setImage(new Image(NormalZombie.imagePath))
          speedZombieImage.setImage(new Image(SpeedZombie.imagePath))
          defenseZombieImage.setImage(new Image(DefenseZombie.imagePath))
          normalZombieInfo.text = s"${NormalZombie.zombieName} \n Speed: ${NormalZombie.zombieSpeed} " +
            s"\n Clicks: ${NormalZombie.zombieRequiredClicks} \n Damage: ${NormalZombie.zombieDamage}"
          speedZombieInfo.text = s"${SpeedZombie.zombieName} \n Speed: ${SpeedZombie.zombieSpeed} " +
            s"\n Clicks: ${SpeedZombie.zombieRequiredClicks} \n Damage: ${SpeedZombie.zombieDamage}"
          defenseZombieInfo.text = s"${DefenseZombie.zombieName} \n Speed: ${DefenseZombie.zombieSpeed} " +
            s"\n Clicks: ${DefenseZombie.zombieRequiredClicks} \n Damage: ${DefenseZombie.zombieDamage}"
          "HardMode: Given a 120-second duration, your goal is to rescue the person from being " +
          "killed by zombies by shooting them with clicks. There are total of 200 zombies appears in this mode " +
          "and 8 zombie spawning in every 15 seconds. There are three types of zombies, each with its own attributes: " +
          "Normal Zombies, Speed Zombies and Defense Zombies. Understanding the characteristics of these zombie types is " +
          "essential for effective gameplay."
      }
    }

    description.text = descriptionText
    startButton.text = "Start"
    backButton.text = "Back"
  }


  // Define a method to handle the button click event
  def handleStartButton(): Unit = {
    MainApp.showGameScene()
  }

  def handelBackButton(): Unit = {
    MainApp.showModeScene()
  }
}