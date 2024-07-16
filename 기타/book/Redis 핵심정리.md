<!-- 맥스웰 데이브슨 다 실바, 휴고 로페스 타바레스 지음 -->

- 예제 코드 https://github.com/redis-essentials/book

<br>

- 레디스는 고성능 키-값 데이터 저장소인 NoSQL
- 데이터 영속성
  - 스냅샷, 저널링
- 기능
  - 키 만료, 트랜젝션, 게시/구독
  - 스크립트(lua) 기능 사용 가능 
- 싱글 스레드 기반으로 경합조건이 발생하지 않음. (원자적)

# 
- 노드로 redis 사용
```sh
cd redis-essentials
npm install redis
vi hello.js
node hello.js
```
- redis-cli
# 데이터 타입
### 문자열
- 텍스트, 정수, 부동소수점, 바이너리 데이터 등 모든 데이터 저장 가능.
- 512MB
- 사용 예시
  - 캐시 메커니즘
  - 자동 만료되는 캐시: SETEX, EXPIRE, EXPIREAT 등
  - 개수 계산 (count): INCR ...
- cli
  - `MSET`,`MGET`: 다중키값 저장
  ```sh
  > MSET first "First Key value" second "Second Key value"
  > MGET first second
  1) "First Key value"
  2) "Second Key value"
  ```
  - `EXPIRE` 키 만료 시간 추가
  - `TTL` 키 생존시간 (초단위)
  ```sh
  > SET current_chapter "Chapter 1"
  > EXPIRE current_chapter 10
  (integer) 1 
  > GET current_chapter
  "Chapter 1"
  > TTL current_chapter
  (integer) -2 
  > GET current_chapter
  (nil)
  ```
  - `INCR` 키 값 1 증가, 반환
  - `INCRBY` 키 값 입력 수치 만큼 증가, 반환
  - `INCRBYFLOAT` 부동소수점을 받아 키 값을 증가, 새롭게 변경된 값 반환
  - `DECR`
  - `DECRBY`
  - `DECRBYFLOAT`
  ```sh
  > SET counter 100
  > INCRBY counter 5
  (integer) 105
  ```

### 리스트
- 콜렉션, 스택, 큐 ...
- 연결 리스트
- 추가, 삭제 : O(1), 접근: O(N) (첫번째, 마지막은 일정)
- 예시
  - 이벤트 큐
  - 최근 사용자 글 저장
- cli
  - `LPUSH`, `RPUSH`: 리스트 앞, 뒤에 데이터 추가
  - `LLEN`: 리스트 길이 리턴
  - `LINDEX`: 인덱스에 해당하는 값 반환 (음수 가능)
  - `LRANGE`: 주어진 인덱스 범위를 배열로 반환
  - `LPOP`, `RPOP`: 값 반환 후 삭제
  - `BRPOP`: RPOP의 블로킹버전. 리스트에 데이터가 없으면 기다렸다가 값 반환 후 삭제
  - `RPOPLPUSH`: RPOP 실행 후 LPUSH 실행 후 값 반환
  
### 해시
- 메모리 효율화에 목적을 둔 양쪽으로 연결된 리스트 (<->최적화)
- cli
  - `HSET` key field value
  - `HMSET` 
  - `HINCRBY`
  ``` sh
  > HSET movie "title" "The Godfather"
  > HMSET movie "year" 1972 "rating" 9.2 "watchers" 1000
  > HINCRBY movie "watchers" 3
  (integer) 1003
  ```
  - `HGET`, `HMGET`
  - `HDEL`
  - `HGETALL`
  - `HKEYS`, `HVALS`
  - `HSCAN` 필드 하나씩 리턴
> - 작은 해시는 ziplist로 인코딩됨 (메모리가 최적화)
> - ziplist로 인코딩될 시 지켜야할 사항
>   - hash-max-ziplist-entries(기본값 512)보다 작은 필드로 유지 
>   - hash-max-ziplist-value(기본값 64byte)보다 작은 필드값로 유지 

## 고급 데이터 타입
### 셋
- 순서가 없고 동일한 문자열이 없는 콜렉션
- 모든 엘리먼트가 정수면 메모리 영역을 줄일 수 있음.
- 최대 갯수 2^32^-1(40억)
- 사용 예시
  - 데이터 필터링 (한도시에서 다른 도시로 도착하는 모든 비행기 필터링)
  - 데이터 그룹핑 (비슷한 제품을 보는 사용자 그룹핑)
  - 엘리먼트십 확인 (블랙리스트 등)
- cli
  - `SADD`: 원소 추가 (여러개 가능)
  - `SINTER`: 모든 셋의 공통 원소 반환
  - `SDIFF A B~1~ B~2~ ...` : 차집합 (A - B~1~ - B~2~ ...)
  - `SUNION`: 모든 셋의 원소 반환
    ```sh
    > SADD user:max:favorite_artists "Arcade Fire" "Arctic Monkeys" "Belle & Sebastian" "Lenien"
    > SADD user:hugo:favorite_artists "Daft Punk" "Arctic Monkeys" "The Kooks"
    > SINTER user:max:favorite_artists user:hugo:favorite_artists
    1) "Arctic Monkeys"
    > SDIFF user:max:favorite_artists user:hugo:favorite_artists
    1) "Belle & Sebastian"
    2) "Lenien"
    3) "Arcade Fire"  
    ```
  - `SRANDMEMBER`: 무작위 원소 반환
  - `SISMEMBER`: 원소가 존재하는지 확인 (존재:1, 없음:0)
  - `SREM`: 원소 삭제 후 남은 갯수 리턴
  - `SCARD`: 원소 수 반환
  - `SMEMBERS`: 모든 원소 배열로 반환

### 정렬된 셋
- 점수로 정렬된 중복없는 콜렉션
- 점수 같을 경우 사전순 정렬
- 추가, 변경, 변경 성능 O(logN)
- 내부 구현은 분리된 2개의 데이터 구조로 되어있음
  - 해시 테이블이 존재하는 스킵리스트(skiplist)
  - 집리스트(ziplist)
- 예시 
  - 실시간 대기자 목록
  - 상위 점수, 비슷한 점수, 친구 점수를 보여주는 리더보드
  - 수백만 개의 단어를 사용한 자동 완성 시스템
- cli
  - `ZADD`: 추가
  - `ZRANGE`, `ZREVRANGE`: 정렬된 원소 반환
    - WITHSCORES - 점수와 함께 리턴
  - `ZREM`: 삭제
  - `ZSCORE`: 점수 반환
  - `ZRANK`, `ZREVRANK`: 등수 반환 (0부터)
  ```sh
  > ZADD leaders 100 "Aclie"
  > ZADD leaders 100 "Zed"
  > ZADD leaders 102 "Hugo"
  > ZADD leaders 101 "Max"
  > ZREVRANGE leaders 0 -1
  1) "Hugo"
  2) "Max"
  3) "Zed"
  4) "Aclie"
  > ZREVRANGE leaders 0 -1 WITHSCORES
  1) "Hugo"
  2) "102"
  3) "Max"
  4) "101"
  ...
  > ZRANK leaders "Max"
  (integer) 2
  ```

### 비트맵
- 실제로는 "문자열" 이나 레디스에서 비트맵으로 다룰 수 있는 커맨드 제공
- 메모리 효율이 좋음 (전체 사용자를 기반으로 할당하기 때문에 항상은 아님)
- **실시간 분석을 포함하는 애플리케이션에 매우 적합**
  - 오늘 사용자 X가 Y 액션을 실행하였는가?
  - 지난 달에 몇명이나 Y액션을 실행하였는가?
- 다른 데이터구조와 다르게 문자열 대신 노드 버퍼를 이용한 레디스 클라이언트 생성이 바이트 제어에 도움됨
- cli
  - `SETBIT`: 1 또는 0만 입력 가능
  - `GETBIT`
  ```sh
  > SETBIT visit:2021-01-01 10 1
  > SETBIT visit:2021-01-01 15 1
  (integer) 0
  > GETBIT visit:2021-01-01 10
  (integer) 1
  ```
  - `BITCOUNT`: 비트맵에 1로 표시된 모든 비트의 개수를 리턴
  - `BITOP`: 대상 키, 비트 연산, 연산을 적용하고 결과를 저장할 키 목록 (OR, AND, XOR, NOT)
  ```sh
  > BITOP OR total_users visit:2021-01-01 visits:2021-01-02
  (integer) 2
  > BITCOUNT total_users
  (integer) 3
  ```

### 하이퍼로그로그
- 개념적으로는 알고리즘 (실제 데이터 타입이 아님)
- 고유 엘리먼트 개수를 근사치로 제공하기 위해 확률화를 사용하는 알고리즘
- 아주 작은 메모리(최대 12KB)를 사용하고 항상 O(1)로 동작
- 0.81퍼센트의 표준오차
- 사용 예시
  - 고유 방문자 수 계산 (vs SET )
  - 특정 날짜 또는 시간에 검색한 고유 키워드 개수 계산
  - 사용자가 사용한 고유 해시태그 개수 계산
  - 책에 나오는 고유 단어 개수 계산
- cli
  - `PFADD`: 추가, 개수가 변경되면 1, 아니면 0 리턴
  ```sh
  > PFADD visit:2021-01-01 "carl" "max" "hugo" "arthur"
  (integer) 1
  > PFADD visit:2021-01-01 "max" "hugo"
  (integer) 0
  ```
  - `PFCOUNT`: 입력된 키에 대한 근사치 개수 반환
  ```sh
  > PFCOUNT visit:2021-01-01
  (integer) 4
  > PFCOUNT visit:2021-01-02
  (integer) 4
  > PFCOUNT visit:2021-01-01 visit:2021-01-02
  (integer) 6
  ```
  - `PFMERGE`: 병합
  ```sh
  > PFMERGE visit:total visit:2021-01-01 visit:2021-01-02
  OK
  > PFCOUNT visit:total
  (integer) 6
  ```


# 시계열
- 시계열 데이터는 많은 실시간 분석 데이터로 인메모리 레디스로 다룰 경우 최적화가 중요

## 해시로 최적화
- 작은 해시는 ziplist로 인코딩됨 (메모리가 최적화)
- ziplist로 인코딩될 시 지켜야할 사항
  - hash-max-ziplist-entries(기본값 512)보다 작은 필드로 유지 
  - hash-max-ziplist-value(기본값 64byte)보다 작은 필드값로 유지 
- 키를 그룹화해서 나누고, INFO 커맨드로 메모리 사용량 확인
- ex
  - 기존
  ```
  namespace:1sec:0 10
  namespace:1sec:1 20
  namespace:1sec:2 30
  namespace:1sec:3 40
  ...
  ```
  - 변경
  ```
  namespace:1sec:0 0 10
  namespace:1sec:0 1 20
  namespace:1sec:0 2 30
  namespace:1sec:3 3 40
  namespace:1sec:3 4 50
  ...
  ```

## 유일 엘리먼트 추가 (정렬된 셋, 하이퍼로그로그 사용)
- zset-max-ziplist-netires(기본 128) 기반으로 키 분배
  
# 커맨드(괴물들이 사는 나라)

## pub/sub
- 예시
  - 뉴스와 날씨 대시보드
  - 채팅 애플리케이션
  - 지하철 지연 경고 등의 푸시 알림
- cli
  - `PUBLISH`: 메세지를 레디스 채널에 보냄
  - `SUBSCRIBE`: 하나 이상의 채널을 구독
  - `PSUBSCRIBE`: 위와 같지만 채널 이름을 글로브(glob) 형태로 받음
  - `UNSUBSCRIBE`, `PUNSUBSCRIBE`: 구독해지
  
## 트랜잭션
- 순서대로 원자적으로 실행되는 커맨드의 열
- DB와 달리 실패하더라도 **롤백이 없다**
- 큐로 쌓이기 때문에, 내부에서 어떠한 결정도 내릴 수 없음
- cli
  - `MULTI`: 트랜잭션의 시작
  - `EXEC`: 트랜잭션의 마지막
  - `DISCARD`: 트랜잭션 커맨드 하나도 실행안함
  - `WATCH`: 키 그룹에 **낙관적 락** 부여, 주시받는 키가 변경되지 않으면 트랜잭션을 실행하고 변경되면 null 반환 후 트랜잭션 다시 실행
  ```js
  client.watch(key, function(watchErr, watchReply) { // 2
    client.zrange(key, 0, 0, function(zrangeErr, zrangeReply) { // 3
      var multi = client.multi(); // 4
      multi.zrem(key, zrangeReply); // 5
      multi.exec(function(transactionErr, transactionReply) { // 6
        if (transactionReply) {
          callback(zrangeReply[0]); // 7
        } else {
          zpop(key, callback); // 8
        }
      });
    });
  });
  ```

## 파이프라인
- 다중 커맨드를 한번에 레디스 서버에 보내는 방법
- 일반적으로, client는 커맨드를 개별 요청함. (응답시간 : RTT)
- **트랜잭션 처리되지 않음**

## 스크립트
- 2.6부터 루아(luascript) 제공
- 많은 사용자들이 WATCH/MULTI/EXEC 형태의 트랜잭션 코드를 루아 스크립트로 교체하고 있음 (병렬적인 변경도 없을 것을 항상 보장)
- 문자열로 루아 스크립트를 전송해야함.
  - `redis.call`, `redis.pcall` 함수 사용
- cli
  - `EVAL`, `EVALSHA`: 루아 스크립트 실행 커맨드 
  > EVAL script numkeys key {key ...} arg [arg ...]
  > script: 문자열로 구성된 루아 스크립트
  > key : 루아 스크립트에서 KEYS 변수
  > arg : 루아 스크립트에서 ARGV 변수
  ```js
  var luaScript = 'return redis.call("GET", KEYS[1])'; // 2
  client.eval(luaScript, 1, "mykey", function(err, reply) { // 3
    console.log(reply); // 4
    client.quit();
  });
  ```

## 기타 중요 커맨드
- `PING`: 서버 연결을 테스트, 메세지 "PONG"을 반환
- `IFNO`: 버전, 메모리 사용량, 저장소, 복제본, 키 스페이스 등 리턴
- `DBSIZE`: 레디스에 존재하는 키 개수 리턴
- `DEBUG SEGFAULT`: 의도적으로 **올바르지 않은 메모리 접근을 수행해 프로세스 종료** (테스트용)
- `MONITOR`: 실시간으로 모든 커맨드를 보여줌. 디버깅시 유효하지만 비용이 큼(성능을 50%까지 떨어뜨릴수 있다함)
- `CLIENT LIST`: 모든 클라이언트 목록 리턴
- `CLIENT SETNAME`: 클라이언트 이름 변경
- `CLIENT KILL`: 클라이언트 강제종료. **IP, 포트, ID, 타입 가능**
```sh
CLIENT KILL ADDR 127.0.0.1:51167
CLIENT KILL ID 22
CLIENT KILL ID TYPE slave
```
- `FLUSHALL`: 레디스의 모든 키 삭제
- `RANDOMKEY`: 무작위로 키 이름 하나 리턴 (대략적으로 보고싶을때 사용, KEYS도 있으나 모든 키를 반환해 서버가 다운될 수 있음)
- `EXPIRE`: N 초뒤 키 만료
- `EXPIREAT`: 유닉스 타임스탬프 기반으로 키 삭제
- `TTL`/`PTTL`: 키의 생존시간 반환. 값이 없으면 -1, 키가 없으면 -2 (초/밀리초 단위)
- `PERSIST`: 키에 부여된 타임아웃 제거 (만료되지 않음)
- `SETEX`: `SET` + `EXPIRE`
- `DEL`: 키 삭제 후 삭제된 키 갯수 반환
- `EXISTS`: 키가 존재하면 1, 없으면 0
- `MIGRATE`: 키를 대상 레디스 서버로 옮김.
  - 원자적이라 양쪽 레디스 서버 블록됨.
  - 키가 존재 시 커맨드 실패
  - COPY, REPLACE 옵션
- `SELECT`: 레디스는 다중 데이터베이스 개념을 가지고 있음. 이를 선택할 수 있는 기능 (기본: 0~15).
  - 이 기능을 쓰는 것보다 여러 개의 레디스 서버를 사용하는 것이 더 좋다. (CPU활용, 병목 확인 좋음)
- `AUTH`: 레디스에 연결할 수 있는 클라이언트를 허가
- `SCRIPT KILL`: 스크립트 종료.
  - 다만 수행 중일 경우 종료되지 않는데, 그럴땐 `SHUTDOWN NOSAVE` 커맨드 사용해야함
- `SHUTDOWN`: 모든 클라이언트를 종료하고, 데이터를 최대한 저장하고, 서버 종료
  - option: SAVE, NOSAVE
- `OBJECT ENCODING`: 키에서 사용 중인 인코딩값 리턴

## 데이터타입 최적화
- redis.conf 파일 수정해 적절한 값을 사용하도록 설정
- 인코딩 타입은 **직접 설정할 수 없고 설정에 따라 자동으로 인코딩 됨으로**, 의도와 데이터 구조에 따라 데이터를 적절히 입력하도록 해야함
### 문자열
  - 인코딩
    - int: 64bit. 부호있는 정수형
    - embstr: 40byte보다 작은 문자열
    - raw: 40byte보다 큰 문자열
  ```sh
  > SET strl 1234
  OK
  > OBJECT ENCODING strl
  "int"
  ```
### 리스트
  - 인코딩
    - 집리스트(ziplist): 리스트 전체 크기의 바이트가 **list-max-ziplist-entries** 설정보다 작고, 리스트의 개별 엘리먼트의 바이트가 **list-max-ziplist-value**보다 작으면 집리스트가 사용됨
    - 연결리스트(linked list): 집리스트가 아니라면 연결리스트
### 셋
  - 인코딩
    - 인트셋(intset): 모든 엘리먼트가 정수이고, 셋의 개수가 set-max-intset-entries 설정보다 작으면
    - 해시테이블(hashtable): 인트셋이 아니면
### 해시
- 인코딩
  - 집리스트: 필드 개수가 hash-max-ziplist-entires 설정보다 작고, 해시의 필드 이름과 값이 hash-max-ziplist-value 설정보다 작으면
  - 해시테이블: 집리스트가 아니면
### 정렬된 셋
- 인코딩
  - 집리스트: 셋의 개수가 set-max-ziplist-entires 설정보다 작고, 셋의 모든 엘리먼트 값이 zset-max-ziplist-value 설정보다 작으면
  - 스킵리스트: 집리스트가 아니면
  
## 메모리 관련
- **해시와 집리시트는 트레이드 오프 관계**
  - 해시는 엘리먼트를 소유할수록 성능이 느려진다. 하지만 검색은 O(1)
  - 집리스트는 메모리를 효율적으로 쓰는 이중 연결 리스트. 검색은 O(n)
  - 메모리와 성능 간의 좋은 트레이드오프를 발견할 때 까지 설정을 변경해보자. (인스타그램 블로그 솔루션 참조)
  
# 여러 언어로 레디스 다루기
- 언어 client 마다 조금씩 사용법이 다름
- 스킵

# 일반적인 실수
### 작업에 대한 잘못된 데이터 타입
- **결정 전 벤치마크 테스트 필수!**
- 셋 -> 비트맵이 효율적이라 판단했으나 실제로 아닌 경우 있음.
  - 예) 유저별로 딜을 입력한 셋이 있다고 가정. 비트맵으로 변경 시, 각 user와 딜에 대해 bitmap key가 필요한데, 키는 보통 최대 길이를 기준으로 만듬으로, userid가 길 경우, key값이 엄청나게 길어짐.
### 다중 레디스 데이터베이스
- 단일 스레드 기반이라 구림
- 사라질 기능.
### 네임스페이스가 없는 키
- 네임스페이스가 없음으로, 키에 적절한 접두어를 붙이는게 좋음.
- ex) music-online:song:1
### 스왑 사용
- 스왑 공간에 접근할때 **운영체제의 작업이 완료될때까지 레디스는 블록**됨
- 최소한으로 사용하는 것이 좋음.
  - linux 커널 매개변수 `swappiness=1` || `swappiness=0`
### 적절한 메모리
- 어떤 전략이라도 활성화하려면 **백업을 수행할 메모리가 충분히 있어야 함**
- 즉, **사용 가능한 메모리의 50퍼센트 이상은 사용하지 않아야한다**.
  - **메모리 사용량에 대한 알림 설정**을 반드시 할 것!
### 부적절한 저장 전략
- 주기적인 백업 전략으로 시스템이 느려질 수 있음.
  - 느려졌을 경우 고려해볼 만한 사항들
    - THP 리눅스 커널 기능 쓰지않기
    - HVM 인스턴스 사용 (fork 호출 시간 문제)
    - 가능한 한 백업을 자주하지 않기
    - 레디스의 자동 저장 비활성화

# 보안 기술
### `requirepass`
  - 레디스는 엄청 빠르기때문에 초당 수천 개의 패드워드 요청을 입력할 수 있음.
  - 최소 64자의 복잡한 패스워드 사용
  - **redis.conf**에 추가 후 재시작
  - `AUTH`커맨드로 인증
### 중요 커맨드 이름 변경(혹은 빈문자열로) 
- **rename-commands.conf** 설정 파일 생성
  ```sh
  rename-commands FLUSHDB e0cc934d13jdfs3c93dje382erfds
  rename-commands FLUSHALL a9c23jdf8v23vfk4k23980afd87fa
  rename-commands CONFIG ""
  rename-commands KEYS ""
  rename-commands DEBUG ""
  rename-commands SAVE ""
  ```
- **redis.conf**
  ```sh
  include /path/rename-commands.conf
  ```
- 상용에서 `SAVE` 대신 `BGSAVE` 사용 추천

### 네트워크 보안
- 방화벽
  - iptables 프로그램
- 직접 접속 대신 루프백 인터페이스로 실행
- 공용 인터넷 대신 가상 사설 클라우드에서 실행
- 클라이언트와 서버 간 통신 암호화
  - stunnel 툴


# 레디스 확장
## 저장
- 레디스는 메모리에 저장
- 메모리는 휘발성 저장소
- 이를위해, Redis Database와 Append-only File을 제공함 (RDB, AOF)
- [레디스 저장 방식](http://oldblog.antirez.com/post/redis-persistence-demystified.html)

### RDB
- 파일 저장 LZF 압축 파일
- SAVE 커맨드는 즉시 RDB를 생성하지만, 레디스를 블록함으로 **`BGSAVE`를 사용할 것.**
  - `BGSAVE`는 자식 프로세스를 사용함.
- 메모리에 올려서 작업하기 때문에 메모리가 충분히 확보되어 있어야함
- 스냅샷 생성
```sh
# 적어도 10000개의 쓰기작업(change)이 실행되면, 60초마다 디스크에 .rdb 파일을 저장한다.
save 60 10000
```
- 설정에서 RDB 관련 지시자
  - `stop-writes-on-bgsave-error`: **"yes"**/"no", 저장 실패시 쓰기작업 정지 여부
  - `rdbcompression`: **"yes"**/"no", .rdb 파일에 대한 LZF 압축 여부
  - `rdbchecksum`: **"yes"**/"no", 체크섬(중복검사) 여부. rdb 파일 로딩 전 체크섬을 수행한 뒤 일치하지 않으면 실행하지 않음
  - `dbfilename`: 기본값 **dump.rdb**
  - `save`: 스냅샷 주기 설정. save 60 10000
  - `dir`: AOF, RDB 파일 경로 설정
  
### AOF
- AOF 파일은 **사람이 읽을 수 있는 로그를 추가만 할 수 있는 로그파일**
- AOF 활성화 시, 데이터 집합 변경하는 커맨드를 받을때마다 AOF 파일에 해당 커맨드를 추가
- 재시작 시 나열된 커맨드를 순서대로 실행하여 복구 (RDB 스냅샷의 대안)
- 1.1 버전부터 완전한 내구성 있는 전략으로 소개
- redis-check-aof 툴 (손상에 대응하는 툴)
  - 성능비용, 추가공간 필요
- 설정에서 AOF 관련 지시자
  - `appendonly`: yes/**no**, AOF 사용 여부
  - `appendfilename`: 기본값 빈 문자열
  - `appendfsyne`: no/always/**everysec**, `fsync`(레디스 flush 호출 함수)에 대한 정책
  - `no-appendfsync-on-rewrite`: yes/**no**, fsync 도중엔 레디스가 블록됨으로 **레디스 지연 문제가 발생 시 해당 옵션 yes 추천**
  - `auto-aof-rewrite-percentage`: 0-**100**, 파일 크기가 해당 퍼센트까지 도달하면 내부적으로 *BGREWRITEAOF* 커맨드 실행됨
  - `auto-aof-rewrite-min-size`: **67,108,864byte**, AOF 파일 최소 크기. *auto-aof-rewrite-percentage* 값 초과해도 해당 옵션값에 도달할 때 까지 AOF 저장이 안됨.
  - `aof-load-truncated`: yes/no, 절단된 AOF 파일에 대해 로드 여부. 
    - 절단 파일은 레디스 크래시 발생 시 생길 수 있음
    - yes: 파일 로드 후 에러 메세지 출력
    - no: 에레 출력 후  레디스 종료
  - `dir`: 경로
  
### RDB vs. AOF
- 복구속도: **RDB** > AOF
- 우선순위: RDB < **AOF** (AOF는 내구성 보장)
- 선택 가이드
  - 저장이 필요 없음 -> RDB·AOF 둘 다 비활성
  - 데이터 손실이 허용 -> RDB
  - 내구성 있는 저장 필요시 -> RDB·AOF 둘 다 사용

## 복제
- 레디스 인스턴스(master)가 쓰기 작업을 진행하는 동안, 다른 레디스 인스턴스(slave)가 마스터의 완벽한 복사본이 되는 것
```sh
# 1개 master, 2개 slave 만들기
> redis-server -port 5555
> redis-server -port 6666 --slaveof 127.0.0.1 5555
> redis-server -port 7777 --slaveof 127.0.0.1 5555

# 확인
> redis-cli -p 5555 SET testkey testvalue
> redis-cli -p 6666 GET testkey
"testvalue"
> redis-cli -p 7777 GET testkey
"testvalue"
```
- 마스터에 크래시가 발생했을 때, `SLAVEOF NO ONE` 커맨드를 통해, 자신(슬레이브)을 마스터로 승격하여 페일 오버를 발생시킬 수 있음.
> 페일 오버(failover)
> 예비 시스템으로 자동전환되는 기능. 여기서는 **슬레이브가 마스터로 승격됨**을 의미
```sh
# 크래시 발생
> redis-cli -p 5555 DEBUG SEGFAULT
> redis-cli -p 6666 SLAVEOF NO ONE
> redis-cli -p 7777 127.0.0.1 6666

# 작동 확인
> redis-cli -p 6666 SET testkey testvalue
> redis-cli -p 7777 GET testkey
"testvalue"

## 주의! 모든 클라이언트들이 127.0.0.1:6666로 변경되었는지 확인할 것!
```
- 관련 설정 지시자
  - `min-slaves-to-write`: **0**, 마스터에 연결된 복제본(slave) 최소 갯수
  - `min-slaves-max-lag`: **10(s)**, 최대 지연 시간

## 파티셔닝
- 데이터를 나누고 여러 장비에 분산시키는 행위
- 최초에는 분산을 염두하지 않았으나 레디스가 커지면서 생김
- 수평 파티셔닝(샤딩)을 가장 많이 씀

### 범위 파티셔닝
- 키 범위를 기반으로 분산
- 고르지 못한 분포도
- 인스턴스 갯수 변경을 쉽게 수용하지 못함

### 해시 파티셔닝
- 키를 해시함수에 넣고 나온 값을 인스턴스 갯수로 나눈 나머지를 인덱스로 사용
- 해시 파티셔닝의 효율 == 해시 함수의 적합성
- 일반적으로 `MD5`, `SHA1` 사용
```js
var index = hashFunction(redisKey) % redisHosts.length;
var host = redisHosts(index);
```
- 충돌을 줄이기 위해 인스턴스 갯수를 소수로 쓰는 것을 권장
- 인스턴스 갯수 변경에 치명적임.
  > 필자는 데이터 목록 서버 두 대를 추가했을 때 레디스 데이터 집합의 75%가 무효화 됐다고 함.

### 미리 샤딩하기
- 미리 샤딩해 레디스 장비의 갯수는 고정함
- 확장이 필요하면 수직적인 확장
- 클러스터의 개수가 고정되어 있기 때문에, 탄력성이 없음(장애에 대처가 힘듬, 서버 자체를 교체해야함)
- 관리하고 모니터링해야 할 인스턴스가 많음. (적당한 툴 없음)
- **대안: 일관적 해싱**

### 일관적 해싱(consistent hashing)
- 해시를 균등하게 분배하기 위해 고안된 알고리즘
- 노드 추가, 제거에 대한 키 이동을 최소화하여 시스템에 미치는 영향을 줄임
- hash ring을 통해 해시를 분배하는 방식
  ![image](https://github.com/hana2set/study/assets/97689567/7b2f463f-d14a-4a6b-a21c-e3725ebf449c)
  - 해시 링을 만들고, 해시를 균등하게 펼침 (예를들어, 해시값을 2^32로 나눈 나머지를 배치)
  - 노드마다 범위를 정하고 포함된 해시를 노드에 배치함.

### 태깅
- 키를 접두사, 접미사를 붙임.
- SDIFF, SINTER 같은 커맨드 사용하기 위해서는 모든 키가 동일 인스턴스에 있어야함.
- 이를 보장하기 위한 방법 중 하나 = 태그
 
### 데이터 저장소 vs. 캐시
- 캐시로 쓸 때는, 일관적 해싱 사용
- 데이터 저장소로 쓸 때는, 키를 동일 인스턴스에 매핑해야 하기 때문에 데이터를 노드에 복제하고 적절한 분산 시스템을 생각해야함 (레디스 클러스터나 질의에 적당한 인스턴스를 찾아주는 솔루션)

### 레디스 파티셔닝의 구현
- **클라이언트, 프록시, 질의 라우터**와 같이 여러 계층으로 구현 가능

### 트웸프록시로 자동 샤딩하기
- 프록시 계층에서 추천하는 툴
- 로드밸런서로 여러 트웸프록스로 분산하는 걸 추천

# 레디스 클러스터와 레디스 센티널(집단지성)
- 마스터/슬래이브 위상은 다음과 같은 시나리오에서 잘 작동
  - 마스터가 모든 데이터를 저장하기에 충분한 메모리를 가짐
  - 전체 읽기 용량이 하드웨어 성능보다 클 때 슬레이브 추가로 해결 가능(네트워크 대역폭 등)
  - 마스터 서버에 점검이 필요할 때 애플리케이션을 중단할 수 있음
  - 슬레이브에 마스터와 동일한 데이터가 있음
  > 마스터/슬래이브 위상
  > 마스터에 모든 쓰기 작업을 받고 그 변경을 나머지 슬레이브에 복제하는 방식
- 이러한 문제로, **레디스 센티널**이라는 프로젝트 실행됨

### CAP 정리
- CAP 정리는 다음 3가지를 모두 만족시키는 분산 시스템이 없다는 것을 증명
  - 일관성(consistency): 읽기 작업은 가장 최근에 쓴 것을 리턴할 수 있도록 보장한다.
  - 가용성(availability): 어떠한 작업이 성공 또는 실패하더라도 해당 작업이 응답을 받을 수 있도록 보장한다.
  - 분할 내성(partition toterance): 시스템은 네트워크 파티션이 일어나더라도 작업을 멈추지 않는다.
- 레디스 센티널과 레디스 클러스터는 일관성을 제공할 수 없음. (쓰기 작업의 분할 떄문)

## 레디스 센티널
- 레디스 마스터가 작동하지 않을 경우 슬레이브 중 하나를 마스터로 자동 승격할 수 있도록 설계된 분산 시스템
- 일반적인 아키텍처 - 레디스 서버마다 레디스 센티널을 설치
- 서버와 분리된 프로세스로, 레디스 서버와 다른 포트를 사용
![image](https://github.com/user-attachments/assets/2118bbec-37f8-4b97-8c9b-d155c2d9e79d)
[이미지 출처](https://github.com/selcukusta/redis-sentinel-with-haproxy?tab=readme-ov-file)
- 사용하려면 센티널을 지원하는 클라이언트를 따로 설치해야함
- 센티널 간 통신 : `__sentinel__:hello` (Pub/Sub 채널)
- `sentinel.conf`
  ```sh
  # 이름 mymaster, IP 주소 127.0.0.1, 포트 6379, 쿼럼 2인 레디스 마스터 모니터링
  sentinel monitor mymaster 127.0.0.1 6379 2 

  # 센티널이 30000 밀리초 동안 마스터에 도달하지 못하면 다른 센티널에 다운됐음을 알림
  sentinel down-after-milliseconds mymaster 30000

  # 페일오버된 마스터 노드는 180000 밀리초 안에 다시 마스터가 될 수 없다
  sentinel failover-timeout mymaster 180000

  # 마스터와 연결하기위해 동시에 재설정될 수 있는 슬레이브 갯수
  sentinel parallel-syncs mymaster 1
  ```
  > 쿼럼
  > 새로운 마스터를 선출하기 전 현재의 마스터가 다운 상태임을 동의하는 센티널의 최소 갯수
  - parallel-syncs 옵션에서 슬레이브를 재설정하는 동안 슬레이브는 사용 불가 상태가 됨으로 값을 최소화하는게 좋다.

### 네트워크 파티션(스플릿-브레인)
- 레디스 센티널은 네트워크 파티션이 발생하는 시나리오에서 일관성이 부족
- 즉, 높은 가용성과 자동 페일오버 문제를 해결하지만 다중 레디스 인스턴스 간의 **데이터 분산 문제는 해결하지 못함** -> 레디스 클러스터로 해결

## 레디스 클러스터
- 하나의 프로세스만 사용, 포트 2개 사용 (클라이언트 간 통신, 노드간 통신)

### 해시 슬롯
- 파티셔닝 메소드를 지칭함
- 16,384 슬롯
```sh
HASH_SLOT = CRC16(key) mod 16384
```
- 고려 사항
  - 마스터는 슬롯을 할당받지 못하면 데이터를 저장할 수 없다.
  - 마스터는 최소 하나의 슬롯을 가져야 함. (없으면 키 전달 불가)
  - 모든 마스터에 할당할 수 있는 전체 슬롯의 개수는 16,384개
  - 마스터에 대한 슬롯의 자동 재분포는 없다. (각 마스터에 대해 수동 할당)

### 해시 태그
- 클러스터에서 다중키를 사용할 수 있는 유일한 방법
```sh
# {user123}이라는 해시태그를 기반으로, 동일한 해시 슬롯에 저장됨
SADD {user123}:friends:usa "John" "Bob"
SADD {user123}:friends:brazil "Max" "Hugo"
SUNION {user123}:all_friends {user123}:friends:usa {user123}:friends:brazil
```

### 기본 클러스터의 생성
- 레디스 다운로드 시 utils/create-cluster 디렉토리에 `create-cluster` 쉘 스크립트가 있음
- 간단하게 관리하기 위해 `redis-trib`이라는 유틸리티 배포하고 있음
- 설정 예시
  ```sh
  cluster-enabled yes #필수, 없으면 레디스는 싱글 인스턴스로 실행됨
  cluster-config-file cluster.conf
  cluster-node-timeout 2000
  cluster-slave-vaildity-factor 10
  cluster-migration-barrier 1
  cluster-require-full-coverage yes
  ```

### 클러스터 관리
#### 커맨드를 통한 관리 (이해 문제로 생략)
- 클러스터 정보 확인
```sh
$ redis-cli -c -p 5000
127.0.0.1:5000> CLUSTER INFO
cluster_state:fail
...

```
- 클러스터 생성
```sh
$ redis-cli -c -p 5000 CLUSTERADDSLOTS (0...5460)
$ redis-cli -c -p 5000 CLUSTERADDSLOTS (5461...10922)
$ redis-cli -c -p 5000 CLUSTERADDSLOTS (10922...16383)
```
- `CLUSTER SET-CONFIG-EPOCH`
- `CLUSTER MEET 127.0.0.1 5001`

#### redis-trib 툴 사용
- 커맨드를 사용하는 것보다 훨씬 간단하게 관리 가능
- 확인, 수정, 재샤딩, 노드 삭제, 타임아웃 설정 등 가능 