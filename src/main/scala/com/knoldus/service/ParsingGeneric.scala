package com.knoldus.service

import net.liftweb.json._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * ParsingGeneric class contains a function parseData which takes in a json extracted string format
 * data and parses it in the form of case classes.
 *
 * @param getUrlData - constructor injection
 */
class ParsingGeneric(getUrlData: GetUrlData) {

  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * This generic function returns extracted json data based on the jsonData and the return type provided.
   *
   * @return - parsed data in the form of List of specified case class.
   */
  def parseData[T](jsonData: String)(implicit m: Manifest[T]): Future[List[T]] = {
    val extractedData = getUrlData.getData(jsonData).map(parse)
    extractedData.map(_.children.map(_.extract[T]))
  }
}

