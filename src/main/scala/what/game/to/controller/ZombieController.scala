package what.game.to.controller
import what.game.to.model.{Person, Zombie}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.AnchorPane
import scalafx.scene.input.MouseEvent
import scalafx.scene.control.ProgressBar
import scalafx.util.Duration

import scala.util.Random
import scalafx.Includes._
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.collections.ObservableBuffer
import scalafx.scene.media.{Media, MediaPlayer}
import what.game.to.model.Person

class ZombieController(
                        val gameArea: AnchorPane,
                        val onZombieClicked: () => Unit,
                        val victim: Person,
                        val healthPoint: ProgressBar,
                        val checkGameOver: () => Unit,
                        var gameRunning: Boolean
                      ) {
  private val timelines = ObservableBuffer[Timeline]()

  def createZombies(zombies: List[Zombie]): Unit = {
    println("Creating zombies")
    println(gameArea.width.value)

    if (!gameRunning) return

    zombies.foreach { zombieInfo =>
      val zombie = new ImageView()
      zombie.getStyleClass.add("ImageView") // Add a style class to the ImageView
      zombie.image = new Image(zombieInfo.imagePath)
      zombie.fitWidth = zombieInfo.zombieWidth
      zombie.fitHeight = zombieInfo.zombieHeight

      val startX = -zombieInfo.zombieWidth - Random.nextInt(600)
      zombie.layoutX = startX
      zombie.layoutY = zombieInfo.layoutY
      println(s"Zombie created at ($startX, ${zombieInfo.layoutY})")

      var remainingClicks = zombieInfo.requiredClicks
      // Event Handler to count Zombie Click
      zombie.onMouseClicked = (_: MouseEvent) => {
        val clickPlayer = createClickSound()
        clickPlayer.stop()
        clickPlayer.play()
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
            println("Timeline running")
            if (gameRunning) {
              if (zombie.layoutX.value + zombieInfo.zombieWidth < gameArea.width.value) {
                val zombieMovement = zombie.layoutX.value + zombieInfo.speed
                if ((zombieMovement + zombie.fitWidth.toInt) <= (gameArea.width.value - victim.getImageView.fitWidth.value + 50)) {
                  zombie.layoutX.value += zombieInfo.speed // move at the defined speed
                }
                println(zombie.layoutX.value + zombieInfo.zombieWidth + ">=" + victim.getImageView.layoutX.value)
                if (zombie.layoutX.value + zombieInfo.zombieWidth >= victim.getImageView.layoutX.value) {
                  if (remainingClicks > 0) {
                    reduceHealth(zombieInfo.attackDamage)
                    print("Reduce Health")
                  }
                }
              } else {
                stopAllZombies() // Ensure the zombie stops if the game is not running
              }
            } else {
              stopAllZombies() // Ensure the zombie stops if the game is not running
            }
          })
        })
      }
      timelines += timeline
      timeline.play()
    }
  }

  private def createClickSound(): MediaPlayer = {
    val soundPath = getClass.getResource("/SoundEffect/shotgun.mp3").toExternalForm
    val clickSound = new Media(soundPath)
    new MediaPlayer(clickSound)
  }

  private def reduceHealth(damage: Int): Unit = {
    println(gameRunning)
    if (gameRunning) {
      val newHealth = healthPoint.progress - damage * 0.0005 // Adjust the decrement factor as needed
      newHealth.onChange((_, _, newvalue) => {
        println(s"Health: ${newvalue}")
      })
      healthPoint.progress = math.max(newHealth.toDouble, 0)
      if (healthPoint.progress.value <= 0) {
        checkGameOver()
      }
    }
  }

  def stopAllZombies(): Unit = {
    if (gameRunning) {
      gameRunning = false
      timelines.foreach(_.stop()) // Stop all timelines
      gameArea.children.clear()
    }
  }
}
