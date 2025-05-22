# 신경망
데이터에서 비선형 패턴을 찾도록 설계된 모델 아키텍처의 한 종류

## 1. 신경망의 구조

### 1.1 노드 (Node)와 은닉층 (Hidden Layer)

- **노드(Node, 뉴런)**: 입력값을 받아 가중치(weight)와 편향(bias)을 적용한 뒤, **활성화 함수**를 통해 출력값을 계산하는 연산 단위
- **은닉층(Hidden Layer)**: 입력층(Input Layer)과 출력층(Output Layer) 사이에 위치하며, 데이터를 내부적으로 변환
  - 은닉층이 많으면 **심층 신경망(Deep Neural Network, DNN)** 이라고 부름
![image](https://github.com/user-attachments/assets/f0ecd4a0-22d4-47ed-91a6-b9c7aee7905b)

---

## 2. 학습의 흐름

### 2.1 피드포워드 (Feedforward, 순전파)

- 신경망의 학습 또는 추론 시 **입력 데이터를 시작으로 출력까지 계산하는 흐름**
- 각 층에서 입력값은 가중치와 편향을 통해 계산되고, **활성화 함수**를 거쳐 다음 층으로 전달
- 학습 시 이 출력은 손실(loss)을 계산하는 데 사용


### 2.2 역전파 (Backpropagation, 역전달)

- 손실 함수(Loss Function)의 결과를 기반으로 **오차를 출력층부터 입력층까지 거꾸로 전파**하며 가중치와 편향의 gradient를 계산
- 계산된 gradient는 **경사 하강법(Gradient Descent)** 등을 통해 파라미터를 업데이트하는 데 사용

---

## 3. 활성화 함수 (Activation Function)

신경망에서 입력 신호를 출력 신호로 변환하는 함수. 신경망에 **비선형성(non-linearity)** 을 부여하는 역할.

### 주요 함수:

- **Sigmoid**: $` \sigma(x) = \frac{1}{1 + e^{-x}} `$
- **Tanh**: $` \tanh(x) = \frac{e^x - e^{-x}}{e^x + e^{-x}} `$
- **ReLU (Rectified Linear Unit)**: $` f(x) = \max(0, x) `$

---

## 4. 그래디언트(Gradient) 문제

깊은 신경망 학습에서 자주 발생하는 **경사 계산의 실패** 

### 4.1 그래디언트 소실 (Vanishing Gradients)

- **설명**: 역전파 시 gradient가 점점 작아져 초기 층까지 도달하지 못하고 학습이 거의 이루어지지 않음.
- **해결**: ReLU 사용, Batch Normalization, 적절한 weight 초기화

### 4.2 그래디언트 폭주 (Exploding Gradients)

- **설명**: gradient가 너무 커져 학습이 발산하거나 불안정해짐.
- **해결**: 배치 정규화, 학습률 낮추기, Gradient Clipping, 안정적인 weight 초기화

### 4.3 죽은 뉴런 현상 (Dead ReLU Units)

- **설명**: ReLU의 출력이 항상 0이 되면 해당 뉴런이 더 이상 학습되지 않음.
- **해결**: 학습률 낮추기, Leaky ReLU, Parametric ReLU, ELU 사용 고려

---

## 5. 정규화 (Regularization)

과적합(overfitting)을 방지하기 위한 일반적인 기법

### 5.1 드롭아웃 정규화 (Dropout Regularization)

- **설명**: 학습 중 일부 뉴런을 무작위로 비활성화(drop)하여 특정 뉴런에 대한 의존도를 줄이고, 일반화를 향상시킴.
- **효과**: 과적합 방지, ensemble 효과
- **추론 시**에는 전체 뉴런을 사용하되 학습 시 비활성화 비율만큼 보정함.

---

## 6. 출력층과 다중 클래스 분류

### 6.1 소프트맥스 (Softmax)

- **설명**: 출력층에서 각 클래스가 정답일 확률을 계산하는 함수
- **공식**:

$$
\sigma(z_i) = \frac{e^{z_i}}{\sum_{j} e^{z_j}}
$$

- **사용 예**: 다중 클래스 분류(Multiclass Classification)

### 6.2 Full Softmax vs. Candidate Sampling

- **Full Softmax**: 전체 클래스에 대해 확률 계산 → 정확하지만 계산량 많음
- **Candidate Sampling**: 일부 정답/부정 샘플만 softmax에 사용 → 계산 효율성 높지만 근사치

---

## 7. 다중 클래스 분류 전략

### 7.1 일대일 (One-vs.-One)

- **설명**: 모든 클래스 쌍마다 개별 분류기를 학습
- **모델 수**: $` \frac{n(n-1)}{2} `$

### 7.2 일대다 (One-vs.-All, OvA)

- **설명**: 각 클래스를 하나의 정답 클래스로 두고 나머지를 통합하여 이진 분류기를 학습
- **모델 수**: $n$

---

## 8. 단일 라벨 vs. 다중 라벨 분류

### 8.1 One-label Classification (단일 라벨)

- 하나의 입력 샘플이 **정확히 하나의 클래스**에 속함  
- 예: 고양이 vs 개 중 하나

### 8.2 Many-label (Multi-label) Classification (다중 라벨)

- 하나의 입력 샘플이 **여러 클래스에 동시에 속할 수 있음**
- 예: 영화가 "액션" + "코미디" 장르일 수 있음

---



출처:
[Neural Networks, Manifolds, and Topology](https://colah.github.io/posts/2014-03-NN-Manifolds-Topology/)
