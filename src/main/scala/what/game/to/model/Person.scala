package what.game.to.model

import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.AnchorPane

class Person(private val gameArea: AnchorPane) {
  private val imageView = new ImageView()

  def setupImage(imagePath: String, layoutX: Double, layoutY: Double, fitWidth: Double, fitHeight: Double): Unit = {
    imageView.image = new Image(imagePath)
    imageView.layoutX = layoutX
    imageView.layoutY = layoutY
    imageView.fitWidth = fitWidth
    imageView.fitHeight = fitHeight
    gameArea.children.add(imageView)
  }


  def getImageView: ImageView = imageView
}
