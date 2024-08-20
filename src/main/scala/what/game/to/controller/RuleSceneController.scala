package what.game.to.controller

import what.game.to.util.Difficulty
import what.game.to.MainApp
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml
import what.game.to.model.ZombieInfo

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

  private def setZombieInfo(imageView: ImageView, zombieInfo: ZombieInfo, infoLabel: Label): Unit = {
    imageView.setImage(new Image(zombieInfo.imagePath))
    infoLabel.text = s"${zombieInfo.zombieName} \n Speed: ${zombieInfo.zombieSpeed} " +
      s"\n Clicks: ${zombieInfo.zombieRequiredClicks} \n Damage: ${zombieInfo.zombieDamage}"
  }

  private def clearZombieInfo(imageView: ImageView, infoLabel: Label): Unit = {
    imageView.setImage(null)
    infoLabel.text = null
  }


  def setDifficulty(difficulty: Difficulty): Unit = {
      difficulty.level match {
        case "Easy" =>
          setZombieInfo(normalZombieImage, ZombieInfo.normalZombie, normalZombieInfo)
          clearZombieInfo(speedZombieImage,speedZombieInfo)
          clearZombieInfo(defenseZombieImage,defenseZombieInfo)

        case "Normal" =>
          setZombieInfo(normalZombieImage, ZombieInfo.normalZombie, normalZombieInfo)
          setZombieInfo(speedZombieImage, ZombieInfo.speedZombie, speedZombieInfo)
          clearZombieInfo(defenseZombieImage,defenseZombieInfo)

        case "Hard" =>
          setZombieInfo(normalZombieImage, ZombieInfo.normalZombie, normalZombieInfo)
          setZombieInfo(speedZombieImage, ZombieInfo.speedZombie, speedZombieInfo)
          setZombieInfo(defenseZombieImage, ZombieInfo.defenseZombie, defenseZombieInfo)

      }
    description.text = difficulty.description
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