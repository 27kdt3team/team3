# S.C.Vs (Stocknews Collection Viewers)

<p align="center">
  <br>
  <img src="./images/prjLogo.png">
  <br>
</p>

KDT 27회차 3조 팀 프로젝트입니다.

## 프로젝트 소개

S.C.Vs (Stocknews Collection Viewers)는 개인화 주식 종목 뉴스 플랫폼이란 주제로, 주식과 관련된 뉴스를 중점으로 정보들을 모아 보여주는 웹 페이지 입니다. 국내 경제 뉴스와 미국 경제 뉴스를 크롤링한 다음 번역과 가공을 통해 뉴스들을 보여주는 기능과 관심 종목에 대한 키워드 분석과 감정 분석을 하여 해당 주식에 대한 뉴스를 긍정/중립/부정으로 평가하여 보여줍니다. 또한 주식 정보와 주요 지표를 확인할 수 있으며 관심 종목에 대한 커뮤니티를 형성하여 의견을 나눌 수 있습니다. 

<br>

## 구성원

| 강민 | 하승우 | 박범서 | 이도훈 | 박주연 | 김건우 | 이기환 |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| <img src="./images/team/zzangkkmin.jpg " alt="강민" width="100"> | <img src="./images/team/invisibleufo101.jpg " alt="하승우" width="100"> | <img src="./images/team/zzangkkmin.jpg " alt="박범서" width="100"> | <img src="./images/team/zzangkkmin.jpg " alt="이도훈" width="100"> | <img src="./images/team/zzangkkmin.jpg " alt="박주연" width="100"> | <img src="./images/team/zzangkkmin.jpg " alt="김건우" width="100"> | <img src="./images/team/zzangkkmin.jpg " alt="이기환" width="100"> |
| [zzangkkmin](https://github.com/zzangkkmin) | [invisibleufo101](https://github.com/invisibleufo101) | [duddnr0514](https://github.com/duddnr0514) | [giveluck](https://github.com/giveluck) | [sw-jooyeon](https://github.com/sw-jooyeon) | [KGW-561](https://github.com/KGW-561) | [Lee-Gi](https://github.com/Lee-Gi) |


<br>

## 구현 기능

### 국내 & 미국 뉴스 크롤링

-  

### 국내 & 미국 주식 정보 크롤링

- 한국투자증권 API를 활용한 국내 시장 지표(KOSPI, KOSDAQ), 미국 시장 지표(NASDAQ, S&P500), 원 달러 환율 정보 추출
- yFinance API를 활용한 국내/미국 주식 정보들 추출

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

## 기술 사용 스택

- Backend:   
  ![java](https://img.shields.io/badge/java-ffffff.svg?&style=for-the-badge&logo=openjdk&logoColor=black) ![spring](https://img.shields.io/badge/spring-6DB33F.svg?&style=for-the-badge&logo=spring&logoColor=white)
- DB  
  ![mysql](https://img.shields.io/badge/mysql-4479A1.svg?&style=for-the-badge&logo=mysql&logoColor=white)
- FrontEnd:  
  ![html5](https://img.shields.io/badge/html5-E34F26.svg?&style=for-the-badge&logo=html5&logoColor=white) ![css3](https://img.shields.io/badge/css3-1572B6.svg?&style=for-the-badge&logo=css3&logoColor=white) ![javascript](https://img.shields.io/badge/javascript-F7DF1E.svg?&style=for-the-badge&logo=javascript&logoColor=white)
- Crawler:  
  ![python](https://img.shields.io/badge/python-3776AB.svg?&style=for-the-badge&logo=python&logoColor=white)
- 네이버 클라우드 서비스:  
  Cloud DB for MySQL(DB), CLOVA Studio(감정분석), Papago Translation(번역)
- API:
  한국투자증권, yFinance

## 역할 분담

|  |  |  |
|-----------------|-----------------|-----------------|
| 강민    |  <img src="./images/team/zzangkkmin.jpg " alt="강민" width="100"> | <ul><li>프로젝트 계획 및 관리</li><li>팀 리딩 및 커뮤니케이션</li><li>커스텀훅 개발</li></ul>     |
| 하승우   |  <img src="./images/team/invisibleufo101.jpg " alt="하승우" width="100">| <ul><li>메인 페이지 개발</li><li>동아리 만들기 페이지 개발</li><li>커스텀훅 개발</li></ul> |
| 박범서   |  <img src="https://github.com/user-attachments/assets/78ce1062-80a0-4edb-bf6b-5efac9dd992e" alt="김나연" width="100">    |<ul><li>홈 페이지 개발</li><li>로그인 페이지 개발</li><li>동아리 찾기 페이지 개발</li><li>동아리 프로필 페이지 개발</li><li>커스텀훅 개발</li></ul>  |
| 이도훈    |  <img src="https://github.com/user-attachments/assets/beea8c64-19de-4d91-955f-ed24b813a638" alt="이승준" width="100">    | <ul><li>회원가입 페이지 개발</li><li>마이 프로필 페이지 개발</li><li>커스텀훅 개발</li></ul>    |
| 박주연    |  <img src="https://github.com/user-attachments/assets/beea8c64-19de-4d91-955f-ed24b813a638" alt="이승준" width="100">    | <ul><li>회원가입 페이지 개발</li><li>마이 프로필 페이지 개발</li><li>커스텀훅 개발</li></ul>    |
| 김건우    |  <img src="https://github.com/user-attachments/assets/beea8c64-19de-4d91-955f-ed24b813a638" alt="이승준" width="100">    | <ul><li>회원가입 페이지 개발</li><li>마이 프로필 페이지 개발</li><li>커스텀훅 개발</li></ul>    |
| 이기환    |  <img src="https://github.com/user-attachments/assets/beea8c64-19de-4d91-955f-ed24b813a638" alt="이승준" width="100">    | <ul><li>회원가입 페이지 개발</li><li>마이 프로필 페이지 개발</li><li>커스텀훅 개발</li></ul>    |

<br/>

## 이것만큼은 좋았다 & 어건 좀 아쉬웠다

<p align="justify">

</p>

<br>

## 라이센스

MIT 

