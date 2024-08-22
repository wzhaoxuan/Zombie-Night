package what.game.to.model

import what.game.to.util.Database
import scalafx.beans.property.{IntegerProperty, StringProperty}
import scalikejdbc.{DB, scalikejdbcSQLInterpolationImplicitDef}
import scala.util.Try

class Player(var name: StringProperty) {
  var difficulty: StringProperty = StringProperty("")
  var timer: IntegerProperty = IntegerProperty(0)
  var zombiesKilled: IntegerProperty = IntegerProperty(0)

  def recordDifficulty(difficulty: String): Unit = {
    this.difficulty.value = difficulty
  }

  def recordTimer(amount: Int): Unit = {
    timer.value = amount
  }

  def recordZombiesKilled(count: Int): Unit = {
    zombiesKilled.value = count
  }


  def clear(): Unit = { // reset
    name.value = "Default"
    difficulty.value = ""
    timer.value = 0
    zombiesKilled.value = 0
  }

  def save(): Try[Int] = { // create or update player record
    if (!isExist) {
      Try(DB autoCommit { implicit session =>
        sql"""
          insert into player (name, difficulty, timer, zombies_killed)
          values (${name.value}, ${difficulty.value}, ${timer.value}, ${zombiesKilled.value})
        """.update.apply()
      })
    } else {
      Try(DB autoCommit { implicit session =>
        sql"""
          update player
          set
            difficulty = ${difficulty.value},
            timer = ${timer.value},
            zombies_killed = ${zombiesKilled.value}
          where name = ${name.value}
        """.update.apply()
      })
    }
  }

  private def isExist: Boolean = { // check if a player already exists in table
    DB readOnly { implicit session =>
      sql"""
        select * from player where
        name = ${name.value}
      """.map(rs => rs.string("name")).single.apply()
    } match {
      case Some(_) => true
      case None => false
    }
  }
}

object Player extends Database {
  def apply(name: String): Player = new Player(StringProperty(name))

  def initializeTable(): Boolean = { // create table, will only be called if table doesn't exist
    DB autoCommit { implicit session =>
      sql"""
      create table player (
        id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
        name VARCHAR(64),
        difficulty VARCHAR(64),
        timer INT,
        zombies_killed INT
      )
      """.execute.apply()
    }
  }

  def getAllPlayers: List[Player] = { // get all players other than default and sort descendingly
    DB readOnly { implicit session =>
      sql"""
        SELECT * FROM player
        WHERE name <> 'Default'
        ORDER BY timer DESC
      """.map(rs => {
        val player = Player(rs.string("name"))
        player.recordDifficulty(rs.string("difficulty"))
        player.recordTimer(rs.int("timer"))
        player.recordZombiesKilled(rs.int("zombies_killed"))
        player
      }).list.apply()
    }
  }
}
