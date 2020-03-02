package com.knoldus

import com.knoldus.service.OperationFinders
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AsyncFlatSpec


class OperationFindersSpec extends AsyncFlatSpec with BeforeAndAfterAll {

  var obj: OperationFinders = _

  override def beforeAll(): Unit = {
    obj = new OperationFinders()
  }


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
  }
}
