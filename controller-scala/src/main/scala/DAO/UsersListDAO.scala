import java.time.LocalDateTime
import doobie.Fragment
import doobie.Fragments.{whereOrOpt}
import doobie._
import doobie.implicits._
import doobie.implicits.javatime._

trait UsersListDAO extends ListDAOTrait {
  final def list(
      offset: Int,
      pageLength: Int,
      search: Option[String],
      sort: Sort
  ) = {
    val matchesUsernameFragment: Option[Fragment] =
      search.map(s => fr"username ILIKE ${s"%$s%"}")

    val matchesEmailAddressFragment: Option[Fragment] =
      search.map(s => fr"email_address ILIKE ${s"%$s%"}")

    val matchesNameFragment: Option[Fragment] =
      search.map(s => fr"first_name ILIKE ${s"%$s%"}")

    val whereFragment: Fragment =
      whereOrOpt(
        matchesUsernameFragment,
        matchesEmailAddressFragment,
        matchesNameFragment
      )

    val withListFragment: Fragment = listFragment(
      offset,
      pageLength,
      search,
      sort,
      whereFragment
    )

    val queryFragment: Fragment = fr"""
    $withListFragment
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
