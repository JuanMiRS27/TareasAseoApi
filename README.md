# Tareas Aseo API

Backend reactivo con arquitectura hexagonal compuesto por dos servicios Spring Boot WebFlux:

- `auth-service`: autenticacion JWT y gestion de usuarios sobre MySQL.
- `task-service`: gestion de tareas sobre PostgreSQL.
- `Dockerfile` raiz unico para construir cualquiera de los dos servicios con `SERVICE_MODULE`.

## Ejecucion local

Levanta todo el stack local con Docker:

```bash
docker compose up --build
```

Servicios locales:

- `auth-service`: `http://localhost:8081`
- `task-service`: `http://localhost:8082`

Build manual de imagenes:

```bash
docker build -f Dockerfile --build-arg SERVICE_MODULE=auth-service -t auth-service .
docker build -f Dockerfile --build-arg SERVICE_MODULE=task-service -t task-service .
```

Si usas un trigger de Cloud Build conectado a GitHub, no debe estar configurado en modo "Dockerfile" directo, porque ese modo no esta pasando `SERVICE_MODULE`. Debe usar `cloudbuild.yaml`.

## Despliegue en Google Cloud

El repositorio quedo preparado para desplegar ambos servicios en Cloud Run usando:

- Artifact Registry para imagenes.
- Cloud Run para ejecucion.
- Secret Manager para secretos.
- Cloud SQL o cualquier MySQL/PostgreSQL accesible por host y puerto.

### Requisitos

1. Crear un proyecto de Google Cloud.
2. Habilitar:
   - Cloud Build API
   - Cloud Run Admin API
   - Artifact Registry API
   - Secret Manager API
3. Crear un repositorio Docker en Artifact Registry.
4. Crear las bases de datos:
   - MySQL para `auth-service`
   - PostgreSQL para `task-service`
5. Crear estos secretos en Secret Manager:
   - `jwt-secret`
   - `auth-db-password`
   - `task-db-password`
   - `auth-admin-password`

### Variables que necesita el despliegue

- `auth-service`
  - `DB_HOST`
  - `DB_PORT`
  - `DB_NAME`
  - `DB_USER`
  - `DB_PASSWORD` desde Secret Manager
  - `JWT_SECRET` desde Secret Manager
  - `JWT_EXPIRATION_SECONDS`
  - `ADMIN_USERNAME`
  - `ADMIN_PASSWORD` desde Secret Manager

- `task-service`
  - `DB_HOST`
  - `DB_PORT`
  - `DB_NAME`
  - `DB_USER`
  - `DB_PASSWORD` desde Secret Manager
  - `JWT_SECRET` desde Secret Manager

### Build y deploy con Cloud Build

El archivo `cloudbuild.yaml` compila, publica y despliega ambos servicios.

Ejemplo:

```bash
gcloud builds submit --config cloudbuild.yaml \
  --substitutions _REGION=us-central1,_REPOSITORY=tareas-aseo,_AUTH_DB_HOST=AUTH_DB_HOST,_AUTH_DB_PORT=3306,_AUTH_DB_NAME=auth_db,_AUTH_DB_USER=auth_user,_TASK_DB_HOST=TASK_DB_HOST,_TASK_DB_PORT=5432,_TASK_DB_NAME=task_db,_TASK_DB_USER=task_user,_ADMIN_USERNAME=admin
```

Reemplaza `AUTH_DB_HOST` y `TASK_DB_HOST` por el host real de tus instancias.

## Endpoints principales

### Auth Service

- `POST /auth/register`
- `POST /auth/login`
- `GET /users` solo `ADMIN`
- `GET /actuator/health`

### Task Service

- `POST /tasks` solo `ADMIN`
- `PUT /tasks/{id}` solo `ADMIN`
- `DELETE /tasks/{id}` solo `ADMIN`
- `GET /tasks`
- `GET /tasks/{id}`
- `PUT /tasks/{id}/complete`
- `GET /actuator/health`

## Notas operativas

- Ambos servicios ahora crean su esquema automaticamente al arrancar mediante `schema.sql`.
- En Cloud Run el puerto se toma desde la variable `PORT`.
- `docker-compose` se mantiene solo para desarrollo local.
