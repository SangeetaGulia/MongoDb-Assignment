import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class SignupControllerSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in new WithApplication {
      route(FakeRequest(GET, "/xyz")) must beSome.which(status(_) == 404)
    }



    "render the signup page" in new WithApplication {
      val home = route(FakeRequest(GET, "/signup")).get

      status(home) must equalTo(OK)
      //contentType(home) must beSome.which(_ == "text/html")
      //contentAsString(home) must contain("email")
    }

    "Incorrect input fields" in new WithApplication {
      val home = route(FakeRequest(POST, "/signup")).get

      status(home) must equalTo(400)
      //contentType(home) must beSome.which(_ == "text/plain")
      //contentAsString(home) must contain("Welcome")
    }

    "correct input fields" in new WithApplication {
      val home = route(FakeRequest(POST, "/signup").withFormUrlEncodedBody("email"->"sangi@sang.com","password"->"1234","confirm-password"->"1234")).get

      status(home) must equalTo(303)
      //contentType(home) must beSome.which(_ == "text/plain")
      //contentAsString(home) must contain("Welcome")
    }

  }
}

