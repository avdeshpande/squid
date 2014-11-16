package models

import java.util.UUID

import play.api.libs.json._

import scala.compat.Platform
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.TableQuery

/**
 * Created by abhijeetd on 10/22/14.
 */
case class AutomobileContent(
                              automobileid: UUID,
                              filename: String,
                              created: Long = Platform.currentTime,
                              updated: Long = Platform.currentTime,
                              id: UUID = UUID.randomUUID()
                              )

object AutomobileContent {
  implicit val automobileContentFormat = Json.format[AutomobileContent]
  val automobileContents = TableQuery[AutomobileContents]
  lazy val database = Database.forURL("jdbc:postgresql://localhost:5432/squid", driver = "org.postgresql.Driver")

  def add(autoContent: AutomobileContent) = {
    println("inserting automobile content")
    database.withSession { implicit session =>
      automobileContents += autoContent
    }
  }

  def findByAutomobileId(autoID: UUID) = {
    database.withSession { implicit session =>
      automobileContents.filter(_.automobileid === autoID).list
    }
  }

  def findById(id: UUID) = {
    database.withSession { implicit session =>
      automobileContents.filter(_.id === id).firstOption
    }
  }
}

class AutomobileContents(tag: Tag) extends Table[AutomobileContent](tag, "automobile_contents") {
  def id = column[UUID]("id", O.PrimaryKey)

  def automobileid = column[UUID]("automobileid")

  def filename = column[String]("filename")

  def created = column[Long]("created")

  def updated = column[Long]("updated")

  def * = (automobileid, filename, created, updated, id) <>((AutomobileContent.apply _).tupled, AutomobileContent.unapply)
}