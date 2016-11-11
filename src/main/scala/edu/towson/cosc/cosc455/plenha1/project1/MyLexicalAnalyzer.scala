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


  override def addChar(): Unit = {
    currentString = currentString + candidate
  }

  override def lookup(): Boolean = {
    var flag = false
    if (lexems.contains(currentString)) {
      flag = true
    }
    flag

  }

  override def getNextToken(): Unit = {
    candidate = getChar()
    while (candidate.equals(' ') || candidate.equals('\r') || candidate.equals('\n')) {
      candidate = getChar()
    }
    if(candidate.equals('\\') || candidate.equals('#') || candidate.equals('*') || candidate.equals('+')
        || candidate.equals('!') || candidate.equals('[') || candidate.equals(']')){
      addChar()
      candidate = getChar()
      while(!(candidate.equals('\n') || candidate.equals('\r') || candidate.equals('['))){
        addChar()
        println(currentString)
        candidate = getChar()
      }
      if(candidate.equals('[')){
        addChar()
      }
      if(lookup()){
        Compiler.currentToken = currentString
        currentString = ""
      }else{
        println(currentString)
        println("Lexical error you idiot")
        System.exit(1)
      }

    }else if (text()){
      while(text()){
        println(currentString)
        addChar()
        candidate = getChar()
      }
      Compiler.currentToken = currentString
      currentString = ""
    }
    else{
      println(currentString)
      println("Lexical error - no valid token presented")
      System.exit(1)
    }

  }

  def text() : Boolean = {
    var isText : Boolean = false
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
        || candidate.equals('!') || candidate.equals('"') || candidate.equals(' ')){
      isText = true
    }
    false
  }

  override def getChar(): Char = {
    pos += 1
    Compiler.fileContents.charAt(pos)
  }



}