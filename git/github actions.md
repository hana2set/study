# Github Actions
Github에 내장된 CI/CD 도구
- [GitHub Actions 설명서](https://docs.github.com/ko/actions)
- 무료이고 [특정구간(500MB/2,000분)](https://docs.github.com/ko/billing/managing-billing-for-github-actions/about-billing-for-github-actions)  초과시 유료

## [구성요소](https://docs.github.com/ko/actions/learn-github-actions/understanding-github-actions#the-components-of-github-actions)
![image](https://github.com/hana2set/study/assets/97689567/7b428990-3970-4ef3-9914-62faf30ac6c5)
![image](https://github.com/hana2set/study/assets/97689567/799edd33-eae3-419d-980a-4234080f280e)

- Workflow
    - 하나 이상의 작업을 실행하는 구성 가능한 자동화된 프로세스
    - Workflow 파일은 `.github/workflows` 폴더 아래 YAML으로 정의
    - event, 수동 혹은 스케쥴러로 트리거
- event
    - 워크플로우 실행을 트리거하는 저장소의 특정 활동
    - push, deploy, release, schedule(cron)... [전체 목록](https://docs.github.com/en/actions/using-workflows/events-that-trigger-workflows)
- runner
    - 워크플로가 트리거될 때 워크플로를 실행하는 서버
    - 하나의 job당 하나의 VM인 runner가 실행됨
- job
    - 하나의 runner에서 실행될 step의 집합
- step
    - 실행 가능한 하나의 shell script 또는 action
- Actions
    - 반복되는 복잡한 작업에 대한 묶음
    - Workflow의 가장 작은 단위로 재사용이 가능

## Workflow(yml, yaml) 파일 구성요소
```yml
name: GitHub Actions Demo
run-name: ${{ github.actor }} is testing out GitHub Actions 🚀
on: [push]
jobs:
  Explore-GitHub-Actions:
    runs-on: ubuntu-latest
    steps:
      - run: echo "🎉 The job was automatically triggered by a ${{ github.event_name }} event."
      - run: echo "🐧 This job is now running on a ${{ runner.os }} server hosted by GitHub!"
      - run: echo "🔎 The name of your branch is ${{ github.ref }} and your repository is ${{ github.repository }}."
      - name: Check out repository code
        uses: actions/checkout@v4
      - run: echo "💡 The ${{ github.repository }} repository has been cloned to the runner."
      - run: echo "🖥️ The workflow is now ready to test your code on the runner."
      - name: List files in the repository
        run: |
          ls ${{ github.workspace }}
      - run: echo "🍏 This job's status is ${{ job.status }}."
```
- name
    - github actions의 이름
- on
    - action이 언제 실행되는지에 대한 부분 ( = event ) (push, pull_request ...)
- jobs
    - 실제 실행할 내용에 대한 부분
        - runs-on: 어떤 환경에서 실행하는지 기술
            - https://docs.github.com/ko/actions/using-github-hosted-runners/about-github-hosted-runners/about-github-hosted-runners
        - steps: 실제 실행할 단계들을 기술
            - name: 실행에 표시될 이름
            - uses:  여러 가지 plugin 사용
                - 다양한 action들을 사용 - https://github.com/marketplace?type=actions
            - with: plugin 에서 사용할 파라미터들
            - run: 실제로 실행할 스크립트


## [빠른 시작](https://docs.github.com/ko/actions/quickstart) 

- 레포지토리 `.github/workflows` 경로의 `.yml` 또는 `.yaml`파일을 읽어서 자동으로 실행  

- 파일 생성  
![image](https://github.com/hana2set/study/assets/97689567/913e36e4-05b2-4268-81fa-40366c9ba7e1)

- 새 브랜치로  
![image](https://github.com/hana2set/study/assets/97689567/6a0cfd59-b8c5-46a6-8d47-6c8a8607de2d)

- 커밋하면 push 이벤트가 트리거되고 워크플로가 실행  
![image](https://github.com/hana2set/study/assets/97689567/c353321a-f1b5-4f05-bea1-6395c6681852)

- 클릭하면 각 step에 대한 실행 결과 확인이 가능  
![image](https://github.com/hana2set/study/assets/97689567/fd0cc9f7-49ef-49be-9206-75779ffe61db)


<details>
    <summary><span style="font-weight: bold; font-size: 24px">실습</span></summary>

## 실습 - [github_sample](https://github.com/hana2set/github-action-sample)  
1. develop, feature/add-workflow 브랜치 추가
2. feature/add-workflow 브랜치에 run-test.yaml 생성
    ```
    # Actions 이름 github 페이지에서 볼 수 있다.
    name: Run Test

    # Event Trigger 특정 액션 (Push, Pull_Request)등이 명시한 Branch에서 일어나면 동작을 수행한다.
    on:
    push:
        # 배열로 여러 브랜치를 넣을 수 있다.
        branches: [ develop, feature/* ]
    # github pull request 생성시
    pull_request:
        branches:
        - develop # -로 여러 브랜치를 명시하는 것도 가능

    # 실제 어떤 작업을 실행할지에 대한 명시
    jobs:
    build:
        # 스크립트 실행 환경 (OS)
        # 배열로 선언시 개수 만큼 반복해서 실행한다. ( 예제 : 1번 실행)
        runs-on: [ ubuntu-latest ]

        # 실제 실행 스크립트
        steps:
        # uses는 github actions에서 제공하는 플러그인을 실행.(git checkout 실행)
        - name: checkout
            uses: actions/checkout@v4

        # with은 plugin 파라미터 입니다. (java 17버전 셋업)
        - name: java setup
            uses: actions/setup-java@v2
            with:
            distribution: 'adopt' # See 'Supported distributions' for available options
            java-version: '17'

        - name: make executable gradlew
            run: chmod +x ./gradlew

        # run은 사용자 지정 스크립트 실행
        - name: run unittest
            run: |
            ./gradlew clean test
    ```
3. feature/add-workflow 에서 workflow 실행됨
![image](https://github.com/hana2set/study/assets/97689567/d209991c-080a-49db-adc5-335a0c4996f9)


4. `run-test.yaml`에 입력된 `push`, `pull_request`과정에서 workflow 실행
![image](https://github.com/hana2set/study/assets/97689567/07cf1c73-85ae-4f22-ae2b-7e81a7f02480)
![image](https://github.com/hana2set/study/assets/97689567/acc2bde9-310f-4717-99df-dd3cbc30d3c0)

5. 테스트 실패시 메일 옴
![image](https://github.com/hana2set/study/assets/97689567/f880bd02-2c41-410d-bc4e-506a7712c7cf)
</details>

