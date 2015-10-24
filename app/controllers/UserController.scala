package controllers

import com.google.inject.Inject
import model.{User, UserForm}
import play.api.mvc.{Action, Controller}
import service.UserService
import scala.concurrent.Future
import play.api.i18n.{MessagesApi, Messages, I18nSupport}
import play.api.libs.concurrent.Execution.Implicits._

/**
 * The user controller.
 *
 * @param messagesApi The Play messages API.
 * @param userService The user service implementation.
 */
 class UserController @Inject()
(userService: UserService,
 val messagesApi: MessagesApi) extends Controller with I18nSupport {

/**
 * Home Page.
 *
 * @return The result to display.
 */
  def home = Action.async { implicit  request =>
    userService.listAllUsers map { users =>
      Ok(views.html.user(UserForm.form,users))
    }
  }

/**
 * Add User.
 *
 * @return The result to display.
 */
  def addUser() = Action.async { implicit request =>
    UserForm.form.bindFromRequest.fold(
      // if any error in submitted data
      errorForm => Future.successful(Ok(views.html.user(errorForm,Seq.empty[User]))),
      data => {
        val newUser = User(0,data.firstName,data.lastName,data.mobile,data.email)
        userService.addUser(newUser).map(res =>
          Redirect(routes.UserController.home()).flashing(Messages("flash.success") -> res)
        )
      })
  }
  
/**
 * Delete User.
 *
 * @return The result to display.
 */
  def deleteUser(id : Long) = Action.async { implicit request =>
    userService.deleteUser(id) map { res =>
      Redirect(routes.UserController.home())
    }
  }

}
