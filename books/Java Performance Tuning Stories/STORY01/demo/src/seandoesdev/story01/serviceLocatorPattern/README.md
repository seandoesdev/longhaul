# Service Locator 패턴
EJB Home 객체나 DB의 DataSoruce를 찾을 때 소요되는 응답 속도를 감소시키기 위해서 사용된다.
cache라는 Map 객체에 home 객체를 찾은결과를 보관하고 있다가, 누군가 그 객체를 필요로 할 때 메모리에서 찾아서 제공하도록 한다. 만약 해당 객체가 cache에 없으면 메모리에서 찾는다.
