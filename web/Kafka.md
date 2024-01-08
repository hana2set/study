https://kafka.apache.org/documentation/#gettingStarted

# 카프카(Kafka)란?

파이프라인, 스트리밍 분석, 데이터 통합 및 미션 크리티컬 애플리케이션을 위해 설계된 고성능 분산 이벤트 스트리밍 플랫폼

링크드인에서 전통적인 엔터프라이즈 메세징 시스템의 대안으로 나왔음

Pub-Sub 모델의 메시지 큐 형태로 동작하며 분산환경에 특화되어 있다.


## 기존 데이터 시스템의 문제점

기존 데이터 시스템의 구조는 각 애플리케이션과 데이터베이스가 end-to-end로 구성되어 있음. 이러한 구조는 간단하지만 각각의 데이터 파이프라인이 분리되어 있어, 요구사항이 증가함에 따라 시스템의 복잡도를 높이는 결과를 가져왔고, 크게 아래와 같은 문제점들이 발생함.

#### 시스템 복잡도의 증가

    중앙화된 데이터 전송 영역이 없어, 데이터의 흐름을 파악하기 어렵고, 시스템 관리가 복잡함.
    시스템의 일부분에 문제가 발생하면, 연결된 모든 애플리케이션들을 확인해야 함.

#### 데이터 일관성 유지의 어려움

    데이터가 여러 시스템과 데이터베이스에 분산되어 있는 경우, 한 시스템에서 변경된 데이터가 다른 시스템에 즉시 반영되지 않아 데이터의 일관성을 유지하기 어려움.

#### 데이터 실시간 처리의 어려움

    전통적인 메시지 큐 시스템이나 데이터베이스는 대부분 배치 처리 방식을 사용함.
    이는 데이터를 실시간으로 처리하는 것이 어렵다는 것을 의미.

#### 확장성 제한

    대부분의 전통적인 메시지 큐 시스템은 한정된 리소스 내에서 작동하므로, 대량의 데이터를 처리하는 데 제한이 있음.
    데이터의 양이 증가하면서 시스템을 확장해야 하는 상황에서 이런 제한이 큰 문제가 될 수 있음.


# Kafka의 기본 구조
![image](https://github.com/hana2set/study/assets/97689567/4a013b98-03ef-4696-b568-674667fd51ac)
출처 : https://en.wikipedia.org/wiki/Apache_Kafka#/media/File:Overview_of_Apache_Kafka.svg

1. 프로듀서(Producer)
    * Kafka에 메시지를 발행하는 역할
    * 다양한 데이터 소스로부터 데이터를 가져와 Kafka의 특정 토픽에 메시지를 발행.

2. 브로커(Broker)
    * 프로듀서로부터 메시지를 받아서 저장하고, 컨슈머에게 메시지를 전달하는 역할
    * Kafka 클러스터는 여러 브로커들로 구성되며, 각 브로커는 하나 이상의 토픽의 메시지를 저장하고 관리.

3. 토픽(Topic)
    * Kafka에서 데이터를 분류하는 단위
    * 프로듀서는 메시지를 특정 토픽에 발행하고, 컨슈머는 토픽을 구독하여 메시지를 소비함. 
    * 토픽은 여러 파티션으로 나뉘어질 수 있고, 이를 통해 데이터를 병렬로 처리 가능. 
    * 각 파티션은 순서가 보장된 메시지 스트림을 제공하며, 브로커가 클러스터 내에서 파티션을 분산하여 저장.

4. 컨슈머(Consumer)
    * Kafka의 특정 토픽을 구독하고, 해당 토픽의 메시지를 소비하는 역할
    * 컨슈머는 하나 이상의 토픽을 구독할 수 있으며, 토픽의 파티션을 동시에 소비할 수 있음.


# Spring for Apache Kafka
https://spring.io/projects/spring-kafka/#overview

---

출처:   
https://velog.io/@holicme7/Apache-Kafka-%EC%B9%B4%ED%94%84%EC%B9%B4%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80   
https://dkswnkk.tistory.com/705