package util

import java.util.UUID

import models.User
import play.api.mvc.Session

/**
 * Created by abhijeetd on 10/8/14.
 */
object Utils {
  def retrieveUserFromSession(session: Session): Option[User] = {
    if (isAuthenticated(session))
      User.findById(UUID.fromString(session.get("accessToken").get))
    else
      None
  }

  def isAuthenticated(session: Session): Boolean = {
    session.get("accessToken") match {
      case Some(token) =>
        val expirationTS = (session.get("fbTokenExpires").getOrElse("0").toLong * 1000)
        System.currentTimeMillis < expirationTS
        true //hack
      case None =>
        false
    }
  }
}
