# 임베딩 (Embedding)

## 1. 임베딩 개요 (Overview)

### 1.1 임베딩이란? (What is Embedding?)
- 고차원 범주형 데이터를 **수학적으로 표현된 벡터에 매핑**하는 것
- 유사한 의미를 가진 항목은 공간상 가깝게 위치

### 1.2 임베딩의 장점

- 가중치 갯수가 많으면 학습에 필요한 데이터가 많아지고, 계산량과 메모리 사용량이 더 증가함.  
- 즉, **모델 크기를 줄이고 가중치를 줄이려 노력해야함** == 임베딩
  
#### • One-hot encoding과 비교
  | 항목 | One-hot Encoding | Embedding |
  |------|------------------|-----------|
  | 가중치(weights) 개수  | 매우 많음 ($n \times n$) | 상대적으로 적음 ($n \times d$, $d \ll n$) |
  | 데이터 포인트 개수 | 많음 (희소 벡터 (sparse)) | 적음 (밀집 벡터 (dense)) |
  | 계산량(Computation) | 많음 | 적음 |
  | 메모리 사용량 | 고용량 | 저용량 |
  | On-device ML (ODML) | 부적합 | 적합 가능 |
  
### 1.3 임베딩 예시

#### Word2Vec

##### • 학습 방식
- Skip-gram: 중심 단어 → 주변 단어 예측
- CBOW: 주변 단어 → 중심 단어 예측

##### • 특성
- 단어 간 의미적 유사성 반영
- 소프트맥스 함수로 정규화
- 훈련 코퍼스에 따라 임베딩 벡터가 달라짐

  
## 3. 신경망과 임베딩 (Embedding in Neural Networks)

### 3.1 임베딩 계층 (Embedding Layer)

- 입력: 정수 인덱스
- 출력: 학습 가능한 밀집 임베딩 벡터
- 이후 은닉 계층(hidden layer), 활성화 함수와 연결

### 3.2 정규화 함수: 소프트맥스 (Softmax Function)
- 확률 분포를 출력으로 변환
- 분류 모델의 마지막 단계에 사용됨

  
## 4. 임베딩 생성 방식 (Obtaining Embeddings)

### 4.1 정적 임베딩 (Static Embedding)
- 학습된 후 고정된 벡터
- 학습은 빠르나 문맥 구분이 불가
- 예시: Word2Vec, GloVe, FastText

### 4.2 신경망 기반 임베딩 학습 (End-to-end Training)
- 모델 학습 중 임베딩 벡터가 업데이트됨
- 유사한 입력은 가까운 벡터로 수렴
- Task-specific 벡터 학습 가능

### 4.3 컨텍스트 임베딩 (Contextual Embedding)
- 문맥에 따라 단어 임베딩이 달라짐
- 예시:
  - ELMo (LSTM 기반 양방향 언어 모델)
  - BERT, GPT (Transformer 기반)
    
| 비교 항목 | 정적 임베딩 | 컨텍스트 임베딩 |
|-----------|-------------|-----------------|
| 입력 길이 고려 | × | ○ |
| 문맥 반영 | × | ○ |
| 대표 모델 | Word2Vec, GloVe | ELMo, BERT |

### 4.4 차원 축소 기법 (Dimensionality Reduction)

- 목적: 시각화, 연산 효율
- 기법:
  - PCA (주성분 분석)
  - t-SNE
  - UMAP
  
## 5. 참고

- 임베딩은 자연어 처리 외에도 범주형 데이터 처리에 활용
- On-device ML에서는 임베딩의 경량성과 효율성이 중요



---
https://developers.google.com/machine-learning/crash-course/embeddings
