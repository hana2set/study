# 개요
여러번 커밋한 이력을 하나의 커밋 이력으로 만듬

# 방법

1. 합치고 싶은 커밋 전의 커밋으로 rebase
    ```
    git rebase -i db078dae819cfed46bc6e6ef8c962648f97c22da 
    ```
    * 결과
        ```
        pick b91e257 first commit
        pick 0118d46 second commit
        pick 1f199b7 third commit

        # Rebase db078da..1f199b7 onto db078da (3 commands)
        #
        # Commands:
        # p, pick <commit> = use commit
        # r, reword <commit> = use commit, but edit the commit message
        # e, edit <commit> = use commit, but stop for amending
        # s, squash <commit> = use commit, but meld into previous commit
        # f, fixup <commit> = like "squash", but discard this commit's log messag

        ...

        ```
2. 위 결과에서 pick을 s로 변경 (`s, squash` 사용)
    ```
    pick b91e257 first commit
    s 0118d46 second commit
    s 1f199b7 third commit

    # Rebase db078da..1f199b7 onto db078da (3 commands)
    ...

    ```
    * 수정 저장 시 결과
        ```
        # This is a combination of 3 commits.
        # This is the 1st commit message:

        first commit

        # This is the commit message #2:

        second commit

        # This is the commit message #3:

        third commit

        # Please enter the commit message for your changes. Lines starting
        # with '#' will be ignored, and an empty message aborts the commit.
        #
        ```
3. 위 메세지 수정

    ```
    # This is a combination of 3 commits.
    # This is the 1st commit message:

    squash commit

    # Please enter the commit message for your changes. Lines starting
    # with '#' will be ignored, and an empty message aborts the commit.
    #
    ```
4. 커밋이 하나만 출력되면 성공
    ```
    $ git log --pretty=oneline
    ...
    099e6f606b3a46b98f0233d558eb07c143a3ec01 (HEAD -> squash-test) squash commit
    db078dae819cfed46bc6e6ef8c962648f97c22da (origin/master, origin/develop, master, develop) Merge pull request #1 from dev-yakuza/rebase-and-squash

    ```
5. push
    ```
    git push origin squash-test
    ```

https://deku.posstree.com/ko/git/git-squash/
