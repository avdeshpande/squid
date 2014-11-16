package api.services

import models._

/**
 * Created by abhijeetd on 10/24/14.
 */
object AutomobileService {
  def getAllAutomobiles() = {
    var result: List[api.resources.Automobile] = Nil
    val automobiles = Automobile.listAll
    println(automobiles)
    automobiles.foreach {
      automobile =>
        val pictures = AutomobileContent.findByAutomobileId(automobile.id)
        result = api.resources.Automobile(
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
          pictures
        ) :: result
    }
    result
  }
}