package api.resources

import java.util.UUID

import models.AutomobileContent
import play.api.libs.json.Json

/**
 * Created by abhijeetd on 10/24/14.
 */
case class AutomobileResource(
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
                               color: Option[String],
                               odometer: Option[Int],
                               vin: Option[String],
                               size: Option[String],
                               bodytype: Option[String],
                               drive: Option[String],
                               engine: Option[String],
                               created: Long,
                               updated: Long,
                               id: UUID,
                               contents: List[AutomobileContent]
                               )

object AutomobileResource {
  implicit val autombileApiFormat = Json.format[AutomobileResource]
}