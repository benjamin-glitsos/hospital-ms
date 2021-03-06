import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes._
import cats.data.NonEmptyChain
import upickle_import.general._

object RejectionRoutes {
  final def notFound(): Route = complete(
    NotFound,
    SerialisedErrors(
      ErrorsUtilities.serialiseErrors(NonEmptyChain(NotFoundError()))
    )
  )

  final def badRequestError(se: String): Route = complete(
    BadRequest,
    SerialisedErrors(se)
  )

  final def internalServerError(se: String): Route = complete(
    InternalServerError,
    SerialisedErrors(se)
  )

  final def authorisationFailed(): Route = complete(
    Forbidden,
    SerialisedErrors(
      ErrorsUtilities.serialiseErrors(NonEmptyChain(AuthorisationFailedError()))
    )
  )
}
