package what.game.to.model


case class ZombieInfo(imagePath: String, zombieName: String, zombieSpeed: Int, zombieRequiredClicks: Int,
                      zombieDamage: Int)

object ZombieInfo {
  val normalZombie: ZombieInfo = ZombieInfo("/Images/Zombie/NormalZombie.gif", "Normal Zombie", 4, 3, 2)
  val speedZombie: ZombieInfo = ZombieInfo("/Images/Zombie/SpeedZombie.gif", "Speed Zombie", 8, 2, 1)
  val defenseZombie: ZombieInfo = ZombieInfo("/Images/Zombie/DefenseZombie.gif", "Defense Zombie", 2, 5, 3)
}

abstract class Zombie(val zombieInfo: ZombieInfo, val zombieWidth: Int, val zombieHeight: Int, val layoutY: Int)

class NormalZombie(_zombieWidth: Int, _zombieHeight: Int, _layoutY: Int)
  extends Zombie(ZombieInfo.normalZombie, _zombieWidth, _zombieHeight, _layoutY)

class SpeedZombie(_zombieWidth: Int, _zombieHeight: Int, _layoutY: Int)
  extends Zombie(ZombieInfo.speedZombie, _zombieWidth, _zombieHeight, _layoutY)

class DefenseZombie(_zombieWidth: Int, _zombieHeight: Int, _layoutY: Int)
  extends Zombie(ZombieInfo.defenseZombie, _zombieWidth, _zombieHeight, _layoutY)
8