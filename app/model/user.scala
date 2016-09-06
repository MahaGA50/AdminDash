package model

import play.api.libs.json.{Json, Reads, JsPath, Writes}
import play.api.libs.functional.syntax._
case class User(id:String, firstName: String, lastName: String, email:Email, location:String,
                  deactivated:Boolean, createdAt:String, contects:Option[Int]= Some(0))

case class Email( value: String, isActive: Option[Boolean] )

case class Contact(key:String, value:Int)

object Email {
  implicit val emailFormat = Json.format[Email]
}
object Contact {
  implicit val emailFormat = Json.format[Contact]
}
object User {
    implicit val usersWriteFormat: Writes[User] = (
      (JsPath \ "owner").write[String] and
      (JsPath \ "names" \ "primary" \"first").write[String] and
      (JsPath \ "names" \ "primary" \"last").write[String] and
      (JsPath \ "emails" )(0).write[Email] and
      (JsPath \ "locations" \ "current" \ "country").write[String] and
      (JsPath \ "validCorporate").write[Boolean] and
      (JsPath \ "createdAt").write[String] and
      (JsPath \ "count").writeNullable[Int]
    )(unlift(User.unapply))

    implicit val usersReadFormat: Reads[User] = (
      (JsPath \ "owner").read[String] and
      (JsPath \ "names" \ "primary" \ "first").read[String] and
      (JsPath \ "names" \ "primary" \ "last").read[String] and
      (JsPath \ "emails" )(0).read[Email] and
      (JsPath \ "locations" \ "current" \ "country").read[String] and
      (JsPath \ "validCorporate").read[Boolean] and
      (JsPath \ "createdAt").read[String] and
      (JsPath \ "count").readNullable[Int]
    )(User.apply(_:String, _: String, _: String,_: Email, _:String, _:Boolean, _: String, _:Option[Int]))

}


