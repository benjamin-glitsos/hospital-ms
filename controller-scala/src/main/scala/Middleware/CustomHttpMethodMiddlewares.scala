import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

object CustomHttpMethodMiddlewares extends CustomHttpMethodsMixin {
  final val report: Directive0 = method(REPORT)
}
