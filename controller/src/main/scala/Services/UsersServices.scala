import doobie._
import cats.data.Validated.{Invalid, Valid}

object UsersServices {
    def create(user: User, user_username: String, notes: Option[String]): ConnectionIO[RecordResponse] = {
        for {
          u <- UsersDAO.open(user_username)
          r <- RecordsDAO.create(user_id = u.id, notes)
          _ <- UsersDAO.create(user.copy(record_id = r.id))
        } yield (RecordResponse(r.uuid))
    }

    def edit(u: User, user_username: String, notes: Option[String]): ConnectionIO[RecordResponse] = {
        for {
            u <- UsersDAO.open(user_username)
            r <- RecordsDAO.edit(
                id = u.record_id,
                user_id = u.id,
                notes
            )
            _ <- UsersDAO.edit(u)
        } yield (RecordResponse(r.uuid))
    }

    def list(maybeNumber: Option[Int], maybeLength: Option[Int]) = {
        val number = maybeNumber.getOrElse(1)
        val length = maybeLength.getOrElse(25)

        UsersDAO.list(Page(number, length))
    }

    def open(username: String, user_username: String): ConnectionIO[User] = {
        for {
          u <- UsersDAO.open(username)

          // val x = u match {
          //     case Valid(u) => for {
          //         s <- StaffDAO.summary(u.staff_id)
          //
          //         r <- RecordsDAO.open(
          //             id = u.record_id,
          //             u.id
          //         )
          //
          //         _ <- RecordsDAO.opened(
          //             id = u.record_id,
          //             u.id
          //         )
          //     } yield (Valid(UserOpen(
          //         user = u,
          //         relations = List(s.head),
          //         record = r.head
          //     )))
          //     case Invalid(es) => Invalid(es)
          // }
        } yield (u)
    }

    def delete(username: String, user_username: String): ConnectionIO[Int] = {
        UsersDAO.delete(username, user_username)
    }

    def restore(username: String, user_username: String): ConnectionIO[Int] = {
        UsersDAO.restore(username, user_username)
    }

    def permanentlyDelete(username: String): ConnectionIO[Long] = {
        UsersDAO.permanentlyDelete(username)
    }
}
