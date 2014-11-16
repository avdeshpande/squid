package controllers

import java.util.UUID

import api.services.AutomobileService
import models.{Automobile, User, Users}
import play.api.Play._
import play.api.libs.json.{JsSuccess, Json}
import play.api.mvc._
import util.{Facebook, FacebookUser}

//import scala.concurrent.ExecutionContext.Implicits.global

import scala.slick.driver.PostgresDriver.simple._
import scala.slick.lifted.TableQuery

object Application extends BaseController {
  val profileContentPath = current.configuration.getString("content.root.path").get + "/profiles/"
  val automobileContentPath = current.configuration.getString("content.root.path").get + "/automobiles/"
  implicit val context = scala.concurrent.ExecutionContext.Implicits.global

  def index1 = Action { implicit request =>
    //    User.add(User("Abhijeet1"))
    val user = User.findById(UUID.fromString("570e2d72-6602-47a4-a0fd-182c084015f8"))
    println(user)
    val users = TableQuery[Users]
    lazy val database = Database.forURL("jdbc:postgresql://localhost:5432/squid", driver = "org.postgresql.Driver")
    database.withSession { implicit session =>
      // create the schema
      //      users.ddl.create
      // insert two User instances
      //          println("Inserting in users")
      //                users += User("Abhijeet","Deshpande","avdeshpande@gmail.com","m")
      User.add(User("Abhijeet1", "Deshpande", "avdeshpande@gmail.com", "4086149118", "m"))
      //          users += User("Fred Smith")
      //      println(users.list)
      //      println("Printing users****************")
      users.foreach(user => println(user))
      //      users.foreach(user => println(Json.stringify(Json.toJson(user))))
      //      users.foreach(user => Json.toJson(user).validate[User] match {
      //        case u: JsSuccess[User] => println (u.get.id)
      //        case _  => println("error")
      //      })
    }
    Ok(views.html.index("Your new application is ready.", None))
  }

  def login = Action { implicit request =>
    Ok("Login done")
  }

  def index = UserAuthenticated({ implicit request =>
//    Automobile.add(Automobile(UUID.fromString("43b6ff26-3bec-41ae-b310-824d2724e237"), "toyota camry LE 2011", "toyota camry LE 2011 description", "camry", "LE", 2011, "automatic", "gas", "clean", 10000))
//    AutomobileContent.add(AutomobileContent(UUID.fromString("629842c4-147e-421e-a2b9-adf07a0d369a"), "content1.jpg"))
//    AutomobileContent.add(AutomobileContent(UUID.fromString("629842c4-147e-421e-a2b9-adf07a0d369a"), "content2.jpg"))
//    AutomobileContent.add(AutomobileContent(UUID.fromString("629842c4-147e-421e-a2b9-adf07a0d369a"), "content3.jpg"))
//    AutomobileContent.add(AutomobileContent(UUID.fromString("629842c4-147e-421e-a2b9-adf07a0d369a"), "content4.jpg"))
//    AutomobileContent.add(AutomobileContent(UUID.fromString("629842c4-147e-421e-a2b9-adf07a0d369a"), "content5.jpg"))
//    AutomobileContent.add(AutomobileContent(UUID.fromString("629842c4-147e-421e-a2b9-adf07a0d369a"), "content6.jpg"))
//    AutomobileContent.add(AutomobileContent(UUID.fromString("629842c4-147e-421e-a2b9-adf07a0d369a"), "content7.jpg"))
//    AutomobileContent.add(AutomobileContent(UUID.fromString("629842c4-147e-421e-a2b9-adf07a0d369a"), "content8.jpg"))
    println(Json.toJson(Automobile.listAll))
    Ok(views.html.index("User authenticated.", Some(request.user)))
  })

  def dummy = UserAuthenticated { implicit request =>
    Ok("Hello world, dummies!")
  }

  def signup = Action { implicit request =>
    println(Json.toJson(AutomobileService.getAllAutomobiles()))
    request.session.get("accessToken").map { access_token =>
      Redirect(routes.Application.index())
    }.getOrElse {
      Ok(views.html.signup(None, Automobile.listAll))
    }
  }

  def submit = Action { implicit request =>
    Ok("Form saved")
  }

  def fbOAuth = Action { request =>
    val oAuthUrl = Facebook.generateOAuthUrl()
    Redirect(oAuthUrl)
  }

  def fbOAuthReturn(state: String, code: String) = Action.async { implicit request =>
    Facebook.retrieveAccessToken(state, code).flatMap {
      tokenResponse => Facebook.tryExtractTokenFromResponse(tokenResponse.body) match {
        case Some((token, expires)) => {
          Facebook.me(token).map {
            fbUserResponse =>
              println("JSON => " + fbUserResponse.json)
              fbUserResponse.json.validate[FacebookUser] match {
                case fbUserInfo: JsSuccess[FacebookUser] => {
                  val fbUser = fbUserInfo.get
                  println(fbUser)
                  User.findByEmail(fbUser.email) match {
                    case Some(user: User) => {
                      println("Found User")
                      navigateOnOAuthReturn(user, request)
                    }
                    case None => {
                      val newUser = User(fbUser.first_name, fbUser.last_name, fbUser.email, "4086149118", fbUser.gender.substring(0, 1), Some(fbUser.imgUrl))
                      User.add(newUser)
                      navigateOnOAuthReturn(newUser, request)
                    }
                  }
                }
                case _ => {
                  Ok
                }
              }
          }
        }
      }
    }
  }

  def navigateOnOAuthReturn(user: User, request: Request[AnyContent]) = {
    val newSession = request.session + ("accessToken" -> user.id.toString) - "postOAuthUri"
    request.session.get("postOAuthUri") match {
      case Some(url) =>
        println(url)
        Redirect(url).withSession(newSession)
      case None =>
        Redirect(routes.Application.index()).withSession(newSession)
    }
  }

  def autoImage(id: String) = Action {
    Ok.sendFile(content = new java.io.File(automobileContentPath + id),
      inline = true
    )
  }
}