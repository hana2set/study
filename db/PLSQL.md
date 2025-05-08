# PL/SQL (Oracle’s Procedural Language extension to SQL)
- 오라클에서 만든 SQL을 확장한 절차적 언어
- SQL 내부에서 절차적인 처리(분기, 예외처리, 반복, Cursor 등)을 수행하기 위해 고안됨
- DML(select, insert, ...), DDL(create, drop, ...), DCL(grant, revoke, ...) 사용 가능

## 기본 구조
```
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

```
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



-----

참고  
[[PL/SQL] 무작정 시작하기 (1) - PL/SQL 이란](https://heodolf.tistory.com/55)
