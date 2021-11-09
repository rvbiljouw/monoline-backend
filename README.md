# monoline-backend

This repository contains the backend code for the new version of a mobile app called Monoline/Monojournal. I've yet to pick the final name. It's a entry-per-day journalling application with support for pictures.

The project is built on the idea of Hexagonal Architecture. The aim is for every component to be loosely coupled and easily testable/swappable. 

In order to substitute implementations easily I used the Koin dependency injection library for Kotlin.

The backend exposes a JSON API via Ktor

Two persistence options are provided. One writes to disk (app.monoline.adapters.flatfile) and the other (app.monoline.adapters.exposed) uses Exposed to write data into an in-memory database using h2.

