# 프롬프트
```
클로드.md 파일에 우리가 치는 프롬프트들 PROMPT.md에 정리해 주는 요구사항 넣어서 만들어줘
```
```
그러면 우리가 readme md 파일에 1. 스타일정리와 관련된 부분들은 전부 선정했어. todo.md 파일도 보고 스타일정리를해줄 수 있을까 ??
```
```
/analyze-before-remove [미사용 @Autowired 제거]
```
```
Implement the following plan: # 1단계: 스타일 정리 구현 계획 (에러 메시지 영어 통일, 예외 처리 패턴 통일, Null 처리 패턴 통일, import 정리 및 코드 포맷팅)
```
```
큰 단위의 작업을 진행했을때 md 파일로 문서화 시켜주는 내용을 Claude.md 에 추가해줘
```
```
너가 방금 스타일 정리 작업을 진행했잖아 ?? 뭔 작업을 진행했는지 한번 정리해서 문서로 만들어줄 수 있을까 ??
```
```
/smart-commit
```
```
너가 그룹화를 진행한게 @REQUIREMENT.md 에 있는 프로그래밍 요구사항의 커밋과 관련된 부분을 따른건지 한번 더 확인해줄 수 있을까 ?
```
```
해당 개선안을 커밋 메세지를 한글로 바꿔서 진행해줘
```
```
테스트 돌려봐
```
```
md 문서들이 지금 너무 root에 많아서 docs 폴더 안에 정리해줄 수 있을까? 폴더 만들어도 상관없어. 스킬로도 해당 내용 만들어놔 ~ 나중에 쓰게!
```
```
/analyze-before-remove Validator 중복 코드 정리
```
```
Implement the following plan: # 2-1. 미사용 @Autowired 제거 실행 계획
```
```
claude.md에 있는 작업 문서화 규칙에 따르면 너가 방금 한 일을 문서화 했어야 했는데 왜 안했어 ?
```
```
@README.md 와 @docs/plan/TODO.md , @docs/plan/REQUIREMENT.md 이 3개의 파일을 보고 2-2의 validator 중복코드 정리 문제를 우리가 선정한 방식으로 리팩터링을 진행해줄 수 있을까 ?? 다 진행하면 잊지말고 문서화 부탁해 ~
```
```
/analyze-before-remove 미사용 kotlin 의존성 정리해줘.
```
```
/analyze-before-remove MemberController 구현 의도
```
```
@docs/removal-analysis-unused-kotlin-dependencies.md 랑 @README.md 보고 2-3에 우리가 어떤 방식으로 리팩토링할지 선정했어. 이걸 기반으로 코드를 리팩토링해줄 수 있을까 ?
```
```
@README.md 랑 @docs/removal-analysis-member-controller.md 파일 보고 3-1에서 우리가 선정한 방식으로 membercontroller 리팩토링 진행해줘
```
```
skills 중에 analyze-before-remove가 있는데 지금 너무 삭제에 취중되어있는거 같애. 삭제가 명칭을 리팩토링으로 바꿔줬으면 좋겠어.
```
```
/analyze-before-refactoring CategoryService 추출
```
```
/analyze-before-refactoring productservice 추출
```
```
@README.md, @docs/refactoring-analysis-product-service-extraction.md , @docs/refactoring-analysis-category-service-extraction.md 이 3개의 md 파일을 보고, readme.md 파일의 3-2, 3-3 부분을 같이 진행해줄 수 있을까 ? 이렇게 부탁하는 이유는 2개의 도메인이 꽤 많이 엮여있는 것 같아서 그래. 3개의 파일을 참고해서 진행해줘. 추가로 @docs/plan/TODO.md , @docs/plan/REQUIREMENT.md 를 참고해도 좋아
```