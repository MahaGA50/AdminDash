package controllers

import javax.inject.{Inject, Singleton}

import model.{Contact, DBConnection, User}
import play.api.mvc._

@Singleton
class Application @Inject() (dbConn: DBConnection ) extends Controller {
  implicit lazy val bucket = dbConn.bucket
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getAllUsers = Action {
    val allDocument :List[User] = Helper.getAllUsersDocument
    val mapOfAlldoc = Helper.changeToMap(allDocument)
    val countOfcontact : List[Contact] = Helper.countUserContacts

    countOfcontact.foreach( e => { mapOfAlldoc.get(e.key) match {
      case Some(user) => mapOfAlldoc += (e.key -> user.copy(contects = Some(e.value)))
      case None => None
    }})

    Ok(views.html.user.listAllUsers(mapOfAlldoc.values.toList))
  }


}