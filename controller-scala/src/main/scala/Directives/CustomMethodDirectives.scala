import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._

object CustomMethodDirectives {
  final val report: Directive0 = method(CustomMethods.REPORT)
}
