# 가상 네트워크 종류
- Host-Only
  - 외부와 단절된 내부 네트워크
  - 구성된 가상머신 끼리만 통신 가능
- NAT  
  - 가상머신을 실행하는 PC에서 가상머신 안에 설치된 OS에게 IP 할당
  - 다른 PC에서는 보이지 않음 (게이트웨이가 다르기 때문)
- Bridged  
  - 외부에서 직접 연결되어 있는 PC처럼 인식하도록 하는 방식
  - 다른 PC에서 보임 (게이트웨이가 같음)
- NAT Network
  - Host-Only Network 드라이버가 가상 드라이버 역할을 해주어, 가상머신 끼리도 통신이 가능하게 하는 방식
  - 다른 PC에서 보임
  - 포트포워딩으로 Host <-> Guest 통신이 가능
![image](https://github.com/user-attachments/assets/5262c5d4-0ddf-4ccb-bc48-db08d7b37cf2)


---

https://www.nakivo.com/blog/virtualbox-network-setting-guide/

https://depotceffio.tistory.com/entry/%EA%B0%80%EC%83%81%EB%A8%B8%EC%8B%A0-%EC%9A%B0%EB%B6%84%ED%88%AC-SSH-%EC%9B%90%EA%B2%A9-%EC%A0%91%EC%86%8D%ED%95%98%EA%B8%B0  
https://blog.naver.com/PostView.naver?blogId=islove8587&logNo=223004216580&photoView=2