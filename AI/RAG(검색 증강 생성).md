# RAG란?

기존의 언어 모델에 외부 지식을 결합하여 더욱 정확하고 최신의 정보를 제공하는 접근 방식. LLMs의 한계를 극복하기 위해 등장.

### 기존 LLMs의 문제점

1. 지식의 한계
2. 환각 문제
3. 출처 추적의 어려움
4. 도메인 특화 지식의 한계

## RAG의 장점

1. 정보의 최신성
2. 정확성 향상
3. 도메인 적응성
4. 비용의 효율성
5. 개선 및 제어가 쉬움

## RAG의 단점
1. 계산 복잡성 증가
2. 데이터 품질 의존성 (외부 데이터베이스 품질에 의존)
3. 프라이버시 및 보안

## RAG의 작동 원리
1. 인덱싱
   - 로드: 데이터 호출 (Document Loaders)
   - 분할: 데이터를 작은 청크로 나눔
   - 저장: 저장하고 인덱싱 (VectorStore, Embeddings 모델)
2. 검색
   - 사용자 질문을 분석하여 키워드를 추출하고, 벡터 유사도 등의 기술을 사용하여 가장 연관성 높은 문서나 정보 선별
3. 증강
   - 검색된 정보를 LLM의 입력에 추가(LLM이 효과적으로 활용할 수 있는 형태로 가공)
4. 생성
   - 응답 생성

![image](https://github.com/user-attachments/assets/fc5d1e2d-b791-49a3-82b7-1a09d7efd11d)

## RAG vs. Fine-tuning
![Image](https://github.com/user-attachments/assets/51998060-a6ce-430b-bb48-f2fd98f3a7d5)



---

https://rimo.tistory.com/36  
https://aws.amazon.com/ko/what-is/retrieval-augmented-generation/