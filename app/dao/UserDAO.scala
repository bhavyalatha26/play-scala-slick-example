package dao

import com.google.inject.ImplementedBy
import model.User
import scala.concurrent.Future

@ImplementedBy(classOf[UserDAOImpl])
trait UserDAO {

  def add(user:User) : Future[String]
  def get(id : Long) : Future[Option[User]]
  def delete(id : Long) : Future[Int]
  def listAll : Future[Seq[User]]

}
