# Custom 예외처리
### 봐야 할 부분

**RestApiExceptionHandler**

- 글로벌 예외 처리에서 커스텀 한 예외 처리를 어떻게 하는지 (TODO 검색)
    - IllegalArgumentException으로 통째로 처리하면 HttpStatus를 한 상태만 보내주게 됨
    - 이것을 Enum으로 상태, 메세지를 정의하고 가져오고 싶어서 Exception을 커스텀 하게 됨
<br>

**PostService**

- 커스텀 예외 처리 예시 (TODO 검색)
<br>

**CustomErrorEnum**

- 예외 처리의 Enum을 정의 (HttpStatus와 메세지를 사용하였음)
<br>

**CustomException**

- CustomErrorEnum으로 예외 처리를 하도록 Exception class를 커스텀 함
<br>

**CustomExceptionDto**

- 예외 처리의 정보를 보내줄 Dto (errorCode, httpStatus, 메세지를 사용하였음)
<br>

---

# hanghaemasterpost
게시글 만들기 LV.5

1. 회원 가입 API
    - username, password를 Client에서 전달받기
    - username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성되어야 한다.
    - password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자`로 구성되어야 한다.
    - DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
    - 회원 권한 부여하기 (ADMIN, USER) - ADMIN 회원은 모든 게시글, 댓글 수정 / 삭제 가능
    - 참고자료
        1. [https://mangkyu.tistory.com/174](https://mangkyu.tistory.com/174)
        2. [https://ko.wikipedia.org/wiki/정규_표현식](https://ko.wikipedia.org/wiki/%EC%A0%95%EA%B7%9C_%ED%91%9C%ED%98%84%EC%8B%9D)
        3. [https://bamdule.tistory.com/35](https://bamdule.tistory.com/35)
        
2. 로그인 API
    - username, password를 Client에서 전달받기
    - DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
    - 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고, 
    발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기
3. 전체 게시글 목록 조회 API
    - 제목, 작성자명(username), 작성 내용, 작성 날짜를 조회하기
    - 작성 날짜 기준 내림차순으로 정렬하기
    - 각각의 게시글에 등록된 모든 댓글을 게시글과 같이 Client에 반환하기
    - 댓글은 작성 날짜 기준 내림차순으로 정렬하기
    - 게시글/댓글에 ‘좋아요’ 개수도 함께 반환하기
4. 게시글 작성 API
    - ~~토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능~~  ⇒ Spring Security 를 사용하여 토큰 검사 및 인증하기!
    - 제목, 작성자명(username), 작성 내용을 저장하고
    - 저장된 게시글을 Client 로 반환하기
5. 선택한 게시글 조회 API
    - 선택한 게시글의 제목, 작성자명(username), 작성 날짜, 작성 내용을 조회하기 
    (검색 기능이 아닙니다. 간단한 게시글 조회만 구현해주세요.)
    - 선택한 게시글에 등록된 모든 댓글을 선택한 게시글과 같이 Client에 반환하기
    - 댓글은 작성 날짜 기준 내림차순으로 정렬하기
    - 게시글/댓글에 ‘좋아요’ 개수도 함께 반환하기
6. 선택한 게시글 수정 API
    - ~~토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능~~  ⇒ Spring Security 를 사용하여 토큰 검사 및 인증하기!
    - 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
    - 게시글에 ‘좋아요’ 개수도 함께 반환하기
7. 선택한 게시글 삭제 API
    - ~~토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 삭제 가능~~  ⇒ Spring Security 를 사용하여 토큰 검사 및 인증하기!
    - 선택한 게시글을 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
8. 댓글 작성 API
    - ~~토큰을 검사하여, 유효한 토큰일 경우에만 댓글 작성 가능~~  ⇒ Spring Security 를 사용하여 토큰 검사 및 인증하기!
    - 선택한 게시글의 DB 저장 유무를 확인하기
    - 선택한 게시글이 있다면 댓글을 등록하고 등록된 댓글 반환하기
9. 댓글 수정 API
    - ~~토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 수정 가능~~  ⇒ Spring Security 를 사용하여 토큰 검사 및 인증하기!
    - 선택한 댓글의 DB 저장 유무를 확인하기
    - 선택한 댓글이 있다면 댓글 수정하고 수정된 댓글 반환하기
    - 댓글에 ‘좋아요’ 개수도 함께 반환하기
10. 댓글 삭제 API
    - ~~토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 댓글만 삭제 가능~~  ⇒ Spring Security 를 사용하여 토큰 검사 및 인증하기!
    - 선택한 댓글의 DB 저장 유무를 확인하기
    - 선택한 댓글이 있다면 댓글 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
11. 예외 처리
    - 토큰이 필요한 API 요청에서 토큰을 전달하지 않았거나 정상 토큰이 아닐 때는 "토큰이 유효하지 않습니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기
    - 토큰이 있고, 유효한 토큰이지만 해당 사용자가 작성한 게시글/댓글이 아닌 경우에는 “작성자만 삭제/수정할 수 있습니다.”라는 에러메시지와 statusCode: 400을 Client에 반환하기
    - DB에 이미 존재하는 username으로 회원가입을 요청한 경우 "중복된 username 입니다." 라는 에러메시지와 statusCode: 400을 Client에 반환하기
    - 로그인 시, 전달된 username과 password 중 맞지 않는 정보가 있다면 "회원을 찾을 수 없습니다."라는 에러메시지와 statusCode: 400을 Client에 반환하기
    - 회원가입 시 username과 password의 구성이 알맞지 않으면 에러메시지와 statusCode: 400을 Client에 반환하기
    
    
💡 **더 나아가기: 과제가 일찍 마무리 되었다면 아래의 내용도 진행해보세요.**
- 🟩 회원탈퇴(기능추가), 게시글 삭제, 댓글 삭제 시 연관된 데이터 모두 삭제될 수 있도록 구현해 보세요!
- ⬜ 대댓글 기능을 만들어 보세요!
    - ⬜ 대댓글 작성하기
    - ⬜ 댓글 조회 시 대댓글도 함께 조회하기
- ⬜ 게시글과 댓글 조회할 때 페이징, 정렬 기능을 추가해 보세요!
- ⬜ 게시글 카테고리 만들어 보세요!
    - ⬜ 게시글을 분류하는 카테고리를 만들어서 게시글을 작성할 때 카테고리 정보도 함께 저장하기
    - ⬜ 카테고리 별로 게시글을 조회하는 기능 추가하기
- ⬜ AccessToken, RefreshToken에 대해 구글링해 보고 RefreshToken을 적용해 보세요!
- 🟩 프로젝트에 swagger 를 구글링해 보고 적용해 보세요!
    - swagger란? Open Api Specification(OAS)를 위한 프레임워크 입니다. API들이 가지고 있는 스펙(spec)을 명세, 관리할 수 있으며, 백엔드와 프론트엔드가 협업할 때 사용할 수 있습니다!
