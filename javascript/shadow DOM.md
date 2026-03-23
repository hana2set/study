# shadow DOM

웹 컴포넌트의 캡슐화를 위해 제공하는 기능.  내부에 새로운 문서를 호출하는 `<iframe>`과는 다르게 내부에 격리된 DOM을 생성.

- 효율적 (vs. `iframe`)
- css 격리됨 (`--main-color` 과 같은 식으로 만들때 외부에서 접근 가능하도록 설정은 가능)
- 이벤트 격리도 가능

## 기본 사용법

일반적으로, 기본 DOM API 조작과 동일 (appendCild 등...)

```js
// let shadow = elementRef.attachShadow({ mode: "closed" }); // 외부 접근 불가 DOM
let shadow = elementRef.attachShadow({ mode: "open" }); 
let myShadowDom = myCustomElem.shadowRoot;
```

- 이벤트 적용
  
    ```js
    const btn = shadow.querySelector('#innerBtn');

    btn.addEventListener('click', () => {
        // 커스텀 이벤트 생성
        const myEvent = new CustomEvent('login-click', {
            detail: { userId: 'admin' }, // 데이터 전달
            bubbles: true,               // 부모로 전파 여부
            composed: true               // Shadow DOM 경계를 뚫고 나갈지 여부
        });
        
        btn.dispatchEvent(myEvent);
    });

    // 외부 코드
    document.querySelector('#host').addEventListener('login-click', (e) => {
        console.log('커스텀 이벤트 수신!', e.detail.userId);
    });

    ```

    > [!note]
    open이면 그냥 이벤트 사용이 가능하나, 캡슐화를 위해 내부에서 커스텀 이벤트를 생성하는 것이 구조적으로 좋음

> 출처:  
https://developer.mozilla.org/ko/docs/Web/API/Web_components/Using_shadow_DOM  
