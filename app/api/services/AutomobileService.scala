package api.services

import api.resources.AutomobileResource
import models._

/**
 * Created by abhijeetd on 10/24/14.
 */
object AutomobileService {
  def getAllAutomobiles() = {
    Automobile.listAll.map(automobile => AutomobileResource(
      automobile.userid,
      automobile.title,
      automobile.description,
      automobile.make,
      automobile.model,
      automobile.year,
      automobile.transmission,
      automobile.fuel,
      automobile.title_status,
      automobile.price,
      automobile.color,
      automobile.odometer,
      automobile.vin,
      automobile.size,
      automobile.bodytype,
      automobile.drive,
      automobile.engine,
      automobile.created,
      automobile.updated,
      automobile.id,
      AutomobileContent.findByAutomobileId(automobile.id)
    ))
  }
}