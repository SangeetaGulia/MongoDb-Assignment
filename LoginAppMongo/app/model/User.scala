package model

import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument
import services.ConnectToDB
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._

case class User (id : Int , name:String , password: String , email : String)

class UserOperation {

  val collection = ConnectToDB.db.collection[BSONCollection]("userRecord")
  def register(user: User):Boolean = {
      try {
        val userBson = BSONDocument("id" -> user.id, "name" -> user.name, "password" -> user.password, "email" -> user.email)
        collection.insert(userBson)
        true
      }
      catch {
        case exception:Exception => false
      }

  }

  def login(email:String,password:String):Future[Boolean]={
    val query = BSONDocument("email" -> email, "password" -> password)
    val userDetails = collection.find(query).one[BSONDocument]
    userDetails map{ userDetail => userDetail match
      {
        case Some(user) => true
        case None => false
      }
    }
  }
}