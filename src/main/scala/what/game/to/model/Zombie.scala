package what.game.to.model

import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.AnchorPane
import scalafx.scene.input.MouseEvent

import scala.util.Random
import scalafx.Includes._
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.scene.control.ProgressBar
import scalafx.util.Duration

abstract class Zombie(gameArea: AnchorPane, onZombieClicked: () => Unit, targetImage: ImageView, healthPoint: ProgressBar) {
  def imagePath: String
  def zombieWidth: Int
  def zombieHeight: Int
  def speed: Int
  def requiredClicks: Int
  def attackDamage: Int


    def createZombies(zombieCount: Int): Unit = {
      println("Creating zombies")

      // Define the stopping position using the ImageView's layout values
      val stopWidth = gameArea.width.value - targetImage.layoutX.value // X position of the ImageView

      for (_ <- 0 until zombieCount) {
        val zombie = new ImageView()
        zombie.getStyleClass.add("ImageView") // Add a style class to the ImageView
        zombie.image = new Image(imagePath)
        zombie.fitWidth = 350
        zombie.fitHeight = 300

        // Set a random position within the game area
//        val randomX = Random.nextDouble() * (gameArea.width.value - zombieWidth)
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
                // Check if the zombie has reached the stopping point
                val futureX = zombie.layoutX.value + speed
                if ((futureX + zombie.fitWidth.intValue()) < stopWidth) {
                  zombie.layoutX.value += speed // move at the defined speed
                } else if (zombie.layoutX.value >= targetImage.layoutX.value) {
                  reduceHealth()
                }
              }
            })
          })

          private def reduceHealth() = {
            val initialHealth = healthPoint.progress.value
            val currentHealth = Math.max(0, initialHealth - (attackDamage / 100.0))
            healthPoint.progress = currentHealth
            println(currentHealth)
          }
        }
        timeline.play()
      }
    }
}

// Normal Zombie
class NormalZombie(_gameArea: AnchorPane, _onZombieClicked: () => Unit, _targetImage: ImageView, _healthPoint: ProgressBar)
  extends Zombie(_gameArea, _onZombieClicked, _targetImage, _healthPoint){
  override def imagePath = "/Images/DefenseZombie.gif"
  override def zombieHeight = 300
  override def zombieWidth = 300
  override def speed = 4
  override def requiredClicks = 2
  override def attackDamage = 1
}

// Speed Zombie
class SpeedZombie(_gameArea: AnchorPane, _onZombieClicked: () => Unit, _targetImage: ImageView, _healthPoint: ProgressBar)
  extends Zombie(_gameArea, _onZombieClicked, _targetImage, _healthPoint){
  override def imagePath = "/Images/SpeedZombie.gif"
  override def zombieHeight = 300
  override def zombieWidth = 300
  override def speed = 10
  override def requiredClicks = 1
  override def attackDamage = 1
}
