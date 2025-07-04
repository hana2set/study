# DB Link
데이터베이스에서 다른 데이터베이스 서버로의 단방향 통신 경로를 정의하는 포인터. `<테이블명>@dblink` 형식으로 사용한다.  
```sql
CREATE [ SHARED ] [ PUBLIC ] DATABASE LINK [ IF NOT EXISTS ] dblink
  [ CONNECT 
     { TO { CURRENT_USER  | user IDENTIFIED BY password [ dblink_authentication ] } 
     | WITH credential }
  | dblink_authentication
  ]...
  [ USING connect_string ] 
```

- `SHARED`: 소스 데이터베이스에서 여러 세션이 공유할 수 있는 DB Link 생성. (연결 수 줄이기가 목적)
- `PUBLIC`: 모든 사용자들이 공유하는 DB Link 생성.

> [!tip]
> 양방향 링크는 `Shared DB Link`


## Create Database Link
- public DB Link
    ```sql
    CREATE PUBLIC DATABASE LINK remote 
    USING 'remote'; 
    ```
    `remote DB`에 대해 모든 사용자에게 DB Link 부여
- private DB Link
    ```sql
    CREATE DATABASE LINK local 
    CONNECT TO hr IDENTIFIED BY password
    USING 'local';
    ```
    `local DB`에 대해 `hr` 유저에게 권한 부여

<br>

- 호출 방법
    ```sql
    UPDATE employees@remote 
    SET salary=salary*1.1 
    WHERE last_name = 'Baer';
    ```
    ```sql
    SELECT * FROM employees@local;
    ``` 


출처:  
[ORACLE - CREATE DATABASE LINK](https://docs.oracle.com/en/database/oracle/oracle-database/23/sqlrf/CREATE-DATABASE-LINK.html)