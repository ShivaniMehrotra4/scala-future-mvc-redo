package com.knoldus.service

import com.knoldus.model._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Operations {
  /**
   * This function is designed to model a case class UserWithPosts
   *
   * @param listOfUsers - is a list of users wrapped in a future
   * @param listOfPosts - is a list of posts wrapped in a future
   * @return - a list of UserWithPosts wrapped up in a future
   */
  def findUsersWithPosts(listOfUsers: Future[List[Users]], listOfPosts: Future[List[Posts]]): Future[List[UsersWithPosts]] = {

    val ans: Future[List[UsersWithPosts]] = listOfUsers
      .map(onlyListUser => listOfPosts
        .map(post => onlyListUser
          .map(user => UsersWithPosts(user, post.filter(_.userId == user.id)))))
      .flatten
    ans
  }

  /**
   * This function is designed to model a case class PostsWithComments
   *
   * @param listOfPosts    - is a list of posts wrapped in a future
   * @param listOfComments - is a list of comments wrapped in a future
   * @return - a list of PostsWithComments wrapped up in a future
   */
  def findPostsWithComments(listOfPosts: Future[List[Posts]], listOfComments: Future[List[Comments]]): Future[List[PostsWithComments]] = {

    val ans: Future[List[PostsWithComments]] = listOfPosts
      .map(onlyListPost => listOfComments
        .map(comments => onlyListPost
          .map(post => PostsWithComments(post, comments.filter(_.postId == post.id)))))
      .flatten
    ans
  }

  /**
   * This function is designed to extract the name of user with maximum posts
   *
   * @param listOfUsersWithPosts - is a list of UsersWithPosts case class wrapped in a future
   * @return - user name with max number of posts
   */
  def findUserWithMaxPosts(listOfUsersWithPosts: Future[List[UsersWithPosts]]): Future[String] = {

    val sortedList: Future[List[UsersWithPosts]] = listOfUsersWithPosts
      .map(_.sortWith((x, y) => x.post.length <= y.post.length))
    val userWithMaxPost = sortedList.map(ansF => ansF.head.user.name)
    userWithMaxPost
  }

  /**
   * This function is designed to extract the name of user with posts having maximum number of comments
   *
   * @param listOfPostsWithComments - is a list of PostsWithComments case class wrapped in a future
   * @param listOfUsers             - is a list of users wrapped in a future
   * @return - user name with post having max number of comments
   */
  def findUserWithMaxPostComments(listOfPostsWithComments: Future[List[PostsWithComments]], listOfUsers: Future[List[Users]]): Future[String] = {
    val sortedList: Future[List[PostsWithComments]] = listOfPostsWithComments
      .map(_.sortWith((x, y) => x.comments.length <= y.comments.length))

    val firstElementOfSortedList: Future[String] = sortedList.map(_.head.post.userId)

    val userName: Future[String] = listOfUsers
      .map(user => firstElementOfSortedList
        .map(v => user.filter(_.id == v).map(_.name).head))
      .flatten

    userName

  }


}
