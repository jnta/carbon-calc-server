# Carbon Calculator

## Technical Notes

### Database

Run `docker compose up` to start the MongoDB database. The database will be populated with the default collection
contents defined in the `init-mongo.js` script when first started - all default emission factors are here. These values
are only for this test and should not be
considered real values for carbon emissions :smile:

If you need to reset the database to its initial state, you can run `docker compose down -v`, which will erase the
database and repopulate the initial values in the next start.

### Running the application

You can use your IDE of choice to run the application. The main class is `CarbonCalculatorApplication`. The server will
run
on port 8085 (http://localhost:8085).

There is a swagger documentation available on http://localhost:8085/swagger-ui.html.
