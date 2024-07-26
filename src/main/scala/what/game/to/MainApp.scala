package what.game.to

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.scene.layout.AnchorPane
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import what.game.to.controller.{EndGameController, GameSceneController, ModeSceneController, RuleSceneController}
import scalafx.Includes._
import scalafx.stage.{Modality, Stage, StageStyle}

object MainApp extends JFXApp {
  // Variable to store difficulty level
  private var difficulty: String = "Easy"

  // Load RootLayout.fxml
  val rootResource = getClass.getResource("WelcomeScene.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load()
  var rootLayout: AnchorPane = loader.getRoot[javafx.scene.layout.AnchorPane] // Convert to ScalaFX AnchorPane

  // Set up the primary stage
  stage = new PrimaryStage {
    title = "ZombieStrike"
    initStyle(StageStyle.Undecorated)
    scene = new Scene {
      root = rootLayout
    }
  }

  def showGameScene(): Unit = {
    val gameSceneResource = getClass.getResource("/what/game/to/GameScene.fxml")
    val gameSceneLoader = new FXMLLoader(gameSceneResource, NoDependencyResolver)
    gameSceneLoader.load()
    val gameSceneLayout: AnchorPane = gameSceneLoader.getRoot[javafx.scene.layout.AnchorPane] // Convert to ScalaFX AnchorPane
    stage.scene = new Scene {
      root = gameSceneLayout
    }
    val control = gameSceneLoader.getController[GameSceneController#Controller]
    control.setDifficulty(difficulty)
    control.initialize()
  }

  def showModeScene(): Unit = {
    val modeSceneResource = getClass.getResource("/what/game/to/ModeScene.fxml")
    if (modeSceneResource == null) {
      println("ModeScene.fxml not found!")
      return
    }
    val modeSceneLoader = new FXMLLoader(modeSceneResource, NoDependencyResolver)
    modeSceneLoader.load()
    val modeSceneLayout: AnchorPane = modeSceneLoader.getRoot[javafx.scene.layout.AnchorPane] // Convert to ScalaFX AnchorPane
    stage.scene = new Scene {
      root = modeSceneLayout
    }
    val control = modeSceneLoader.getController[ModeSceneController#Controller]
  }

  def showRule(): Unit = {
    val ruleSceneResource = getClass.getResource("RuleScene.fxml")
    val ruleSceneLoader = new FXMLLoader(ruleSceneResource, NoDependencyResolver)
    ruleSceneLoader.load()
    val ruleSceneLayout: AnchorPane = ruleSceneLoader.getRoot[javafx.scene.layout.AnchorPane] // Convert to ScalaFX AnchorPane
    stage.scene = new Scene {
      root = ruleSceneLayout
    }
    val control = ruleSceneLoader.getController[RuleSceneController#Controller]
    control.difficultyDescription(difficulty)

  }

  def showEndGameScene(healthProgress: Double, zombiesKilled: Int): Unit = {
    val victorySceneResource = getClass.getResource("EndGameScene.fxml")
    val victorySceneLoader = new FXMLLoader(victorySceneResource, NoDependencyResolver)
    victorySceneLoader.load()
    val victorySceneLayout: AnchorPane = victorySceneLoader.getRoot[javafx.scene.layout.AnchorPane] // Convert to ScalaFX AnchorPane
    stage.scene = new Scene {
      root = victorySceneLayout
    }
    val control = victorySceneLoader.getController[EndGameController#Controller]
    if(healthProgress <= 0){
      control.gameOver(healthProgress, zombiesKilled)
    }
    else{
      control.showVictory(healthProgress, zombiesKilled)
    }


    //    control.enterButton()

  }

  def showWelcome(): Unit = {
    val welcomeSceneResource = getClass.getResource("WelcomeScene.fxml")
    val welcomeSceneLoader = new FXMLLoader(welcomeSceneResource, NoDependencyResolver)
    welcomeSceneLoader.load()
    val welcomeSceneLayout: AnchorPane = welcomeSceneLoader.getRoot[javafx.scene.layout.AnchorPane] // Convert to ScalaFX AnchorPane
    stage.scene = new Scene {
      root = welcomeSceneLayout
    }
  }



  def setDifficulty(diff: String): Unit = {
    difficulty = diff
  }

  // Entry point
  showWelcome()
}