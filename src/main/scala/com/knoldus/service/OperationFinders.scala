package com.knoldus.service

import com.knoldus.model._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * This is the main class where all operations are performed.
 */
class OperationFinders {

  val jsonUser: Future[String] = Future {
    ParsingGeneric.getData("https://jsonplaceholder.typicode.com/users")
  }
  val finalListUsers: Future[List[Users]] = Future {
    jsonUser.map(str =>
    ParsingGeneric.parseData[Users](str))
  }.flatten

  val jsonPost: Future[String] = Future {
    ParsingGeneric.getData("https://jsonplaceholder.typicode.com/posts")
  }
  val finalListPosts: Future[List[Posts]] = Future {
    jsonPost.map(str =>
    ParsingGeneric.parseData[Posts](str))
  }.flatten

  val jsonComment: Future[String] = Future {
    ParsingGeneric.getData("https://jsonplaceholder.typicode.com/comments")
  }
  val finalListComments: Future[List[Comments]] = Future {
    jsonComment.map(str =>
    ParsingGeneric.parseData[Comments](str))
  }.flatten

  /**
   * findAnswers class is specific for finding the answers to queries like max posts and post with max comments
   *
   * @param choice - specifies whether to max posts or max commented post of user
   * @return - user name
   */
  def findAnswers(choice: String): Future[String] = {
    val listUsersWithPosts: Future[List[UsersWithPosts]] = Operations.findUsersWithPosts(finalListUsers, finalListPosts)
    val listPostsWithComments: Future[List[PostsWithComments]] = Operations.findPostsWithComments(finalListPosts, finalListComments)

    if (choice.toLowerCase() == "posts") {
      val userWithMaxPosts: Future[String] = Operations.findUserWithMaxPosts(listUsersWithPosts)
      //println(userWithMaxPosts)
      userWithMaxPosts
    }
    else if (choice.toLowerCase == "comments") {
      val userForPostWithMaxComments: Future[String] = Operations.findUserWithMaxPostComments(listPostsWithComments, finalListUsers)
      //println(userForPostWithMaxComments)
      userForPostWithMaxComments
    }
    else {
      val errorMessage: Future[String] = Future {
        "Not valid"
      }
      errorMessage
    }
  }
}
