## 수정해도 파일이 계속 트래킹되는 문제

* 로컬 저장소와 원격 저장소에서 파일 삭제
```cmd
git rm filename 
git commit ...
```
* 원격 저장소에서 파일 삭제
```cmd
git rm --cached filename 
git commit ...
```

## 브랜치 덮어쓰기

```
git checkout hotfix-0.0.1
git reset --hard hotfix-0.0.1
git push -f origin dev
```