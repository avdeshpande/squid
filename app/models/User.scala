package models

import java.util.UUID

import play.api.libs.json._

import scala.compat.Platform
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.TableQuery

/**
 * Created by abhijeetd on 9/28/14.
 */
case class User(
                 firstName: String,
                 lastName: String,
                 email: String,
                 phone: String,
                 gender: String,
                 imageUrl: Option[String] = None,
                 created: Long = Platform.currentTime,
                 updated: Long = Platform.currentTime,
                 id: UUID = UUID.randomUUID()
                 )

object User {
  implicit val userFormat = Json.format[User]
  val users = TableQuery[Users]
  lazy val database = Database.forURL("jdbc:postgresql://localhost:5432/squid", driver = "org.postgresql.Driver")

  def add(user: User) = {
    println("Inserting user: " + user)
    database.withSession { implicit session =>
      users += user
    }
  }

  def findById(id: UUID) = {
    database.withSession { implicit session =>
      users.filter(_.id === id).firstOption
    }
  }

  def findByEmail(email: String) = {
    database.withSession { implicit session =>
      users.filter(_.email === email).firstOption
    }
  }
}

class Users(tag: Tag) extends Table[User](tag, "users") {
  // Auto Increment the id primary key column
  def id = column[UUID]("id", O.PrimaryKey)

  def firstName = column[String]("first_name")

  def lastName = column[String]("last_name")

  def email = column[String]("email")

  def phone = column[String]("phone")

  def gender = column[String]("gender")

  def imageUrl = column[Option[String]]("image_url")

  def created = column[Long]("created")

  def updated = column[Long]("updated")

  // the * projection (e.g. select * ...) auto-transforms the tupled
  // column values to / from a User
  def * = (firstName, lastName, email, phone, gender, imageUrl, created, updated, id) <>((User.apply _).tupled, User.unapply)
}

