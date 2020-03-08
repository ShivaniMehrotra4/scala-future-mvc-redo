package com.knoldus.service

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * QueryHandler class is the main class where deciding upon user input result is displayed.
 *
 * @param operation - constructor injection
 */
class QueryHandler(operation: Operations) {

  /**
   * findAnswers function is specific for finding the answers to queries like max posts and post with max comments
   *
   * @param choice - specifies whether to max posts or max commented post of user
   * @return - userName
   */
  def findAnswers(choice: String): Future[String] = {

    if (choice.toLowerCase() == "posts") {
      val userWithMaxPosts: Future[String] = operation.findUserWithMaxPosts(
        operation.findUsersWithPosts(operation.getFinalListUsers, operation.getFinalListPosts))
      userWithMaxPosts
    }
    else if (choice.toLowerCase == "comments") {
      val userForPostWithMaxComments: Future[String] = operation.findUserWithMaxPostComments(
        operation.findPostsWithComments(operation.getFinalListPosts, operation.getFinalListComments), operation.getFinalListUsers)
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

