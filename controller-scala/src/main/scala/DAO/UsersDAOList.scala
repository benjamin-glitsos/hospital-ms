import doobie_bundle.database.dc._
import doobie.Fragment
import doobie.Fragments.{setOpt, whereAnd}
import doobie._
import doobie.implicits._
import doobie.implicits.javasql._
import doobie.implicits.javatime._

trait UsersDAOList {
  final def list(offset: Int, pageLength: Int, search: String) = {
    val select: Fragment = fr"SELECT *"

    val from: Fragment = fr"FROM users_list"

    // TODO: where clause isnt working yet
    // TODO: use whereAndOpt by making search be Option[String] hence not using default in the json schema for that
    // TODO: isInSearch will use just the name and email fields from the users_list view.
    // val notDeleted = fr"is_deleted=no"
    //
    // val isInSearch = fr"""
    // | (username LIKE "%$search%")
    // | OR (email_address LIKE "%$search%")
    // | OR (first_name LIKE "%$search%")
    // | OR (last_name LIKE "%$search%")
    // | OR (other_names LIKE "%$search%")
    // """
    //
    // val where: Fragment = whereAnd(notDeleted, isInSearch)

    val sort: Fragment = fr"ORDER BY edited_at DESC"

    val page: Fragment = fr"LIMIT $pageLength OFFSET $offset"

    (select ++ from ++ sort ++ page).query[UsersList].to[List]
  }
}
