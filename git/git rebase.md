# git rebase

커밋 시퀀스를 새 기준 커밋으로 이동하거나 결합하는 프로세스.  
주로 메인 브랜치를 깔끔하게 유지하기 위해 사용

```console
git checkout feature
git rebase main
```
![01 What is git rebase](https://github.com/user-attachments/assets/d971c673-0c48-42f9-a25a-acf577c9aeca)

## 옵션
- `-i, --interactive`: 대화형 rebase. 각 커밋에 대한 수정내용을 지정할 수 있는 편집기가 열림
- `--onto <newbase> <oldbase>`: 브랜치(oldbase)를 다른 브랜치(newbase)에 전달 


## 주의사항
- 병합(merge)보다 더 많은 충돌이 일어날 수 있음으로, 프로젝트 성격에 맞게 사용
- 손실되는 커밋이 있을 수 있음. **강제푸쉬 하지 않으면 복원 가능**함
- **공개 저장소에 rebase하지 말 것**

---

출처: https://www.atlassian.com/ko/git/tutorials/rewriting-history/git-rebase
