a음식점, 베이커리 등에서 남은 음식을 특가로 판매하고,  
사용자가 예약/픽업하는 “Too Good To Go” 플랫폼의 백엔드 클론입니다.

---

## 📋 프로젝트 개요

- **주요 기능**
    - 매장/상품(특가) 등록 및 관리 (사장님)
    - 위치 기반 특가상품/매장 검색 (고객)
    - 상품 예약/구매 및 픽업 관리
    - 마이페이지, 예약 내역, 리뷰
    - JWT 기반 인증, RESTful API

---

## 📚 ERD 설계

User 1---N Store 1---N Product 1---N Reservation 1---1 Review

[User]
- id (PK)
- email (unique)
- password 
- name 
- phone 
- role (CUSTOMER | STORE_OWNER | ADMIN)
- created_at

[Store]

- id (PK)
- owner_id (FK, User.id)
- name, address, phone, description 
- open_time, close_time 
- latitude, longitude 
- created_at

[Product]

- id (PK)
- store_id (FK, Store.id)
- name, description 
- original_price, discount_price 
- quantity 
- pickup_start_time, pickup_end_time, available_date 
- image_url 
- status (AVAILABLE | SOLD_OUT | EXPIRED)
- created_at

[Reservation]

- id (PK)
- product_id (FK, Product.id)
- user_id (FK, User.id)
- store_id (FK, Store.id)
- status (RESERVED | PICKED_UP | CANCELED)
- reserved_at, picked_up_at

[Review]

- id (PK)
- reservation_id (FK, Reservation.id)
- user_id (FK, User.id)
- store_id (FK, Store.id)
- rating (1~5)
- content 
- created_at

---

## 🚦 주요 API 명세 (Swagger 스타일)

### 회원/인증

- **POST /api/auth/signup**  
    회원가입 (고객/사장님 구분)
- **POST /api/auth/login**  
    로그인(JWT 발급)
- **GET /api/users/me**  
    내 정보 조회

### 매장

- **POST /api/stores**  
    매장 등록 (사장님)
- **GET /api/stores**  
    매장 목록 조회
- **GET /api/stores/{id}**  
    매장 상세 조회

### 상품(특가)

- **POST /api/products**  
    상품(특가) 등록 (사장님)
- **GET /api/products**  
    상품 목록/검색 (위치 기반 등)
- **GET /api/products/{id}**  
    상품 상세

### 예약/구매

- **POST /api/reservations**  
    상품 예약
- **GET /api/reservations**  
    예약 내역 조회
- **PATCH /api/reservations/{id}/pickup**  
    픽업 완료 처리

### 리뷰

- **POST /api/reviews**  
    리뷰 등록
- **GET /api/reviews?storeId=**  
    매장별 리뷰 목록 조회

---

## 📝 예시 API 응답

#### 회원가입 (POST /api/auth/signup)

```json
{
  "success": true,
  "code": 201,
  "message": "회원가입 성공",
  "data": {
    "userId": 1,
    "email": "test@togo.com",
    "name": "홍길동",
    "role": "CUSTOMER"
  },
  "timestamp": "2025-07-23T18:25:42.381+09:00"
}
```

#### 상품 등록 (POST /api/products)
```json
{
  "success": true,
  "code": 201,
  "message": "상품 등록 성공",
  "data": {
    "productId": 22,
    "storeId": 10,
    "status": "AVAILABLE"
  },
  "timestamp": "2025-07-23T18:32:22.888+09:00"
}
```
#### 상품 예약 (POST /api/reservations)
```json
{
  "success": true,
  "code": 201,
  "message": "예약이 완료되었습니다.",
  "data": {
    "reservationId": 101,
    "productId": 22,
    "userId": 1,
    "status": "RESERVED",
    "reservedAt": "2025-07-23T18:40:00"
  },
  "timestamp": "2025-07-23T18:40:00.123+09:00"
}
```

#### 리뷰 등록 (POST /api/reviews)
```json
{
  "success": true,
  "code": 201,
  "message": "리뷰가 등록되었습니다.",
  "data": {
    "reviewId": 30,
    "storeId": 10,
    "rating": 5,
    "content": "너무 맛있었어요! 추천합니다 :)",
    "createdAt": "2025-07-23T18:50:00"
  },
  "timestamp": "2025-07-23T18:50:00.111+09:00"
}
```
# 2025-08-06
- 회원가입, 로그인 -> Spring Security 기본기 + JWT
  - @AuthenticationPrincipal, SecurityContextHolder 이해
  - Role 기반 인가(hasRole, @PreAuthorize)
  - JWT 발급/검증/필터 구현
  - 인증 실패 시 예외 처리
- 학습 주제
  - UsernamePasswordAuthenticationFilter
  - JwtAuthenticationFilter, JwtProvider, SecurityConfig


# 추가 개발/확장 참고
- JWT 인증, 역할별 인가(Spring Security)
- Enum 사용: role, status 등은 Enum 클래스로 관리 
- API Response: 성공/실패, 에러 메시지, 상세 코드 등 통일된 응답 포맷 사용 
- 페이징, 정렬, 검색 옵션 
- ERD/Swagger/테스트코드/배포 자동화 등 실무 프로세스 반영


# 개발/기여 안내
- Java 17+, Spring Boot 3.x 권장 
- IDE: IntelliJ, VSCode 등 
- DB: MySQL, H2(개발/테스트용)
- API 문서 자동화: Swagger(OpenAPI 3.0)
- ERD 이미지 및 전체 API 상세 문서는 /docs 폴더 참고(추후 추가 예정)
- PR/이슈/문의: Github Discussions or Issues 활용


# 참고 사이트
- https://toogoodtogo.com/
- https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/
- https://editor.swagger.io/
- https://dbdiagram.io/