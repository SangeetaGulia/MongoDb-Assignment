import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class HomeControllerSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication {
      route(FakeRequest(GET, "/abc")) must beSome.which(status(_) == 404)
    }



    "unauthorised access to home page" in new WithApplication {
      val home = route(FakeRequest(GET, "/home")).get

      status(home) must equalTo(401)
      //contentType(home) must beSome.which(_ == "text/html")
      //contentAsString(home) must contain("email")
    }

    "show logout page" in new WithApplication {
      val home = route(FakeRequest(GET, "/logout")).get

      status(home) must equalTo(200)
      //contentType(home) must beSome.which(_ == "text/plain")
      //contentAsString(home) must contain("Welcome")
    }


  }
}

