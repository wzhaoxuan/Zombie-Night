package what.game.to.util

import scalikejdbc._
import what.game.to.model.Player


trait Database {
  private val derbyDriverClassname: String = "org.apache.derby.jdbc.EmbeddedDriver"

  private val dbURL: String = "jdbc:derby:myDB;create=true;";
  // initialize JDBC driver & connection pool
  Class.forName(derbyDriverClassname)

  ConnectionPool.singleton(dbURL, "me", "mine")

  // ad-hoc session provider on the REPL
  implicit val session: AutoSession.type = AutoSession

}

object Database extends Database {
  def setupDB(): AnyVal = {
    if (!hasDBInitialize)
      Player.initializeTable()
  }

  private def hasDBInitialize: Boolean = {
    DB.getTable("Player") match {
      case Some(x) => true
      case None => false
    }
  }
}