import org.scalatestplus.play.PlaySpec


import play.api.test._


class IntegrationSpec extends PlaySpec {

  "Application" should {

    "work from within a browser" in new WithBrowser {

      browser.goTo("http://localhost:" + port)

      browser.pageSource must include ("Your new application is ready.")
    }
  }
}
