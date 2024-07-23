package what.game.to.model

import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.AnchorPane
import scalafx.scene.input.MouseEvent

import scala.util.Random
import scalafx.Includes._
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.util.Duration

class Zombie(gameArea: AnchorPane, onZombieClicked: () => Unit) {

  def createZombies(zombieCount: Int): Unit = {
    println("Creating zombies")
    val zombieWidth = 300
    val zombieHeight = 300

    for (_ <- 0 until zombieCount) {
      val zombie = new ImageView()
      zombie.getStyleClass.add("ImageView") // Add a style class to the ImageView
      zombie.image = new Image("/Images/zombie.gif")
      // Set a random position within the game area
      val randomX = Random.nextDouble() * (gameArea.width.value - zombieWidth)
      zombie.layoutX = randomX
      zombie.layoutY = 350

      println(s"Zombie created at ($randomX, 300)")
      // Event Handler to count Zombie Click
      zombie.onMouseClicked = (e: MouseEvent) => {
        gameArea.children.remove(zombie)
        onZombieClicked()
      }
      gameArea.children.add(zombie)

      // Animation for zombie moving to right
      val timeline = new Timeline{
        cycleCount = Timeline.Indefinite // infinite number of cycles
        keyFrames = Seq({
          KeyFrame(Duration(30), onFinished = _ => {
            if (zombie.layoutX.value + zombieWidth < gameArea.width.value) {
              zombie.layoutX.value += 2 // moving speed
            }
          })
        })
      }
      timeline.play()
    }
  }
}
