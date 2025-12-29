# START WITH ... CONNECT BY
오라클에서 계층형 데이터 쿼리를 만드는 데 사용하는 구문  
> [!NOTE]
> MySQL 계층형 쿼리를 별도로 제공하지 않아 재귀쿼리(recursive WITH)를 사용함.

## 예제

```sql
SELECT last_name, employee_id, manager_id, LEVEL      -- LEVEL: 계층 레벨을 표시해주는 키워드
      FROM employees
      START WITH employee_id = 100                    -- employee_id 100부터 조회
      CONNECT BY PRIOR employee_id = manager_id       -- 다음 행 조회시 이전 값(PRIOR employee_id)을 참조 
      ORDER SIBLINGS BY last_name;                    -- 현재 레벨에서 last_name을 기준으로 정렬 

LAST_NAME                 EMPLOYEE_ID MANAGER_ID      LEVEL
------------------------- ----------- ---------- ----------
King                              100                     1
Cambrault                         148        100          2
Bates                             172        148          3
Bloom                             169        148          3
Fox                               170        148          3
Kumar                             173        148          3
Ozer                              168        148          3
Smith                             171        148          3
De Haan                           102        100          2
Hunold                            103        102          3
Austin                            105        103          4
Ernst                             104        103          4
Lorentz                           107        103          4
Pataballa                         106        103          4
Errazuriz                         147        100          2
Ande                              166        147          3
Banda                             167        147          3
...
```

### 1. START WITH:

- **계층 구조의 시작점**을 지정함
- 없이 CONNECT BY만 쓰면 전체 항목에 대한 계층 조회를 함으로, 중복항이 다수 생김

### 2. CONNECT BY:

- **부모와 자식 간의 관계**를 지정
- 복합 조건도 가능
- `PRIOR`: **이전 레벨의 컬럼**의 참조
- `NOCYCLE`: 루프가 있더라 반환 (원래는 에러 발생) 

### 3. ORDER SIBLINGS BY:

- **같은 계층 레벨의 행을 정렬**하는 데 사용
- 기본 ORDER BY절은 계층 정렬을 해제하니 지양할 것

<br>


## 계층형 쿼리 의사컬럼(Pseudocolumns)
계층형 쿼리를 돕기 위해 오라클에서 제공하는 가상열

### 1. LEVEL
- 현재 계층의 레벨을 반환
 
### 2. CONNECT_BY_ROOT
- 부모값(이전값) 반환

### 3. CONNECT_BY_ISLEAF
- CONNECT BY조건을 만족하는 다음 행이 있으면 1, 없으면 0 반환
- 계층 레벨은 제한하지만 다음행이 있는지 확인하고 싶을 때 유용

### 4. CONNECT_BY_ISCYCLE
- 루프가 있으면 1, 없으면 0 반환 (NOCYCLE 키워드랑 같이 쓰여야함)

## 그 외 계층형 쿼리 키워드
### 1. SYS_CONNECT_BY_PATH
- 처음부터 현재 노드까지 열 값을 반환함.
  ```sql
  SELECT LPAD(' ', 2*level-1)||SYS_CONNECT_BY_PATH(last_name, '/') "Path"
     FROM employees
     START WITH last_name = 'Kochhar'
     CONNECT BY PRIOR employee_id = manager_id;
  
  Path
  ------------------------------
       /Kochhar/Greenberg/Chen
       /Kochhar/Greenberg/Faviet
       /Kochhar/Greenberg/Popp
       /Kochhar/Greenberg/Sciarra
       /Kochhar/Greenberg/Urman
       /Kochhar/Higgins/Gietz
     /Kochhar/Baer
     /Kochhar/Greenberg
     /Kochhar/Higgins
     /Kochhar/Mavris
     /Kochhar/Whalen
   /Kochhar
  ```

<br>

---

출처:  
[[SQLD] START WITH...CONNECT BY PRIOR](https://sharonprogress.tistory.com/261)  
[Hierarchical Queries](https://docs.oracle.com/en/database/oracle/oracle-database/18/sqlrf/Hierarchical-Queries.html#GUID-0118DF1D-B9A9-41EB-8556-C6E7D6A5A84E)  
