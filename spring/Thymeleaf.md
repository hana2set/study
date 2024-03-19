# 참고자료
https://www.thymeleaf.org/  
https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html  
https://www.thymeleaf.org/doc/tutorials/3.1/thymeleafspring.html

# Thymeleaf란?
Java 기반의 서버 측 템플릿 엔진이다.  
HTML, XML, JavaScript, CSS 등의 마크업 언어를 처리하며,   
자연스러운 템플릿과 통합되어 있어 데이터를 서버에서 동적으로 렌더링하는 데 용이하다.  
특히 스프링 프레임워크와의 통합이 강하며, 스프링 Web Application에 사용된다.  
Thymeleaf는 간편하고 직관적인 문법을 제공하여 템플릿 작성 및 유지보수를 용이하게 만든다.

# 특징
* 스프링과의 통합
    - 스프링과 자연스럽게 통합되고, 스프링의 다양한 기능을 편리하게 사용할 수 있게 지원한다.


* Server Side Rendering (SSR)
    - BackEnd 서버에서 HTML을 동적으로 Rendering 하는것.

* Natural Template
    - 타임리프는 순수 HTML을 최대한 유지하는 특징을 가지고 있다.
        - 웹 브라우저에서 파일을 직접 열어도 정상적인 내용을 확인할 수 있다.
        - 참고 : JSP 등의 파일은 확인할 수 없다.  
    - Server에서 동적으로 변경된 결과를 확인할 수 있다.
        - 동적 HTML, SSR
        - JSP는 Server에서 Rendering 되고 HTML 응답 결과를 받아야만 화면을 확인할 수 있다.
        - Thymeleaf는 위와같은 경우 동적인 결과는 조회하지 못하지만 HTML은 조회할 수 있다.
    - HTML 마크업 결과를 바로 확인할 수 있다.
        - 순수 HTML
    - 순수 HTML을 유지하면서 View Template 까지 사용할 수 있는것을 **Natural Template** 이라고 한다.


# Thymeleaf 문법
대부분의 html 속성을  th:xxx 로 변경할 수 있다.  
ex: th:text="${변수명}"

 

|표현|설명|예제|
|-|-|-|
|@{ ... }|	URL 링크 표현식|	th:href="@{/css/bootstrap.min.css}"<br>th:href="@{/{itemId}/edit(itemId=${item.id})}"|
|\| ... \||	리터럴 대체|	th:text="|Hi ${user.name}!|"<br>(= th:text="'Hi '+${user.name}+'!'"|
|${ ... }|	변수|	th:text=${user.name}  |
|th:each|	반복 출력|	\<tr th:each="item: \${items}"><br>\<td th:text="${item.price}">100\</td><br>\</tr>|
|*{ ... }|	선택 변수|	\<tr th:object="${items}"><br>  \<td th:text="*{price}">100<br>\</td><br>\</tr>|
|#{ ... }|	메시지. properties 같은 외부 자원에서 코드에 해당하는 문자열 get.|	th:text="#{member.register}"|