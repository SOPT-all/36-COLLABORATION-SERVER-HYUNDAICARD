# 현대카드 웹 프로젝트 폴더 구조 가이드

## 프로젝트 구조 개요

이 프로젝트는 도메인 중심의 패키지 구조를 채택하여 관심사 분리 원칙(Separation of Concerns)을 준수하고 있습니다. 전체 구조는 다음과 같은 주요 패키지로 구성되어 있습니다:

```
org.soptcollab.web1.hyundaicard
├── api
│   ├── controller.card
│   └── service.card
│       └── dto
├── domain.card
│   └── exception
├── global
│   ├── common
│   │   ├── entity
│   │   ├── enums
│   │   └── response
│   ├── config
│   ├── error
│   │   └── exception
│   └── util
```

## 폴더 구조 설계 의도

### 1. 도메인 중심 설계

프로젝트는 **도메인 중심의 패키지 구조**를 채택했습니다. 이는 기능이나 계층이 아닌 비즈니스 도메인에 따라 코드를 구성함으로써 다음과 같은 이점을 제공합니다:

- **응집도 향상**: 관련된 기능이 함께 위치하여 코드 탐색 및 이해가 용이
- **확장성 증가**: 새로운 도메인 추가 시 기존 코드에 영향 최소화
- **유지보수성 향상**: 도메인별 독립적인 개발 및 테스트 가능

### 2. 관심사의 명확한 분리

코드는 크게 세 가지 주요 관심사로 분리되어 있습니다

- **api**: 외부와의 인터페이스 담당 (컨트롤러, 서비스, DTO)
- **domain**: 핵심 비즈니스 로직 및 엔티티 관리
- **global**: 프로젝트 전반에 걸쳐 사용되는 공통 코드

이러한 분리는 각 영역의 책임을 명확히 하고, 코드의 재사용성과 테스트 용이성을 높입니다.

### 3. 예외 처리 전략

도메인별 예외와 글로벌 예외를 분리하여 관리함으로써 아래의 사항들을 얻고자 하였습니다.

- 도메인 특화된 예외는 해당 도메인 패키지 내에서 처리
- 공통 예외는 global.error.exception에서 중앙 관리
- 이를 통해 일관된 예외 처리 및 명확한 오류 메시지 제공

### 4. 공통 코드 중앙화

global 패키지를 통해 공통 코드를 중앙화함으로써 아래의 사항들을 얻고자 하였습니다.

- 코드 중복 방지
- 일관된 기능 구현
- 변경 사항 발생 시 수정 포인트 최소화

## 패키지별 사용 가이드

### api 패키지

외부 요청을 처리하고 서비스 계층과 연결하는 역할을 담당합니다.

- **controller**: HTTP 요청 처리 및 응답 반환
    - 도메인별로 하위 패키지 구성 (예: controller.card)
    - 요청 검증과 응답 변환에 집중
    - 비즈니스 로직은 포함하지 않음

- **service**: 비즈니스 로직을 처리
    - 도메인별로 하위 패키지 구성 (예: service.card)
    - 트랜잭션 관리
    - 도메인 객체 조작 및 저장소와의 상호작용

- **dto**: 데이터 전송 객체
    - 요청/응답에 사용되는 객체 정의
    - 각 서비스 패키지 하위에 위치

### domain 패키지

비즈니스 핵심 개념과 규칙을 표현하는 영역입니다.

- **domain.card**: 카드 도메인 관련 클래스 관리
    - 엔티티 (Card.java)
    - 저장소 (CardRepository.java)
    - 도메인 특화 예외 (exception 패키지)

### global 패키지

애플리케이션 전반에 걸쳐 사용되는 공통 코드를 관리합니다.

- **common**: 공통 기능 관리
    - **entity**: 기본 엔티티 클래스 (BaseEntity.java)
    - **enums**: 공통으로 사용되는 열거형 (ErrorCode.java)
    - **response**: API 응답 형식 정의 (ApiResponse.java)

- **config**: 애플리케이션 설정 관리
    - 로깅 설정 (LoggingConfig.java)
    - 기타 애플리케이션 설정

- **error**: 예외 처리 관련 코드
    - 전역 예외 처리기 (GlobalExceptionHandler.java)
    - 공통 예외 클래스 (exception 패키지)

- **util**: 유틸리티 클래스
    - 로깅 유틸리티 (LoggingUtil.java, SystemPrintLoggingUtil.java)
    - 기타 유틸리티 기능

## 작업 가이드라인

### 1. 새로운 도메인 추가 시

새로운 비즈니스 도메인을 추가할 때는 다음 단계를 따릅니다

1. domain 패키지 아래에 새 도메인 패키지 생성
2. 해당 도메인의 엔티티, 저장소, 도메인별 예외 클래스 생성
3. api 패키지 아래에 controller와 service 패키지 생성
4. service 패키지 하위에 dto 패키지 생성

### 2. 엔티티 작성 시

엔티티 작성 시 다음 원칙을 따릅니다

1. 모든 엔티티는 global.common.entity.BaseEntity를 상속받아 생성/수정 시간 등 공통 필드 활용
2. JPA 관련 어노테이션은 엔티티 클래스 내부에만 사용
3. 가능한 불변성(Immutability)을 유지하도록 설계
4. 엔티티 간 연관관계는 명확하게 정의

### 3. 예외 처리

예외 처리는 다음 지침을 따릅니다

1. 도메인 특화 예외는 해당 도메인의 exception 패키지에 생성
2. 모든 비즈니스 예외는 global.error.exception.BusinessException을 상속
3. 예외 코드는 global.common.enums.ErrorCode에 정의
4. 예외 발생 시 GlobalExceptionHandler에서 일관된 응답 형식으로 변환

### 4. API 응답 형식

모든 API 응답은 다음 규칙을 따릅니다

1. global.common.response.ApiResponse를 사용하여 일관된 응답 형식 유지
2. 성공/실패 여부, 데이터, 메시지 등 표준화된 필드 포함
3. 컨트롤러에서 직접 엔티티를 반환하지 않고 항상 DTO로 변환하여 응답

### 5. 로깅 전략

로깅은 다음 지침을 따릅니다

1. global.util.LoggingUtil 활용
2. 로그 레벨 적절히 구분 (ERROR, WARN, INFO, DEBUG)
3. 개발 환경과 운영 환경의 로깅 설정 분리 (global.config.LoggingConfig)

## 주의사항

1. **계층 간 의존성 방향**: domain은 api에 의존하지 않아야 함 (api가 domain에 의존)
2. **컨트롤러 책임 범위**: 컨트롤러는 요청 검증과 응답 변환에만 집중, 비즈니스 로직은 서비스에 위임
3. **테스트 용이성**: 각 계층이 명확히 분리되어 단위 테스트 작성 용이
4. **확장성 고려**: 새로운 기능 추가 시 기존 구조를 참고하여 일관성 유지