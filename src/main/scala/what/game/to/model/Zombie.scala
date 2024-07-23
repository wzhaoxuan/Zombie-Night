package what.game.to.model

import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.AnchorPane
import scalafx.scene.input.MouseEvent

import scala.util.Random
import scalafx.Includes._
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.util.Duration

abstract class Zombie(gameArea: AnchorPane, onZombieClicked: () => Unit) {
  def imagePath: String
  def zombieWidth: Int
  def zombieHeight: Int
  def speed: Int
  def requiredClicks: Int


    def createZombies(zombieCount: Int): Unit = {
      println("Creating zombies")

      for (_ <- 0 until zombieCount) {
        val zombie = new ImageView()
        zombie.getStyleClass.add("ImageView") // Add a style class to the ImageView
        zombie.image = new Image(imagePath)

        // Set a random position within the game area
        val randomX = Random.nextDouble() * (gameArea.width.value - zombieWidth)
        val startX = -zombieWidth - Random.nextInt(700)
        zombie.layoutX = startX
        zombie.layoutY = 350
        println(s"Zombie created at ($startX, 300)")

        var remainingClicks = requiredClicks

        // Event Handler to count Zombie Click
        zombie.onMouseClicked = (e: MouseEvent) => {
          remainingClicks -= 1
          if (remainingClicks <= 0){
            gameArea.children.remove(zombie)
            onZombieClicked()
          }
        }

        gameArea.children.add(zombie)

        // Animation for zombie moving to right
        val timeline = new Timeline{
          cycleCount = Timeline.Indefinite // infinite number of cycles
          keyFrames = Seq({
            KeyFrame(Duration(30), onFinished = _ => {
              if (zombie.layoutX.value + zombieWidth < gameArea.width.value) {
                zombie.layoutX.value += speed // moving speed
              }
            })
          })
        }
        timeline.play()
      }
    }
}


// Normal Zombie
class NormalZombie(_gameArea: AnchorPane, _onZombieClicked: () => Unit) extends Zombie(_gameArea, _onZombieClicked){
  override def imagePath = "/Images/SpeedZombie.gif"
  override def zombieHeight = 200
  override def zombieWidth = 200
  override def speed = 6
  override def requiredClicks = 2
}

// Speed Zombie
class SpeedZombie(_gameArea: AnchorPane, _onZombieClicked: () => Unit) extends Zombie(_gameArea, _onZombieClicked){
  override def imagePath = "/Images/DefenseZombie.gif"
  override def zombieHeight = 200
  override def zombieWidth = 200
  override def speed = 10
  override def requiredClicks = 1
}
