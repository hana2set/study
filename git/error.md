## 초기 git 명령어 시 권한 문제
```
git@github.com: Permission denied (publickey).
```
- ssh 권한이 막혀있음.
  - 1. url을 http로 변경
  - 2. ssh 권한을 추가


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