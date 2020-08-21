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

object UsersDAO {
    val dc = new DoobieContext.Postgres(SnakeCase)
    import dc._

    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)
    implicit val usersSchemaMeta = schemaMeta[User]("users")
    implicit val recordSchemaMeta = schemaMeta[Record]("records")

    def insert(u: User) = {
        val q = quote {
            query[User].insert(
                _.record_id -> lift(u.record_id),
                _.staff_id -> lift(u.staff_id),
                _.username -> lift(u.username),
                _.password -> lift(u.password)
            )
        }
        run(q)
    }

    def update(u: User) = {
        val q = quote {
            query[User]
                .filter(x => x.record_id == lift(u.record_id))
                .update(
                    _.username -> lift(u.username),
                    _.password -> lift(u.password)
                )
        }
        run(q)
    }

    def list() = {
        val q = quote {
            (for {
                u <- query[User]
                r <- query[Record].join(_.id == u.record_id)
            } yield (u, r))
                .filter(_._2.deleted_at.isEmpty)
                .sortBy(_._2.edited_at)(Ord.descNullsLast)
        }
        run(q)
    }
}
