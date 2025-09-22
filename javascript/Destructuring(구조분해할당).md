# Destructuring
객체의 속성을 해체하여 개별 변수에 담도록 하는 Javascript 표현식

## 예제

```javascript
let { a: { a1, a2 } = {}, b = [] } = data || {};
// data = { a: {a1: 5, a2: 7}, b: [5]}
// a1 == 5, a2 == 7, b == [5]
```
- 할당된 변수 : `a1`, `a2`, `b`
- data가 null/undefined → `{}` → a1: undefined, a2: undefined, b = []
- data.a가 null/undefined → a: `{}` → a1: undefined, a2: undefined, b = data.b
- data.b가 null/undefined → b: `[]` → a1: data.a.a1, a2: data.a.a2, b = []