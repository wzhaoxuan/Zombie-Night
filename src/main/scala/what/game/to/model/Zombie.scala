package what.game.to.model

import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.AnchorPane
import scalafx.scene.input.MouseEvent
import scalafx.scene.control.ProgressBar
import scalafx.util.Duration

import scala.util.Random
import scalafx.Includes._
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.scene.media.{Media, MediaPlayer}

import scala.collection.mutable.ListBuffer

trait ZombieInfo{
  def imagePath: String
  def zombieName: String
  def zombieSpeed: Int
  def zombieRequiredClicks: Int
  def zombieDamage: Int
}

abstract class Zombie(gameArea: AnchorPane, onZombieClicked: () => Unit, victim: Person,
                      healthPoint: ProgressBar, checkGameOver: () => Unit, var gameRunning:Boolean) {
  def imagePath: String
  def zombieWidth: Int
  def zombieHeight: Int
  def speed: Int
  def requiredClicks: Int
  def attackDamage: Int
  def layoutY: Int

  private val soundPath = getClass.getResource("/SoundEffect/shotgun.mp3").toExternalForm
  private val clickSound = new Media(soundPath)
  private val clickPlayer = new MediaPlayer(clickSound)

  private var zombieTimeline: ListBuffer[Timeline] = ListBuffer()

  def createZombies(zombieCount: Int): Unit = {
    println("Creating zombies")
    println(gameArea.width.value)

    if(!gameRunning) return

    for (_ <- 0 until zombieCount) {
      println(zombieCount)
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
              if (zombie.layoutX.value + zombieWidth < gameArea.width.value) {
                val zombieMovement = zombie.layoutX.value + speed
                if ((zombieMovement + zombie.fitWidth.toInt) <= (gameArea.width.value - victim.getImageView.fitWidth.value + 50)) {
                  zombie.layoutX.value += speed // move at the defined speed
                }
                println(zombie.layoutX.value + zombieWidth + ">=" + victim.getImageView.layoutX.value)
                if (zombie.layoutX.value + zombieWidth >= victim.getImageView.layoutX.value) {
                  if (remainingClicks > 0) {
                    reduceHealth()
                    print("Reduce Health")
                  }
                }
              }
            }
          })
        })
      }
      zombieTimeline += timeline
      timeline.play()
    }
  }

  private def reduceHealth(): Unit = {
    println(gameRunning)
    if(gameRunning){
      val newHealth = healthPoint.progress - attackDamage * 0.0005 // Adjust the decrement factor as needed
      newHealth.onChange((_, _, newvalue) => {
        println(s"Health: ${newvalue}" )
      })
      healthPoint.progress = math.max(newHealth.toDouble, 0)
      if (healthPoint.progress.value <= 0) {
        checkGameOver()
      }
    }
  }

  def stopAllZombies(): Unit = {
    zombieTimeline.foreach(_.stop()) // Stop all timelines
    gameArea.children.clear()
    zombieTimeline.clear()
  }
}



// Normal Zombie
class NormalZombie(_gameArea: AnchorPane, _onZombieClicked: () => Unit, _victim: Person,
                   _healthPoint: ProgressBar, _checkGameOver: () => Unit, _gameRunning:Boolean)
  extends Zombie(_gameArea, _onZombieClicked, _victim, _healthPoint,_checkGameOver, _gameRunning){
  override def imagePath: String = NormalZombie.imagePath
  override def zombieWidth = 300
  override def zombieHeight = 300
  override def speed: Int = NormalZombie.zombieSpeed
  override def requiredClicks: Int = NormalZombie.zombieRequiredClicks
  override def attackDamage: Int = NormalZombie.zombieDamage
  override def layoutY = 360
}

object NormalZombie extends ZombieInfo {
  val imagePath = "/Images/Zombie/NormalZombie.gif"
  val zombieName = "Normal Zombie"
  val zombieSpeed = 4
  val zombieRequiredClicks = 3
  val zombieDamage = 2
}

// Speed Zombie
class SpeedZombie(_gameArea: AnchorPane, _onZombieClicked: () => Unit, _victim: Person,
                  _healthPoint: ProgressBar, _checkGameOver: () => Unit, _gameRunning:Boolean)
  extends Zombie(_gameArea, _onZombieClicked, _victim, _healthPoint,_checkGameOver, _gameRunning){
  override def imagePath: String = SpeedZombie.imagePath
  override def zombieWidth = 250
  override def zombieHeight = 200
  override def speed: Int = SpeedZombie.zombieSpeed
  override def requiredClicks: Int = SpeedZombie.zombieRequiredClicks
  override def attackDamage: Int = SpeedZombie.zombieDamage
  override def layoutY = 450
}

object SpeedZombie extends ZombieInfo {
  val imagePath = "/Images/Zombie/SpeedZombie.gif"
  val zombieName = "Speed Zombie"
  val zombieSpeed = 8
  val zombieRequiredClicks =2
  val zombieDamage = 1
}

class DefenseZombie(_gameArea: AnchorPane, _onZombieClicked: () => Unit, _victim: Person,
                    _healthPoint: ProgressBar, _checkGameOver: () => Unit, _gameRunning:Boolean)
  extends Zombie(_gameArea, _onZombieClicked, _victim, _healthPoint,_checkGameOver, _gameRunning){
  override def imagePath: String = DefenseZombie.imagePath
  override def zombieWidth = 300
  override def zombieHeight = 400
  override def speed: Int = DefenseZombie.zombieSpeed
  override def requiredClicks: Int = DefenseZombie.zombieRequiredClicks
  override def attackDamage: Int = DefenseZombie.zombieDamage
  override def layoutY = 280
}

object DefenseZombie extends ZombieInfo {
  val imagePath = "/Images/Zombie/DefenseZombie.gif"
  val zombieName = "Defense Zombie"
  val zombieSpeed = 2
  val zombieRequiredClicks = 5
  val zombieDamage = 3
}
