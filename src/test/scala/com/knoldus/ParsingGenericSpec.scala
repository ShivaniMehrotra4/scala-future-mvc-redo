package com.knoldus

import com.knoldus.model._
import com.knoldus.service.{GetUrlData, ParsingGeneric}
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito.{when, _}
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}

import scala.concurrent.Future

class ParsingGenericSpec extends AsyncFlatSpec with BeforeAndAfterAll with MockitoSugar {

  val getUrlData: GetUrlData = mock[GetUrlData]

  var parseAction = new ParsingGeneric(getUrlData)

  val userUrl = "https://jsonplaceholder.typicode.com/users"
  val postUrl = "https://jsonplaceholder.typicode.com/posts"
  val commentUrl = "https://jsonplaceholder.typicode.com/comments"

  override def beforeAll(): Unit = {
    implicit val formats: DefaultFormats.type = DefaultFormats
    val userJsonData: String = write(stubUserList)
    val postJsonData: String = write(stubPostList)
    val commentJsonData: String = write(stubCommentsList)

    when(getUrlData.getData(userUrl)).thenReturn(Future(userJsonData))
    when(getUrlData.getData(postUrl)).thenReturn(Future(postJsonData))
    when(getUrlData.getData(commentUrl)).thenReturn(Future(commentJsonData))
  }

  val stubUserList = List(Users("1", "1", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12",
    Company("13", "14", "15")),
    Users("2", "2", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12",
      Company("13", "14", "15")))
  val stubPostList = List(Posts("1", "2", "3", "4"), Posts("2", "2", "3", "4"))
  val stubCommentsList = List(Comments("1", "2", "3", "4", "5"), Comments("2", "2", "3", "4", "5"))


  "Parsing function - parseData " should "Parse user json string data into a case class of User" in {
    val futureListOfUsers: Future[List[Users]] = parseAction.parseData[Users](userUrl)
    futureListOfUsers map { eachUser => assert(eachUser == stubUserList)
    }
  }

  "Parsing function - parseData " should "Parse posts json string data into a case class of Post" in {
    val futureListOfPosts: Future[List[Posts]] = parseAction.parseData[Posts](postUrl)
    futureListOfPosts map { eachPost => assert(eachPost == stubPostList)
    }
  }

  "Parsing function - parseData " should "Parse comments json string data into a case class of Comment" in {
    val futureListOfComments: Future[List[Comments]] = parseAction.parseData[Comments](commentUrl)
    futureListOfComments map { eachComment => assert(eachComment == stubCommentsList)
    }
  }
}

