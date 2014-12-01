package api.services

import java.util.UUID

import api.resources.UserResource
import models.User

/**
 * Created by abhijeetd on 11/23/14.
 */
object UserService {
  def findById(id: UUID) = {
    User.findById(id) match {
      case Some(user) => {
        UserResource(
          user.firstName,
          user.lastName,
          user.email,
          user.phone,
          user.gender,
          user.imageUrl,
          user.created,
          user.updated,
          user.id,
          AutomobileService.getByUserId(user.id)
        )
      }
      case _ => None
    }
  }
}
