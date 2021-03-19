import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle_bundle.general._

object EditUserRoutes {
  final def apply(username: String): Route = get {
    Validation("open-user") { body: ujson.Value =>
      complete(UsersServices.open(username))
    }
  }
}
