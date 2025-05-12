> 요약
> 
> 공식적인 의견은 없음. 그러나 대부분 프로그래머들이 비권장함(대안 존재, 부정적인 요소가 많음).  
> 따라서, javascript 액션을 분리해 `e.preventDefault`를 사용하거나, 랑크가 필요없다면 `<a>`요소를 사용하지 않는 것을 권장

## 기술적 설명
- `javascript:void(0);`는 void(0);가 undefined를 반환하며 `javascript:[가상URL]`이 동작하지 않아 <a>태그가 작동하지 않음.
  - `undefined` 대신 `void(0);`를 사용하는 것은, 글자가 더 짧아서 사용하는 오랜 관행
- 따라서 <a>태그가 링크로 보이면서 작동하지 않을 때 사용.  

<br> 

## 사용 비권장 사유
- **해당 동작을 사용해야할 이유가 없음**[^1][^2]
  - 명확함 ↓
  - 보안 문제 가능성 ↑
  - 접근성 ↓, 사용자 혼란 ↑
  - 자바스크립트 실행으로 인한 성능 저하
  - HTML 태그에 javascript로 인한 가독성 저하
  - 다음과 같은 대체 방안이 존재함
    - `onclick="return false"` 사용
    - 이벤트 리스너에서 `e.preventDefault()` 호출
    - 링크가 실질적으로 필요하지 않은 경우, `<button>` 등 더 적절한 시맨틱 요소 사용

<br>


[^1]: [What does `javascript:void(0)` mean? (Stack Overflow)](https://stackoverflow.com/questions/1291942/what-does-javascriptvoid0-mean)  
[^2]: [Which href value should I use for JavaScript links? (Stack Overflow)](https://stackoverflow.com/questions/134845/which-href-value-should-i-use-for-javascript-links-or-javascriptvoid0)
