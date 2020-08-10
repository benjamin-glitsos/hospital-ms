import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts

object Main {
    implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

    val xa = Transactor.fromDriverManager[IO](
        "org.postgresql.Driver",
        s"jdbc:postgresql://${System.getenv("DATABASE_SERVICE")}/${System.getenv("POSTGRES_DATABASE")}",
        System.getenv("POSTGRES_USER"),
        System.getenv("POSTGRES_PASSWORD"),
        Blocker.liftExecutionContext(ExecutionContexts.synchronous)
    )

    def insert(personId: Int, username: String, password: String): Update0 =
        sql"INSERT INTO users (person_id, username, password) values ($personId, $username, $password)".update

    def select() = {
        sql"SELECT person_id, username, password FROM users"
            .query[(Int, String, String)]
            .to[List]
            .transact(xa)
            .unsafeRunSync
            .take(5)
            .foreach(println)
    }

    def main(args: Array[String]) {
        insert(1, "un", "pw").run.transact(xa).unsafeRunSync
        select()
    }
}
