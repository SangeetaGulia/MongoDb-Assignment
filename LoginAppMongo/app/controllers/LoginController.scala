package controllers

import model.UserOperation
import play.api.data.Forms._
import play.api.data._
import play.api.mvc.{Action, _}
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.i18n.Messages.Implicits._
import play.api.Play.current

import scala.concurrent.Future


case class UserData(email:String,password:String)

class LoginController extends Controller{

  /**
    *  Login Form Creation
    */
  val obj = new UserOperation
  val userForm=Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(UserData.apply)(UserData.unapply)
  )

  def loginPage():Action[AnyContent]=Action{implicit request =>
    Ok(views.html.login(userForm))
  }

  def authenticate:Action[AnyContent] = Action.async { implicit request =>
    userForm.bindFromRequest.fold(
      formWithErrors =>{
        Future{Redirect(routes.LoginController.loginPage()).flashing("error"->"Form Contains Errors")}
        //BadRequest(views.html.login(formWithErrors)).flashing("error"->"Form Contains Errors")
  },
        UserData => {
          val isUser: Future[Boolean] = obj.login(UserData.email, UserData.password)
          isUser map {  validUser=> validUser match{
              case true => Redirect(routes.AccountController.showAccountsPage).withSession(
                "email" -> UserData.email)
              case false => Redirect(routes.LoginController.loginPage()).flashing("error" -> "Enter Valid Credentials")
            }
          }
      })
  }

}
