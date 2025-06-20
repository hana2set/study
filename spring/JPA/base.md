* 영속성 컨텍스트
    ![image](https://github.com/hana2set/study/assets/97689567/b52cb9ee-ea45-480e-a8c7-eee3afea7eb6)
* EntityManager
* EntityManagerFactory
    * /resource/META-INF/persistence.xml로 EntityManager생성
* JPA 트랜젝션
* 1차 캐시
* 2차 캐시

## 엔터티 생명주기

* 비영속 (new/transient)<br>
  영속성 컨텍스트와 전혀 관계가 없는 새로운 상태 

* 영속 (managed)<br>
영속성 컨텍스트에 관리되는 상태 

* 준영속 (detached)<br>
영속성 컨텍스트에 저장되었다가 분리된 상태 

* 삭제 (removed)<br>
삭제된 상태
<br>

## 영속성 컨텍스트 장점

* 1차 캐시 
* 동일성(identity) 보장 
* 트랜잭션을 지원하는 쓰기 지연(transactional write-behind) 
* 변경 감지(Dirty Checking) 
* 지연 로딩(Lazy Loading)

<br>


## @Transactional
* propagation = Propagation.REQUIRED
    * REQUIRED (기본값): 자식 method의 transaction을 부모에게 합침

<br>

