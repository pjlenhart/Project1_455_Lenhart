package edu.towson.cosc.cosc455.plenha1.project1

import scala.collection.mutable

/**
  * Created by plenhart on 10/23/16.
  */


class MyLexicalAnalyzer extends LexicalAnalyzer {

  val lexems: List[String] = List("\\BEGIN","\\END", "\\TITLE[","]", "#", "\\PARB",
                  "\\PARE", "**", "*", "+", "\\", "[", "(", ")", "![",
                  "\\DEF[", "=", "\\USE[")
  var pos : Int = -1
  var candidate : Char = ' '
  var currentString : String = " "
  val lexicalStack = new mutable.Queue[Char]()


  override def addChar(): Unit = {
    lexicalStack.enqueue(candidate)
    currentString = lexicalStack.mkString
    //println(currentString)
  }

  override def lookup(candidateToken : String): Boolean = {
    var flag = false
    if(lexems.contains(currentString)){
      flag = true
    }
    flag

  }

  override def getNextToken(): Unit = {
    candidate  = getChar()
    if(candidate.equals(' ')){
      while(candidate.equals(' ')){
        candidate = getChar()
      }
    }
    else if(candidate.equals('\\') || candidate.equals('#') || candidate.equals('*') || candidate.equals('[') ||
              candidate.equals('!') || candidate.equals('+')){
      addChar()
      candidate = getChar()
      while(!(candidate.equals('\\') || candidate.equals('#') || candidate.equals('*') || candidate.equals('[') ||
        candidate.equals('!') || candidate.equals('+') || candidate.equals('\r') || candidate.equals('\n'))){
        addChar();
        candidate = getChar();
      }
      println(currentString)
      if(lookup(currentString)){
        Compiler.currentToken == currentString
      }
      else{
        println("Lexical Error- Token Not Found.")
        System.exit(1)
    }

    }

  }

  override def getChar(): Char = {
    pos += 1
    Compiler.fileContents.charAt(pos)
  }



}