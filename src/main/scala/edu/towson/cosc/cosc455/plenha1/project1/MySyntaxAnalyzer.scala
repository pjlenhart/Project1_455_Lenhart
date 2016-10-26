package edu.towson.cosc.cosc455.plenha1.project1

/**
  * Created by plenhart on 10/23/16.
  */
class MySyntaxAnalyzer extends SyntaxAnalyzer{

  var parseTree = new scala.collection.mutable.Stack[String]

  override def gittex(): Unit = {
    if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.DOCB)){
      parseTree.push(Compiler.currentToken)
      Compiler.Scanner.getNextToken()
      variableDefine()
      title()
      body()
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.DOCE)){

      }
    }
    else {
      println("Error")
      System.exit(1)
    }
  }

  override def paragraph(): Unit = {

    if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.PARAB)) {
      parseTree.push(CONSTANTS.PARAB)
      variableDefine()
      innerText()
      Compiler.Scanner.getNextToken()

      if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.PARAE)) {
        parseTree.push(CONSTANTS.PARAE)
        Compiler.Scanner.getNextToken()
      } else {
        println("Syntax Error")
        System.exit(1)
      }
    } else {
      println("Syntax Error")
      System.exit(1)
    }
  }

  override def innerItem(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.USEB)){
      variableUse()
      innerItem()
    }else if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BOLD)){
      bold()
      innerItem()
    }else if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ITALICS)){
      italics()
      innerItem()
    }else if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LINKB)){
      link()
      innerItem()
    }else {
      reqText()
      innerItem()
    }
  }

  override def innerText(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.USEB)){
      variableUse()
      innerText()
    }else if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.HEADER)){
      heading()
      innerText()
    }else if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BOLD)){
      bold()
      innerText()
    }else if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ITALICS)){
      italics()
      innerText()
    }else if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LISTITEM)){
      italics()
      innerText()
    }else if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.IMAGEB)){
      image()
      innerText()
    }else if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LINKB)){
      link()
      innerText()
    }else if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.NEWLINE)){
      newline()
      innerText()
    }else{
      text()
      innerText()
    }
  }

  override def link(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LINKB)){
      parseTree.push(CONSTANTS.LINKB)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
    reqText()
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)){
      parseTree.push(CONSTANTS.BRACKETE)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSB)){
      parseTree.push(CONSTANTS.ADDRESSB)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
    reqText()
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSE)){
      parseTree.push(CONSTANTS.ADDRESSE)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
  }

  override def italics(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ITALICS)){
      parseTree.push(CONSTANTS.ITALICS)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
    text()
  }

  override def body(): Unit = {
    if(Compiler.
    body()
  }

  override def bold(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BOLD)){
      parseTree.push(CONSTANTS.BOLD)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
    text()
  }

  override def newline(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.NEWLINE)){
      parseTree.push(CONSTANTS.NEWLINE)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
  }

  override def title(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.TITLEB)){
      parseTree.push(CONSTANTS.TITLEB)
      Compiler.Scanner.getNextToken()
      reqText()
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)){
        parseTree.push(CONSTANTS.BRACKETE)
        Compiler.Scanner.getNextToken()
      }
    }else {
      println("Syntax Error")
      System.exit(1)
    }
  }

  override def variableDefine(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.DEFB)){
      parseTree.push(CONSTANTS.DEFB)
      Compiler.Scanner.getNextToken()
      reqText()
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.EQSIGN)){
        parseTree.push(CONSTANTS.EQSIGN)
        Compiler.Scanner.getNextToken()
        reqText()
        if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)){
          parseTree.push(CONSTANTS.BRACKETE)
          Compiler.Scanner.getNextToken()
        }else{
          println("Syntax Error")
          System.exit(1)
        }
      }else{
        println("Syntax Error")
        System.exit(1)
      }
    }else{
      println("Syntax Error")
      System.exit(1)
    }
    //variableDefine()
  }

  override def image(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.IMAGEB)){
      parseTree.push(CONSTANTS.IMAGEB)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
    reqText()
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)){
      parseTree.push(CONSTANTS.BRACKETE)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSB)){
      parseTree.push(CONSTANTS.ADDRESSB)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
    reqText()
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSE)){
      parseTree.push(CONSTANTS.ADDRESSE)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
  }

  override def variableUse(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.USEB)){
      parseTree.push(CONSTANTS.USEB)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
    reqText()
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)){
      parseTree.push(CONSTANTS.BRACKETE)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
  }

  override def heading(): Unit = {
    if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.HEADER)) {
      parseTree.push(CONSTANTS.HEADER)
      Compiler.Scanner.getNextToken()
    } else {
      println("Syntax Error")
      System.exit(1)
    }
      reqText()
  }

  override def listItem(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LISTITEM)){
      parseTree.push(CONSTANTS.LISTITEM)
      Compiler.Scanner.getNextToken()
    }else{
      println("Syntax Error")
      System.exit(1)
    }
    innerItem()
    listItem()
  }


  def text(): Unit = {
    letters()
    numbers()
    specials()
  }

  def reqText(): Unit = {
    val specialList: List[String] = List("," , "." , ":" , "?" , "_" , " \" ")
    for(reqText <- 'A' to 'Z', '0' to '9', j <- 0 until specialList.length-1)
  }

  def letters(): Unit = {
    for(letters <- 'A' to 'Z'){
      if(Compiler.currentToken.equals(letters)){
        val charStr = letters.toString
        parseTree.push(charStr)
      }
    }
  }

  def numbers(): Unit = {
    for(numbers <- '0' to '9'){
      if(Compiler.currentToken.equals(numbers)){
        val numStr = numbers.toString
        parseTree.push(numStr)
      }
    }
  }

  def specials(): Unit = {
    val specialList: List[String] = List("," , "." , ":" , "?" , "_" , " \" ")
    for(j <- 0 until specialList.length-1){
      if(Compiler.currentToken.equals(specialList{j})){
        parseTree.push(specialList{j})
      }
    }
  }

  def reqLetters(): Unit = {
    for(letters <- 'A' to 'Z'){
      if(Compiler.currentToken.equals(letters)){
        val charStr = letters.toString
        parseTree.push(charStr)
      }else{
        println("Syntax Error")
        System.exit(1)
      }
    }
  }

  def reqNumbers(): Unit = {
    for(numbers <- '0' to '9'){
      if(Compiler.currentToken.equals(numbers)){
        val numStr = numbers.toString
        parseTree.push(numStr)
      }else{
        println("Syntax Error")
        System.exit(1)
      }
    }
  }

  def reqSpecials(): Unit = {
    val specialList: List[String] = List("," , "." , ":" , "?" , "_" , " \" ")
    for(j <- 0 until specialList.length-1){
      if(Compiler.currentToken.equals(specialList{j})){
        parseTree.push(specialList{j})
      }else{
        println("Syntax Error")
        System.exit(1)
      }
    }
  }
}

