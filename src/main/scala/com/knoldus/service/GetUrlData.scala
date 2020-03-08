package com.knoldus.service

import org.apache.commons.io.IOUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * This class provides a function that can be overridden by it's subclasses
 */
class GetUrlData {
  /**
   * getData function takes in a url
   *
   * @param url - json url
   * @return - extracted data from json in string
   */
  def getData(url: String): Future[String] = {
    val request = new HttpGet(url)
    val client = HttpClientBuilder.create().build()
    Future {
      val response = client.execute(request)
      IOUtils.toString(response.getEntity.getContent)
    }
  }
}
