# java_world

A Java Spring Boot MVC RESTful application for tracking users and their tasks, featuring a modern UI with Turbo (Hotwire) and Bootstrap, PostgreSQL integration, and JUnit testing.

## Features
- Spring Boot MVC RESTful API
- PostgreSQL database integration (with JPA/Hibernate)
- JUnit for testing
- Users table with at least 25 seeded users (first and last names)
- Tasks table with one-to-many relationship (each user can have many tasks)
- Data seeding for users and example tasks
- Landing page at http://localhost:3000:
  - Displays a paginated, filterable, and Bootstrap-styled user list
  - Each user row shows their name and a badge with their task count
  - Clicking a user row navigates to their show page
  - Add user form (Turbo-powered, clears on submit)
  - Assign task form with user dropdown (shows all users)
  - Success/error banners for feedback
- User show page:
  - Lists all tasks assigned to the user
  - Each task has a 'Complete' button to remove it
  - Link back to home
- Turbo (Hotwire) for client-side interactivity (Turbo Frames, Streams, partial updates)
- Bootstrap 5 for modern, responsive UI
- Hot reloading for development (Spring Boot DevTools)
- All page titles and headings set to "Task Tracker" with a themed logo

## Getting Started
1. Build the project with Maven: `mvn clean install`
2. Run the application: `mvn spring-boot:run`
3. Visit [http://localhost:3000](http://localhost:3000) to see the landing page.

## Project Structure
- `src/main/java` - Application source code
- `src/main/resources` - Configuration and static resources
- `src/test/java` - JUnit tests

## Requirements
- Java 17 or later
- Maven
- PostgreSQL (running and accessible)

---
