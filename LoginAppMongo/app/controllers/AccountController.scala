package controllers


import play.api.mvc.{Action, _}
import play.api.i18n.Messages.Implicits._
import play.api.Play.current


class AccountController extends Controller{

  def showAccountsPage(): Action[AnyContent]=Action { implicit request =>
      request.session.get("email").map { user =>
        Ok(views.html.accountsPage(user))
      }.getOrElse {
        Unauthorized("Oops, you are not connected")
      }
  }


  def logout: Action[AnyContent] = Action {  implicit request =>
    Redirect(routes.LoginController.loginPage()).withNewSession
  }

}
