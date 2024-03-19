# 개요
Node.js 서버 측 애플리케이션을 빌드하기 위한 프레임워크
- https://docs.nestjs.com/
- Architecture + typescript + Node.js = Nest.js
- node 계의 Spring. 레이어 구분이 쉽고 interceptor, validation 등 서버 구현에 필요한 기능들 제공

### NestJS 철학
> However, while plenty of superb libraries, helpers, and tools exist for Node (and server-side JavaScript), none of them effectively solve the main problem of - Architecture.

-> 어느것도 노드에서 **아키텍처 문제**를 해결하지 못했기에 NestJS 를 만들었다  
-> 손쉽게 사용하는 아키텍처 지향

## 설치

1. cli 사용
    ```cmd
    npm i -g @nestjs/cli
    nest new project-name
    ```
2. cli 없이
    ```cmd
    git clone https://github.com/nestjs/typescript-starter.git project
    cd project
    npm install
    npm run start
    ```


## 자주 사용하는 기능
@Module() - 구조를 명시. 구현체 주입 위임
@Injectable() - provider 클래스임을 명시
@controller
@import
@Export

@Global - 글로벌 클래스로 변경