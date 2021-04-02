import java.time.LocalDateTime
import doobie.Fragment
import doobie.Fragments.{whereOrOpt}
import doobie._
import doobie.implicits._
import doobie.implicits.javatime._

trait UsersListDAO {
  final def list(offset: Int, pageLength: Int, search: Option[String]) = {
    val matchesUsernameFragment = search.map(s => fr"username ILIKE ${s"%$s%"}")
    val matchesEmailAddressFragment =
      search.map(s => fr"email_address ILIKE ${s"%$s%"}")
    val matchesNameFragment = search.map(s => fr"first_name ILIKE ${s"%$s%"}")

    val whereFragment: Fragment =
      whereOrOpt(
        matchesUsernameFragment,
        matchesEmailAddressFragment,
        matchesNameFragment
      )

    val sortFragment: Fragment = fr"ORDER BY edited_at DESC"

    val pageFragment: Fragment = fr"LIMIT $pageLength OFFSET $offset"

    val queryFragment: Fragment = fr"""
    WITH total AS(
        SELECT
            *
          , COUNT(*) OVER() AS total_count
        FROM users_list
    ), filtered AS(
        SELECT
            *
          , COUNT(*) OVER() AS filtered_count
        FROM total
        $whereFragment
        $sortFragment
    ), limited AS(
      SELECT
          *
        , row_number() OVER() AS row_number
      FROM filtered
      $pageFragment
    ), page AS(
      SELECT
          *
        , MIN(row_number) OVER() AS page_start
        , MAX(row_number) OVER() AS page_end
      FROM limited
    )
    SELECT 
        total_count
      , filtered_count
      , page_start
      , page_end
      , username
      , email_address
      , first_name
      , last_name
      , other_names
      , created_at
      , edited_at
    FROM page
    """

    queryFragment
      .query[
        (
            Int,
            Int,
            Int,
            Int,
            String,
            String,
            String,
            String,
            Option[String],
            LocalDateTime,
            Option[LocalDateTime]
        )
      ]
      .to[List]
  }
}
