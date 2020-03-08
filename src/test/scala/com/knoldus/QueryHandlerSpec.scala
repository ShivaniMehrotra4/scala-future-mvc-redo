package com.knoldus

import com.knoldus.controllers.QueryHandler
import com.knoldus.model.{Address, Comments, Company, Geo, Posts, PostsWithComments, Users, UsersWithPosts}
import com.knoldus.service.Operations
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}

import scala.concurrent.Future

class QueryHandlerSpec extends AsyncFlatSpec with BeforeAndAfterAll with MockitoSugar {

  val operation: Operations = mock[Operations]
  var queryHandler: QueryHandler = new QueryHandler(operation)

  val stubUserList = List(Users("1", "1", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12",
    Company("13", "14", "15")),
    Users("2", "2", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12",
      Company("13", "14", "15")))
  val stubPostList = List(Posts("1", "2", "3", "4"), Posts("2", "2", "3", "4"))
  val stubCommentsList = List(Comments("1", "2", "3", "4", "5"), Comments("2", "2", "3", "4", "5"))

  val stubUserWithPostList = List(UsersWithPosts(stubUserList.head, List(stubPostList.head)),
    UsersWithPosts(stubUserList(1), List(stubPostList(1))))
  val stubPostWithCommentList = List(PostsWithComments(stubPostList.head, List(stubCommentsList(1))),
    PostsWithComments(stubPostList(1), List(stubCommentsList(1))))


  override def beforeAll(): Unit = {
    when(operation.findUserWithMaxPosts(
      operation.findUsersWithPosts(operation.getFinalListUsers, operation.getFinalListPosts)))
      .thenReturn(Future.successful("2"))

    when(operation.findUserWithMaxPostComments(
      operation.findPostsWithComments(operation.getFinalListPosts, operation.getFinalListComments), operation.getFinalListUsers))
      .thenReturn(Future.successful("2"))

  }

  "find Answers method " should " find User with Maximum number of posts " in {
    val expectedResult = "2"
    val actualResult: Future[String] = queryHandler.findAnswers("posts")
    actualResult map { value => assert(value == expectedResult) }
  }

  "it " should " find User whose Post has maximum number of comments " in {
    val expectedResult = "2"
    val actualResult = queryHandler.findAnswers("comments")
    actualResult map { value => assert(value == expectedResult) }
  }

  "it " should " fail if anything other than posts/comments is entered " in {
    val actualResult = queryHandler.findAnswers("aaaa")
    actualResult map { value => assert(value == "Not valid") }
  }

}

