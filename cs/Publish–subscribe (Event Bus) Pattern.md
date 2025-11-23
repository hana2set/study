# Publish–subscribe(Event Bus) Pattern

소프트웨어 아키텍처에서, 컴포넌트간 종속성을 줄이기 위해 사용되는 Publish-Subscribe 형식의 메세징 디자인 패턴. 
- observer pattern(관찰자 패턴)과 대비
- event-driven architecture(EDA)에서 활용
- Kafka, JMS...

### 장점

- 느슨한 결합
- 확장성
- 메세지 복잡성 최소화 + 전달 보장

### 단점

- 단일 실패 지점
- 성능 오버헤드 가능성
- 이벤트 순서 보장하기 어려움
- 버그 추적 어려움
- 동기 방식에 부적합