package what.game.to.util

class Difficulty(var level: String) {
  var spawnZombieNum = 0
  var spawnZombieTime = 0
  var maxZombies = 0
  var description = ""
  level match {
    case "Easy" =>
      spawnZombieNum = 3
      spawnZombieTime = 5
      maxZombies = 50
      description = "EasyMode: Given a 120-second duration, your goal is to rescue the person from being " +
        "killed by zombies by shooting them with clicks. There are total of 50 zombies appears in this mode" +
        "and 3 zombie spawning in every 5 seconds. Understanding the characteristics of these zombie " +
        "types is essential for effective gameplay."
    case "Normal" =>
      spawnZombieNum = 5
      spawnZombieTime = 10
      maxZombies = 150
      description = "NormalMode: Given a 120-second duration, your goal is to rescue the person from being " +
        "killed by zombies by shooting them with clicks. There are total of 150 zombies appears in this mode " +
        "and 5 zombie spawning in every 10 seconds. There are two types of zombies, each with its own attributes: " +
        "Normal Zombies and Speed Zombies. Understanding the characteristics of these zombie types is " +
        "essential for effective gameplay."
    case "Hard" =>
      spawnZombieNum = 3
      spawnZombieTime = 15
      maxZombies = 200
      description = "HardMode: Given a 120-second duration, your goal is to rescue the person from being " +
        "killed by zombies by shooting them with clicks. There are total of 200 zombies appears in this mode " +
        "and 8 zombie spawning in every 15 seconds. There are three types of zombies, each with its own attributes: " +
        "Normal Zombies, Speed Zombies and Defense Zombies. Understanding the characteristics of these zombie types is " +
        "essential for effective gameplay."
  }
}