# 개요
인터넷 주소를 도메인 이름(예: www.google.com) 으로 변환해주는 시스템  
- naver.com -> 223.130.200.236

![image](https://github.com/hana2set/study/assets/97689567/bbd04d86-e498-45e7-8e8f-bd7718b2e53f)

## DNS 서버 계층
![image](https://github.com/hana2set/study/assets/97689567/615e1fa2-28ea-4d17-8e8b-95f676662aec)

## DNS Architecture
![image](https://github.com/hana2set/study/assets/97689567/73504342-6918-4f27-b191-e5e190920995)



## 작동 순서
![image](https://github.com/hana2set/study/assets/97689567/e56a8a97-7725-40d1-b7a0-bfdb3c39b9ac)
출처: https://www.linkedin.com/pulse/how-does-dns-process-work-mohamed-ayman-elshazly-/




1. Browser (User)
    - 사용자가 브라우저에 DNS 입력
    - **host 파일, 캐시**에서 일치하는 IP 주소를 찾지 못한 경우  
    -> DNS 쿼리(요청)을 4개의 DNS 서버에 보냄
2. **Recursive DNS resolver** (or server)
    - 컴퓨터와 다른 DNS 서버간 중개자 역할
    - 먼저 캐시를 검사하고 없으면 다음 DNS 서버(Root DNS)로 요청을 보냄
3. **Root DNS Server**
    - 최상위 DNS 서버로 적절한 TLD로 보내는 역할
4. **TLD DNS Server**
    - TLD(Top Level Domain)을 관리하는 DNS 서버 (.com, .net, .org ...)
    - 일치하는 Authoritative Nameserver IP 주소를 반환함
5. **Authoritative Nameserver**
    - 신뢰할 수 있는 도메인 목록을 제공하는 설정된 서버 집합
    - IP 주소를 포함하여 방문하려는 도메인 이름과 관련된 모든 정보 반환 (**DNS 레코드**, 이 정보를 토대로 DNS resolver가 DNS 캐싱 수행)

## DNS 레코드
Authoritative Nameserver에 저장되어 있는 도메인에 대한 정보(IP 주소, 처리 방법, 설정)를 보관하는 파일
- IP 주소 및 해당 도메인에 대한 요청의 처리 방법에 대한 정보를 제공
- ex. TTL(time-to-live): 레코드 새로 고침 빈도


### DNS 레코드 타입
|이름|설명|
|-|-|
|**A 레코드** | 웹 사이트가 호스팅되는 컴퓨터의 IPv4 주소|
|**AAAA 레코드** | IPv6 주소 |
|**CNAME**| 호스트 네임을 다른 호스트 네임과 연결. A나 AAAA 레코드가 있어야함 |
|**ANAME**| cname처럼 작동하지만 호스트 네임을 호스트 네임의 IP로 가리킴 |
|**NS**|호스트존의 네임 서버 지정|
|MX|전자 메일 서버를 가리킴|
|PTR|호스트 이름에 대한 IP 주소|
|SOA|DNS 영역에 대한 관리 정보 포함|
|SRV|다른 서비스에 대한 서비스 레코드|
|TXT|확인, SPF, DKIM, DMARC 등에 주로 사용되는 텍스트 레코드|
|CAA|SSL/TLS 인증서에 대한 인증 기관 레코드|

- (Route 53) Alias 레코드 : AWS 리소스 매핑

> IPv4 192.168.0.1.   
> IPv6 2001:0db8:85a3:0000:0000:8a2e:0370:7334

> 참고  
https://cloudinfrastructureservices.co.uk/what-is-dns-hierarchy/  
https://www.ibm.com/kr-ko/topics/dns  
https://aws.amazon.com/ko/route53/what-is-dns/  
https://www.cloudflare.com/learning/dns/what-is-dns/
https://www.geeksforgeeks.org/working-of-domain-name-system-dns-server/  
https://www.cloudflare.com/ko-kr/learning/dns/dns-records/  
