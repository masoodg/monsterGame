package com.mycode

/**
  * Created by admin on 28/01/2017.
  */
case class SmallMonster() extends Cell {


  override val winValue: Int = 4
  override val looseValue: Int = -1
  override val power: Int = 6
  override val cellType: String = "SmallMonster"


//  var winValueT: Int = 4
//  var powerT: Int = 10
//  var looseValueT: Int = 1
//
//  if (isVisited()) {
//
//    winValueT = 0
//    powerT = 0
//    looseValueT = 0
//
//  }
//
//
//  override var winValue: Int = winValueT
//  override var power: Int = powerT
//  override var looseValue: Int = looseValueT
}
