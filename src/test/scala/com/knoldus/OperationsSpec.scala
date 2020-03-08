package com.knoldus

import com.knoldus.model.{Address, Comments, Company, Geo, Posts, PostsWithComments, Users, UsersWithPosts}
import com.knoldus.service.{Operations, ParsingGeneric}
import org.mockito.Mockito.when
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}
import org.scalatest.mockito.MockitoSugar

import scala.concurrent.Future

class OperationsSpec extends AsyncFlatSpec with BeforeAndAfterAll with MockitoSugar {

  val parsingGenericMock: ParsingGeneric = mock[ParsingGeneric]
  val operation = new Operations(parsingGenericMock)

  val userUrl = "https://jsonplaceholder.typicode.com/users"
  val postUrl = "https://jsonplaceholder.typicode.com/posts"
  val commentUrl = "https://jsonplaceholder.typicode.com/comments"

  val stubUserList = List(Users("1", "1", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12",
    Company("13", "14", "15")),
    Users("2", "2", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12",
      Company("13", "14", "15")))
  val stubPostList = List(Posts("1", "2", "3", "4"), Posts("2", "2", "3", "4"))
  val stubCommentsList = List(Comments("1", "2", "3", "4", "5"), Comments("2", "2", "3", "4", "5"))
  val stubUserWithPostList = List(UsersWithPosts(stubUserList(0), List(stubPostList(0))),
    UsersWithPosts(stubUserList(1), List(stubPostList(1))))
  val stubPostWithCommentList = List(PostsWithComments(stubPostList(0), List(stubCommentsList(1))),
    PostsWithComments(stubPostList(1), List(stubCommentsList(1))))

  override def beforeAll(): Unit = {
    when(parsingGenericMock.parseData[Users](userUrl)).thenReturn(Future.successful(stubUserList))
    when(parsingGenericMock.parseData[Posts](postUrl)).thenReturn(Future.successful(stubPostList))
    when(parsingGenericMock.parseData[Comments](commentUrl)).thenReturn(Future.successful(stubCommentsList))
  }

  "getFinalListUsers function " should "return list of users" in {
    val expectedResult = stubUserList
    val futureListOfUsers: Future[List[Users]] = operation.getFinalListUsers
    futureListOfUsers map { eachUser => assert(eachUser == expectedResult)
    }
  }

  "getFinalListPosts function " should "return list of users" in {
    val expectedResult = stubPostList
    val futureListOfPosts: Future[List[Posts]] = operation.getFinalListPosts
    futureListOfPosts map { eachPost => assert(eachPost == expectedResult)
    }
  }

  "getFinalListComments function " should "return list of users" in {
    val expectedResult = stubCommentsList
    val futureListOfComments: Future[List[Comments]] = operation.getFinalListComments
    futureListOfComments map { eachComment => assert(eachComment == expectedResult)
    }
  }

  "findUsersWithPosts function " should "return list of users with posts" in {
    val expectedResult = stubUserWithPostList
    val futureListOfUserWithPosts: Future[List[UsersWithPosts]] = operation.findUsersWithPosts(Future(stubUserList), Future(stubPostList))
    futureListOfUserWithPosts map { usersWithPost => assert(usersWithPost == expectedResult)
    }
  }

  "findPostsWithComments function " should "return list of posts with comments" in {
    val expectedResult = stubPostWithCommentList
    val futureListOfPostsWithComments: Future[List[PostsWithComments]] = operation.findPostsWithComments(Future(stubPostList), Future(stubCommentsList))
    futureListOfPostsWithComments map { postWithComment => assert(postWithComment == expectedResult)
    }
  }

  "findUserWithMaxPosts function " should "return User name with maximum number of posts" in {
    val expectedResult = "2"
    val futureUserName: Future[String] = operation.findUserWithMaxPosts(Future(stubUserWithPostList))
    futureUserName map { userName => assert(userName == expectedResult)
    }
  }

  "findUserWithMaxPostComments function " should "return User name with post having maximum number of comments" in {
    val expectedResult = "2"
    val futureUserName: Future[String] = operation.findUserWithMaxPostComments(Future(stubPostWithCommentList), Future(stubUserList))
    futureUserName map { userName => assert(userName == expectedResult)
    }
  }

}
