# 참조 유형 (Reference Type)
Java에서는 GC를 다루기 위해 4가지 참조 유형을 제시함

## Strong Reference (강한 참조)
- 기본 참조 유형. null로 무효화할 때 까지 GC가 수집하지 않음.

## Soft Reference (소프트 참조)
- **JVM에 추가 메모리가 필요할 경우** GC가 수집.
```java
Class class = new Class();
SoftReference<Class> sRef = new SoftReference<Class>(class);

class = null;

class = sRef.get();
```

## Weak Reference (약한 참조)
- GC가 실행되면 수집 
- **짧은 주기에 자주 사용되는 객체를 캐시할 때 유용**
```java
Class class = new Class();
WeakReference<Class> wRef = new WeakReference<Class>(class); 

class = null;

class = wRef.get();
```

## Phantom Reference (팬텀 참조)
- 메모리 회수 전 반드시 정리해야 할 리소스가 있다면 사용. **일반적으로 사용하지 않음**

--- 
https://jerry92k.tistory.com/8  
https://jangjjolkit.tistory.com/31   
https://be-study-record.tistory.com/52   