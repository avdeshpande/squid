package controllers

import java.util.UUID

import api.resources.UserResource
import api.services.UserService
import play.api.libs.json.Json
import play.api.mvc.Action

/**
 * Created by abhijeetd on 11/23/14.
 */
object UserController extends BaseController {
  def getById(id: UUID) = Action {
    UserService.findById(id) match {
      case user: UserResource => Ok(Json.toJson(user))
      case None => NotFound("User with this ID not found.")
    }
  }
}
