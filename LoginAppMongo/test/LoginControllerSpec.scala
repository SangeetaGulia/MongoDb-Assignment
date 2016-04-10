import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class LoginControllerSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication {
      route(FakeRequest(GET, "/xyz")) must beSome.which(status(_) == 404)
    }



    "render the login page" in new WithApplication {
      val home = route(FakeRequest(GET, "/login")).get

      status(home) must equalTo(OK)
      //contentType(home) must beSome.which(_ == "text/html")
      //contentAsString(home) must contain("email")
    }

    "unauthorized access" in new WithApplication {
      val home = route(FakeRequest(POST, "/login")).get

      status(home) must equalTo(303)
      //contentType(home) must beSome.which(_ == "text/plain")
      //contentAsString(home) must contain("Welcome")
    }

    "user access" in new WithApplication {
      val home = route(FakeRequest(POST, "/login").withFormUrlEncodedBody("email"->"sang@sang.com","password"->"1234")).get

      status(home) must equalTo(303)
      //contentType(home) must beSome.which(_ == "text/plain")
      //contentAsString(home) must contain("Welcome")
    }

  }
}

