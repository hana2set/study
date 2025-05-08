# PL/SQL (Oracle’s Procedural Language extension to SQL)
- 오라클에서 만든 **SQL을 확장한 절차적 언어**
- SQL 내부에서 절차적인 처리(분기, 예외처리, 반복, Cursor 등)을 수행하기 위해 고안됨
- DML(select, insert, ...), DDL(create, drop, ...), DCL(grant, revoke, ...) 사용 가능
- 크게 Procedure, Function, Trigger로 나눔


### 장점
- 성능 ↑
- 이식성 ↑
- 모듈식으로 개발 가능
- SQL 단점 보완
  - 변수
  - 제어문 (IF, LOOP)
  - 예외 처리


## 기본 구조
```sql
DECLARE --변수 정의
 
BEGIN -- 처리 로직 시작
 
EXCEPTION -- 예외 처리    
 
END -- 처리 로직 종료
;
```


<details>
  <summary>
    예제
  </summary>

```sql
--실행 결과를 출력하도록 설정
SET SERVEROUTPUT ON
 
--스크립트 경과 시간을 출력하도록 설정
SET TIMING ON
 
 
DECLARE
--변수를 정의하는 영역
    /**
     * PL/SQL에서 사용할 변수를 정의.
     * IDENTIFIER [CONSTANT] DATATYPE [NOT NULL] [DEFAULT 값];
     */
     
    V_STRD_DT       VARCHAR2(8);
    
    V_STRD_DEPTNO   NUMBER;
    
    V_DEPTNO        NUMBER;
    V_DNAME         VARCHAR2(50);
    V_LOC           VARCHAR2(50);
    
    V_RESULT_MSG    VARCHAR2(500) DEFAULT 'SUCCESS';
 
BEGIN
--작업 영역
    /**
     * DEPTNO가 10인 부서의 부서번호, 부서명, 지역을 조회.
     */
    
    --기준일자 - 내장함수 사용.
    V_STRD_DT := TO_CHAR(SYSDATE, 'YYYYMMDD');
    
    --조회 부서번호 변수 설정
    V_STRD_DEPTNO := 10;
    
    BEGIN
        --조회 - INTO절로 조회된 데이터 저장.
        SELECT T1.DEPTNO
             , T1.DNAME
             , T1.LOC
          INTO V_DEPTNO
             , V_DNAME
             , V_LOC
          FROM SCOTT.DEPT T1
         WHERE T1.DEPTNO = V_STRD_DEPTNO
        ;
    END
    ;
 
    --조회 결과 변수 설정
    V_RESULT_MSG := 'RESULT > DEPTNO='||V_DEPTNO||', DNAME='||V_DNAME||', LOC='||V_LOC;
    
    --조회 결과 출력
    DBMS_OUTPUT.PUT_LINE( V_RESULT_MSG );
    
EXCEPTION
--예외 처리
    WHEN OTHERS THEN
        V_RESULT_MSG := 'SQLCODE['||SQLCODE||'], MESSAGE =>'||SQLERRM;
        
        DBMS_OUTPUT.PUT_LINE( V_RESULT_MSG );
 
END
;
--작업 종료
```
  
</details>

## 변수 선언
- `DECLARE - BEGIN - END;` 구조로 구성
  ```sql
  DECLARE 변수명 자료형; BEGIN 변수명 := 값; END;
  ```
- SELECT문으로 선언 가능
  ```sql
  DECLARE
      employee_name VARCHAR2(100);
  BEGIN
      SELECT first_name INTO employee_name FROM employees WHERE employee_id = 101;
      DBMS_OUTPUT.PUT_LINE('Employee Name: ' || employee_name);
  END;
  ```
### 구성요소
- `DECLARE identifier [CONSTANT] datatype [NOT NULL] [:= | DEFAULT expression]`
  - identifier - 데이터를 저장할 공간에 붙이는 이름
  - CONSTANT - 한 번 설정하면 변경할 수 없는 값
  - datatype - 변수에 저장될 데이터의 유형
  - NOT NULL - 값 반드시 포함
  - expression : Literal, 다른 변수, 연산자나 함수를 포함하는 표현식
    - 변수의 값을 지정하거나 재지정하기 위해서 := 를 사용
### 종류
- 스칼라: `NUMBER`, `VARCHAR2`
  ```sql
   DECLARE employee_name VARCHAR2(50) NOT NULL := '0kim';
   ```
- 레퍼런스: `컬럼명%TYPE`으로 선언 선언. 행단위 참조는 `테이블명%ROWTYPE` 으로 사용
  ```sql
  DECLARE employee_salaryEMPLOYEES.SALARY%TYPE;
  ```
-----

## 분기
- `IF-THEN`, `IF-THEN-ELSE`, `IF-THEN-ELSIF-ELSE`
  ```sql
  IF [condtion] THEN
      [excution];
  ELSIF [condtion] THEN
      [excution];
  ELSE
      [excution];
  END IF;
  ```
## 반복문
- **BASIC LOOP** : `LOOP 실행할 문장 EXIT WHEN 조건; END LOOP;`
   ```sql
   LOOP
     [excution];
     EXIT WHEN [condtion];
   END LOOP;
   ```
- **FOR LOOP**: `FOR 변수 IN [REVERSE] 시작값..끝값 LOOP 실행할 문장 END LOOP;`
  
  ```sql
  FOR 변수 IN [REVERSE] [시작값..끝값] LOOP   -- [REVERSE] 키워드: 역순반복
    [excution];
  END LOOP;
  ```
- **WHILE LOOP**: `WHILE 조건 LOOP 실행할 문장 END LOOP;`
  ```sql
  WHILE [condtion] LOOP
    [excution];
  END LOOP;
  ```

참고  
[[PL/SQL] 무작정 시작하기 (1) - PL/SQL 이란](https://heodolf.tistory.com/55)
[OracleSQL 핵심정리11 - PL/SQL](https://youngkim90.github.io/posts/oracle-sql-11/?utm_source=chatgpt.com)
