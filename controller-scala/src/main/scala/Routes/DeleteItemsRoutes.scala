import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import upickle.default._
import akka.http.scaladsl.model.StatusCodes.NoContent

object DeleteItemsRoutes {
  final def apply(): Route = delete {
    Validation("delete-items") { body: ujson.Value =>
      {
        val method: String    = body("method").str
        val key: List[String] = read[List[String]](body("key"))

        complete(NoContent, ItemsService.delete(method, key))
      }
    }
  }
}