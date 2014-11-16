package util

import play.api.{Application, GlobalSettings}

import scala.slick.driver.PostgresDriver.simple._

/**
 * Created by abhijeetd on 9/26/14.
 */

object Global extends GlobalSettings {
  override def onStart(app: Application) = {
    implicit lazy val database = Database.forURL("jdbc:postgresql://localhost:5432/squid", driver = "org.postgresql.Driver")
  }
}