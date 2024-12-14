# S.C.Vs (Stocknews Collection Viewers)

<p align="center">  <img src="./images/prjLogo.png">
  <br>
</p>

KDT 27회차 3조 팀 프로젝트입니다.

## 프로젝트 소개

S.C.Vs (Stocknews Collection Viewers)는 개인화 주식 종목 뉴스 플랫폼이란 주제로, 주식과 관련된 뉴스를 중점으로 정보들을 모아 보여주는 웹 페이지 입니다. 국내 경제 뉴스와 미국 경제 뉴스를 크롤링한 다음 번역과 가공을 통해 뉴스들을 보여주는 기능과 관심 종목에 대한 키워드 분석과 감정 분석을 하여 해당 주식에 대한 뉴스를 긍정/중립/부정으로 평가하여 보여줍니다. 또한 주식 정보와 주요 지표를 확인할 수 있으며 관심 종목에 대한 커뮤니티를 형성하여 의견을 나눌 수 있습니다. 

<br>

## 구성원

| 강민 | 하승우 | 이도훈 | 박범서 | 박주연 | 김건우 | 이기환 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| <img src="./images/team/zzangkkmin.jpg " alt="강민" width="100"> | <img src="./images/team/invisibleufo101.jpg " alt="하승우" width="100"> | <img src="./images/team/LeeDoHun.png" alt="이도훈" width="100"> | <img src="./images/team/ParkBeomSeo.jpg " alt="박범서" width="100"> | <img src="./images/team/ParkBeomSeo.jpg " alt="박주연" width="100"> | <img src="./images/team/ParkBeomSeo.jpg " alt="김건우" width="100"> | <img src="./images/team/ParkBeomSeo.jpg " alt="이기환" width="100"> |
| [zzangkkmin](https://github.com/zzangkkmin) | [invisibleufo101](https://github.com/invisibleufo101) | [giveluck](https://github.com/giveluck) | [duddnr0514](https://github.com/duddnr0514) | [sw-jooyeon](https://github.com/sw-jooyeon) | [KGW-561](https://github.com/KGW-561) | [Lee-Gi](https://github.com/Lee-Gi) |


<br>

## 구현 기능

### 국내 & 미국 뉴스 크롤링

-  국내 뉴스 사이트(,,)와 미국 뉴스 사이트(,,)에 있는 경제 / 주식 기사들을 크롤링
-  주식 종목 별 키워드로 분류
-  미국 뉴스의 경우 Papago Translation을 이용한 번역 작업
-  해당 주식에 대한 뉴스가 긍정

### 국내 & 미국 주식 정보 크롤링

- 한국투자증권 API를 활용한 국내 시장 지표(KOSPI, KOSDAQ), 미국 시장 지표(NASDAQ, S&P500), 원 달러 환율 정보 추출
- yFinance API를 활용한 국내/미국 주식 정보들 추출
- 추출된 지표 정보는 메인 페이지 및 국내/미국 뉴스 페이지에서 확인
- 추출된 주식 정보는 관심 종목 세부 페이지에서 확인

### 관심 주식 커뮤니티

- 사용자가 관심 주식을 선택한 다음, 그 주식에 대한 긍정/부정 투표 가능
- 관심 주식에 대한 댓글을 남겨 그 주식의 관심있는 다른 사용자와 함께 의견 공유

### 그 외

- 로그인 / 계정 정보
- 공지사항
- 관리자용 계정 관리

<br>

## 프로젝트 구조

구조 이미지


### scvs (웹 프로젝트 구조)
```plaintext
scvs/
├── gradle/wrapper
├── src/
│   ├── main
|   |   ├── java/com/team3/scvs          
│   |   |   ├── config/                  # 스프링 설정 코드
│   |   |   ├── constant/                # 상수
│   |   |   ├── controller/              # 컨트롤러
│   |   |   ├── dto/                     # DTO
│   |   |   ├── entity/                  # 엔티티
│   |   |   ├── repository/              # 리포지토리
│   |   |   ├── security/                # 스프링 보안 설정
│   |   |   ├── service/                 # 서비스
│   |   |   ├── util/                    # 유틸
│   |   |   ├── ScvsApplication.java     # 어플리케이션 실행 코드
│   |   |   └── ServletInitializer.java  # 서블릿 초기화 파일
|   |   └── resources
|   |       ├── static
|   |       |   ├── css/                 # css 스타일
|   |       |   ├── images/              # 사용 이미지
|   |       |   └── js/                  # javascript
|   |       ├── templates
|   |       |   ├── Account/             # 사용자 로그인/로그아웃 html
|   |       |   ├── Admin/               # 관리자 html
|   |       |   ├── News/                # 뉴스 html
|   |       |   ├── PSA/                 # 공지사항 html
|   |       |   ├── Partials/            # html 조각 모음
|   |       |   ├── Stockwatch/          # 관심 종목 html
|   |       |   ├── User/                # 사용자 정보 html
|   |       |   ├── error/               # 에러 html
|   |       |   ├── layouts/             # 레이아웃 구성 모음
|   |       |   └── index.html           # 메인 페이지
|   |       └── application.properties   # 스프링 어플리케이션 설정 값
|   |
|   └── test/java/com/team3              # 테스트 코드 
|
├── .gitattributes                       # Git 설정 파일
├── .gitignore                           # Git 무시 파일 목록
├── build.gradle                         # gradle 빌드 설정 파일
├── gradlew                              # gradle 실행 파일
├── gradlew.bat                          # gradle 배치 실행 파일
└── settings.gradle                      # gradle 설정 파일
```
<br>

### API EndPoint Lists

[API_ENDPOINT_LIST](API_endpoint_list.md)

## 기술 사용 스택

- Backend:   
  ![java](https://img.shields.io/badge/java-ffffff.svg?&style=for-the-badge&logo=openjdk&logoColor=black) ![spring](https://img.shields.io/badge/spring-6DB33F.svg?&style=for-the-badge&logo=spring&logoColor=white) ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)

- DB  
  ![mysql](https://img.shields.io/badge/mysql-4479A1.svg?&style=for-the-badge&logo=mysql&logoColor=white) 

- FrontEnd:  
  ![html5](https://img.shields.io/badge/html5-E34F26.svg?&style=for-the-badge&logo=html5&logoColor=white) ![css3](https://img.shields.io/badge/css3-1572B6.svg?&style=for-the-badge&logo=css3&logoColor=white) ![javascript](https://img.shields.io/badge/javascript-F7DF1E.svg?&style=for-the-badge&logo=javascript&logoColor=white) ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white) ![Bootstrap](https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white)

- Design:  
  ![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white)

- Crawler:  
  ![python](https://img.shields.io/badge/python-3776AB.svg?&style=for-the-badge&logo=python&logoColor=white)

- IDE:  
  ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white) ![Visual Studio Code](https://img.shields.io/badge/Visual%20Studio%20Code-0078d7.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white) 

- Version Control:  
  ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)

- 네이버 클라우드 서비스:  
  Cloud DB for MySQL(DB), CLOVA Studio(감정분석), Papago Translation(번역)

- API:  
  한국투자증권, yFinance


## 역할 분담

|  |  |  |
|-----------------|-----------------|-----------------|
| 강민 |  <img src="./images/team/zzangkkmin.jpg " alt="강민" width="100"> | <ul><li>프로젝트 계획 및 관리</li><li>GIT 최종 관리자</li><li>메인 페이지 및 레이아웃 구성</li><li>주식 정보 및 지수 정보 크롤링</li></ul>     |
| 하승우 |  <img src="./images/team/invisibleufo101.jpg " alt="하승우" width="100">| <ul><li>파이선 크롤러 담담</li><li>DB 설계자</li><li>국내/미국 뉴스 크롤링</li><li>키워드 추출 및 분리</li></ul> |
| 이도훈 |  <img src="./images/team/LeeDoHun.png" alt="이도훈" width="100">    | <ul><li>기사 감정분석 적용</li><li>DB 설계자</li><li>토론방 페이지 디자인</li><li>토론방 백앤드 기능 담당</li></ul> |
| 박범서 |  <img src="./images/team/ParkBeomSeo.jpg" alt="박범서" width="100"> |<ul><li>공지사항 페이지 디자인</li><li>공지사항 백앤드 기능 담당</li></ul>  |
| 박주연 |  <img src="./images/team/ParkBeomSeo.jpg" alt="박주연" width="100">    | <ul><li>국내/미국/관심주식 뉴스 페이지 디자인</li><li>국내/미국/관심주식 뉴스 부분 백앤드 기능 담당</li></ul>    |
| 김건우 |  <img src="./images/team/ParkBeomSeo.jpg" alt="김건우" width="100">    | <ul><li>회원가입/로그인/로그아웃/계정정보/관리자 페이지 디자인</li><li>회원가입/로그인/로그아웃/계정정보/관리자 백앤드 기능 담당</li></ul>    |
| 이기환 |  <img src="./images/team/ParkBeomSeo.jpg" alt="이기환" width="100">    | <ul><li>관심주식 페이지 디자인</li><li>관심주식 백앤드 기능 담당</li></ul>    |

<br/>

## 프로젝트 기간

2024/11/04 ~ 2024/12/17 (6W, 2days)

| week 1 | week 2 | week 3 | week 4 | week 5 | week 6 | week 7 |
|--------|--------|--------|--------|--------|--------|--------|
|기획    |         |       |        |        |        |        |
|        |설계     |       |        |        |        |        |
|        |         |구현   |구현     |구현    |        |        |
|        |         |       |        |        |테스트  |        |
|        |         |       |        |        |        |완성    |

## 작업 과정

### GIT 전략

- 개발 시 `main` 브랜치에서 파생된 `develop` 브랜치를 기반으로 작업한다.
- `develop` 브랜치에서 `features/` 에 미리 정의한 기능 ID로 브랜치를 나누어서 작업한다.
- `features/[기능ID]` 에서 기능 개발이 완료한 뒤 github에서 `pull-request`를 보낸다.
- `pull-request`를 보내면 보낸 이를 제외한 최소 1명이 github에서 보낸 pull-request 내용을 본 후 **허용**을 해야 `merge pull-request`가 될 수 있다.
- `merge pull-request` 시 **충돌이 발생**한 경우 github에 나와있는 가이드라인을 따라 `develop`을 pull한 다음에 pull-request 대상 브랜치로 체크아웃해서 pull된 `develop`을 머지하여 충돌을 해결한 다음, `merge pull-request`를 실행한다.
- 테스트 도중 나온 오류에 대한 브랜치는 `bugfix/` 에 테스트 ID로 브랜치를 나누어서 오류를 해결한 다음 `pull-request`를 보내서 `develop`에 반영한다.
- commit을 남길때 형식은 `[기능/테스트ID] 주요내용` 으로 남기고 부가적인 내용은 commit 세부 내용에 담는다.
- 최종적으로 개발된 `develop` 브랜치는 `main`으로 반영하여 개발을 완료한다.

<img src="./images/git.gif" alt="GIT">

## 이것만큼은 좋았다...

### 철저한 GIT 관리
- 팀 프로젝트인 만큼 GIT 규칙 및 양식 등 사전에 협의하여 실제 작업와 비슷하게 개발 과정을 관리했다.
- 거의 모두가 GIT을 처음 사용을 했지만, 모두가 규약을 설계하고 철저히 지키며, 예상치 못한 충돌에는 git 경험자의 빠른 피드백 서비스로 조치하며 원할한 개발 흐름을 유지했다.

### 배운 과정 외 기술 탐색 및 활용
- 교육 과정 중 배운 Mybatis, el표기법 외 JPA 적용과 타임리프 활용법 그리고 파이선을 이용한 크롤링을 활용하면서 과정 외 기술들을 분석하고 사용하는 방법을 익혀서 개발할 수 있었다.
- 과정 외 기술들을 잘 활용한 결과 프로젝트 구조 구축과 개발 편의성 향상에 큰 도움이 되었다. 

### 우리 프로젝트만의 Kick
- 주제가 주식 뉴스 데이닝 마이닝인 만큼, 기존 스프링 구조와 별개로 크롤링만을 위한 프로젝트를 따로 두어서 관리하였다. 즉 스프링 실행 작업과 크롤링 실행 작업을 따로 두는 구조로 개발하여 서로 독립적으로 관리할 수 있다.
- 크롤링 과정에선 주기적으로 정보를 긁어와 DB에 넣어주는 작업만 담당하고, 스프링 과정에선 사용자 요청에 따라 정보르 DB에서 읽어오는 작업만 담당하게 구성하였다.

## 그래도 이건 좀 아쉬웠다...

### 누구나 그럴싸한 계획을 가지고 있다, 실전을 접하기 전까지는...
- 개개인의 역량을 고려하면서 계획을 짯었으나... 실전은 계획대로 흘러가지 않았다.
- "배운대로 하면 된다"란 생각은 예외 상황 및 돌발 상황 대처에  
<br>




# 박범서님
- 수업 도중 학습했었던 기본에 충실하고 싶었기에 CRUD 구현이라는 목적에 가장 어울리는 파트를 맡았다. 배운대로만 하면 된다는 생각에 꽤나 빨리 끝날 것이라 예상하고 있었는데, 안타깝게도 현실은 그렇지 못했다. 자바 코드에서 오류가 생기는 건 당연하고, 전혀 예상치도 못하게 화면 디자인 쪽에서도 계속해서 구멍이 생기는 판에 예상 시간보다 질질 끌리는 상황을 피할 수가 없었다. 다 완성되었다고 생각한 기능조차도 제 3자가 보기에는 불편한 오류들이 군데군데 있었기에 수정에도 상당한 시간이 소요되었다. 결국은 공지사항 관련 기능 완성에 거의 모든 시간을 소요했다.

git 관련에서도 꽤나 많은 고생을 했는데, 팀 프로젝트용 github 사용이 거의 처음이다보니 갈피를 못 잡고 이것저것 헤메게 되었다. 다행히도 브랜치 오사용이나 잘못된 머지로 팀원들의 작업물을 날려먹는 대참사는 일어나지 않았지만 도중에 pull 실수로 내 작업을 날려먹는다던가... 꽤나 많은 일이 있었다. 그래도 이 부분에서는 조장인 강민님이 깃 관리를 철저히 해 주신 덕에 더 꼬이는 일 없이 원활한 해결이 되었다고 생각한다. pr시에도 원인 모를 충돌을 해결한다고 꽤나 끙끙댔었는데, 도훈님의 도움 덕에 빠르게 해결할 수 있었다. 이번에 작업을 하며 데여 본 경험 덕에 다음부터는 좀 더 원활한 작업이 가능하지 않을까 생각한다.

상술했듯 많은 문제들이 생겼었던 프로젝트였으나, 그간의 학습 내용을 총망라해서 되돌아볼 수 있는 좋은 시간이었다. 귀중한 팀 프로젝트 경험을 쌓을 수 있었다는 점 또한 좋은 부분이다. 시작부터 꽤나 삐걱대는 소리가 났지만 이런 상황에서도 끝까지 포기하지 않고 달려와준, 힘들 때 도와 준 팀원들에게 감사할 따름이다.

# 이기환님
느낀점: 이번 프로젝트를 시작하기 전부터 스프링부트 학습이 부진하여 내가 모자란걸 알고 있었지만, 실제로 다른 분들과 프로젝트를 해보니 생각보다 부족한 부분이 더 많다는걸 알 수 있었다. 같은 강의를 들은 다른 사람들과 비교해 볼 수 있는 좋은 기회였던거 같다.



 그리고 대부분 다른 조원분들이 완성했지만 프로젝트가 완성되어가는 모습을 볼 때, 혹은 작은 부분이라도 내가 한 부분이 완성된걸 보니 뿌듯했다.
 만들어지는 과정중에 제대로 수행 가능한 부분이 적어서 많이 의존했고, 도움을 받았는데 상당히 미안한 마음이 들었다. 다음에는 이런 상황이 없도록 좀 더 정진해야겠다고 느꼈다.


나는 피그마를 통해 디자인된 몇몇 화면을 구현하는 역할을 맡게됐다. html, css 등을 활용하여 화면을 구현하면 그 화면을 조원분들이 받아서 수정 후 활용하는 방식이었다.
개인적으로 해당 화면을 gpt를 이용해 큰 틀을 짜고, 거기에 내가 살을 덧대거나 부분적으로 프로젝트에 맞게 수정하면 될거라고 생각했는데,
화면마다 사이즈가 다르기 때문에 반응형도 고려해야했고, gpt가 만들어준 코드가 생각보다 부실하여 개인적으로 공부가 필요했다.
결국 원하는 부분을 gpt에게 묻고, 그 부분을 책을 보고 공부하면서 화면을 구하는 방식으로 해결할 수 밖에 없었다. 그럼에도 사소한 문제를 일으켜서 팀원들에게 미안했다.
문제를 해결하려 해도 실력의 문제이니 당장 해결이 불가능할 거라고 생각해서, 많은 주석을 달다보니 또 생각보다 시간이 많이 필요하게 됐다.
왜 강사님이 주석다는 버릇을 중요하게 여기셨는지 몸으로 느낄 수 있었다.
그리고 눈으로 보고 코드를 해석하는것과 실제로 만드는것의 난이도 차이가 생각보다 너무 크다는 것도 느꼇고, 강의 초반부에 게을러서 강사님이 권장해 주신 학습을 하지 않은 점이 아쉬웠다.



프로젝트를 하면서 생각하지 못한 부분에서 어려움을 느낀점이 있었는데, 깃허브 사용이 생각보다 많이 복잡했다.
 처음에 설명을 들었을때는, 스테이지에 올리고 커밋만 하면 되는 단순한 시스템이라고 생각했는데,
 실제로 사용해보니 자칫 잘못하단 충돌이 일어날 수도 있고 merge, check out, pull up 등 기능도 많이 존재헤서,
 공부를 간략하게라도 하지 않으면 사용 할 수도 없었다. 



문서 작업이 생각보다 너무 많다고 생각했는데, 직접 프로젝트를 해보니 정말 필요한 부분이라고 생각됐다.
개인적으로 문서화가 됐는데도 실수한 부분이 있었고, 문서가 없었으면 더 많은 실수를 했을거라고 생각한다.
여러명이 함께 작업을 한다는 점에서, 사람들을 묶어주는 문서가 없으면 의사소통부터 명확히 되지 않았을 것이라는 생각이 든다.

그리고 피그마를 통해 설계 및 디자인을 직접해보고,
 html을 통해 디자인된 화면을 구현하는 과정에서 개인적으로 불명확했던 프론트, 백엔드, 디자인의 차이를 확실하게 알게된거 같고, 그 차이를 알게 되니 왜 회사에서 다른 영역으로 분류해서 채용하는지, 왜 여러 사람들이 한 분야를 깊게 파야한다고 하는지 느낄 수 있었다.

이번 프로젝트를 통해 내가 부족하단 점을 여실히 느낄 수 있었고, 예상못한 문제나 의아했던 부분등 직접 해보지 않고는 알 수 없던 부분들을 배워간거 같습니다.
프로젝트를 거름삼아 공부하여 다른 조원분들의 코드를 이해하고 스스로 구현할 수 있게 하고, 개발자의 길에 한 걸음 더 나아가도록 하겠습니다.

# 이도훈님
DB 설계
토론방 기능 구현
다른 페이지 기능 지원
설치 가이드 일부 작성

느낀점
이번 프로젝트에서 가장 도전적인 부분은 아무것도 없는 상태에서 DB를 설계하는 것이었습니다. 테이블을 정의하면서 다른 테이블과의 관계, 각 열의 특성, 그리고 정규화 같은 여러 가지를 고민해야 했기에 초기에는 상당히 어렵게 느껴졌습니다. 하지만 혼자 하는 작업이 아니라 팀 프로젝트였기 때문에, 팀원들과 아이디어를 공유하고 논의하며 어려운 부분을 해결할 수 있었습니다.

백엔드 개발에 관심이 많았던 저는 토론방 기능을 직접 구현하기로 했습니다. 처음 도전하는 개발인 만큼 CRUD 전부를 포함하는 기능을 완성해 본다면 다른 부분도 쉽게 다룰 수 있을 거라 생각했습니다. 이 과정에서 처음으로 Thymeleaf와 JPA를 사용했는데, 제가 설계한 기능이 처음 작성한 DB 구조와 잘 맞지 않아 코드와 DB를 다시 설계하고 정리하는 과정이 필요했습니다. 그럼에도 불구하고 계속 학습하고 작업을 진행한 결과, 실력이 늘었음을 느꼈고, 다른 페이지 기능 구현에도 기여할 수 있어 뿌듯했습니다.

또한, 팀 프로젝트에서의 Git 관리는 새로운 경험이었습니다. Git 브랜치 관리는 익숙하지 않아 처음에는 낯설었지만, 팀원 중 강민 님이 브랜치 관리 체계를 잘 정리해 주신 덕분에 큰 어려움 없이 Git을 활용할 수 있었습니다.

이번 프로젝트를 통해 백엔드 개발의 프로세스와 진행 방식을 배울 수 있었습니다. 무엇보다 개발하고자 했던 기능들을 모두 완성하며 뜻깊은 시간을 보낼 수 있어 매우 만족스러웠습니다.