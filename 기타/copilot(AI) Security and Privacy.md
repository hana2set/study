# [GitHub Copilot Security and Privacy Concerns: Understanding the Risks and Best Practices](https://blog.gitguardian.com/github-copilot-security-and-privacy/)

- AI 기반 코드 완성 도구는 보안 및 개인정보 보호에 대한 우려가 있음
  - 학습 과정에서 질문할 때 입력하는 프롬프트도 학습.
    - 기본적으로 인터넷에 공개됨
    - 이미 학습된 데이테에 대해선 괜찮지만, 내부 및 개인 코드는 아님
    - 실제로 Copilot이 활성화된 리포지토리의 6%에서 유출이 되었다고 함.
- 보안 문제
  - 비밀 및 개인코드의 잠재적 유출
    - 실제 프롬프트로 copilot 내에서 하드코딩된 자격증명, 시큐리티를 수집했다함(상당수). [Yes, GitHub's Copilot can Leak (Real) Secrets](https://blog.gitguardian.com/yes-github-copilot-can-leak-secrets/)
    - **copilot 활성화 저장소는 일반 저장소보다 유출률이 40% 더 높다**고 함
  - 안전하지 않은 코드제안
    - 개발자 평균 기반 -> 노후화된 코드, 보안 결함
  - 오염된 데이터(악성코드)를 제안하기도 함
  - hallucination
  - 라이센스 문제. (출처가 불분명.)
- 개인정보 문제
  - 개인 코드 공유(조직에서 원하지 않는다면?)
  - 사용자 데이터 보존
- 따라서 다음과 같은 항목에 신경쓸 것
  - 코드 제안 주의 깊게 검토
  - 프롬프트에 security 입력 금지
  - ai 내부 ㅗ안 설정
  
   
  ### 요약
  - AI 활용 개발자라면 스스로 다음과 같은 사항에 유의해야함
      - 민감한 정보 유출
      - 안전하지 않은 코드 제안 - 대다수 개발자들이 하는 실수가 학습
      - 악성 코드 삽입
      - 잘못된 패키지 제안(hallucination) - 존재하지 않는 패키지 제안.
        - 환각 스쿼팅: 악성 개발자가 해당 패키지를 실제로 만듬으로서 악성 코드 삽입
      - 라이선스 문제 (출처를 제공하지 않음)
