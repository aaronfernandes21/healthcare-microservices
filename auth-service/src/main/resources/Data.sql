-- Ensure the 'users' table exists
CREATE TABLE IF NOT EXISTS "users" (
                                       id UUID PRIMARY KEY,
                                       email VARCHAR(255) UNIQUE NOT NULL,
                                       password VARCHAR(255) NOT NULL,
                                       role VARCHAR(50) NOT NULL
);

-- Insert the user if no existing user with the same id or email exists
INSERT INTO "users" (id, email, password, role)
SELECT '223e4567-e89b-12d3-a456-426614174006', 'testuser@test.com',
       '$2b$12$7hoRZfJrRKD2nIm2vHLs7OBETy.LWenXXMLKf99W8M4PUwO6KB7fu', 'ADMIN'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '223e4567-e89b-12d3-a456-426614174006'
       OR email = 'testuser@test.com'
);

INSERT INTO "users" (id, email, password, role)
SELECT '323e4567-e89b-12d3-a456-426614174007', 'aaron@gmail.com',
       '$2a$10$8Wf2qVQ8QpK9h9cS6Y6u4u9gqF0n5vX1n6xQ2hV2b8y6X5f3W3a2K', 'USER'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '323e4567-e89b-12d3-a456-426614174007'
       OR email = 'aaron@gmail.com'
);


INSERT INTO "users" (id, email, password, role)
SELECT '323e4567-e89b-12d3-a456-426614174009', 'aaron2@gmail.com',
       '$2a$10$3LNHUX0I3gYXhC6U8zzkiOuyHvWm8qxRpNO/pdvpnbvL2w36dsvvG', 'USER'
WHERE NOT EXISTS (
    SELECT 1
    FROM "users"
    WHERE id = '323e4567-e89b-12d3-a456-426614174009'
       OR email = 'aaron2@gmail.com'
);


