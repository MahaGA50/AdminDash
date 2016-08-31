package controller


import controllers.Helper
import org.scalatest.FlatSpec

class HelperSpec extends FlatSpec {

  "An users document " should "have all document" in {
   assert(Helper.getAllUsersDocument.isEmpty == false)
  }

}
