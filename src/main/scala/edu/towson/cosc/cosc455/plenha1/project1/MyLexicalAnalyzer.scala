package edu.towson.cosc.cosc455.plenha1.project1

import scala.collection.mutable

/**
  * Created by plenhart on 10/23/16.
  */


class MyLexicalAnalyzer extends LexicalAnalyzer {

  val lexems: List[String] = List("\\BEGIN", "\\END", "\\TITLE[", "]", "#", "\\PARB",
    "\\PARE", "**", "*", "+", "\\", "[", "(", ")", "![",
    "\\DEF[", "=", "\\USE[")
  //a list of acceptable lexemes that are allowed by the language
  var pos: Int = -1
  //the index of the Big string of input
  var candidate: Char = ' '
  //the character in question, will be checked against the grammar
  var currentString: String = "" //the potential string which will be a token that is fully checked against the grammar
  var isText : Boolean = false //checks whether or not tokens are text
  var check : Boolean = false


  override def addChar(): Unit = {
    currentString = currentString + candidate
  }

  override def lookup(): Boolean = {
    var flag = false
    if (lexems.contains(currentString.toUpperCase())) {
      flag = true
    }
    flag

  }

  override def getNextToken(): Unit = {
    Compiler.Parser.isText = false
    if(isSpace()){
      candidate = getChar()
      while(isSpace()){
        candidate = getChar()
      }
    }
    if(lexems.contains(candidate.toString)){
      Compiler.currentToken = candidate.toString
      if(candidate.equals('=') || candidate.equals(']'))
        candidate = getChar()
    }else if(isSpecial()){
      addChar()
      candidate = getChar()
      while(!end()){
        addChar()
        candidate = getChar()
      }
      if(lexems.contains(candidate.toString))
        addChar()
      if(lookup()){
        Compiler.currentToken = currentString
        currentString = ""
        candidate = getChar()
      }else{
        println(currentString)
        println("Lexical Error - Unrecognized Special Character")
        System.exit(1)
      }
    }else if(text()){
      while(text()){
        Compiler.Parser.isText = true
        addChar()
        candidate = getChar()
      }
      Compiler.currentToken = currentString
      currentString = ""
    }
  }

  def isSpace(): Boolean ={
    if(candidate.equals('\r') || candidate.equals('\n') || candidate.equals(' ') || candidate.equals('\t')){
      true
    }else{
      false
    }
  }

  def isSpecial(): Boolean ={
    candidate match{
      case '\\' | '*' | '#' | '+' | '[' | '!' => true
      case _ => false
    }
  }

  def end(): Boolean ={
    candidate match{
      case '\r' | '\n' | '[' | '\\' | ']' | '*' | ')' | '(' => true
      case _ => false
    }
  }

  def text() : Boolean = {
    isText = false
    for(ch <- 'A' to 'Z'){
      if(candidate.equals(ch))
        isText = true
    }
    for(ch <- 'a' to 'z'){
      if(candidate.equals(ch))
        isText = true
    }
    for(ch <- '0' to '9'){
      if(candidate.equals(ch))
        isText = true
    }
    if(candidate.equals(',') || candidate.equals('.') || candidate.equals('?') || candidate.equals('_')
        || candidate.equals('/') || candidate.equals('\"') || candidate.equals(' ')){
      isText = true
    }
    isText
  }

  override def getChar(): Char = {
    pos += 1
    Compiler.fileContents.charAt(pos)
  }



}