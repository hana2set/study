# Flashback Query
특정 시점의 테이블 데이터를 조회하는 기능.

## 과거 데이터 복구
- 조회
```sql
-- 2025년 11월 17일 오후 4시 데이터 조회
SELECT *
FROM  your_table AS OF TIMESTAMP TO_TIMESTAMP('2025-11-17 16:00:00', 'YYYY-MM-DD HH24:MI:SS');

-- 1시간 전 데이터 조회
SELECT *
FROM  your_table AS OF TIMESTAMP (SYSTIMESTAMP - INTERVAL '1' HOUR);
```

- 과거 데이터로 변경
```sql
MERGE INTO your_table t
USING (
    SELECT *
    FROM  your_table AS OF TIMESTAMP (SYSTIMESTAMP - INTERVAL '1' HOUR)
) s
ON (t.primary_key = s.primary_key)
WHEN MATCHED THEN
  UPDATE SET
    t.column1 = s.column1,
    t.column2 = s.column2;
```