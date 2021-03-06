import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server._

object Version1Routes {
  final def apply(): Route =
    (AccessControlMiddleware() & HandleRejectionsMiddleware())(
      concat(
        pathPrefix("schemas")(SchemasRoutes()),
        pathPrefix("users")(UsersRoutes()),
        pathPrefix("items")(ItemsRoutes()),
        PreflightRoutes()
      )
    )
}
