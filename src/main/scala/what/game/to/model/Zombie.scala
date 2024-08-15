package what.game.to.model
import what.game.to.controller.ZombieController
import scalafx.scene.layout.AnchorPane
import scalafx.scene.control.ProgressBar

trait ZombieInfo{
  def imagePath: String
  def zombieName: String
  def zombieSpeed: Int
  def zombieRequiredClicks: Int
  def zombieDamage: Int
}

abstract class Zombie(val imagePath: String,
                      val zombieWidth: Int,
                      val zombieHeight: Int,
                      val speed: Int,
                      val requiredClicks: Int,
                      val attackDamage: Int,
                      val layoutY: Int) {
  def createController(gameArea: AnchorPane, onZombieClicked: () => Unit, victim: Person, healthPoint: ProgressBar,
                        checkGameOver: () => Unit, gameRunning: Boolean): ZombieController

}



// Normal Zombie
class NormalZombie(_imagePath: String,
                   _zombieWidth: Int,
                   _zombieHeight: Int,
                   _speed: Int,
                   _requiredClicks: Int,
                   _attackDamage: Int,
                   _layoutY: Int) extends Zombie(
  _imagePath,
  _zombieWidth,
  _zombieHeight,
  _speed,
  _requiredClicks,
  _attackDamage,
  _layoutY
) {
  override def createController(gameArea: AnchorPane, onZombieClicked: () => Unit, victim: Person, healthPoint: ProgressBar, checkGameOver: () => Unit, gameRunning: Boolean): ZombieController = {
    new ZombieController(gameArea, onZombieClicked, victim, healthPoint, checkGameOver, gameRunning)
  }
}

object NormalZombie extends ZombieInfo {
  val imagePath = "/Images/Zombie/NormalZombie.gif"
  val zombieName = "Normal Zombie"
  val zombieSpeed = 4
  val zombieRequiredClicks = 3
  val zombieDamage = 2
}

// Speed Zombie
class SpeedZombie(_imagePath: String,
                  _zombieWidth: Int,
                  _zombieHeight: Int,
                  _speed: Int,
                  _requiredClicks: Int,
                  _attackDamage: Int,
                  _layoutY: Int) extends Zombie(
  _imagePath,
  _zombieWidth,
  _zombieHeight,
  _speed,
  _requiredClicks,
  _attackDamage,
  _layoutY
) {
  override def createController(gameArea: AnchorPane, onZombieClicked: () => Unit, victim: Person, healthPoint: ProgressBar, checkGameOver: () => Unit, gameRunning: Boolean): ZombieController = {
    new ZombieController(gameArea, onZombieClicked, victim, healthPoint, checkGameOver, gameRunning)
  }
}

object SpeedZombie extends ZombieInfo {
  val imagePath = "/Images/Zombie/SpeedZombie.gif"
  val zombieName = "Speed Zombie"
  val zombieSpeed = 8
  val zombieRequiredClicks =2
  val zombieDamage = 1
}


// Defense Zombie
class DefenseZombie (_imagePath: String, _zombieWidth: Int, _zombieHeight: Int, _speed: Int, _requiredClicks: Int,
                     _attackDamage: Int, _layoutY: Int)
  extends Zombie(_imagePath, _zombieWidth, _zombieHeight, _speed, _requiredClicks, _attackDamage, _layoutY
) {
  override def createController(gameArea: AnchorPane, onZombieClicked: () => Unit, victim: Person, healthPoint: ProgressBar, checkGameOver: () => Unit, gameRunning: Boolean): ZombieController = {
    new ZombieController(gameArea, onZombieClicked, victim, healthPoint, checkGameOver, gameRunning)
  }
}

object DefenseZombie extends ZombieInfo {
  val imagePath = "/Images/Zombie/DefenseZombie.gif"
  val zombieName = "Defense Zombie"
  val zombieSpeed = 2
  val zombieRequiredClicks = 5
  val zombieDamage = 3
}

