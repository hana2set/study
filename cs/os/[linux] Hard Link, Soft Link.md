## Hard Link
- `ln [Source] [Target]` 으로 생성
- 원본파일과 동일한 i-node를 가짐
- 원본파일이 삭제되어도 사용 가능 (동일한 지위)
> [!note]
> i-node: 파일 속성을 가리키는 값. 파일 속성에는 파일 크기, 접근 권한, 아이노드 넘버, 소유자 그릅 등의 정보가 들어있다.
> <img width="550" height="111" alt="Image" src="https://github.com/user-attachments/assets/ceadecbd-8295-4df7-84d1-e51e015c5a5e" />
> 해당 이미지에 `65405713`

## Soft Link (Symbolic Link)
- `ln -s [Source] [Target]` 으로 생성
- 포인터 개념
- 원본파일 삭제 시 사용 불가
- 파일 종류가 `l`로 시작


> 출처  
> https://plummmm.tistory.com/385  
> https://6kkki.tistory.com/10