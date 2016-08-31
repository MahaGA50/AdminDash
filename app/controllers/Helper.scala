package controllers

import model.{Contact, DB, User}
import play.api.libs.json.Json
import rx.lang.scala.JavaConversions._


object Helper {

  def getAllUsersDocument = {
    val usersIds = toScalaObservable(DB.getViewResult("analytics","by_users")).flatMap(_.rows()).
      toBlocking.toList.map { doc => doc.id() }
    usersIds.map{ doc => toScalaObservable(DB.getDocument(doc)).map(docRow =>
        Json.parse(docRow.content()).validate[User].fold(
          invalid = { fieldErrors => None },
          valid = { user => Some(user) }
        )).toBlocking.toList
    }.flatMap(d => d).flatMap(a => a)
  }

  def countUserContacts = toScalaObservable(DB.getViewResult("contacts","by_owner",true,true))
    .flatMap(_.rows()).
    toBlocking.toList.map { doc =>
    Contact(doc.key().toString,doc.value().toString.toInt)
  }

  def changeToMap( list : List[User]) ={
    list.foldLeft(scala.collection.mutable.Map.empty[String, User])((map, value) => map += (value.id -> value))
  }

}
