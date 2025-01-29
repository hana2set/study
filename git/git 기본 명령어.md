# 기본
- 새로운 저장소 생성
```bash
git init
```

- 상태 확인
```bash
git status
```

- 원격 저장소의 변경사항을 받아오기
```bash
git pull 
```


# 원격 저장소 관리
- 원격 저장소를 내 컴퓨터에 받아오기
```bash
git clone <url>
```

- 원격 저장소 확인
```bash
git remote -v
```

- 특정 원격 저장소 확인
```bash
git remote show <name>
```

- 원격 저장소 추가
```bash
git remote add <name(=alias)> <url>
```

- 원격 저장소 제거
```bash
git remote remove <name> <url>
```

- 원격 저장소 이름 변경
```bash
git remote rename <old> <new>
```

- 원격 저장소 url 변경
```bash
git remote set-url <name> <new url>
```

# 브랜치 관리
- 브랜치 목록
```bash
git branch
```

- 새 브랜치 생성 (로컬)
```bash
git branch <branch_name>
```

- 브랜치 생성 & 이동
```bash
git checkout -b <branch_name>
```

- 브랜치 삭제
```bash
git branch -d <branch_name>
```

- 로컬 브랜치를 원격 저장소에 전송
```bash
git push origin <branch_name>
```

- 원격에 저장된 git 프로젝트의 현재 상태를 다운 + 현재 위치한 브랜치로 병합
```bash
git pull <remote> <branch_name>
```


# 변경사항

- 커밋에 파일 추가
```bash
git add <file_name>
git add *
git add -A # 모두 포함
```

- 커밋 생성 (변경 실제 적용)
```bash
git commit -m <commit_message>
```

- 로컬의 변경사항을 원격 저장소에 업로드
```bash
git push <remote> <branch_name>
git push -u <remote> <branch_name> # push의 기본 저장소를 remote/branch로 지정
```

