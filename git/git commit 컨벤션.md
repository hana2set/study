팀마다 다르기 때문에, [Udacity Git Commit Message Style Guide](https://udacity.github.io/git-styleguide/) 를 기반으로 작성

# commit 메세지 
## 구조 (제목, 본문, 꼬리말)
```bash
type: subject

body 

footer
```
- 빈 줄로 구분할 것
- 예시
    ```text
    feat: Summarize changes in around 50 characters or less

    More detailed explanatory text, if necessary. Wrap it to about 72
    characters or so. In some contexts, the first line is treated as the
    subject of the commit and the rest of the text as the body. The
    blank line separating the summary from the body is critical (unless
    you omit the body entirely); various tools like `log`, `shortlog`
    and `rebase` can get confused if you run the two together.

    Explain the problem that this commit is solving. Focus on why you
    are making this change as opposed to how (the code explains that).
    Are there side effects or other unintuitive consequences of this
    change? Here's the place to explain them.

    Further paragraphs come after blank lines.

    - Bullet points are okay, too

    - Typically a hyphen or asterisk is used for the bullet, preceded
    by a single space, with blank lines in between, but conventions
    vary here

    If you use an issue tracker, put references to them at the bottom,
    like this:

    Resolves: #123
    Ref: #48
    See also: #456, #789
    ```


### type
feat     : 기능 (새로운 기능)
fix      : 버그 (버그 수정)
refactor : 리팩토링
docs     : 문서 (문서 추가, 수정, 삭제)
test     : 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없음)

- 팀마다 더 추가하기도 함.

### subject
- 최대 50글자
- 마침표, 특수기호 X
- 간결하고 요점적인 서술
- 영문인 경우
  - 동사(원형)를 가장 앞에 두고 첫 글자는 대문자
  - 과거 시제를 사용X

### body
- 선택 사항 (subject로 충분하면 생략가능)
- 최대 72글자
- **무엇**을 **왜** 했는지를 작성 (어떻게 X)

### footer
- 선택 사항 
- 이슈 트래거 ID 참조용
  
#### 분류 참고용
  - Fixes: 이슈 수정중 (아직 해결되지 않은 경우)
  - Resolves: 이슈를 해결했을 때 사용
  - Ref: 참고할 이슈가 있을 때 사용
  - Related to: 해당 커밋에 관련된 이슈번호 (아직 해결되지 않은 경우)

### 이모지
- 구분하기 쉽게 이모지를 추가하기도 함
- [Gitmoji 사용하기](https://treasurebear.tistory.com/70)

https://udacity.github.io/git-styleguide/
https://velog.io/@shin6403/Git-git-%EC%BB%A4%EB%B0%8B-%EC%BB%A8%EB%B2%A4%EC%85%98-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0