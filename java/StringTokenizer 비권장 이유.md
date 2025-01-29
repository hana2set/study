- [현재 java에서는 사용을 비권장하고 있음](https://docs.oracle.com/javase/8/docs/api/index.html?java/util/StringTokenizer.html)

```
StringTokenizer is a legacy class that is retained for compatibility reasons although its use is discouraged in new code. It is recommended that anyone seeking this functionality use the split method of String or the java.util.regex package instead.
```

- 레거시 호환성을 위해 유지하는 클래스일 뿐임.
- [내부적으로 비교하는 로직 때문에 (특히 구분기호) 효율적이지 못하다함](https://blog.naver.com/PostView.nhn?blogId=chogahui05&logNo=221474002967&categoryNo=12&parentCategoryNo=0&viewDate=&currentPage=1&postListTopCurrentPage=1&from=postView)
- https://stackoverflow.com/questions/6983856/why-is-stringtokenizer-deprecated