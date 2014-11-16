package util

import play.api.Play._
import play.api.libs.json.Json
import play.api.libs.ws.WS

import scala.util.matching.Regex

/**
 * Created by abhijeetd on 10/8/14.
 */

case class FacebookUser(
                         id: String,
                         email: String,
                         first_name: String,
                         gender: String,
                         last_name: String,
                         link: String
                         ) {
  val imgUrl = "https://graph.facebook.com/" + id + "/picture"
}
object FacebookUser {
  implicit val fbUserFormat = Json.format[FacebookUser]
}
object Facebook {
  lazy val baseUrl = "https://graph.facebook.com/v2.1/"

  implicit val context = scala.concurrent.ExecutionContext.Implicits.global

  val appId = current.configuration.getString("facebook.appId").get
  val appSecret = current.configuration.getString("facebook.appSecret").get
  val redirectUri = current.configuration.getString("domain.urlBase").get + "/fbOAuthReturn"
  val sessionSecret = current.configuration.getString("facebook.state").get
  val requestedPermissions = current.configuration.getString("facebook.requestedPermissions").get

  def generateOAuthUrl(): String = {
    val fbOauthUrl = s"https://www.facebook.com/dialog/oauth?client_id=${appId}&redirect_uri=${redirectUri}&state=${sessionSecret}&scope=${requestedPermissions}"
    println(fbOauthUrl)
    fbOauthUrl
  }

  def retrieveAccessToken(state: String, code: String) = {
    WS.url("https://graph.facebook.com/oauth/access_token").withQueryString(
      ("client_id" -> appId),
      ("redirect_uri" -> redirectUri),
      ("client_secret" -> appSecret),
      ("code" -> code)
    ).get()
  }

  def tryExtractTokenFromResponse(resp: String): Option[(String, Long)] = {
    try {
      val tokenExtractor = new Regex("access_token=(.*?)&expires=(.*)")
      val tokenExtractor(token: String, expires: String) = resp
      val expireTimestamp = (expires.toLong * 1000) + System.currentTimeMillis
      Some((token, expireTimestamp))
    } catch {
      case e: MatchError => None
    }
  }

  def me(accessToken: String) = {
    WS.url(baseUrl + "me").withQueryString("access_token" -> accessToken).get
  }

}
