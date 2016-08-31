package controller


import play.api.test.Helpers._
import org.scalatestplus.play._
import play.api.test.FakeRequest


class ApplicationSpec  extends PlaySpec with OneAppPerTest {

  "Routes" should {
    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/test")).map(status(_)) mustBe Some(NOT_FOUND)
    }
  }

  "Application" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Your new application is ready.")
    }

    "return All Users" in {
      contentAsString(route(app, FakeRequest(GET, "/admin")).get) must include ("NEXT USER")
   }
 }
}
