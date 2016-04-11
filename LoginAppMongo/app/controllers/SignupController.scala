package controllers

import model.{User, UserOperation}
import play.api.data.Forms._
import play.api.data._
import play.api.mvc.{Action, _}
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.i18n.Messages.Implicits._
import play.api.Play.current


case class UserSignUpData(id:Int,name:String,email:String,password:String,confirm:String)

class SignupController extends Controller{

  val obj = new UserOperation
  val userForm=Form(
    mapping(
      "id" -> number,
      "name" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText,
      "confirm-password"->nonEmptyText
    )(UserSignUpData.apply)(UserSignUpData.unapply)
      .verifying("Invalid user name or password",result => check(result)
    )
  )


  def check(data: UserSignUpData):Boolean = {
    if(data.password == data.confirm)
      true
    else
      false
  }


  def showSignUpPage:Action[AnyContent] =Action { implicit request =>
    Ok(views.html.sign_up(userForm))
  }

  def createUser: Action[AnyContent] = Action { implicit request =>
      userForm.bindFromRequest.fold(
        formWithErrors =>{
          BadRequest(views.html.sign_up(formWithErrors))
         },
        UsersData => {
          val data = User(UsersData.id,UsersData.name,UsersData.password,UsersData.email)
          val res=obj.register(data)
          val result = res match {
            case true =>
              Redirect(routes.AccountController.showAccountsPage).withSession("email" -> UsersData.email)
            case false =>
//              Redirect(routes.AccountController.showAccountsPage).withSession("connected" -> UsersData.email)
              Ok("Added New User")
          }
          result
        })
  }

}
