# dnd-5th-10-backend   

## 프로젝트 소개
맞춤형 면접 문제 공유 서비스

## 개발 양식
### git branch 
- 기본적으로 git-flow 의 양식을 따르고 그 기능을 적극 활용한다  
[git-flow](https://danielkummer.github.io/git-flow-cheatsheet/index.ko_KR.html)  

### git branch naming 
- 이슈 단위로 개발을 진행한다
- 이슈 번호를 브랜치 이름의 prefix로 적용하고 뒤에 브랜치 이름을 연결짓는다   `feature/{number}-{name}`    
  
깃허브 이슈는 매 이슈마다 독립적인 Issue Number 를 지닌다.  
예시) `Swagger-ui 의존성 및 설정 추가 #5` 이라는 이슈를 할당받고 이 기능을 구현하려는 브랜치를 만드는 경우 이름을 `feature/6-swagger-ui` 로 정하도록 합시다!

### git commit 
- 자유롭게 쓰되, 본문이 없다면 제목을 명확히 작성하도록 하자!  
`테스트11번째` -> X  
`git commit 연습을 위한 테스트용 커밋 작성` -> O
- 제목과 본문은 한 줄 띄워 분리하자  
이것은 git 의 메뉴얼에 존재하는 제안이다
- 이슈를 끝내는 커밋에는 `close #issue번호` 로 마무리 지어보자  
깃허브에서 이슈를 닫을 필요없이 커밋내용을 통해 자동으로 닫히게 된다  
  
- commit Type 을 제목에 붙이자  [참고](https://velog.io/@new_wisdom/Clean-Coding-Commit-Message-Conventions)
```yaml
feat : 기능
fix : 버그 수정
docs : 문서 수정
style : 서식 지정, 세미콜론 누락 등 코드 변경이 없는 경우
refactor : 코드 리팩터링
test : 누락된 테스트 코드를 추가할 때
chore : 잡일(?), 빌드 업무나 패키지 매니저 수정할 때
```

그 외 참고할만한 내용: [좋은 커밋 작성법](https://meetup.toast.com/posts/106)