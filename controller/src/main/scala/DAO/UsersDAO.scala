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

    def list() = {
        val q = quote {
            query[Users]
        }
        run(q)
    }

    def insert(u: Users) = {
        val q = quote {
            query[Users].insert(
                _.record_id -> lift(u.record_id),
                _.staff_id -> lift(u.staff_id),
                _.username -> lift(u.username),
                _.password -> lift(u.password)
            )
        }
        run(q)
    }

    def update(u: Users) = {
        val q = quote {
            query[Users]
                .filter(x => x.record_id == lift(u.record_id))
                .update(
                    _.username -> lift(u.username),
                    _.password -> lift(u.password)
                )
        }
        run(q)
    }
}
