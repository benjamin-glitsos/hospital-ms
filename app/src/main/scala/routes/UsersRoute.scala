import cats.effect._
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.syntax._
import org.http4s.dsl.io._
import org.http4s.implicits._
// import org.http4s.circe.CirceEntityEncoder._
import org.http4s.circe._
import io.circe.syntax._
import io.circe.generic.auto._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.global
import scala.concurrent.ExecutionContext.Implicits.global

case class Hello(name: String)

object UsersRoute {
    implicit val cs: ContextShift[IO] = IO.contextShift(global)

    val service = HttpRoutes.of[IO] {
        case GET -> Root => Ok(IO.fromFuture(IO(Future {
            println("I run when the future is constructed.")
            "Greetings from the future!"
        })))
        // UsersDAO.list.map(_.asJson)
    }.orNotFound
}

// TODO:
// users
// users?page=1&sort.username=desc&search.username=lorem
// users/1
// users/1?tab=person

// case GET -> Root => UsersDAO.all.flatMap(_.fold(NotFound())(Ok(_.asJson)))

// case GET -> Root => UsersDAO.all onComplete {
//     case Success(x) => Ok(x.asJson)
//     case Failure(x) => NotFound()
// }
