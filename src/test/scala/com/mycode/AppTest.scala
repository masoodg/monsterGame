
package com.mycode


import org.scalatest._

class AppTest extends FlatSpec with Matchers {

  "An initialized game" should "have proper initialized values" in {

    App.newGame(Option("mynickname"))
    assert(App.nickname == "mynickname")
    assert(App.initialBoard.gameBoard.length == App.initialRow)
  }

  "Move commands" should "move the position of player" in {

    App.newGame(Option("testCharacter"))
    App.runGame(Option(Array("d", "s", "d", "save", "d", "d", "exit")))
    assert(App.currentBoard.currentPosition() == (1, 4))

  }

  "Load a game" should "load the correct state" in {
    App.startMenu(Option("2", null))
    assert(App.currentBoard.currentPosition() == (1, 2))

  }

  "game state" should "get won, when score is enough" in {

    App.score = 100
    App.updateStatus()
    assert(App.gameState == "WON")

  }

  "game state" should "get game_over, when score is few" in {

    App.score = -100
    App.updateStatus()
    assert(App.gameState == "GAME_OVER")

  }
}
	