# Spring_TodoList

> 기초 CRUD, Redis, Spring Scheduler 학습을 위한 TodoList  
> [Jmeter 부하테스트 및 동시성 문제 기록 저장소](https://ldhbenecia.notion.site/d05c86852648447ab0e0e0194b0f1255?pvs=4)

## 목표
1. Todo에 대한 기초적인 CRUD를 구현합니다.
2. Spring Security를 이용하여 자체 로그인을 구현합니다.
3. 로그인 사용자 기반 API를 구현합니다.
4. 좋아요 기능을 구현합니다.
5. 테스트 코드를 작성합니다.
6. 부하테스트를 진행합니다.
7. 동시성 문제를 해결합니다.

### 세부 목표
- Spring Security를 이용한 자체 로그인 후 사용자 객체 정보를 어노테이션으로 추출합니다.
- Redis를 사용하여 캐싱 처리를 합니다.
- Spring Scheduler를 이용하여 캐싱한 데이터를 DB에 반영 후 캐싱을 제거합니다.
- 부하테스트를 진행하면서 지표를 확인합니다.
- 동시성 제어에 대해 학습하고 해결합니다.

### 리팩토링
- 전역 예외처리를 구현합니다.
- 최대한 모든 경우에 대한 예외처리를 고민해보고 구현합니다.

<br />

## 기술 스택
| 분류        | 스택                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
|-----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| BackEnd   | <img src="https://img.shields.io/badge/Java-007396?logo=openjdk&logoColor=white"/>  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/Spring Security-6DB33F?logo=springsecurity&logoColor=white"/> <img src="https://img.shields.io/badge/H2-3D03A7?logo=databricks&logoColor=white"/> <img src="https://img.shields.io/badge/Redis-FF4438?logo=redis&logoColor=white"/> |
| Test Tool | <img src="https://img.shields.io/badge/junit5-25A162?logo=junit5&logoColor=white"/> <img src="https://img.shields.io/badge/Apache Jmeter-D22128?logo=apachejmeter&logoColor=white"/>                                                                                                                                                                                                                                                                      |

<br />

## 진행 상황
- [x] TODO 기초 CRUD 구현
- [x] Spring Security를 사용하여 자체 로그인 구현
- [x] Security 기반 API 구현
- [x] 전역 Exception 처리
- [x] Todo 테스트 코드 작성
- [x] Jmeter를 사용하여 부하테스트 진행
- [ ] Redis를 사용하여 동시성 문제 해결
- [ ] Redis를 사용하여 좋아요 캐싱 후 DB 반영 구현