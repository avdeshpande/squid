package controllers

import models.User
import play.api.mvc._
import util.Utils

/**
 * Created by abhijeetd on 10/7/14.
 */
trait BaseController extends Controller {

  def UserAuthenticated(f: UserAuthenticatedRequest => Result) = Action { implicit request =>
    Utils.retrieveUserFromSession(request.session) match {
      case Some(user: User) =>
        f(UserAuthenticatedRequest(user, request))
      case None =>
        val newSession = request.session + ("postOAuthUri" -> request.uri)
//        println("Redirecting to signup: " + newSession.get("userId").getOrElse("no user"))
        //        Redirect(routes.Application.signup).withSession(newSession)
        Ok(views.html.home("User un-authenticated.")).withSession(newSession)
    }
  }

  case class UserAuthenticatedRequest(val user: User, request: Request[AnyContent]) extends WrappedRequest(request)

}
