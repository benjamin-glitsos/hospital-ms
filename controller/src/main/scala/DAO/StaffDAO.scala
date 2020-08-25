import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.postgres._
import doobie.postgres.implicits._
import io.getquill.{ idiom => _, _ }
import doobie.quill.DoobieContext

object StaffDAO {
    val dc = new DoobieContext.Postgres(SnakeCase)
    import dc._

    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
    implicit val staffSchemaMeta = schemaMeta[User]("staff")
    implicit val personSchemaMeta = schemaMeta[Person]("people")
    implicit val usersSchemaMeta = schemaMeta[User]("users")
    implicit val recordSchemaMeta = schemaMeta[Record]("records")

    def summary(id: Int) = {
        run(quote(
            (for {
                s <- query[Staff].filter(_.id == lift(id))
                p <- query[Person].join(_.id == s.person_id)
            } yield (StaffSummary(
                first_name = p.first_name,
                other_names = p.other_names,
                last_name = p.last_name,
                staff_number = s.staff_number
            )))
        ))
    }
}