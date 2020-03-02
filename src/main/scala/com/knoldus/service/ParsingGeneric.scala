package com.knoldus.service

import net.liftweb.json.DefaultFormats

object ParsingGeneric extends GetUrlData {

  implicit val formats: DefaultFormats.type = DefaultFormats

  /**
   * This function returns extracted json data based on the URL given
   *
   * @param url - urls for users
   * @return - extracted data in string
   */
  override def getData(url: String): String = super.getData(url)

  def parseData[T](jsonData: String)(implicit m: Manifest[T]): List[T] = {

    val parsedData = net.liftweb.json.parse(jsonData)
    parsedData.children.map { item =>
      item.extract[T]
    }

  }

}
