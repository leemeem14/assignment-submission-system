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

## 사용 방법

### 1. 회원가입
- 홈페이지에서 "회원가입" 클릭
- 사용자 정보 입력 및 역할 선택 (교수/학생)

### 2. 로그인
- 등록한 사용자명과 비밀번호로 로그인

### 3. 교수 사용법
- 과제 제출용으로 제작되었습니다.
바이브 코딩 조아
