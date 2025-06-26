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


## 모델 크기에 따른 메모리(RAM) 추천
- Small models (7B)
    - Minimum RAM: 8GB
    - Recommended RAM: 16GB
- Medium models (13B)
  - Minimum RAM: 16GB
  - Recommended RAM: 32GB
- Large models (70B)
  - Minimum RAM: 64GB
  - Recommended RAM: 128GB

> [!tip]
> 항상 권장 최소 메모리보다 더 많은 메모리를 할당할 것!
> - 성능 병목 현상 방지 가능
> 
> 가장 큰 모델을 선택할 필요는 없다!  
> - 가벼운 작업, 빠른 추론(대화형 AI) 정도는 소형 모델(7B)로 충분.
>
> 양자화 기술(Quantization techniques)로 필요 메모리를 획기적으로 줄일 수 있음.  
>
> 


출처: [How much memory does ollama need?](https://www.byteplus.com/en/topic/405436?title=how-much-memory-does-ollama-need)



## 기타

-  로그 전체 보기
    ```cmd
    journalctl -u ollama.service > ollama_logs.txt
    ```
- 실시간 로그 조회
    ```cmd
    journalctl -u ollama.service -f
    ```
- WSL 서버 죽이기 (VmmemWSL이 높은 메모리를 차지하고 살아있음) 
    ```cmd
    # window 에서 실행
    wsl --shutdown

    # 하나씩 죽이기
    wsl --list --running
    wsl --terminate <distroName>
    ```

## 에러
-   ```
    CUDA error: an illegal memory access was encountered
    ```
    CPU 메모리가 부족해서 발생하는 문제. 


### [Final Words: Do Not Use Ollama](https://ahmadosman.com/blog/do-not-use-llama-cpp-or-ollama-on-multi-gpus-setups-use-vllm-or-exllamav2/)
> Ollama is a wrapper around llama.cpp, and in my opinion it is a tool that sets environment variables, does a bad job at calculating VRAM splits and offloading, and leads to a lot of frustration. It is great if you have only 1 GPU and don’t want to do much except running basic models for chat sessions; anything beyond that and it isn’t worth using.


---

출처:  
https://www.hostinger.com/tutorials/what-is-ollama  
https://picovoice.ai/blog/local-llms-llamacpp-ollama/   
