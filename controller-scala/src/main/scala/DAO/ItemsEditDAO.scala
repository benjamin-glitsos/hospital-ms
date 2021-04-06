import doobie_bundle.database.dc._
import doobie.Fragment
import doobie.Fragments.{setOpt, whereAnd}
import doobie._
import doobie.implicits._

trait ItemsEditDAO {
  final def edit(
      oldKey: String,
      newKey: Option[String],
      name: Option[String],
      description: Option[String],
      metakey: Option[String],
      notes: Option[String]
  ) = {
    val update: Fragment =
      fr"UPDATE items_with_meta"

    val set: Fragment = setOpt(
      newKey.map(s => fr"key=$s"),
      name.map(s => fr"name=$s"),
      description.map(s => fr"description=$s"),
      metakey.map(s => fr"metakey=$s"),
      notes.map(s => fr"notes=$s")
    )

    val where: Fragment = whereAnd(fr"key=$oldKey")

    (update ++ set ++ where).update.run
  }
}