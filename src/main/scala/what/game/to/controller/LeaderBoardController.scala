package what.game.to.controller


import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{Button, TableColumn, TableView}
import scalafx.beans.binding.Bindings
import scalafxml.core.macros.sfxml
import what.game.to.model.Player

@sfxml
class LeaderBoardController(
                             val playerTable: TableView[Player],
                             val nameColumn: TableColumn[Player, String],
                             val levelColumn: TableColumn[Player, String],
                             val timeColumn: TableColumn[Player, Int],
                             val zombieKilledColumn: TableColumn[Player, Int],
                             val victimHealthColumn: TableColumn[Player, Int],
                             val backButton: Button
                           ) {
  private val playerScores: ObservableBuffer[Player] = ObservableBuffer[Player]()

  // Update playerScores according to values from the database
  playerScores ++= Player.getAllPlayers
  playerTable.items = playerScores

  // Initialize columns' cell values
  nameColumn.cellValueFactory = { cellData =>
    cellData.value.name
  }

  levelColumn.cellValueFactory = { cellData =>
    Bindings.createObjectBinding[String](() => cellData.value.difficulty.value, cellData.value.difficulty)
  }

  timeColumn.cellValueFactory = { cellData =>
    Bindings.createObjectBinding[Int](() => cellData.value.timer.value, cellData.value.timer)
  }

  zombieKilledColumn.cellValueFactory = { cellData =>
    Bindings.createObjectBinding[Int](() => cellData.value.zombiesKilled.value, cellData.value.zombiesKilled)
  }

//  victimHealthColumn.cellValueFactory = { cellData =>
//    Bindings.createObjectBinding[Int](() => cellData.value.victimHealth.value, cellData.value.victimHealth)
//  }
}


