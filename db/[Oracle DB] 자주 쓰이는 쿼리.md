- 특정 테이블이나 컬럼을 사용하는 패키지/프로시저 찾기
    ```sql
    SELECT NAME, LINE, TEXT
    FROM USER_SOURCE 
    WHERE  upper(TEXT) like '%테이블명||컬럼명%'
    ORDER BY NAME, LINE
    ```