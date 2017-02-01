package com.mycode

import java.io.ByteArrayInputStream

import scala.sys.process._

object App {


  var gameState = "RUNNING"
  var score, i, j = 0
  val initialRow, initialCol = 15
  val gameOverScore = -3
  val winScore = 15
  var flag = true
  var msg, nickname, colour: String = ""
  var currentBoard, initialBoard = new Board(1, 1)


  def printGame(board: Board): Unit = {
    "clear".!
    board.printBoard(nickname, score, msg)
  }

  def startMenu(manualMenu: Option[(String, String)] ): Unit = {

    val menuCommand = manualMenu match {
      case Some(x) => x
      case _ =>
        println("Welcome to MonsterBusters Game")
        println("Your mission is to fight against monsters and get 40 scores")
        println("1) New Game")
        println("2) Load Game (if exists)")
        (scala.io.StdIn.readLine("1 or 2, then hit 'enter': "), null)
    }
    val nickname = menuCommand._2

    menuCommand._1 match {

      case "1" => newGame(Option(nickname))
      case "2" =>
        currentBoard = loadGame()
        msg = "Game is loaded (if any exist)!"
        printGame(currentBoard)
      case _ =>
        println(Console.RED + "Wrong input!" + Console.RESET)
        startMenu(null)
    }

  }

  def setScore(newScore: Int): Unit = score = newScore

  def newGame(manualNickname: Option[String]): Unit = {

    println("Create your character")
    nickname = manualNickname match {
      case Some(x) => x
      case _ => scala.io.StdIn.readLine("Please enter a nickname: ")
    }
    val myCharachter = new Character(nickname)
    initialBoard = new Board(initialRow, initialCol)
    initialBoard.setPlayerPosition(i, j)
    initialBoard.gameBoard(i)(j).makeVisited()
    score = score + initialBoard.gameBoard(i)(j).winValue
    currentBoard = initialBoard
    "clear".!
    initialBoard.printBoard(nickname, score)

  }

  def loadGame(): Board = {

    import scala.io.Source
    if (new java.io.File("save.txt").exists) {
      val lines = Source.fromFile("save.txt").getLines.toArray
      score = lines(0).toInt
      nickname = lines.last
      val newBoard = new Board(lines(3).toInt, lines(4).toInt, true)
      val loadedBoard = newBoard.loadBoard(newBoard, lines)
      i = lines(1).toInt
      j = lines(2).toInt
      loadedBoard
    } else {
      currentBoard
    }

  }

  def movePlayer(board: Board, newX: Int, newY: Int): Unit = {
    board.setPlayerPosition(newX, newY)
  }

  def moveRight(): Unit = if (j == currentBoard.cols - 1) j = 0 else j = j + 1

  def moveLeft(): Unit = if (j == 0) (j = currentBoard.cols - 1) else j = j - 1

  def moveUp(): Unit = if (i == 0) (i = currentBoard.rows - 1) else i = i - 1

  def moveDown(): Unit = if (i == currentBoard.rows - 1) i = 0 else i = i + 1

  def calculateScore(board: Board): Int = {
    if (!board.gameBoard(i)(j).ifVisited()) {
      board.gameBoard(i)(j).makeVisited()
      val addedScore = board.gameBoard(i)(j).power match {
        case x if (x <= score) => board.gameBoard(i)(j).winValue
        case x if (x > score && !board.gameBoard(i)(j).isInstanceOf[Bonus]) => board.gameBoard(i)(j).looseValue
        case _ => board.gameBoard(i)(j).winValue
      }
      score = score + addedScore
      score
    } else score
  }

  def updateStatus(): Unit = calculateScore(currentBoard) match {

    case x if (x < gameOverScore) =>
      flag = false
      gameState = "GAME_OVER"
    case x if (x >= winScore) =>
      flag = false
      gameState = "WON"
    case x if (x < gameOverScore + 2) => msg = Console.RED + "Watchout, -3 score is game over!" + Console.RESET
    case _ =>
  }

  def runGame(manualCommand: Option[Array[String]]): Unit = {

    while (flag) {

      msg = ""
      val command = manualCommand match {
        case Some(x) => x
        case _ => Array(scala.io.StdIn.readLine("Enter move direction and hit 'enter': "))
      }

      command.foreach {
        case "w" => {
          moveUp()
          movePlayer(currentBoard, i, j)
          updateStatus()
          printGame(currentBoard)

        }
        case "a" => {
          moveLeft()
          movePlayer(currentBoard, i, j)
          updateStatus()
          printGame(currentBoard)
        }
        case "s" => {
          moveDown()
          movePlayer(currentBoard, i, j)
          updateStatus()
          printGame(currentBoard)
        }
        case "d" => {
          moveRight()
          movePlayer(currentBoard, i, j)
          updateStatus()
          printGame(currentBoard)
        }
        case "save" => {
          currentBoard.save(score, nickname)
          msg = "Game is saved successfully!"
          printGame(currentBoard)
        }
        case "load" => {
          currentBoard = loadGame()
          msg = "Game is loaded (if any exist)!"
          printGame(currentBoard)

        }
        case "help" => {
          println(Console.BLUE + ":: Bonus (*) gives you 1 score")
          println(":: SmallMonsters (ü) have 6 powers, if you have enough score, you beat them and get 4 score. If you loose againts them, you loose 1 score")
          println(":: BigMonsters (Ö) have 20 powers, if you have enough score, you beat them and get 10 score. If you loose againts them, you loose 3 score" + Console.RESET)

        }
        case "exit" => {
          flag = false

        }
        case s =>
          msg = Console.RED + s"$s is a wrong key/command" + Console.RESET
          printGame(currentBoard)
      }
    }
    gameState match {
      case "GAME_OVER" => println("GAME OVER :(")
      case "WON" => println(Console.GREEN + "*********** YOU WON! ***********")
      case _ => println(Console.GREEN + "Thank you for playing")
    }
  }


  def main(args: Array[String]) {

    "clear".!
    startMenu(null)
    runGame(null)

  }


}
