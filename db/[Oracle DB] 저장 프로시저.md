# 오라클 저장 프로시저(Oracle Stored Procedure)
데이터베이스에 미리 저장된 SQL과 PL/SQL 블록

### 단점
- 유지보수 어려움
- 문자나 숫자열 연산이 java보다 느릴 수 있음

## 프로시저 생성
```sql
CREATE OR REPLACE PROCEDURE [프로시저명]
(
    매개변수 목록 --- 매개변수 [ IN || OUT ] 타입;
)
IS
    변수, 상수 선언
BEGIN
    실행될 내용
END;
```

## 예제 [^2]
- 생성
    ```sql
    -- 프로시저 생성
    CREATE OR REPLACE PROCEDURE get_customer_info
    (
      p_customer_id IN NUMBER
    ) 
    IS
      v_customer_name VARCHAR2(100);
    BEGIN
      -- 고객의 이름을 조회하는 SQL 문
      SELECT customer_name INTO v_customer_name
      FROM customers
      WHERE customer_id = p_customer_id;
      
      -- 고객 이름을 출력
      DBMS_OUTPUT.PUT_LINE('Customer Name: ' || v_customer_name);
    EXCEPTION
      WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Customer not found.');
      WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('An error occurred: ' || SQLERRM);
    END get_customer_info;
    ```
- 실행
    ```sql
    -- 1
    BEGIN
      get_customer_info(101);
    END;

    -- 2. 최신 방법 (oracle 9)
    CALL get_customer_info(101);

    -- 3
    EXEC get_customer_info(101);


    -- OUT 이 포함된 프로시저
    DECLARE
      outtxt VARCHAR2(100);
    BEGIN
      proc_get_customer_info('1005', outtxt);
      DBMS_OUTPUT.PUT_LINE(outtxt);
    END;

    ```

[^1]: 
[^2]: [Oracle Stored Procedure, 꼭 알아야 할 데이터베이스의 필수 개념과 활용법](https://digitalbourgeois.tistory.com/343)
