## SELECT

- 금액 형식
    ```sql
        NVL(TO_CHAR(NULLIF(amount, 0), 'FM999,999,999,990'), '-') AS formatted_amount
    ```