package edu.towson.cosc.cosc455.plenha1.project1

/**
  * Created by plenhart on 10/23/16.
  */
import java.awt.Desktop
import java.io.{File, IOException, PrintWriter}

class MySemanticAnalyzer {

  var tree : List[String] = Nil
  var resolvedTree : List[String] = Nil
  var ind = 0
  tree = Compiler.Parser.parseTree.toList.reverse

  def semantics(): Unit = {
    while(tree.nonEmpty){
      tree match{
        case CONSTANTS.DOCB::stack =>
          resolvedTree = "<!DOCTYPE html>\n<html>\n<head>\n" ::resolvedTree
          tree = stack
        case CONSTANTS.DOCE::stack =>
          resolvedTree = "</body>\n</html>"::resolvedTree
          tree = stack
        case CONSTANTS.TITLEB::stack =>
          resolvedTree = "<title>"::resolvedTree
          tree = stack
        case CONSTANTS.PARAB::stack =>
          resolvedTree = "<p>\n"::resolvedTree
          tree = stack
        case CONSTANTS.PARAE::stack =>
          resolvedTree = "</p>\n"::resolvedTree
          tree = stack
        case CONSTANTS.ITALICS::stack =>
          resolvedTree = "<em>"::resolvedTree
          tree = stack
        case CONSTANTS.ITALICS::stack =>
          resolvedTree = "</em>"::resolvedTree
          tree = stack
        case CONSTANTS.BOLD::stack =>
          resolvedTree = "<b>"::resolvedTree
          tree = stack
        case CONSTANTS.BOLD::stack =>
          resolvedTree = "</b>"::resolvedTree
          tree = stack
        case CONSTANTS.LISTITEM::stack =>
          resolvedTree = "<li>"::resolvedTree
          tree = stack
        case CONSTANTS.LISTITEM::stack =>
          resolvedTree = "</li>\n"::resolvedTree
          tree = stack
        case CONSTANTS.HEADER::stack =>
          resolvedTree = "<h1>"::resolvedTree
          tree = stack
        case CONSTANTS.HEADER::stack =>
          resolvedTree = "</h1>"::resolvedTree
          tree = stack
        case CONSTANTS.NEWLINE::stack =>
          resolvedTree = "<br>\n"::resolvedTree
          tree = stack
        case CONSTANTS.DEFB::stack =>
          tree = stack
        case CONSTANTS.EQSIGN::stack =>
          tree = stack.tail
        case CONSTANTS.BRACKETE::stack =>
          tree = stack.tail
        case CONSTANTS.LINKB::stack =>
          tree = stack
        case CONSTANTS.IMAGEB::stack =>
          tree = stack
        case CONSTANTS.USEB::stack =>
          tree = stack
        case CONSTANTS.BRACKETE::stack =>
          resolvedTree = getContent(stack.head)::resolvedTree
          tree = stack.tail
        case CONSTANTS.ADDRESSE::stack =>
          val linkImg = linkImage(tree)
          val link = stack.head
          val desc = stack.drop(3).head
          linkImg match{
            case 0 =>
              resolvedTree = "<a href=" + link + ">" +desc +"</a>\n"::resolvedTree
            case 1 =>
              resolvedTree = "<img src="+ link +" alt=\"" + desc +"\">\n"::resolvedTree
            case _ =>
              println("Semantic Error - Unable to locate link or image")
          }
          tree = stack.drop(4)
        case x::stack =>
          resolvedTree = x::resolvedTree
          tree = stack
        case Nil => Nil
      }
    }
    writeHTML(resolvedTree.mkString(""))
    openHTMLFileInBrowser(Compiler.output + ".html")
  }



  def getContent(s : String): String ={
    var temp = 0
    var flag = false
    var answer = ""
    while(temp < tree.size -1 && !flag){
      if(Compiler.Scanner.lexems.contains(tree(temp))){
        if(tree(temp) == "DEFE"){
          val tera = tree(temp + 3).replaceAll("\\s", "")
          if(tera == s.replaceAll("\\s","")){
            flag = true
            answer = tree(temp+1)
          }
        }else{
          val beginning = tree(temp).dropRight(2) + "B>"
          while(tree(temp) != beginning && temp < tree.size-1){
            temp = temp + 1
          }
        }
      }
      temp= temp +1
    }
    if(!flag){
      println("Semantic Error! Cannot find variable def for: " + s)
      System.exit(1)
    }
    answer
  }


  def linkImage(eList : List[String]): Int ={
    var t = 0
    var flag = false
    while(t < tree.size -1 && !flag){
      if(Compiler.Scanner.lexems.contains(tree(t))){
        tree(t) match{
          case "LINKB" => flag = true
            return 0
          case "IMAGEB" => flag = false
            return 1
        }
      }
      t = t+1
    }
    -1
  }


  def writeHTML(s : String): Unit = {
    val writer = new PrintWriter(new File(Compiler.output + ".html"))
    writer.write(s)
    writer.close()
  }

  /* * Hack Scala/Java function to take a String filename and open in default web browser. */
  def openHTMLFileInBrowser(htmlFileStr : String) = {
    val file : File = new File(htmlFileStr.trim)
    println(file.getAbsolutePath)
    if (!file.exists())
      sys.error("File " + htmlFileStr + " does not exist.")

    try {
      Desktop.getDesktop.browse(file.toURI)
    }
    catch {
      case ioe: IOException => sys.error("Failed to open file:  " + htmlFileStr)
      case e: Exception => sys.error("He's dead, Jim!")
    }
  }

}
