CREATE TABLE IF NOT EXISTS tasks (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL,
    assigned_user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL
);
