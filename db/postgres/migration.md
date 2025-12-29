
## GENERATED ALWAYS AS IDENTITY
- 그냥 넣으면 `cannot insert into column "id"` 발생
```sql
INSERT INTO users (id, name) OVERRIDING SYSTEM VALUE VALUES (1, 'A');
INSERT INTO users (id, name) OVERRIDING SYSTEM VALUE VALUES (2, 'B');
INSERT INTO users (id, name) OVERRIDING SYSTEM VALUE VALUES (100, 'C');

SELECT setval(pg_get_serial_sequence('users', 'id'), (SELECT MAX(id) FROM users));

-- 테스트
INSERT INTO users (name) VALUES ('D');
```