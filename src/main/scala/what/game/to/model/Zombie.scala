package what.game.to.model

import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.AnchorPane
import scalafx.scene.input.MouseEvent
import scalafx.scene.control.ProgressBar
import scalafx.util.Duration

import scala.util.Random
import scalafx.Includes._
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.geometry.Bounds


abstract class Zombie(gameArea: AnchorPane, onZombieClicked: () => Unit, targetImage: ImageView, healthPoint: ProgressBar) {
  def imagePath: String
  def zombieWidth: Int
  def zombieHeight: Int
  def speed: Int
  def requiredClicks: Int
  def attackDamage: Int
  def layoutY: Int


  def createZombies(zombieCount: Int): Unit = {
    println("Creating zombies")
    println(gameArea.width.value)

    // Define the stopping position using the ImageView's layout values
    val stopWidth = gameArea.width.value - targetImage.layoutX.value // X position of the ImageView
    println(targetImage.layoutX.value)

    for (_ <- 0 until zombieCount) {
      val zombie = new ImageView()
      zombie.getStyleClass.add("ImageView") // Add a style class to the ImageView
      zombie.image = new Image(imagePath)
      zombie.fitWidth = zombieWidth
      zombie.fitHeight = zombieHeight

      // Set a random position within the game area
      // val randomX = Random.nextDouble() * (gameArea.width.value - zombieWidth)
      val startX = -zombieWidth - Random.nextInt(600)
      zombie.layoutX = startX
      zombie.layoutY = layoutY
      println(s"Zombie created at ($startX, 300)")

      var remainingClicks = requiredClicks

      // Event Handler to count Zombie Click
      zombie.onMouseClicked = (e: MouseEvent) => {
        remainingClicks -= 1
        if (remainingClicks <= 0) {
          gameArea.children.remove(zombie)
          onZombieClicked()
        }
      }

      gameArea.children.add(zombie)

      // Animation for zombie moving to right
      val timeline = new Timeline {
        cycleCount = Timeline.Indefinite // infinite number of cycles
        keyFrames = Seq({
          KeyFrame(Duration(30), onFinished = _ => {
            if (zombie.layoutX.value + zombieWidth < gameArea.width.value) {
              // Check if the zombie has reached the stopping point
              val futureX = zombie.layoutX.value + speed
              println("Future" + futureX )
              if ((futureX + zombie.fitWidth.toInt) <= stopWidth) {
                println("Future + zombiewidth " + (futureX + zombie.fitWidth.toInt))
                zombie.layoutX.value += speed // move at the defined speed
              }
              if(zombie.layoutX.value + zombieWidth >= (gameArea.width.value - targetImage.fitWidth.value)){
                println("Zombie size: " + (zombie.layoutX.value + zombieWidth) + ">=" + (gameArea.width.value - targetImage.fitWidth.value))
                if(remainingClicks > 0){
                  reduceHealth()
                }
              }
            }
          })
        })
      }
      timeline.play()
    }
  }

  private def reduceHealth() = {
    val newHealth = healthPoint.progress - attackDamage * 0.001 // Adjust the decrement factor as needed
    healthPoint.progress = math.max(newHealth.toDouble, 0)
    if (healthPoint.progress == 0) {
      // Handle game over or health depletion scenario
      println("Game Over!")
      // Optionally, stop the game or reset
    }
  }
}



// Normal Zombie
class NormalZombie(_gameArea: AnchorPane, _onZombieClicked: () => Unit, _targetImage: ImageView, _healthPoint: ProgressBar)
  extends Zombie(_gameArea, _onZombieClicked, _targetImage, _healthPoint){
  override def imagePath = "/Images/Zombie/DefenseZombie.gif"
  override def zombieWidth = 300
  override def zombieHeight = 300
  override def speed = 4
  override def requiredClicks = 2
  override def attackDamage = 1
  override def layoutY = 350
}

// Speed Zombie
class SpeedZombie(_gameArea: AnchorPane, _onZombieClicked: () => Unit, _targetImage: ImageView, _healthPoint: ProgressBar)
  extends Zombie(_gameArea, _onZombieClicked, _targetImage, _healthPoint){
  override def imagePath = "/Images/Zombie/SpeedZombie.gif"
  override def zombieWidth = 250
  override def zombieHeight = 200
  override def speed = 10
  override def requiredClicks = 1
  override def attackDamage = 1
  override def layoutY = 450
}
