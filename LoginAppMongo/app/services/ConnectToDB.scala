package services

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object ConnectToDB {

  val driver = new reactivemongo.api.MongoDriver
  val connection = driver.connection(List("localhost"))
  val db = Await.result(connection.database("login_db"),10.seconds)

}
