@OneToOne
* mappedBy: 참조 테이블에서 주인 테이블에 연결할 컬럼 (entity 변수)

@JoinColumn - 주인쪽에서 조인할 컬럼 지정
* name: 현 객체에서 target 객체에 연결할 컬럼 이름


@OneToOne인 경우 주인이 아닌 곳에서 조회하면 지연로딩이 작동하지 않음. (조인 컬럼이 없기때문에 null을 표현할 방법이 없음.)

https://www.baeldung.com/jpa-joincolumn-vs-mappedby