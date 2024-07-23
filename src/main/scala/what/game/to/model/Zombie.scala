package what.game.to.model

import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.AnchorPane
import scalafx.scene.input.MouseEvent
import scala.util.Random
import scalafx.Includes._

class Zombie(gameArea: AnchorPane, onZombieClicked: () => Unit) {

  def createZombies(zombieCount: Int): Unit = {
    println("Creating zombies")
    val zombieWidth = 300
    val zombieHeight = 300

    for (_ <- 1 to zombieCount) {
      val zombie = new ImageView()
      zombie.getStyleClass.add("ImageView") // Add a style class to the ImageView
      zombie.image = new Image("/Images/zombie.gif")

      // Set a random position within the game area
      val randomX = Math.abs(Random.nextDouble() * (gameArea.width.value - zombieWidth))
      val randomY = Math.abs(Random.nextDouble() * (gameArea.height.value - zombieHeight))
      zombie.layoutX = randomX
      zombie.layoutY = randomY

      println(s"Zombie created at ($randomX, $randomY)")
      // Event Handler to count Zombie Click
      zombie.onMouseClicked = (e: MouseEvent) => {
        gameArea.children.remove(zombie)
        onZombieClicked()
      }
      gameArea.children.add(zombie)
    }
  }
}
