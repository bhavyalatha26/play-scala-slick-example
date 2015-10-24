package service

import com.google.inject.ImplementedBy
import model.User
import scala.concurrent.Future

@ImplementedBy(classOf[UserServiceImpl])
trait UserService {

  def addUser(user:User) : Future[String]
  def getUser(id : Long) : Future[Option[User]]
  def deleteUser(id : Long) : Future[Int]
  def listAllUsers : Future[Seq[User]]

}
