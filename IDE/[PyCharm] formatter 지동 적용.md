저장시 자동 적용 반영은 일반적으로 **File Watcher + Black(포매터)** 가 보편적임

# 적용 방법
1. **가상환경(venv)** 에 Black 설치 (권장)
    ```bash
    pip install black
    ```
2. Intellij에서 File Watcher 플러그인 설치
3. Settings > Tools → File Watchers
    <img width="1300" height="941" alt="Image" src="https://github.com/user-attachments/assets/54f9fb70-bb90-4e68-9eb3-155d5b2ccd15" /> 
   - `+` 버튼 → Custom 추가
     - Name: Black
     - File type: Python
     - Scope: Current File
     - Program: `./venv/Scripts/black.exe` (window)
     - Arguments: `$FilePath$`
     - Working directory: `$ProjectFileDir$`
     - Output paths: `$FilePath$`
4. Black 옵션 적용을 위한 `pyproject.toml`파일 생성
    ```toml
    [tool.black]
    line-length = 160
    ```



## 포매터 선택 관련
https://www.reddit.com/r/Python/comments/zrhg5o/which_code_formatter_do_you_use/

- Black, YAPF ,lint 등이 가장 널리 쓰이는 것으로 보임.
- **Black은 세세한 설정이 불가능**하다 불평하는데, 이게 핵심이라 함(YAPF로 할 경우, 어떤 구성을 짤지에 대해 논의하게 됨)