package com.mycode

/**
  * Created by admin on 28/01/2017.
  */
case class BigMonster() extends Cell{
  override val winValue: Int = 10
  override val looseValue: Int = -3
  override val power: Int = 20
  override val cellType: String = "BigMonster"
}
