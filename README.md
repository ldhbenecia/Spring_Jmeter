# Spring_TodoList

> 기초 CRUD, Redis, Spring Scheduler 학습을 위한 TodoList

## 목표
1. Todo에 대한 기초적인 CRUD를 구현합니다.
2. Spring Security를 이용하여 자체 로그인을 구현합니다.
3. 조회수 혹은 좋아요 기능을 구현합니다.  
4. 쿼리 최적화를 진행합니다.

### 세부 목표
- Spring Security를 이용한 자체 로그인 후 사용자 객체 정보를 어노테이션으로 추출합니다.
- 조회수, 좋아요 기능을 구현할 때 MySQL에 쿼리를 날리는 식으로 구현합니다.
- Redis를 사용하여 캐싱 처리를 합니다.
- Spring Scheduler를 이용하여 캐싱한 데이터를 DB에 반영 후 캐싱을 제거합니다.

### 리팩토링
- 전역 예외처리를 구현합니다.
- 최대한 모든 경우에 대한 예외처리를 고민해보고 구현합니다.

<br />

## 기술 스택
| 분류 | 스택 |
| --- | --- |
|    BackEnd    | <img src="https://img.shields.io/badge/Java-007396?logo=openjdk&logoColor=white"/>  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?logo=springsecurity&logoColor=white"/> <img src="https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=white"/> <img src="https://img.shields.io/badge/Redis-FF4438?logo=redis&logoColor=white"/> |
