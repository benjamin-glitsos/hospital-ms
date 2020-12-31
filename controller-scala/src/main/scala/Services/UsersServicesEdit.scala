import doobie.implicits._
import doobie_bundle.connection._

trait UsersServicesEdit {
  final def edit(
      oldUsername: String,
      newUsername: Option[String],
      password: Option[String],
      emailAddress: Option[String],
      notes: Option[String]
  ): String = {
    try {
      UsersDAO
        .edit(oldUsername, newUsername, password, emailAddress, notes)
        .transact(xa)
        .unsafeRunSync
    } catch {
      case e: java.sql.SQLException =>
        System.err.println(e.getMessage)
        System.err.println(e.getSQLState)
    }

    new String
  }
}