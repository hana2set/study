# 요약
- 정확도가 중요
    - RDB
- 입출력이 잦음
    - Document DB
- 빨라야함
    - Key-value DB
- 시계열 데이터, 실시간 분석
    - Column-family DB

# 표
<table>
    <thead>
        <tr>
            <th>Database</th>
            <th>특징</th>
            <th>용도</th>
            <th>종류</th>
        </tr>
    </thead>
  <tbody>
    <tr>
      <td>RDB</td>
      <td>
        <ul>
          <li>정규화로 정확도가 높음</li>
        </ul>
      </td>
      <td>
        <ul>
          <li>정확도가 매우 중요한 서비스</li>
          <li>일반적인 선택</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>Key-value DB</td>
      <td>
        <ul>
          <li>빠름</li>
          <li>가벼움</li>
        </ul>
      </td>
      <td>
        <ul>
            <li>메인 데이터의 서브 용도로 적합</li>
            <li>채팅을 위한 pub/sub</li>
            <li>자주 쓰는 데이터 캐싱</li>
            <li>영상 스트리밍</li>
            <li>로그인 기록 저장</li>
        </ul>
      </td>
      <td>
        <ul>
            <li>redis (인메모리(rem) -> 빠름)</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>Graph DB</td>
      <td>
        <ul>
          <li>방향, 가중치 등이 메인</li>
          <li>전용 graph query language 필요</li>
        </ul>
      </td>
      <td>
        <ul>
            <li>방향, 관계가 중요한 서비스</li>
            <li>비행기 노선</li>
            <li>SNS 친구 관계</li>
            <li>바이러스 전염맵</li>
            <li>추천 서비스</li>
        </ul>
      </td>
      <td>
        <ul>
            <li>neo4j</li>
            <li>Sparsity</li>
            <li>OreintDB</li>
            <li>ArangoDB</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>Document DB</td>
      <td>
        <ul>
            <li>데이터 JSON형태로 저장</li>
            <li>중복제거(정규화) 안함</li>
            <li>분산처리를 염두해둠. 입출력이 많은 서비스에 유리</li>
            <li>일관성이 떨어짐</li>
        </ul>
      </td>
      <td>
        <ul>
            <li>입출력이 많은 서비스에 유리(분산 처리 잘됨)</li>
            <li>SNS</li>
            <li>실시간 채팅</li>
            <li>게시판</li>
            <li>온라인 게임</li>
        </ul>
      </td>
      <td>
        <ul>
            <li>MongoDB</li>
            <li>CouchDB</li>
            <li>Cloud Firestore</li>
        </ul>
      </td>
    </tr>
    <tr>
      <td>Column-family DB</td>
      <td>
        <ul>
            <li><b>join 지원 안함</b></li>
            <li>column 기반 데이터베이스</li>
            <li>table 내 row를 만들고 자유롭게 기입하는 식 (컬럼 달라도됨)</li>
            <li>RDB와 비슷하지만 스키마가 없기 때문에 여유로움</li>
            <li>분산처리를 염두해둠. 입출력이 많은 서비스에 유리</li>
            <li>Column 기반 작업이 많을 때 유리 (컬럼에 독립적 접근 가능)</li>
        </ul>
      </td>
      <td>
        <ul>
            <li>실시간 분석</li>
            <li>시계열 데이터</li>
        </ul>
      </td>
      <td>
        <ul>
            <li>Cassandra</li>
            <li>Hbase</li>
            <li>GCP BigTable</li>
            <li>Microsoft Azure Cosmos DB</li>
        </ul>
      </td>
    </tr>
  </tbody>
</table>

- 참고
    - Document vs. Column-family  
    [choosing-between-columnar-and-document-database](https://www.skinternational.com/post/choosing-between-columnar-and-document-database)


<!-- ## RDB
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
    - Google Cloud Search -->


> 출처:  
https://www.youtube.com/watch?v=ZVuHZ2Fjkl4
