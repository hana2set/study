<!-- 서지영 지음 -->

## 1.1 LLM 개념

### 언어모델
- 통계적 언어 모델
  - `n-gram`: n개로 단어를 나눔
- 신경망 언어 모델
  - `RNN`: 데이터가 순서대로 정렬, 순서가 중요 정보
  - `LSTUM`: 긴 순서가 중요
- 트랜스포머: 문장, 단락 전체를 처리
  - 버트
  - GPT
### 거대 언어 모델
- 모델의 크기 = 파라미터의 수
  - GPT-3는 1750억 개의 파라미터

## 1.2 LLM 특징과 종류
1. 방대한 텍스트로 학습
2. 언어를 이해하고 생성하는데 특화
3. 파인튜닝 (특정 작업을 위한 추가 학습)
4. 상당한 컴퓨팅 자원 필요

### LLM의 종류
LLM 생태계 검색
- 최근 3개
  - GPT-4 (멀티모달 지원 - 여러가지 방법을 섞어씀. 읽기 듣기 등(유튜브영상 듣기))
  - 팜2 (PaLM 2) 
  - 라마2 (LLaMA2) - 오픈소스. 페이스북 

### LLM vs. GAI, SLM
> [!note]
> GAI: 생성형 AI(Generative AI)  
> SLM: 소규모 언어 모델(Small Language Model)


- LLM vs. GAI
  - GAI가 더 포괄적 의미 
    - GAI: 입력 데이터 -> 새로운 컨텐츠 생성
    - LLM: 언어 데이터 -> 언어 데이터
- LLM vs. SLM
  - SLM은 LLM의 축소, 특화형
    - SLM: 단순 작업, 모바일 특화, 일주일 정도면 학습 가능
    - LLM: 범용성이 높음, 엄청난 인프라 필요(그래서 클라우드 사용), 학습에만 몇달

## 1.3 LLM 생성 과정

### 모델 라이프 사이클
1. 데이터 수집 및 준비
   - 수집
     - 식별, 수집
   - 정제
     - 중복, 노이즈 제거
   - 전처리
     - 토큰화, 정규화
   - 형식 변경
2. 모델 설계
   - 하이퍼파라미터 설정(계층 수, 학습률, 배치크기)
3. 모델 학습
4. 평가 및 검증
   - 정확도, 정밀도, 재현율, F1 점수, ROC 곡선 및 AUC
5. 배포 및 유지 보수

## 1.4 LLM 생성 후 추가 고려 사항
- 윤리적 고려 및 보정: 설계, 개발, 배포할 때 **윤리적, 법적, 사회적 책임을 고려하는 접근 방식**
  - 책임감 있는 AI 원칙
    - 공정성
    - 신뢰성, 안전성
    - 프라이버시
    - 포용성, 다양성
    - 윤리적 사용
    - 투명성
    - 책임성
- 지속적 모니터링

## 2.1 LLM 활용 방법
### 파인 튜닝
- 기존 LLM을 특정 작업이나 상황에 맞게 훈련시키는 과정
- 이 책에서는 다루지 않음  
  - 과도한 비용
  - 기대치(성능)에 대해 누구도 확신할 수 없음
  - 데이터 준비가 어려움
- 그래서 RAG에 다룰 예정
> [!note]
> 전이 학습: 한 분야의 문제를 다른 분야에서 학습한 지식으로 해결하는 방식 

### RAG (Retrieval-Augmented Generation)
정보 검색과 생성을 결합한 인공지능 모델
- 복잡하고 정보가 필요한 질문에 답변하기 위해 설계됨
    1. 정보 검색 단계(retrieval)
       - 질문
       - 쿼리(문서 검색)
       - 정보 검샐 결과: (문서 -> 클라이언트)
    2. 텍스트 생성 단계(generation)
       - 정보 전달 (클라이언트 -> LLM)
       - 텍스트 생성

### 퓨샷 러닝(Few Shot Learning)
매우 적은 양의 데이터로 학습하는 능력 (데이터가 충분치 않을 때 좋음)
- 제로샷 러닝: 학습하지 않았던 데이터 예측 -> 모델이 높은 수준의 추상적 사고와 일반화 능력이 있어야함.
- 원 샷 러닝: 하나의 데이터만 학습, 하나의 분류 -> 데이터가 제한적일 때 유리
- 퓨 샷 러닝: 여러개 데이터 학습, 하나의 분류

### LLM 활용 시 주의사항
- 정보 필터링 (개인정보 등)
- 법적인 규제
- 할루시네이션
  - `temperature=0`: 정확한 답변만
  - 할루시네이션 필터링 추가해야함
- 보안
  - 마이크로소프트 애저 오픈AI 고려

### LLM의 한계
- 편향과 공정성
- 투명성: 왜 그런 답변을 했는지 모름
- 데이터 의존성: 학습 데이터에 따라 결과가 바뀜
- 정보의 일반화
- 오류 가능성
- 개인정보 보호
- 새로운 정보 결여
- 기업 내 데이터 미활용



## 3.1 RAG 개념
LLM이 스스로 정보를 검색(Retrieval)하여 텍스트를 생성(Generation)하는 기술 
- 더 신뢰성 있는 내용을 만들 수 있음

---

## 3.2 RAG 구현 과정

1. 정보 검색(retrieval)
   1. 질문 입력(쿼리, query)
   2. 검색
   3. 유사도 검색
      - 키워드 검색
      - 시맨틱 검색: 문맥을 이해하여 보다 관련성 높은 결과를 제공하는 기술
   4. 랭킹 처리: 검색 결과에 유사도 순위를 매김
      1. 유사도 계산 (코사인 유사도, 유클리드 유사도)
      2. 문맥과 의도 파악
      3. 랭킹 산출
> [!tip]
> ***백터와 유사도***
> - 유사도 계산
>   - 코사인 유사도: 정규화된 내적
>   - 유클리드 유사도: 실제 거리
> - 검색 결과 랭킹 처리
>   - 페이지 랭크: 검색 결과에 랭킹을 매기는 알고리즘(예: 자주 참조되는 웹사이트에 가중치)
>   - TF-IDF: 나타나는 빈도로 가중치
>     - 단어 빈도, 문서빈도
>   - 클릭률(CTR)
1. 텍스트 생성(generation)
   1. 결과 반환
   2. 텍스트 생성


## 3.3 RAG 구현에 필요한 것

- 데이터
  - 시맨틱 검색
  - 벡터 검색
> [!note]
>  임베딩이란?  
> 복잡한 데이터를 간단하게 바꿈  
> 예) 사과, 바나나 -> [1.0, 0.0], [0.9, 0.1]
>
> --- 
> 임베딩 모델  
> - Word2Vec
> - GloVe
> - 오픈AI 임베딩 모델


### 벡터 데이터베이스
임베딩 모델을 통헤 "유사한" 정보를 찾는 DB
- 종류

  | 데이터베이스 | 특징 | 장점 | 단점 |
  |--------------|------|------|------|
  | 파인콘 (Pinecone) | - 클라우드 기반 벡터 DB<br>- 서버리스 아키텍처 제공 | - 확장성 우수<br>- API 중심 설계<br>- 벡터 검색에 최적화 | - 가격이 비쌈<br>- 온프레미스 환경 부재 |
  | 밀버스 (Milvus) | - 오픈 소스<br>- 이미지, 텍스트, 오디오 등 멀티모달 지원<br>- 벡터 데이터 처리에 특화<br>- 고성능, 광범위한 지원 | - 무료<br>- 다양한 데이터 유형 지원<br>- 대규모 처리 가능 | - 운영 복잡도 존재<br>- 설정과 튜닝 필요 |
  | 쿼드런트 (Qdrant) | - 오픈 소스<br>- 고차원 벡터 처리에 특화<br>- Rust 기반 고성능 엔진 | - 빠른 검색 속도<br>- REST/gRPC API 지원<br>- 복잡한 검색 쿼리 가능<br>- 벡터, 스칼라 데이터 모두 지원 | - 생태계 작음<br>- 도입 사례 부족 |
  | 크로마 (Chroma) | - LLM 어플리케이션에 특화<br>- 통합형 파이썬 친화 DB | - 간편한 사용법<br>- 파이썬 기반 LLM 워크플로우와 호환 | - 대규모 배포에 부족<br>- 성능 한계 존재(이미지, 오디오 처리 최적화 부족족) |
  | 엘라스틱서치 (Elasticsearch) | - 텍스트 검색 기반<br>- 벡터 검색 기능 포함 | - 텍스트 + 벡터 통합 검색 가능<br>- 강력한 분석 기능<br>- 다양한 플러그인, 통합 옵션 | - 고성능 벡터 검색에 불리<br>- 유료 라이선스로 전환됨 |
  | 파이스 (Faiss) | - 오픈 소스<br>- Facebook 개발<br>- CPU/GPU 기반 고속 검색 라이브러리 | - 매우 빠른 검색 성능<br>- 다양한 인덱싱 전략 제공 | - DB가 아닌 라이브러리 수준<br>- 분산 처리 미지원<br>- 인덱스에 대한 지식 필요 |


### 프레임워크 (랭체인)
LLM을 이용한 서비스 개발 프레임워크

--- 
## 4.1 랭체인 훑어보기
LLM과 외부 도구를 엮어 결합시켜 주는 프레임워크.  

## 4.2 환경 구성
두 가지 방법 중 하나 선택해야 함.
- 내 컴퓨터에 아나콘다 설치
- 구글에서 제공하는 코랩


### 아나콘다 환경 구성
LLM과 랭체인으로 구현한 코드를 웹페이지에서 확인하기 위해서는 스트림릿을 사용해야함  
-> 아나콘다 설치

#### 1. 아나콘다 설치
- 다운로드
- install for: Just Me
- 경로 기본값
- 환경변수 추가 설정 체크
  - 내 PC > 속성 > 고급 시스템 설정 > 환경 변수
  - ![Image](https://github.com/user-attachments/assets/c18b2d72-00e4-4620-865f-257f9becdda4)

#### 2. 가상 환경 생성
1. Anaconda3 > Anaconda Prompt 열기
1. 예제 버그가 많아서 python 3.13 설치 (책과 다름)
    ```console
    conda create -n llm
    # conda create -n llm python=3.8
    ```
    - 참고
      ```cmd
      # 가상환경 확인
      conda env list

      # 가상환경 삭제
      conda env remove -n llm
      ```
2. 가상환경 활성화
    ```cmd
    # 가상환경 활성화
    activate llm
    ```
3. 가상환경에 주피터 설치
    ```cmd
    pip install ipykernel
    ``` 
    ```cmd
    # 커널 연결 설정
    python -m ipykernel install --user --name llm --display-name "llm"
    ```
4. 주피터 실행
    ```cmd
    jupyter notebook
    ```
5. new로 새로운 소스 만들기  
    ![Image](https://github.com/user-attachments/assets/f1828e24-775e-4b27-96a6-42576a0b182f)  
6. 프롬프트에서 작업 후 Run으로 확인  
    ![Image](https://github.com/user-attachments/assets/b29cd1dc-91c7-4521-9d27-be518064b7c5)
7. vscode에 jupyter 플러그인 설치 후 프로젝트 연동

#### 3. 필요 라이브러리 설치
```
pip install langchain
pip install langchain_community
pip install openai
pip install huggingface-hub
pip install streamlit
```

> [!note]
> 오픈AI
> - 오픈AI에서 제공하는 모델의 API를 호출하는 데 사용
> 
> 허깅페이스(Hugging Face)
> - 인공지능 연구 및 개발을 위한 도구. 특히 자연어 처리 분야의 거대 언어 모델과 이를 쉽게 사용할 수 있는 API, 관련 라이브러리를 제공함.
>
> 스트림릿 (streamlit)
> - 파이썬으로 머신러닝을 위한 웹 애플리케이션을 빠르고 쉽게 개발할 수 있는 오픈소스 라이브러리


#### 4. 키 발급
- 오픈AI 키를 발급함 (sk-...) (유료 추천. 토큰 제한 있음)
- 허깅페이스LLM 키 발급 (hf-...)
- 코드에 입력
  ```py
  import os
  os.environ["OPENAI_API_KEY"] = "sk-XXXXXXXXXXX..."
  os.environ["HUGGINGFACEHUB_API_TOKKEN"] = "hf-XXXXXX..."
  ```

## 4.3 랭체인 주요 모듈 ([실습예제](https://github.com/gilbutITbook/080413) 확인)
![image](https://github.com/user-attachments/assets/1601e522-6c2a-431c-a257-dd9ed529ab2b)  
랭체인: LLM을 잘 활용할 수 있도록 하는 모듈의 모음
- 모델 I/O
- 데이터 연결
- 체인
- 메모리
- 에이전트/툴

> [!warning]
> openAI는 유료이고, huggingface-hub는 에러가 있어서 ollama로 진행함.
> ```console
> pip install langchain-ollama
> ```
> 
> ```py
> from langchain_community.llms import Ollama
>
> llm3 = Ollama(model="exaone3.5:2.4b")
> 
> prompt = "진희는 강아지를 키우고 있습니다. 진희가 키우고 있는 동물은?"
> completion = llm3.invoke(prompt)
> print(completion)
> ```

### 1. 모델 I/O
![image](https://github.com/user-attachments/assets/c45b66e5-5987-453c-9e2e-938dd2cdb7a9)
언어 모델과 상호 작용을 위한 모듈
- LLM에 전달될 프롬프트 생성
- 답변을 받기 위해 모델 API 호출
- 답변에 대한 출력

#### 출력 파서
- `from langchain.output_parsers` 에서 제공
  - PydanticOutputParser: 정의된 필드에 맞게 출력
  - SimpleJsonOutputParser
  - CommaSeparatedListOutputParser
  - DatetimeOutputParser
  - XMLOutputParser

### 2. 데이터 연결
![image](https://github.com/user-attachments/assets/d4cacbb5-73a6-4d69-86f0-9d6649f059a4)
- 순서
  1. 문서 가져오기(document `loaders`)
  2. 문서 변환(document `trasformers`): 데이터 -> 청크[^chunk]
  3. 문서 임베딩(`embedding` model): 데이터 -> 벡터
  4. 벡터 저장소(`vector stores`): 벡터를 저장/관리/검색
  5. 검색기(`retrievers`): 정보 검색
- 관련 라이브러리
  - `pypdf`(5.6.1): 파이썬에서 pdf 다루기용
  - `tiktoken`(0.9.0): 오픈AI에서 제공하는 임베딩 라이브러리
  - `faiss-cpu`(1.7.4): 페이스북에서 AI 라이브러리, 벡터 유사도 검색
  - `sentence-transformers`(2.2.2): 자연어 처리에서 문장 또는 단락을 벡터로 변환
    ```
    pip install pypdf
    pip install tiktoken
    pip install faiss-cpu
    pip install sentence-transformers
    ```


### 3. 체인
여러 구성 요소를 조합해 하나의 파이프라인을 구성해주도록 함.  
- (예시. 번역하는 LLM1과 요약하는 LLM2를 합쳐 하나의 파이프라인으로 구성해줌)
  
  ```py
  #프롬프트1 정의
  prompt1 = PromptTemplate(
      input_variables=['sentence'],
      template="다음 문장을 한글로 번역하세요.\n\n{sentence}"
  )

  #번역(체인1)에 대한 모델
  chain1 = LLMChain(llm=llm, prompt=prompt1, output_key="translation")

  #프롬프트2 정의
  prompt2 = PromptTemplate.from_template(
      "다음 문장을 한 문장으로 요약하세요.\n\n{translation}"
  )
  #요약(체인2)에 대한 모델
  chain2 = LLMChain(llm=llm, prompt=prompt2, output_key="summary")

  from langchain.chains import SequentialChain
  all_chain = SequentialChain(
      chains=[chain1, chain2],
      input_variables=['sentence'],
      output_variables=['translation','summary'],
  )
  sentence="""
  """
  all_chain.invoke(sentence)
  ```

### 4. 메모리
다음과 같은 형태로 저장 가능
- 모든 대화 유지
- 최근 k개의 대화 유지
- 대화를 요약해서 유지

> [!tip]
> ConversationChain deprecated -> RunnableWithMessageHistory로 대체 

### 5. 에이전트/툴
일반적인 데이터로 학습해, 특정한 산업에 특화되지 않음. 이러한 한계를 극복하기 위한 툴.  
- 예: 위키피디아, 빙과 같은 툴과 연계
- agent는 langGraph로 마이그레이션 된다고 하니 참고
```
# 위키피디아 기사 검색용
!pip install wikipedia

# 연산
!pip install numexpr
```

## 5. 실전 예제. 상세 내용 패스

```cmd
# 벡터 데이터베이스
pip install chromadb

# 문장 -> 벡터, 의미적 유사성 계산
pip install sentence-transformers

# 텍스트 파일 같은 구조화되지 않은 데이터 다루는 용도
pip install unstructured

# PyPDF와 비슷. 읽기, 분할, 병합, 순서 바꾸기, 암호화 등 제공
pip install PyPDF2

# 벡터 검색을 위한 인덱싱 및 검색 알고리즘
pip install faiss-cpu

# 챗봇 사용자 인터페이스 생성용 streamlit
pip install streamlit-chat

# langchain에서 외부 데이터 소스(판다스) 연결용 라이브러리
pip install langchain-experimental

# 데이터 조작 및 분석용 라이브러리
pip install pandas

# 테이블 형태의 데이터를 보기 쉽게 출력
pip install tabulate
```


## 6. 실제 사용처?
- 콜센터 상담원 대체 혹은 돕는 도구
  - 대화 내용 스크립트 생성
  - 상품 리뉴얼 관련 지식 교육용 스크립트 생성
- 상품 추천
- 보험 언더라이팅
- 코드 생성 및 리뷰
- 문장 생성
- M365 코파일럿

[^chunk]: 큰 데이터 덩어리를 다루기 쉬운 작은 조각으로 나눈 것 

