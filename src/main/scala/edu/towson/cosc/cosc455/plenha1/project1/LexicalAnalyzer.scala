package edu.towson.cosc.cosc455.plenha1.project1

/**
  * Created by plenhart on 10/23/16.
  */
trait LexicalAnalyzer {
  def addChar() : Unit
  def getChar() : Char
  def getNextToken() : Unit
  def lookup(candidateToken : String) : Boolean = {
    println("this is lookup implementation!")
    true
  }
}
