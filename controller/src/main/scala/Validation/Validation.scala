import org.everit.json.schema.{Schema, ValidationException}
import org.everit.json.schema.loader.{SchemaClient, SchemaLoader}
import akka.http.scaladsl.server._
import org.json.{JSONObject, JSONTokener}
import scala.io.Source
import scala.jdk.CollectionConverters._
import akka.http.scaladsl.server.Directives._
import scala.concurrent.duration._
import akka.http.scaladsl.model.HttpEntity
import upickle.default._
import cats.Applicative
import cats.implicits._
import cats.data.Validated.{Valid, Invalid}
import cats.data.ValidatedNec
import scala.util.{Try, Success, Failure}

object Validation extends ValidationTrait {
  private val staticEndpoints = List("open-user")

  private def validateJsonSchema(
      endpointName: String,
      entityText: String
  ): Validated[ujson.Value] = {
    val entityObject: JSONObject = new JSONObject(entityText)

    val schemaSource: Source =
      Source.fromResource(s"schemas/$endpointName.json")

    val schemaString: String =
      try schemaSource.mkString
      finally schemaSource.close()

    val rawSchema: JSONObject =
      new JSONObject(
        new JSONTokener(schemaString)
      )

    val schema: Schema =
      SchemaLoader
        .builder()
        .schemaClient(SchemaClient.classPathAwareClient())
        .useDefaults(true)
        .schemaJson(rawSchema)
        .resolutionScope("classpath://schemas/")
        .draftV7Support()
        .build()
        .load()
        .build()

    Try(schema.validate(entityObject)) match {
      case Success(_) => {
        ujson.read(entityObject.toString()).validNec
      }
      case Failure(e) =>
        JsonSchemaError("TODO").invalidNec
    }
  }

  def apply(endpointName: String): Directive1[ujson.Value] =
    extractStrictEntity(3.seconds)
      .flatMap((entity: HttpEntity.Strict) => {
        if (staticEndpoints contains endpointName) {
          provide(ujson.read("{}"))
        } else {
          val entityText = entity.data.utf8String

          validateJsonSchema(endpointName, entityText) match {
            case Valid(v) => provide(v)
            case Invalid(e) =>
              reject(
                ValidationRejection("")
              )
          }
        }
      })
}