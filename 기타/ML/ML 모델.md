- 주요 용어
  
  | 영어 용어       | 한국어 번역                              | 설명                                 |
  | ----------- | ----------------------------------- | ---------------------------------- |
  | **Feature** | **특징**, **특성**, **입력 변수**           | 모델이 학습에 사용하는 입력값 (예: 키, 몸무게, 나이 등) |
  | **Label**   | **레이블**, **정답**, **목표값**, **출력 변수** | 예측하고자 하는 대상 (예: 질병 유무, 가격, 카테고리 등) |


# 선형 회귀
- 특성(입력)과 라벨(출력) 간의 선형 관계를 모델링하여 예측하는 통계 기법
- 데이터를 통해 최적선을 그림
  ![image](https://github.com/user-attachments/assets/22712076-18ec-4b81-abdf-f0a9bc5921ef)

## 선형 회귀식
- 단일 특성: `y' = b + w₁x₁`
- 다중 특성: `y' = b + w₁x₁ + w₂x₂ + ... + wₙxₙ`
  - `y'`: 예측값
  - `b`: 편향(bias)
  - `w₁, w₂, ..., wₙ`: 각 특성의 가중치(weights)
  - `x₁, x₂, ..., xₙ`: 입력 특성들


## 손실
- 모델의 예측이 얼마나 잘못되었는지를 나타내는 숫자 항목
   ![image](https://github.com/user-attachments/assets/531794a2-1279-4d37-915f-482d9360f9e1)

### 손실 유형

  | 손실 함수 이름 | 정의 | 수식 표현 |
  |----------------|------|-----------|
  | **L1 손실** (절댓값 손실) | 예측값과 실제값의 차이의 절댓값의 합 | Σ &#124;y - y'&#124; |
  | **MAE** (평균 절대 오차) | L1 손실의 평균 | (1/N) × Σ &#124;y - y'&#124; |
  | **L2 손실** (제곱 손실) | 예측값과 실제값의 차이의 제곱의 합 | Σ (y - y')² |
  | **MSE** (평균 제곱 오차) | L2 손실의 평균 | (1/N) × Σ (y - y')² |
  
  - `MAE`모델: 이상치에 둔감. 대부분의 포인트에 가깝다. -> 간단함, 직관적
    ![image](https://github.com/user-attachments/assets/6510cafe-c25c-45de-b4b3-0b276e19fe05)
  - `MSE`모델: 이상치에 민감. 오차가 커짐 
    ![image](https://github.com/user-attachments/assets/c9440698-3429-4969-ac88-67a67d42842f)
    

## 경사하강법
- 손실이 가장 작은 모델을 생성하는 가중치와 편향을 반복적으로 찾는 수학적 기법
    1. 현재 가중치와 편향으로 손실을 계산
    2. 손실을 줄이는 가중치와 편향을 이동할 방향을 결정
    3. 가중치와 편향 값을 손실을 줄이는 방향으로 조금씩 이동
    4. 1단계로 돌아가 모델이 더 이상 손실을 줄일 수 없을 때까지 이 과정을 반복
- 선형 모델의 손실 함수는 **항상 볼록 곡면을 생성**하기 때문에, 모델이 수렴하면 결국 가장 낮은 손실을 생성하는 값을 찾을 수 있음
  ![image](https://github.com/user-attachments/assets/7056f3ec-66c3-4c02-a2ff-62c4191920c4)
 
## 초매개변수(하이퍼파라미터)
- 사용자가 학습의 다양한 측면을 제어하는 변수
- **매개변수는 사용자가 제어하는 값이 아님. 모델이 계산하는 값**


### 학습률 
- 모델이 수렴하는 속도에 영향을 미치는 부동 소수점 수
  - 작으면 학습 속도 느림, 크면 수렴하지 않고 진동
  - **데이터 세트마다 자체적인 이상적 학습률**이 있음


### 배치 크기
- 갱신할 때 처리할 데이터 세트의 수 (1~N)
  - 대규모 데이터 세트를 일괄 처리하는 것(`전체 배치`)은 비효율적
  - 하나만 하는 경우 (`확률적 경사하강법, SGD`) 노이즈가 많다. (업데이트 과정에 손실이 커짐)
  - 그 절충안으로 임의의 배치 크기(1~N)를 입력 `미니 배치 확률적 경사하강법`

### 에포크
- 모델이 학습 세트의 **모든 예시를 한 번 처리했음**을 의미
- 배치 크기에 에포크 수치가 영향 받음


# 로지스틱 회귀
- 주어진 결과의 **확률**을 예측하도록 고안된 회귀 모델 (날씨 확률률, 스팸 확률 등)
- 매우 효율적인 확률 계산 메커니즘

## 확률 계산
- 선형 방정식의 출력을 시그모이드 함수에 대입 계산
- 바이너리 카테고리로 변환하기 좋음.

### 시그모이드 함수
- S자 형태이며, 항상 0과 1사이의 값을 출력할 수 있음
- 표준 로지스틱 함수 라고도 하며 수식은 다음과 같음.
  
$$
f(x) = \frac{1}{1 + e^{-x}}
$$

![image](https://github.com/user-attachments/assets/cf2eaa37-688c-4f84-b28f-0f004bef4811)

#### 선형 출력을 변환

$$
z = w_1x_1 + w_2x_2 + \cdots + w_nx_n + b
$$

- $z$: 선형 방정식의 출력으로, $log-odds$라고도 함
- $w_i$: 각 특성에 대한 가중치  
- $x_i$: 특성 값  
- $b$: 편향

이 결과 $z$를 시그모이드 함수에 입력하여 계산
![image](https://github.com/user-attachments/assets/c961f327-506e-4684-8e0c-ce4d4f341626)


## 손실
- 선형 회귀 모델과의 차이점
  - 로그 손실 함수 사용 (선형은 제곱 손실)
  - 정규화 적용이 과적합을 방지

#### 로그 손실
$$
\text{Log Loss} = \sum_{(x, y) \in D} -y \log(y') - (1 - y) \log(1 - y')
$$

- $y$: 로지스틱 회귀임으로, 0 또는 1
- $y'$: 모델의 예측값 (0 ~ 1)


## 정규화
- 모델 복잡성에 패널티를 적용하는 메커니즘. (복잡성을 줄이도록 함)
- 없으면 손실이 0으로 계속 이동
- 따라서 두가지 방식을 채용
  - L_2 정규화
  - 조기 중단


# 분류
- 확률이 아닌, 카테고리 출력이 목표인 경우 사용
- 임계값

## 측정 항목
### 기본 개념: 혼동 행렬(Confusion Matrix)

| 실제 \ 예측 | Positive (예측값 = 1) | Negative (예측값 = 0) |
|-------------|-----------------------|-----------------------|
| Positive (실제값 = 1) | True Positive (TP)       | False Negative (FN)      |
| Negative (실제값 = 0) | False Positive (FP)      | True Negative (TN)       |

### 1. 정확성(Accuracy)
- 전체 데이터 중 모델이 정확하게 맞춘 비율  
- 데이터 불균형 때 최적화 필요

$$
\text{Accuracy} = \frac{TP + TN}{TP + TN + FP + FN}
$$

### 2. 정밀도 (Precision)
- 모델이 Positive라고 예측한 것 중 실제로 Positive인 비율

$$
\text{Precision} = \frac{TP}{TP + FP}
$$


### 3. 재현율, 참양성률 (Recall, Sensitivity, True Positive Rate, TPR)

- 실제 Positive 중 모델이 Positive로 정확히 예측한 비율  

$$
\text{Recall} = \frac{TP}{TP + FN}
$$

### 4. 거짓 양성률 (False Positive Rate, FPR)
- Positive으로 잘못 분류된 Negative의 비율
- 0일 수록 좋
- **거짓 경보 가능성**

$$
\text{FPR} = \frac{FP}{FP + TN}
$$



### 번외. 5. F1 Score
- 정밀도와 재현율의 조화평균 (균형 잡힌 평가 지표)  

$$
F1 = 2 \times \frac{\text{Precision} \times \text{Recall}}{\text{Precision} + \text{Recall}}
$$



## 측정항목 선택 및 절충점
| 측정항목       | 정의 및 특징                                        | 장점                                         | 단점                                           | 주요 절충점 및 사용 상황                              |
|----------------|--------------------------------------------------|--------------------------------------------|----------------------------------------------|----------------------------------------------------|
| **정확도 (Accuracy)**   | 전체 데이터 중 맞게 예측한 비율                         | 직관적이고 전반적인 성능 평가에 용이                 | 클래스 불균형 시 잘못된 판단 가능                     | 클래스가 균형 잡힌 데이터에서 사용 권장                |
| **정밀도 (Precision)**  | Positive 예측 중 실제 Positive 비율                     | False Positive(오탐) 줄이는데 중요                    | False Negative(누락) 증가 가능                        | 오탐 최소화가 중요한 경우(예: 스팸 필터링, 사기 탐지)     |
| **재현율 (Recall)**     | 실제 Positive 중 올바르게 예측한 비율                     | False Negative(누락) 줄이는데 중요                    | False Positive 증가 가능                              | 누락 최소화가 중요한 경우(예: 의료 진단, 결함 탐지)       |
| **거짓 양성률 (FPR)**   | 실제 Negative 중 잘못 Positive로 예측한 비율               | 오탐률 구체적으로 파악 가능                           | 한 측면만 평가하므로 단독 사용은 한계                    | FPR을 낮추면서 재현율과 균형 맞추는 상황에서 중요          |


## ROC-AUC
### ROC(수신자 조작 특성 곡선)
- 다양한 임계값(threshold)에 대해 **재현율(TPR)** 과 **거짓 양성률(FPR)** 의 관계를 시각화한 곡선
- 모든 임곗값의 모델 성능을 시각적으로 나타낸 것
- 특징
  - 좌상단(0,1)에 가까울수록 좋은 분류기
  - 무작위 추측(Random guess)은 대각선 (y = x) 형태로 나타남
  - 여러 분류기의 성능을 동일 기준으로 비교할 수 있음
- 선택사항<details><summary> **정밀도-재현율 곡선**  </summary>

   데이터 세트의 균형이 맞지 않는 경우 정밀도-재현율은 더 나은 비교 결과를 제공할 수 있음
  ![image](https://github.com/user-attachments/assets/83abd3b1-0a37-4bb1-b5fd-cd3e34f3cbbf)

</details> 


### AUC(곡선 아래 면적)
- ROC 아래 면적
- 분류기의 전체적인 판별 능력을 수치화한 값
- 수치별 의미
  - AUC = 1.0: 완벽한 분류기
  - AUC = 0.5: 랜덤 추측 수준 (무작위 예측과 동일)
  - AUC < 0.5: 성능이 랜덤보다 못함 (예측이 반대로 되어있을 수 있음)
- 대각선 ROC와 AUC 그림
  ![image](https://github.com/user-attachments/assets/ab5df174-6f24-4309-99da-7282d40c2f97)  


### 임계값 선택

![image](https://github.com/user-attachments/assets/ac83cb57-4429-4a4f-913f-49284e78d40b)

- A: 오탐 비용이 큰 경우, 낮은 FPR을 제공하는 임계값 사용
- B: 오탐과 거짓 음성 비용이 비슷한 경우. 
- C: 거짓 음성(양성 누락) 비용이 큰 경우, 높은 TPR 사용


## 예측 편향
- 모델이나 훈련 데이터의 문제를 조기에 해결할 수 있는 빠른 검사
- 모델 예측의 평균과 데이터 내 실제 레이블의 평균 간의 차이
- 발생한다면(차이가 크다면), 학습 데이터셋, 모델이 적용된 새 데이터셋, 모델 중 문제가 있는 것. 
- 발생 원인
  - 훈련 세트에 대한 편향된 샘플링을 포함한 데이터의 편향 또는 노이즈
  - 너무 강력한 정규화 (모델이 단순화되어 필요한 복잡성을 잃음)
  - 모델 학습 파이프라인의 버그
  - 모델에 제공된 데이터셋이 충분하지 않음


## 다중 클래스 분류
- 이진 분류의 확장. 특정 클래스에 대해 이진 분류기를 조합해 만듬
- 








----

참고:  
[Crash Course > 선형 회귀](https://developers.google.com/machine-learning/crash-course/linear-regression?hl=ko)  
[Python 예제 - 선형 회귀 - Keras 라이브러리](https://developers.google.com/machine-learning/crash-course/linear-regression/programming-exercise?hl=ko)
[Python 예제 - classification](https://developers.google.com/machine-learning/crash-course/classification/programming-exercise)
