# Tareas Aseo - Backend Reactivo (Hexagonal + JWT)

## Servicios
- `auth-service` (Spring WebFlux + R2DBC MySQL)
- `task-service` (Spring WebFlux + R2DBC PostgreSQL)
- `mysql`
- `postgres`

## Levantar todo
```bash
docker compose up --build
```

## Endpoints principales

### Auth Service (`http://localhost:8081`)
- `POST /auth/register`
- `POST /auth/login`
- `GET /users` (solo ADMIN)

### Task Service (`http://localhost:8082`)
- `POST /tasks` (ADMIN)
- `PUT /tasks/{id}` (ADMIN)
- `DELETE /tasks/{id}` (ADMIN)
- `GET /tasks` (ADMIN/USER)
- `GET /tasks/{id}` (ADMIN/USER)
- `PUT /tasks/{id}/complete` (ADMIN/USER)

## Credenciales de ejemplo
1. Registra un usuario ADMIN en MySQL con script/manualmente.
2. Usa `/auth/login` para obtener JWT.
3. Env�a `Authorization: Bearer <token>` al task-service.
