# STORY01 디자이 패턴, 꼭 써야 한다.

TL;DR
- J2EE 패턴은 MVC 구조를 기반으로 한다.
- JSP 모델1은 JSP에서 자바 빈을 호출하고 데이터베이스에 대한 처리가 가능하다.
    - 유지/보수가 어렵다.
    - 개발자의 역량에 따라 코드가 달라짐.
    - 컨트롤러가 없어서 MVC 패턴이라고 보기 어려움
- JSP 모델2는 서블릿으로 JSP를 요청하고, 서블릿에서 데이터 접근해서 처리까지 가능하다.
    - 서블릿이 컨트롤러 역할을 한다.
- J2EE 패턴
    - Intercepting Filter 패턴 : 요청 타입에 따라 다른 처리를 하기 위한 패턴.
    - Front Controller 패턴 : 요청 전후에 처리하기 위한 컨트롤러를 지정하는 패턴.
    - View Helper 패턴 : 프레젠테이션 로직과 상관 없는 비즈니스 로직을 헬퍼로 지정하는 패턴.
    - Composite View 패턴 : 최소 단위의 하위 컴포넌트를 분리하여 화면을 구성하는 패턴.
    - Service to Worker 패턴 : Front Controller와 View Helper 사이에 디스패처를 두어 조합하는 패턴.
    - Dispatcher View 패턴 : Front Controller와 View Helper로 디스패처 컴포넌트를 형성한다. 뷰 처리가 종료될 때까지 다른 활동을 지연한다는 점이 Service to Worker 패턴과 다르다.

    - Business Delegate 패턴 : 비즈니스 서비스 접근을 캡슐화하는 패턴.
    - Service Locator 패턴 : 서비스와 컴포넌트 검색을 쉽게 하는 패턴.
    - Session Facade 패턴 : 비즈니스 티어 컴포넌트를 캡슐화하고, 원격 클라이언트에서 접근할 수 있는 서비스를 제공하는 패턴.
    - Composite Entity 패턴 : 로컬 엔티티 빈과 POJO를 이용하여 큰 단위의 엔티티 객체를 구현하는 패턴
    - Transfer Object 패턴 : 일명 Value Object(VO) 패턴이라고도 한다. 데이터를 전송하기 위한 객체에 대한 패턴.
    - Transfer Object Assembler 패턴 : 하나의 Transfer Object로 모든 타입 데이터를 처리할 수 없으므로, 여러 Transfer Object를 조합하거나 변형한 객체를 생성하여 사용하는 패턴.
    - Value List Handler 패턴 : 데이터 조회를 처리하고, 결과를 임시 저장하며, 결과 집합을 검색하여 필요한 항목을 선택하는 역할을 수행.

    - Data Access Object 패턴 : DAO라고도 하며, DB에 접근을 전담하는 클래스를 추상화하고 캡슐화함.
    - Service Activator 패턴 : 비동기적 호출을 처리하기 위한 패턴.
