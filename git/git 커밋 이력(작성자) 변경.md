# git commit comment 작성자 변경

1. 히스토리 확인
    ```git
    git log
    ```
    <img width="532" height="261" alt="Image" src="https://github.com/user-attachments/assets/9062dbe6-e1a4-42db-9945-9503ea4fac6f" />
2. 현재 계정의 사용자 변경
    ````cmd
    git config --local user.name "hana2set"
    git config --local user.email "devkim4251@gmail.com"
    ````
3. git rebase 실행
   - 3개 변경
        ```cmd
        git rebase -i HEADQ~3
        ```
    - 전체 변경
        ```cmd
        git rebase -i --root
        ```

    <img width="654" height="366" alt="Image" src="https://github.com/user-attachments/assets/54c78570-b6e2-4558-bd55-7ac81d7fbac7" />
4. pick을 e로 변경 (직접 수정해도 됨)
    ```git
    :%s/pick/e
    ```
5. 파일 저장 뒤 수정, 진행 반복
   <img width="475" height="187" alt="Image" src="https://github.com/user-attachments/assets/79bcb778-0e1a-4241-bf63-77dc2efdd518" />
   - 직접 수정
        ```cmd
        git commit --amend
        ```
   - 저자 수정
        ```cmd
        git commit --amend --author="hana2set <devkim4251@gmail.com>"
        ```
    - 다음 파일로 진행
        ```cmd
        git rebase --continue
        ```
6. 끝난 뒤 로그 확인
    ```cmd
    git log
    ```
    <img width="536" height="261" alt="Image" src="https://github.com/user-attachments/assets/7ea23f65-d1af-41f4-86cf-c6a6343abdbc" />


[[Github] Git Commit 이력 변경 - 작성자(Author), 커밋 날짜(CommitDate) 변경하기](https://hirlawldo.tistory.com/156)
          