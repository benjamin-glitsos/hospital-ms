import doobie_import.database.dc._

trait ItemsCreateDAO {
  final def create(
      sku: String,
      upc: String,
      name: String,
      description: Option[String],
      acquisitionDate: String,
      expirationDate: Option[String],
      unitCost: String,
      unitPrice: Option[String],
      quantityAvailable: Int,
      quantitySold: Int,
      additionalNotes: Option[String]
  ) = {
    run(
      quote(
        query[ItemsOpen]
          .insert(
            _.sku                -> lift(sku),
            _.upc                -> lift(upc),
            _.name               -> lift(name),
            _.description        -> lift(description),
            _.acquisition_date   -> lift(acquisitionDate),
            _.expiration_date    -> lift(expirationDate),
            _.unit_cost          -> lift(unitCost),
            _.unit_price         -> lift(unitPrice),
            _.quantity_available -> lift(quantityAvailable),
            _.quantity_sold      -> lift(quantitySold),
            _.additional_notes   -> lift(additionalNotes)
          )
      )
    )
  }
}
