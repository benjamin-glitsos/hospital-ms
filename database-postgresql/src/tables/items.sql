CREATE TABLE items (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY
  , sku SKU UNIQUE NOT NULL
  , upc UPC UNIQUE NOT NULL
  , name NAME NOT NULL
  , description LONG_TEXT
  , acquisition_date DATE NOT NULL
  , expiration_date DATE
  , unit_cost MONEY NOT NULL
  , unit_price MONEY
  , quantity_available INT_MIN_ONE NOT NULL
  , quantity_sold INT_MIN_ONE NOT NULL
  , meta_id INTEGER UNIQUE NOT NULL REFERENCES meta(id)
);
