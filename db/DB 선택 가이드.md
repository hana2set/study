# 요약
- 정확도가 중요
    - RDB
- 입출력이 잦음
    - Document DB
- 빨라야함
    - Key-value DB
- 시계열 데이터, 실시간 분석
    - Column-family DB

## RDB
- 특징
    - 정규화로 정확도가 높음
- 용도
    - 정확도가 매우 중요한 서비스
    - 일반적인 선택

## Key-value DB
- 특징
    - 빠름
    - 가벼움
- 용도
    - 메인 데이터의 서브 용도로 적합
    - 채팅을 위한 pub/sub
    - 자주 쓰는 데이터 캐싱
    - 영상 스트리밍
    - 로그인 기록 저장
- 종류
    - redis(인메모리(rem) -> 빠름)
## Graph DB 
- 특징
    - 방향, 가중치 등이 메인
    - 전용 graph query language 필요
- 용도
    - 방향, 관계가 중요한 서비스
    - 비행기 노선
    - SNS 친구 관계
    - 바이러스 전염맵
    - 추천 서비스
- 종류
    - neo4j
    - Sparsity
    - OreintDB
    - ArangoDB

## Document DB
- 특징 
    - 데이터 JSON형태로 저장
    - 중복제거(정규화) 안함
    - 분산처리를 염두해둠. 입출력이 많은 서비스에 유리
    - 일관성이 떨어짐
- 용도
    - 입출력이 많은 서비스에 유리(분산 처리 잘됨)
    - SNS
    - 실시간 채팅
    - 게시판
    - 온라인 게임
- 종류
    - MongoDB
    - CouchDB
    - Cloud Firestore

## Column-family DB
- 특징
    - column 기반 데이터베이스
    - table 내 row를 만들고 자유롭게 기입하는 식 (컬럼 달라도됨)
    - RDB와 비슷하지만 스키마가 없기 때문에 여유로움
    - 분산처리를 염두해둠. 입출력이 많은 서비스에 유리
    - Column 기반 작업이 많을 때 유리 (컬럼에 독립적 접근 가능)
    - __join 지원 안함__
- 용도
    - 실시간 분석
    - 시계열 데이터
    - 
- 종류
    - Cassandra
    - Hbase
    - GCP BigTable
    - Microsoft Azure Cosmos DB
- 참고
    - [choosing-between-columnar-and-document-database](https://www.skinternational.com/post/choosing-between-columnar-and-document-database)

## Search engine
- 특징
    - index 보관에 특화
- 용도
    - 검색 엔진
    - 실시간 검색어
    - 추천 검색어
    - 검색어 오타교정

- 종류
    - Elastic Search
    - Amazon Cloud Search
    - Google Cloud Search


> 출처:  
https://www.youtube.com/watch?v=ZVuHZ2Fjkl4