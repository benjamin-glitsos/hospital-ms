## Item Management System

A full-stack information system with a dockerised Model-View-Controller (MVC) architecture.

### Controller-Scala Container

* Layered architecture: Routing, Middleware, Validation, Service, DAO, and Seeding layers
* JSON Schema is used for back-end and front-end validation, and custom properties are used for custom validation messages
* RESTful API design
* Strong type-safety by using monads and other Algebraic Data Types, and an SQL query builder
* Object-oriented multiple-trait-inheritance pattern
* Optimised to minimise round-trips and network load

### Admin-React Container

* React Hooks, Context and Effects
* Container/Presenter pattern for complex components
* Ramda functional data pipelines
* UI/UX using Atlassian Design System
* Automated regression testing using Cypress with Page Object Model pattern
* Composition of test cases into both integration and E2E workflow test scenarios

### Database-PostgreSQL Container

* Architecture: rich metadata is abstracted into a separate table, however views, ‘instead of’ triggers and stored procedures allow operating on data as if it were contained in a single table
* Trigram indexes speed up text search
* Domain constraints ensure data integrity
* Fourth normal form (4NF)
