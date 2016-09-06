package controllers

import com.couchbase.client.java.AsyncBucket
import model._
import play.api.libs.json.Json
import rx.lang.scala.JavaConversions._


object Helper {
  def getAllUsersDocument()(implicit bucket:AsyncBucket)= {
    val usersIds = toScalaObservable(DBHandler.getViewResult("analytics","by_users")).flatMap(_.rows()).
      toBlocking.toList.map { doc => doc.id() }
    usersIds.map{ doc => toScalaObservable(DBHandler.getDocument(doc)).map(docRow =>
        Json.parse(docRow.content()).validate[User].fold(
          invalid = { fieldErrors => None },
          valid = { user => Some(user) }
        )).toBlocking.toList
    }.flatMap(d => d).flatMap(a => a)
  }

  def countUserContacts()(implicit bucket:AsyncBucket) = toScalaObservable(DBHandler.getViewResult("contacts","by_owner",true,true))
    .flatMap(_.rows()).
    toBlocking.toList.map { doc =>
    Contact(doc.key().toString,doc.value().toString.toInt)
  }

  def changeToMap( list : List[User]) ={
    list.foldLeft(scala.collection.mutable.Map.empty[String, User])((map, value) => map += (value.id -> value))
  }

}
