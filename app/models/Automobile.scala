package models

import java.util.UUID

import play.api.libs.json.Json

import scala.compat.Platform
import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.TableQuery

/**
 * Created by abhijeetd on 10/15/14.
 */
case class Automobile(
                       userid: UUID,
                       title: String,
                       description: String,
                       make: String,
                       model: String,
                       year: Int,
                       transmission: String,
                       fuel: String,
                       title_status: String,
                       price: Int,
                       color: Option[String] = None,
                       odometer: Option[Int] = None,
                       vin: Option[String] = None,
                       size: Option[String] = None,
                       bodytype: Option[String] = None,
                       drive: Option[String] = None,
                       engine: Option[String] = None,
                       created: Long = Platform.currentTime,
                       updated: Long = Platform.currentTime,
                       id: UUID = UUID.randomUUID()
                       )

object Automobile {
  val automobiles = TableQuery[Automobiles]
  lazy val database = Database.forURL("jdbc:postgresql://localhost:5432/squid", driver = "org.postgresql.Driver")

  def add(automobile: Automobile) = {
    println("Inserting automobile: " + automobile)
    database.withSession { implicit session =>
      automobiles += automobile
    }
  }

  def findById(id: UUID) = {
    database.withSession { implicit session =>
      automobiles.filter(_.id === id).firstOption
    }
  }

  def findByUserId(userId: UUID) = {
    database.withSession { implicit session =>
      automobiles.filter(_.userid === userId).list
    }
  }

  def listAll = {
    database.withSession { implicit session =>
      automobiles.list
    }
  }

  def list(offset: Int = 0, size: Int = 50) = {
    database.withSession { implicit session =>
      automobiles.list.drop(offset).take(size)
    }
  }
}


class Automobiles(tag: Tag) extends Table[Automobile](tag, "automobiles") {
  def id = column[UUID]("id", O.PrimaryKey)

  def userid = column[UUID]("userid")

  def title = column[String]("title")

  def description = column[String]("description")

  def make = column[String]("make")

  def model = column[String]("model")

  def year = column[Int]("year")

  def transmission = column[String]("transmission")

  def fuel = column[String]("fuel")

  def titleStatus = column[String]("title_status")

  def price = column[Int]("price")

  def color = column[Option[String]]("color")

  def odometer = column[Option[Int]]("odometer")

  def vin = column[Option[String]]("vin")

  def size = column[Option[String]]("size")

  def bodytype = column[Option[String]]("bodytype")

  def drive = column[Option[String]]("drive")

  def engine = column[Option[String]]("engine")

  def created = column[Long]("created")

  def updated = column[Long]("updated")

  def * = (userid, title, description, make, model, year, transmission, fuel, titleStatus, price, color, odometer, vin, size, bodytype, drive, engine, created, updated, id) <>((Automobile.apply _).tupled, Automobile.unapply)
}