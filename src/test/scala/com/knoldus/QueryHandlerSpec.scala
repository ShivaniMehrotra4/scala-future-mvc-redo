package com.knoldus

import com.knoldus.model.{Address, Comments, Company, Geo, Posts, PostsWithComments, Users, UsersWithPosts}
import com.knoldus.service.{OperationFinders, Operations}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncFlatSpec, BeforeAndAfterAll}
import scala.concurrent.Future

class OperationFindersSpec extends AsyncFlatSpec with BeforeAndAfterAll with MockitoSugar {

  val operation: Operations = mock[Operations]
  var operationFinders: OperationFinders = new OperationFinders(operation)

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
    when(operation.findUserWithMaxPosts(
      operation.findUsersWithPosts(operation.getFinalListUsers, operation.getFinalListPosts)))
      .thenReturn(Future.successful("2"))

    when(operation.findUserWithMaxPostComments(
      operation.findPostsWithComments(operation.getFinalListPosts, operation.getFinalListComments),operation.getFinalListUsers))
      .thenReturn(Future.successful("2"))

  }

  "find Answers method " should " find User with Maximum number of posts " in {
    val expectedResult = "2"
    val actualResult: Future[String] = operationFinders.findAnswers("posts")
    actualResult map { value => assert(value == expectedResult) }
  }

  "it " should " find User whose Post has maximum number of comments " in {
    val expectedResult = "2"
    val actualResult = operationFinders.findAnswers("comments")
    actualResult map { value => assert(value == expectedResult) }
  }

  "it " should " fail if anything other than posts/comments is entered " in {
    val actualResult = operationFinders.findAnswers("aaaa")
    actualResult map { value => assert(value == "Not valid") }
  }

}


/*
val stubListOfUser = List(Users("1","Leanne Graham","Bret","Sincere@april.biz",
  Address("Kulas Light","Apt. 556","Gwenborough","92998-3874",
    Geo("-37.3159","81.1496")),
  "1-770-736-8031 x56442","hildegard.org",
  Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets")),

  Users("2","Ervin Howell","Antonette","Shanna@melissa.tv",
    Address("Victor Plains","Suite 879","Wisokyburgh","90566-7771",
      Geo("-43.9509","-34.4618")),
    "010-692-6593 x09125","anastasia.net",
    Company("Deckow-Crist","Proactive didactic contingency","synergize scalable supply-chains")))


val stubListOfPost = List(Posts("1","1","sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
  "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum " +
  "rerum est autem sunt rem eveniet architecto"),

  Posts("2","2","qui est esse",
    "est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\\nfugiat blanditiis voluptate " +
    "porro vel nihil molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi nulla"))

val stubListOfComment = List(Comments("1","1","id labore ex et quam laborum","Eliseo@gardner.biz",
  "laudantium enim quasi es…am sapiente accusantium"),

  Comments("1","2","quo vero reiciendis velit similique earum","Jayne_Kuhic@sydney.com",
    "est natus enim nihil est…oluptatem reiciendis et"))

val stubListUsersWithPosts = List(UsersWithPosts(stubListOfUser(0),stubListOfPost),UsersWithPosts(stubListOfUser(1),stubListOfPost))
val stubListPostsWithComments = List(PostsWithComments(stubListOfPost(0),stubListOfComment),PostsWithComments(stubListOfPost(1),stubListOfComment))

*/
/*
val userJsonData: String = write(stubUserList)
val postJsonData: String = write(stubPostList)
val commentJsonData: String = write(stubCommentsList)*/


/*when(parseAction.parseData[Users](userJsonData)).thenReturn(Future.successful(stubUserList))
when(parseAction.parseData[Posts](postJsonData)).thenReturn(Future.successful(stubPostList))
when(parseAction.parseData[Comments](commentJsonData)).thenReturn(Future.successful(stubCommentsList))

when(operation.findUsersWithPosts(Future.successful(stubUserList), Future.successful(stubPostList))).thenReturn(Future.successful(stubUserWithPostList))
when(operation.findPostsWithComments(Future.successful(stubPostList), Future.successful(stubCommentsList))).thenReturn(Future.successful(stubPostWithCommentList))
*/

/*val stubUserList = List(Users("1", "1", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12",
  Company("13", "14", "15")),
  Users("2", "2", "3", "4", Address("5", "6", "7", "8", Geo("9", "10")), "11", "12",
    Company("13", "14", "15")))
val stubPostList = List(Posts("1", "2", "3", "4"), Posts("2", "2", "3", "4"))
val stubCommentsList = List(Comments("1", "2", "3", "4", "5"), Comments("2", "2", "3", "4", "5"))

val stubUserWithPostList = List(UsersWithPosts(stubUserList.head, stubPostList),
  UsersWithPosts(stubUserList(1), stubPostList))
val stubPostWithCommentList = List(PostsWithComments(stubPostList.head, stubCommentsList),
  PostsWithComments(stubPostList(1), stubCommentsList))


implicit val formats: DefaultFormats.type = DefaultFormats
val userJsonData: String = write(stubUserList)
val postJsonData: String = write(stubPostList)
val commentJsonData: String = write(stubCommentsList)

when(getUrlData.getData(userUrl)).thenReturn(Future(userJsonData))
when(getUrlData.getData(postUrl)).thenReturn(Future(postJsonData))
when(getUrlData.getData(commentUrl)).thenReturn(Future(commentJsonData))

when(parseAction.parseData[Users](Future(userJsonData))).thenReturn(Future(stubUserList))
when(parseAction.parseData[Posts](Future(postJsonData))).thenReturn(Future(stubPostList))
when(parseAction.parseData[Comments](Future(commentJsonData))).thenReturn(Future(stubCommentsList))

when(operation.findUsersWithPosts(Future(stubUserList), Future(stubPostList))).thenReturn(Future(stubUserWithPostList))
when(operation.findPostsWithComments(Future(stubPostList), Future(stubCommentsList))).thenReturn(Future(stubPostWithCommentList))

when(operation.findUserWithMaxPosts(Future(stubUserWithPostList))).thenReturn(Future("sdsfddfs"))
when(operation.findUserWithMaxPostComments(Future(stubPostWithCommentList), Future(stubUserList))).thenReturn(Future("2"))

"find Answers method " should " find User with Maximum number of posts " in {
  val actualResult = obj.findAnswers("posts")


  actualResult map { value => assert(value == "Clementina DuBuque") }
}

"it " should " find User whose Post has maximum number of comments " in {
  val actualResult = obj.findAnswers("comments")
  actualResult map { value => assert(value == "Clementina DuBuque") }
}

"it " should " fail if anything other than posts/comments is entered " in {
  val actualResult = obj.findAnswers("aaaa")
  actualResult map { value => assert(value == "Not valid") }
}*/

