### 일반적인 생성자 (권장)
```java
@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

}

```

### 기본생성자 anotation으로 구현
```java
@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

}

```

### context로 구현
```java
@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public MemoService(ApplicationContext context) {
        // MemoRepository memoReposiroty = (MemoRepository) context.getBean("MemoRepository");
        MemoRepository memoReposiroty = (MemoRepository) context.getBean(MemoRepository.class);
        this.memoRepository = memoReposiroty;
    }

}

```