# INDEX

## INDEX 판단
- 사용
  - 테이블 행의 수가 많고 WHERE문에 해당 컬럼이 많이 사용될 때
  - 검색 결과가 전체 데이터의 2~4% 정도일 때
  - NULL을 포함하는 컬럼이 많을 때
- 비권장
  - 검색결과의 양이 10% 가 넘음
  - DML이 자주 일어나는 경우
