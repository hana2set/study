# multi-row insert in Oracle

## UNION ALL (권장)
```sql
INSERT INTO pager (PAG_ID,PAG_PARENT,PAG_NAME,PAG_ACTIVE)
          SELECT 8000,0,'Multi 8000',1 FROM dual
UNION ALL SELECT 8001,0,'Multi 8001',1 FROM dual
```
- `sequnce.nextval`를 사용해야한다면, INSERT ALL 사용해야함

## INSERT ALL (기본 oracle 지원 명령어, 느린편)
```sql
INSERT ALL
   INTO t (col1, col2, col3) VALUES ('val1_1', 'val1_2', 'val1_3')
   INTO t (col1, col2, col3) VALUES ('val2_1', 'val2_2', 'val2_3')
   INTO t (col1, col2, col3) VALUES ('val3_1', 'val3_2', 'val3_3')
   .
   .
   .
SELECT 1 FROM DUAL;
```
- INSERT ALL은 하위 서브쿼리가 필요하기 때문에 `SELECT 1 FROM DUAL` 구문 필요.

## INSERT INTO VALUES (**Oracle 23c** 부터 지원)
```sql
INSERT INTO t(col1, col2, col3) VALUES
('val1_1', 'val1_2', 'val1_3'),
('val2_1', 'val2_2', 'val2_3'),
('val3_1', 'val3_2', 'val3_3');
```