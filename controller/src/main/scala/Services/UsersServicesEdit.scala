import doobie.implicits._
import doobie_bundle.connection._
import upickle.default._

trait UsersServicesEdit {
  def edit(
      oldUsername: String,
      newUsername: Option[String],
      password: Option[String],
      emailAddress: Option[String],
      notes: Option[String]
  ): String = {
    UsersDAO
      .edit(oldUsername, newUsername, password, emailAddress, notes)
      .transact(xa)
      .unsafeRunSync

    new String
  }
}
