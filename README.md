# username-password-athentication

----
# SecurityConfig.java
```java
.formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
```
* 로그인 설정
  * ```loginPage("/login")``` : 변수로 입력한 URL로 로그인 페이지 redirect
  * ```defaultSuccesUrl("/")```: 로그인 성공 시 Redirect할 기본 URL 설정
  * Controller를 생성하여 GET 메서드를 받아 login 페이지 이동
  
  * 알게 된 부분
    * 스프링 시큐리티에서 로그인 로직을 모두 수행해 주기 때문에 POST 메서드로 요청을 받을 필요가 없다.
    * 헷갈리지 말자. ```loginPage("/login")```은 단순히 페이지 이동 설정이고 실제 로그인도 일단 ```"/login"```은 맞다.
  ```javascript
     <form th:action="@{/login}" method="post">
        <div>
            <input type="text" name="username" placeholder="id"/>
        </div>
        <div>
            <input type="password" name="password" placeholder="password"/>
        </div>
        <input type="submit" value="로그인" />
    </form>
  ```
  * thymeleaf를 사용하여 URL 요청을 하는 데, 여기서 /login이 바로 스프링 시큐리티가 내부적으로 지원해주는 POST 방식의 login 요청인 듯하다.
  * 물론, 해당 login 요청 URL을 바꾸고 싶다면, 위에서 ```.loginprocessingurl()```을 통해 변경가능하고 form에서도 변경해주면 된다.
---
```java
  .logout()
        .logoutUrl("/logout")
        .deleteCookies("JSESSIONID")
        .logoutSuccessUrl("/login");
```
* 로그 아웃
  * ```logoutUrl("/logout")``` : 설정한 URL로 요청이 들어오면 스프링 시큐리티 내부적으로 구성된 "/logout" 요청을 통해 로그아웃이 실행된다.
  * ```deleteCookies("JSESSIONID")``` : 해당 쿠키를 삭제하여 응답한다.
  * ```logoutSuccessUrl("/login")``` : 로그아웃 성공후 설정한 URL로 Redirect 된다.
  * 물론 여러 추가 설정도 가능.
  * 그리고 ```logoutUrl()```는 csrf가 활성화 되어 있을 경우에는 POST 요청을 해야 한다. 따라서 form에서 로그아웃요청을 POST로 설정하자.
  ```javascript
  <form th:action="@{/logout}" th:method="POST">
        <button type="submit">logout</button>
  </form>
  ```

* 스프링 시큐리티의 로그인, 로그아웃을 구현하면서 가장 느낀 점은 개발자가 추가 로직을 구현하지 않아도 각 URL에 맞는 요청만 제대로 설정해 준다면 로그인과 로그아웃을 보장해 준다는 것.
