package com.mycode

/**
  * Created by admin on 28/01/2017.
  */
trait Cell {

  private var visitStatus: Boolean = false
  val winValue: Int
  val looseValue: Int
  val power: Int
  val cellType: String

  def makeVisited(): Unit = {
    visitStatus = true
  }

  def ifVisited() = {
    visitStatus
  }

}
