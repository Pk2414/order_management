# Campus Cafe

A small campus cafeteria ordering app built with Spring Boot and a responsive web front end. Students can create an account, browse the menu, add items to a bag, place an order, and review past orders. Payment is intentionally not part of the flow; students pay at pickup.

## Run locally

Requirements: Java 17 or later and Maven 3.8+.

```bash
mvn spring-boot:run
```

Then open [http://localhost:8080](http://localhost:8080). The first startup creates the H2 database and seeds the menu. Data is stored under `./data` and remains available after restarting the app.

To build a runnable jar:

```bash
mvn package
java -jar target/cafeteria-1.0.0.jar
```

## Project layout

- `src/main/java/com/campus/cafeteria/controller` — signup, login, menu, and order API
- `src/main/java/com/campus/cafeteria/model` — users, menu items, orders, and order lines
- `src/main/java/com/campus/cafeteria/repository` — Spring Data JPA repositories
- `src/main/resources/static` — single-page interface, styles, and browser code
- `src/main/resources/application.properties` — server, session, and H2 configuration

## API overview

- `POST /api/auth/signup` and `POST /api/auth/login` establish a session
- `GET /api/auth/me` and `POST /api/auth/logout` manage the current session
- `GET /api/menu` returns the seeded menu
- `POST /api/orders` places an order for the signed-in student
- `GET /api/orders` returns that student's order history

The UI and API are served from the same origin. Passwords are stored as BCrypt hashes, and orders are associated with the signed-in user.
