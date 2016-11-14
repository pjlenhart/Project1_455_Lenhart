package edu.towson.cosc.cosc455.plenha1.project1

/**
  * Created by plenhart on 10/23/16.
  */
class MySyntaxAnalyzer extends SyntaxAnalyzer{

  var parseTree = new scala.collection.mutable.Stack[String]
  var isText : Boolean = false
  var inner : Boolean = false

  override def gittex(): Unit = {
    println(Compiler.currentToken)
    if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.DOCB)){
      parseTree.push(Compiler.currentToken)
      Compiler.Scanner.getNextToken()
      title()
      variableDefine()
      body()
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.DOCE)){
        parseTree.push(Compiler.currentToken)
        if(Compiler.fileContents.length - Compiler.pos > 15){
          println("Syntax Error - Nothing after end tag")
          System.exit(1)
        }
      }
    }else{
      println("Syntax Error - Missing Start tag")
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
        println("Syntax Error - Missing paragraph ending")
        System.exit(1)
      }
    }
  }

  override def innerItem(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.USEB)){
      inner = true
      variableUse()
      innerItem()
    }else if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BOLD)){
      inner = true
      bold()
      innerItem()
    }else if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ITALICS)){
      inner = true
      italics()
      innerItem()
    }else if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LINKB)){
      inner = true
      link()
      innerItem()
    }else {
      if(!inner){
        if(isText){
          text()
        }else{
          println("Syntax Error - Missing Text Required")
        }
      }
    }
  }

  def text(): Unit = {
    while(isText){
      parseTree.push(Compiler.currentToken)
      Compiler.Scanner.getNextToken()
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
     // println("fork")
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
      if(isText){
        text()
        innerText()
      }

    }
  }

  override def link(): Unit = {
    if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LINKB)) {
      parseTree.push(CONSTANTS.LINKB)
      Compiler.Scanner.getNextToken()
      if(isText){
        parseTree.push(Compiler.currentToken)
        Compiler.Scanner.getNextToken()
      }else{
        println("Syntax Error - Missing Text Required")
      }
      if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)) {
        parseTree.push(CONSTANTS.BRACKETE)
        Compiler.Scanner.getNextToken()
        if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSB)) {
          parseTree.push(CONSTANTS.ADDRESSB)
          Compiler.Scanner.getNextToken()
          if(isText){
            parseTree.push(Compiler.currentToken)
            Compiler.Scanner.getNextToken()
          }else{
            println("Syntax Error - Missing Text Required")
          }
          if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSE)) {
            parseTree.push(CONSTANTS.ADDRESSE)
            Compiler.Scanner.getNextToken()
          }else{
            println("Syntax Error - Missing Address ending")
            System.exit(1)
          }
        }else{
          println("Syntax Error - Missing Address beginning")
          System.exit(1)
        }
      }else{
        println("Syntax Error - Missing ending Bracket in Link")
        System.exit(1)
      }
    }else{
      println("Syntax Error - Not a proper link beginning")
      System.exit(1)
    }
  }

  override def italics(): Unit = {
    if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ITALICS)) {
      parseTree.push(CONSTANTS.ITALICS)
      Compiler.Scanner.getNextToken()
      if (isText) {
        text()
      }
      if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ITALICS)) {
        parseTree.push(CONSTANTS.ITALICS)
        Compiler.Scanner.getNextToken()
      } else {
        println("Syntax Error - Missing italics indication")
        System.exit(1)
      }

    }
  }

  override def body(): Unit = {
    innerText()
    paragraph()
    newline()
    while(!Compiler.currentToken.equalsIgnoreCase(CONSTANTS.DOCE)){
      Compiler.Scanner.getNextToken()
      body()
    }
  }

  override def bold(): Unit = {
    if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BOLD)) {
      parseTree.push(CONSTANTS.BOLD)
      Compiler.Scanner.getNextToken()
      if (isText) {
        text()
      } else {
        println("Syntax Error - Missing Bold indicator")
        System.exit(1)
      }

    }
  }

  override def newline(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.NEWLINE)){
      parseTree.push(CONSTANTS.NEWLINE)
      Compiler.Scanner.getNextToken()
    }
  }

  override def title(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.TITLEB)){
      parseTree.push(CONSTANTS.TITLEB)
      Compiler.Scanner.getNextToken()
      if(isText){
        parseTree.push(Compiler.currentToken)
        Compiler.Scanner.getNextToken()
      }else{
        println("Syntax Error - Missing Text Required")
      }
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)){
        parseTree.push(CONSTANTS.BRACKETE)
        Compiler.Scanner.getNextToken()
      }else{
        println("Syntax Error - Missing end bracket for Title")
        System.exit(1)
      }
    }else {
      println("Syntax Error - Missing Title Beginning")
      System.exit(1)
    }
  }

  override def variableDefine(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.DEFB)){
      parseTree.push(CONSTANTS.DEFB)
      Compiler.Scanner.getNextToken()
      if(isText){
        parseTree.push(Compiler.currentToken)
        Compiler.Scanner.getNextToken()
      }else{
        println("Syntax Error - Text Required")
        System.exit(1)
      }
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.EQSIGN)){
        parseTree.push(CONSTANTS.EQSIGN)
        Compiler.Scanner.getNextToken()
        if(isText){
          parseTree.push(Compiler.currentToken)
          Compiler.Scanner.getNextToken()
        }else{
          println("Syntax Error - Text Required")
          System.exit(1)
        }
        if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)){
          parseTree.push(CONSTANTS.BRACKETE)
          Compiler.Scanner.getNextToken()
          variableDefine()
        }else{
          println("Syntax Error - Missing ending Bracket in variable definition")
          System.exit(1)
        }
      }else{
        println("Syntax Error - Missing Equals sign for variable definition")
        System.exit(1)
      }
    }

  }

  override def image(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.IMAGEB)){
      parseTree.push(CONSTANTS.IMAGEB)
      Compiler.Scanner.getNextToken()
      if(isText){
        parseTree.push(Compiler.currentToken)
        Compiler.Scanner.getNextToken()
      }else{
        println("Syntax Error - Missing Text Required")
      }
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)){
        parseTree.push(CONSTANTS.BRACKETE)
        Compiler.Scanner.getNextToken()
        if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSB)){
          parseTree.push(CONSTANTS.ADDRESSB)
          Compiler.Scanner.getNextToken()
          if(isText){
            parseTree.push(Compiler.currentToken)
            Compiler.Scanner.getNextToken()
          }else{
            println("Syntax Error - Missing Text Required")
          }
          if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.ADDRESSE)){
            parseTree.push(CONSTANTS.ADDRESSE)
            Compiler.Scanner.getNextToken()
          }else{
            println("Syntax Error - Missing address ending in image declaration")
            System.exit(1)
          }
        }else{
          println("Syntax Error - Missing address beginning in image declaration")
          System.exit(1)
        }
      }else{
        println("Syntax Error - Missing bracket ending in imager declaration")
        System.exit(1)
      }
    }else{
      println("Syntax Error - Missing image beginning declaration")
      System.exit(1)
    }
  }

  override def variableUse(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.USEB)){
      parseTree.push(CONSTANTS.USEB)
      Compiler.Scanner.getNextToken()
      if(isText){
        text()
      }else{
        println("Syntax Error - Text Required")
        System.exit(1)
      }
      if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.BRACKETE)){
        parseTree.push(CONSTANTS.BRACKETE)
        Compiler.Scanner.getNextToken()
        variableUse()
      }else {
        println("Syntax Error - Missing end bracket in variable usage")
        System.exit(1)
      }
    }
  }


  override def heading(): Unit = {
    if (Compiler.currentToken.equalsIgnoreCase(CONSTANTS.HEADER)) {
      parseTree.push(CONSTANTS.HEADER)
      Compiler.Scanner.getNextToken()
      if(isText){
        parseTree.push(Compiler.currentToken)
        Compiler.Scanner.getNextToken()
      }
    } else {
      println("Syntax Error - Missing header character ")
      System.exit(1)
    }

  }

  override def listItem(): Unit = {
    if(Compiler.currentToken.equalsIgnoreCase(CONSTANTS.LISTITEM)){
      parseTree.push(CONSTANTS.LISTITEM)
      Compiler.Scanner.getNextToken()
      innerItem()
      listItem()
    }

  }
}

