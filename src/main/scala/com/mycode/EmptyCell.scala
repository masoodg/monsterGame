package com.mycode

/**
  * Created by admin on 28/01/2017.
  */
case class EmptyCell() extends Cell{

  override val winValue: Int = 0
  override val power: Int = 0
  override val looseValue: Int = 0
  override val cellType: String = "EmptyCell"

}
