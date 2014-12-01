package controllers

import api.services.AutomobileService
import play.api.libs.json.Json
import play.api.mvc.Action

/**
 * Created by abhijeetd on 11/20/14.
 */
object AutomobileController extends BaseController {
  def list(page: Int = 1) = Action {
    Ok(Json.toJson(AutomobileService.getAutomobiles(page)))
  }
}
