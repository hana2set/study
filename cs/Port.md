## Port 분류
- 0번 ~ 1023번: 잘 알려진 포트 (well-known port)
- 1024번 ~ 49151번: 등록된 포트 (registered port)
- 49152번 ~ 65535번: 동적 포트 (dynamic port)

  
잘 알려진 포트(well-known port)는 특정한 쓰임새를 위해서 IANA에서 할당한 TCP 및 UDP 포트 번호.


## 자주 쓰는 Port 

| 포트   | 프로토콜  | 의미                                | 
|--------|-----------|---------------------------------------------|
| 20     | TCP       | FTP (데이터 전송, 파일 다운로드 시)                           | 
| **21** | TCP       | **FTP (제어. FTP클라이언트가 서버 접속 시)** | 
| **22** | TCP       | **SSH (보안 셸, 원격 접속)**                | 
| 23     | TCP       | 텔넷 (비보안 원격 셸)                        | 
| 25 | TCP       | SMTP (메일 발송)                        | 
| **53** | TCP/UDP   | **DNS (도메인 네임 시스템)**                | 
| **80** | TCP       | **HTTP (웹, 비암호화)**                     | 
| 119    | TCP       | NNTP (Network News Transfer Protocol)       | 
| **443**| TCP       | **HTTPS (HTTP + TLS/SSL, 보안 웹)**         | 
| 1521 | TCP       | Oracle 데이터베이스         | 
| **3000**| TCP      | **프론트엔드 개발용 서버 (React, Vue 등)**  | 
| 3306| TCP      | MySQL                      |  
| 5000| TCP      | Flask / FastAPI 개발 서버               |  
| 5432| TCP      | PostgreSQL                 |  
| 6379| TCP      | Redis           |  
| **8080**| TCP      | **개발용 HTTP 서버 (톰캣, Oracle 기본 개발 포트)**            |  
| **8443** | TCP       | **개발용 HTTPS 서버**               |
| 9200| TCP      | Elasticsearch 검색엔진                  |  
| 27017| TCP     | MongoDB            |  
