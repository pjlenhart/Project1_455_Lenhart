package edu.towson.cosc.cosc455.plenha1.project1

/**
  * Created by plenhart on 10/23/16.
  */
trait SyntaxAnalyzer {
  def gittex() : Unit
  def title() : Unit
  def body() : Unit
  def paragraph() : Unit
  def innerText() : Unit
  def heading() : Unit
  def variableDefine() : Unit
  def variableUse() : Unit
  def bold() : Unit
  def italics() : Unit
  def listItem() : Unit
  def innerItem() : Unit
  def link() : Unit
  def image() : Unit
  def newline() : Unit
}

