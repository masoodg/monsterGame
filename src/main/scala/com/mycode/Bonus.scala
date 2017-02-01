package com.mycode

/**
  * Created by admin on 29/01/2017.
  */
case class Bonus() extends Cell {
  override val winValue: Int = 1
  override val looseValue: Int = 0
  override val power: Int = 0
  override val cellType: String = "Bonus"
}
