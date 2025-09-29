# 과제 제출 시스템

Spring Boot를 사용한 웹 기반 과제 제출 및 관리 시스템입니다.

## 주요 기능

### 공통 기능
- 사용자 로그인/로그아웃
- 역할 기반 접근 제어 (교수/학생)
- 회원가입

### 교수 기능
- 과제 생성, 수정, 삭제
- 학생 제출물 확인
- 과제 채점 및 피드백 제공
- 댓글을 통한 소통

### 학생 기능
- 과제 목록 확인
- 과제 제출 (파일 업로드 포함)
- 제출 현황 및 점수 확인
- 질문을 위한 댓글 작성

## 기술 스택

- **Framework**: Spring Boot 3.1.0
- **Security**: Spring Security 6
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA
- **Template Engine**: Thymeleaf
- **Frontend**: Bootstrap 5, HTML5, CSS3, JavaScript
- **Build Tool**: Gradle

## 시스템 요구사항

- Java 17 이상
- MySQL 8.0
- Gradle 7.0 이상

## 설치 및 실행

### 1. 데이터베이스 설정

MySQL에 데이터베이스를 생성하세요:

```sql
CREATE DATABASE assignment_db;
```

### 2. 프로젝트 설정

`src/main/resources/application.properties` 파일에서 데이터베이스 연결 정보를 확인하세요:

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/assignment_db?useSSL=false&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=1234
```

### 3. 프로젝트 실행

```bash
./gradlew bootRun
```

또는 IntelliJ IDEA에서:
1. 프로젝트를 열기
2. `AssignmentSubmissionSystemApplication.java` 파일을 찾기
3. 메인 메소드 옆의 실행 버튼 클릭

### 4. 애플리케이션 접속

브라우저에서 `http://localhost:8080`으로 접속하세요.

## 사용 방법

### 1. 회원가입
- 홈페이지에서 "회원가입" 클릭
- 사용자 정보 입력 및 역할 선택 (교수/학생)

### 2. 로그인
- 등록한 사용자명과 비밀번호로 로그인

### 3. 교수 사용법
- 대시보드에서 "새 과제 만들기" 클릭
- 과제 정보 입력 후 저장
- 제출물 확인 및 채점

### 4. 학생 사용법
- 대시보드에서 과제 목록 확인
- 과제 클릭 후 제출물 작성
- 파일 첨부 가능 (최대 10MB)

## 프로젝트 구조

```
src/
├── main/
│   ├── java/com/assignment/system/
│   │   ├── config/          # 설정 클래스
│   │   ├── controller/      # 컨트롤러
│   │   ├── dto/            # 데이터 전송 객체
│   │   ├── entity/         # JPA 엔티티
│   │   ├── repository/     # 리포지토리 인터페이스
│   │   ├── service/        # 서비스 레이어
│   │   └── AssignmentSubmissionSystemApplication.java
│   └── resources/
│       ├── static/         # 정적 리소스 (CSS, JS)
│       ├── templates/      # Thymeleaf 템플릿
│       ├── uploads/        # 업로드된 파일
│       └── application.properties
└── test/                   # 테스트 코드
```

## 데이터베이스 구조

### 주요 테이블
- `users`: 사용자 정보 (교수/학생)
- `assignments`: 과제 정보
- `submissions`: 과제 제출물
- `grades`: 채점 정보
- `comments`: 댓글

## 보안

- Spring Security를 통한 인증/인가
- 비밀번호 암호화 (BCrypt)
- 역할 기반 접근 제어
- CSRF 보호

## 파일 업로드

- 최대 파일 크기: 10MB
- 지원 파일 형식: PDF, DOC, DOCX, TXT, ZIP, RAR
- 업로드 디렉토리: `src/main/resources/uploads/`

## 문제 해결

### 데이터베이스 연결 오류
1. MySQL 서비스가 실행 중인지 확인
2. 데이터베이스 이름, 사용자명, 비밀번호 확인
3. 방화벽 설정 확인

### 파일 업로드 오류
1. 업로드 디렉토리 권한 확인
2. 파일 크기 제한 확인
3. 지원되는 파일 형식인지 확인

## 라이센스

이 프로젝트는 교육용으로 제작되었습니다.
