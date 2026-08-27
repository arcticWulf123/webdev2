# webdev2 — Spring Boot Product Service

A Spring Boot application demonstrating dependency injection, externalized
configuration, and a startup report powered by Spring's IoC container.

## Building the Application

From the project root:

**Windows**
```
.\mvnw.cmd clean package
```

**macOS / Linux**
```
./mvnw clean package
```

This compiles the code, runs any tests, and packages the app into a runnable
JAR under `target/`.

## Running the Application

Windows:
```
.\mvnw.cmd spring-boot:run
```

macOS / Linux:
```
./mvnw spring-boot:run
```

## Port

The application runs on port **8082**, set via `server.port=8082` in
`application.properties`.

## Tasks Done

1. **Bootstrap the Project** — Generated the Spring Boot project with Spring
   Initializr (Maven, Java 17, Spring Web) and replaced the Week 1 project
   files while keeping the existing `.git` folder. Configured the app to run
   on port 8082 and verified it starts with no errors.
2. **Service & Repository Beans** — Created the `Product` class (name,
   price), a `ProductRepository` annotated with `@Repository` holding an
   in-memory `List<Product>`, and a `ProductService` annotated with
   `@Service` that filters products above a given price threshold.
3. **Constructor Dependency Injection** — Updated `ProductService` to
   receive `ProductRepository` through its constructor instead of creating
   it manually, letting Spring's IoC container wire the dependency.
4. **Externalized Configuration** — Added `shop.name` and `shop.currency` to
   `application.properties` and created a `ShopProperties` class annotated
   with `@ConfigurationProperties(prefix = "shop")` to bind them, then
   injected it into `ProductService` alongside `ProductRepository`.
5. **Startup Wiring Report** — Added a `CommandLineRunner` bean that runs
   automatically at startup, using the fully wired `ProductService` to print
   the shop name, currency, and filtered product list to the console.

## What Happens on Startup

When the application starts, Spring:

1. Builds the `ProductRepository` bean (in-memory product list).
2. Builds the `ShopProperties` bean from `application.properties`.
3. Builds `ProductService`, injecting both of the above via its constructor.
4. Runs the `CommandLineRunner` bean, which uses `ProductService` to print a
   startup report to the console, e.g.:

```
================================
       PRODUCT REPORT
================================
Shop: My Web Store
Currency: PHP

Products above 5000.0:

Laptop - 45000.0
Monitor - 12000.0
Printer - 8500.0
================================
```

You should also see log output confirming the embedded server started on
port `8082` with no errors.