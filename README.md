

# Newsfeed API 프로젝트

Spring Boot 기반의 간단한 뉴스피드 API 서버입니다.  
회원가입, 로그인(JWT), 게시글 및 댓글 CRUD를 제공합니다.

---

## 프로젝트 구조
src
└── main
└── java/com/example/newsfeed
├── comment # 댓글 관련 기능
├── common # 공통 유틸 및 설정 (JWT, 상수 등)
├── dto # API 공통 응답 DTO
├── exception # 커스텀 예외
├── post # 게시물 관련 기능
├── user # 사용자 관련 기능
└── NewsfeedApplication.java


---

## 주요 기능

###  인증/인가
- JWT 기반 로그인 (`Authorization: Bearer {token}`)
- 로그인 필수 요청 시 토큰 검증
- JWT 유틸리티 클래스 제공 (`JwtService`)

### 사용자 (User)
- 회원가입 `POST /users/signup`
- 로그인 `POST /users/login`
- 비밀번호 수정 `PATCH /users/me`
- 프로필 조회 `GET /users/{id}`

### 게시글 (Post)
- 게시글 생성 `POST /posts`
- 게시글 수정 `PATCH /posts/{id}`
- 게시글 삭제 `DELETE /posts/{id}`
- 게시글 단건 조회 `GET /posts/{id}`
- 게시글 전체 조회 (페이징 + 날짜 필터)  
  `GET /posts?periodStart=20250101&periodEnd=20251231`

### 댓글 (Comment)
- 댓글 작성 `POST /posts/{postId}/comments`
- 댓글 전체 조회 (게시물 기준) `GET /posts/{postId}/comments`
- 댓글 전체 조회 (내가 쓴 것) `GET /posts/users/me/comments`
- 댓글 수정 `PUT /comments/{commentId}` 미구현
- 댓글 삭제 `DELETE /comments/{commentId}` 미구현

---

## 사용 기술

| 기술 스택 | 설명 |
|-----------|------|
| Java 17 | 개발 언어 |
| Spring Boot 3.x | 프레임워크 |
| JPA / Hibernate | ORM |
| MySQL | 데이터베이스 |
| JWT (jjwt) | 인증 방식 |
| Lombok | 보일러플레이트 제거 |
| Validation | DTO 검증 처리 |
| Gradle | 빌드 도구 |

---

 예시 요청
POST /users/login

{
  "email": "user@example.com",
  "password": "password123"
}
응답
{
  "status": 200,
  "message": "로그인 성공!",
  "data": {
    "userId": 1,
    "username": "홍길동",
    "token": "eyJhbGciOiJIUzI1NiJ9..."
  }
}
## 개선 포인트

JWT 인증 중복 처리 개선

현재 컨트롤러나 서비스에서 매번 다음과 같은 로직이 반복되고 있음:
String authorization = request.getHeader("Authorization");
if (authorization == null || !authorization.startsWith("Bearer ")) {
    throw new UserNeedLoginException();
}
String token = authorization.substring(7);
long userId = jwtService.getUserIdFromToken(token);

## 개선안
 Spring Filter/Interceptor/ArgumentResolver 등을 활용해 @AuthenticationPrincipal처럼 처리하는 방식으로 확장
