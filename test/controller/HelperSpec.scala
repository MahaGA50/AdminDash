package controller


import com.couchbase.client.java.CouchbaseCluster
import com.couchbase.client.java.document.RawJsonDocument
import controllers.Helper
import model.{Email, User}
import org.scalatest.{BeforeAndAfter, FlatSpec}
import play.api.libs.json.Json
import rx.lang.scala.JavaConversions._

class HelperSpec extends FlatSpec with BeforeAndAfter {
  lazy val cluster = CouchbaseCluster.create("127.0.0.1")
  lazy implicit val bucket = cluster.openBucket("smardex").async()
  before {
    val user1 =
      """{
                 |	"createdAt": "1470921294999",
                 |	"emails": [{
                 |		"value": "maha@gmail.com"
                 |	}],
                 |	"locations": {
                 |		"current": {
                 |			"country": "EG" },
                 |		"home": {
                 |			"country": "EG" }
                 |	},
                 |	"names": {
                 |		"primary": {
                 |			"first": "Radwa",
                 |			"last": "gamal" }
                 |	},
                 |	"owner": "0acf7858-a4d0-493b-99e4-f1aa69b7d880",
                 |	"phones": [{
                 |		"mobile": "+201005219974",
                 |		"type": "Mobile",
                 |		"value": "+20 100 521 9974"
                 |	}],
                 |	"validCorporate": false
                 |}"""
    bucket.upsert(RawJsonDocument.create("0acf7858-a4d0-493b-99e4-f1aa69b7d880", user1))
  }

   "An users document " should "have a document " in {
     assert(!Helper.getAllUsersDocument.isEmpty)
   }

   "An users document " should "have a document type is list[user]" in {
     assert(Helper.getAllUsersDocument.isInstanceOf[List[User]] )
   }

   "An users document " should "have a document of user name" in  {
     assert(Helper.getAllUsersDocument().exists(_.firstName =="Radwa") == true)
  }



}
