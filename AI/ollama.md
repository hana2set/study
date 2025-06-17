# ollama란?
(2023년 시작) "Optimized LLaMA"의 줄임말로, llama.cpp 기반으로 구축된 오픈소스 도구인 **로컬 배포형 AI 모델 실행기**다. 백그라운드로 실행되며 Mistral, Meta, Google의 Gemma 등 다양한 모델군을 직접 다운하여 실행할 수 있다.

### 이점
- 보안이 보장됨
- 인터넷 연결 없이 LLM 사용 가능
- 모든 데이터는 로컬 기기에 저장됨

## 주요 구성요소
### Model file
언어 모델이 따를 지침이나 가이드 라인을 미리 프로그래밍할 수 있음
- 기본 시스템 프롬프트 정의
- LoRA 미세 조정 (완전한 미세 조정은 지원X)
- 기본 파라미터인 temperature, top-P, and top-K 조정


## 설치
- [ollama 공식 설치 사이트](https://ollama.com/download/windows)
    ```cmd
    curl -fsSL https://ollama.com/install.sh | sh
    ```
- http://127.0.0.1:11434/ 에서 실행 여부 확인 가능
- 공식 사이트에서 다운로드 가능한 llm 확인 가능
    ```cmd
    ollama pull llama3
    ```
- 다운받은 모델로 실행
    ```cmd
    ollama run llama3
    ```
    ![Image](https://github.com/user-attachments/assets/03657c81-649b-4fc3-a320-232dc2c53b72)
---

출처:  
https://www.hostinger.com/tutorials/what-is-ollama  
https://picovoice.ai/blog/local-llms-llamacpp-ollama/  
