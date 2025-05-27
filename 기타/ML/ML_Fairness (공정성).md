# Fairness (공정성)

- ML 모델 기반 자동화된 의사 결정 과정에서 알고리즘 편향을 교정하려는 다양한 시도
- ML에는 의도치 않은 인간의 편견(bias)이 반영될 수 있다. (예: 같은 지표라도 남성 지원자에게 높은 가산점을 부여함 등)

## 편향(bias)의 유형

[위키백과의 인지 편향 목록](https://en.wikipedia.org/wiki/List_of_cognitive_biases)에는 판단에 영향을 미칠 수 있는 100가지 이상의 다양한 인간 편향이 나열되어 있음.  
그 중 ML과 관련성 깊은 일부만 나열

<details>
  <summary>펼쳐보기</summary>


### 보고 편향(Reporting bias)
실제 빈도와 기록된 빈도수가 다른 편향.  
특이한 상황이 더 기억에 남고 기록할 필요도 많다 생각하기 때문.
- 책에 대한 리뷰는 극단적인 리뷰가 더 많다.(중간 평가자는 리뷰를 잘 남기지 않음)

### 역사적 편향(Historical bias)
역사적 자료가 그 당시 세상에 존재했던 불평등을 반영

### 자동화 편향(Automation bias)
오류율에 상관 없이 자동화된 시스템을 더 선호하는 경향

### 선택 편향(Selection bias)
표본을 잘못 선택함으로써 통계 분석이 왜곡되는 것을 뜻함. 다양한 형태의 선택 편향이 있음.

#### 1. 범위 편향(Coverage bias)
매체나 집단의 선택이 적절하게 이루어지지 못한 경우 발생하는 편향.  
- 선거 예상 득표스 조사를 위해 전화번호부에 등록된 사람들로 설문조사를 시행했지만, 전화를 받지 못하거나 집이 없는 사람들이 정반대의 투표를 함.

#### 2. 무응답 편향(Non-Response bias)
수집 과정에서 표본의 참여 격차로 데이터가 의미를 갖지 못하게되는 경우
- 특정 설문에 대해 반대자는 설문조사 참여를 거부할 확률이 훨씬 높았고, 표본에서 이 데이터는 과소 대표가 됨.

#### 3. 샘플링 편향(Sampling bias)
데이터 수집 시 적절한 무작위화가 사용되지 않은 경우
- 설문 응답을 빠르게 한 최초 200명을 표본으로 잡은 경우, 일반 구매자보다 이미 관심이 높아 오염된 표본일 가능성이 높다.

<br>

### 집단 귀인 편향(Group attribution bias)
한 개인에게 적용되는 사실이 그 집단에 그대로 일반화 하려는 것

#### 1.내집단 편향(In-group bias)
자신이 속한 집단이나 특성을 더 선호하는 것
- 이력서 검토에서 동문인 지원자가 더 적합하다 생각함

#### 2. 외집단 동질성 편향(Out-group homogeneity bias)
외집단이 내집단(본인이 속한 집단)보다 더 유사하다고 인식하는 것.  
반대로 내집단의 구성원들은 더 다양한 특성을 가진다고 여기는 것.
- 소프트웨어 개발자 전공이 아닌 다른 실무자들은 전문 지식이 부족하다 생각하는 경향이 있음


<br>

### 암묵적 편견(Implicit Bias)
자신의 사고와 개인적 경험에 근거하여 가정하는 경향
- 일부 지역에서는 좌우로 고개 흔들기가 "YES"라는 의미

### 확인 편향(Confirmation bias)
무의식적으로 기존의 신념과 가설을 긍정하는 방식으로 데이터를 처리할 때 발생

### 실험자 편향(Experimenter's bias)
원래 가설과 일치하는 결과가 나올 때까지 모델을 계속 학습할 때 발생

> 출처: 공정성: [편견의 유형](https://developers.google.com/machine-learning/crash-course/fairness/types-of-bias)
  
</details>



> 출처: [google-developers > machine-learning > crash-course > fairness](https://developers.google.com/machine-learning/crash-course/fairness)

