package com.mycode


/**
  * Created by admin on 28/01/2017.
  */
class Board(val rows: Int = 3, val cols: Int = 3, empty: Boolean = false) {

  private var xPosition: Int = 0
  private var yPosition: Int = 0

  def loadBoard(gameBoard: Board, loadedData: Array[String]): Board = {

    var j = 0
    var a = 0
    var m = 0

    for (i <- 5 to loadedData.length - 2 if i % 2 == 1) {

      a = i - 4
      val k = a / (cols * 2)

      loadedData(i) match {
        case "EmptyCell" =>
          gameBoard.gameBoard(k)(m) = EmptyCell()
          if (loadedData(i + 1) == "true") gameBoard.gameBoard(k)(m).makeVisited()
        case "SmallMonster" =>
          gameBoard.gameBoard(k)(m) = SmallMonster()
          if (loadedData(i + 1) == "true") gameBoard.gameBoard(k)(m).makeVisited()
        case "BigMonster" =>
          gameBoard.gameBoard(k)(m) = BigMonster()
          if (loadedData(i + 1) == "true") gameBoard.gameBoard(k)(m).makeVisited()
        case "Bonus" =>
          gameBoard.gameBoard(k)(m) = Bonus()
          if (loadedData(i + 1) == "true") gameBoard.gameBoard(k)(m).makeVisited()
      }
      if (m == cols - 1) m = 0 else m = m + 1
    }
    setPlayerPosition(loadedData(1).toInt, loadedData(2).toInt)
    gameBoard
  }

  def save(score: Int, nickname: String): Unit = {
    import java.io._

    new File("save.txt").delete()

    val pw = new PrintWriter(new File("save.txt"))
    pw.write(score + "\n")
    pw.write(currentPosition()._1 + "\n")
    pw.write(currentPosition()._2 + "\n")
    pw.write(rows + "\n")
    pw.write(cols + "\n")
    for {
      a <- 0 until rows
      b <- 0 until cols
    } pw.write(gameBoard(a)(b).cellType + "\n" + gameBoard(a)(b).ifVisited() + "\n")
    pw.write(nickname + "\n")

    pw.close()
  }

  def setPlayerPosition(newX: Int, newY: Int): Unit = {
    xPosition = newX
    yPosition = newY
  }

  def currentPosition(): (Int, Int) = (xPosition, yPosition)

  val numOfBM = 2
  val numOfSM = 4
  val numOfBonus = 7

  val gameBoard: Array[Array[Cell]] = Array.fill(rows, cols) {
    EmptyCell(): Cell
  }
  if (!empty) {

    val r = scala.util.Random
    for (i <- 1 to numOfSM) yield gameBoard(r.nextInt(rows))(r.nextInt(cols)) = SmallMonster()
    for (i <- 1 to numOfBM) yield gameBoard(r.nextInt(rows))(r.nextInt(cols)) = BigMonster()
    for (i <- 1 to numOfBonus) yield gameBoard(r.nextInt(rows))(r.nextInt(cols)) = Bonus()
  }

  def printBoard(nickname: String, score: Int, msg: String = ""): Unit = {

    println(s"HELLO " + Console.GREEN + nickname + Console.RESET)
    println(Console.YELLOW + s"===================== your current score is: $score ==========================" + Console.RESET)
    println(s"| MOVE UP: 'w' , MOVE DOWN: 's' , MOVE RIGHT: 'd' , MOVE LEFT: 'a'      |")
    println(s"| SAVE: 'save' , LOAD: 'load' , HELP: 'help' , Exit: 'exit'             |")
    println("|" + Console.GREEN + " @" + Console.RESET + ": You , " + Console.RED + "ü" + Console.RESET + ": Small Monster , " + Console.RED + "Ö" + Console.RESET + ": Big Monster , " + Console.YELLOW + "*" + Console.RESET + ": Bonus                 |")
    println(Console.YELLOW + s"=========================================================================" + Console.RESET)

    for {
      a <- 0 until rows
      b <- 0 until cols
    } if (a == currentPosition()._1 && b == currentPosition()._2) {

      if (b == cols - 1) print(Console.GREEN + "@\n" + Console.RESET) else print(Console.GREEN + "@\t" + Console.RESET)

    }
    else {

      gameBoard(a)(b) match {
        case x: SmallMonster if (b == cols - 1 && x.ifVisited()) => print(Console.BLACK + "ü\n" + Console.RESET)
        case x: SmallMonster if (b == cols - 1) => print(Console.RED + "ü\n" + Console.RESET)
        case x: SmallMonster if (x.ifVisited()) => print(Console.BLACK + "ü\t" + Console.RESET)
        case x: SmallMonster => print(Console.RED + "ü\t" + Console.RESET)
        case x: BigMonster if (b == cols - 1 && x.ifVisited()) => print(Console.BLACK + "Ö\n" + Console.RESET)
        case x: BigMonster if (b == cols - 1) => print(Console.RED + "Ö\n" + Console.RESET)
        case x: BigMonster if (x.ifVisited()) => print(Console.BLACK + "Ö\t" + Console.RESET)
        case x: BigMonster => print(Console.RED + "Ö\t" + Console.RESET)
        case x: Bonus if (b == cols - 1 && x.ifVisited()) => print(Console.BLACK + "*\n" + Console.RESET)
        case x: Bonus if (b == cols - 1) => print(Console.YELLOW + "*\n" + Console.RESET)
        case x: Bonus if (x.ifVisited()) => print(Console.BLACK + "*\t" + Console.RESET)
        case x: Bonus => print(Console.YELLOW + "*\t" + Console.RESET)
        case x if ((b == cols - 1) && x.ifVisited()) => print(Console.BLACK + s"-\n" + Console.RESET)
        case x if ((b == cols - 1)) => print(s"-\n")
        case x if (gameBoard(a)(b).ifVisited()) => print(Console.BLACK + s"-\t" + Console.RESET)
        case _ => print(s"-\t")
      }
    }
    println(Console.MAGENTA + s"$msg" + Console.RESET)

  }

}
