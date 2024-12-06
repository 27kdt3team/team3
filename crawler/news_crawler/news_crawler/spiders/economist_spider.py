import json
import scrapy
from news_crawler.news_crawler.user_agents import get_random_user_agent

# 이코노미스트 사이트 크롤러
class EconomistSpider(scrapy.Spider):
    name = 'economist' # 이름 명시 (CLI로 크롤러를 호출할 때 필수)
    
    # 서버에 GET 요청
    def start_requests(self):
        # 10 articles per page
        # 크롤링할 페이지만큼 iterate한다
        for i in range(1, 51):
            yield scrapy.Request(f'https://economist.co.kr/article/items/ecn_SC001001000?returnType=ajax&page={i}', headers=get_random_user_agent())
            
    # 서버에서 받은 응답을 JSON으로 변환
    # 각 뉴스 목록 페이지에 있는 모든 기사를 가져오며 각 뉴스 기사의 정보를 dict 형태로 저장
    def parse(self, response):
        response_json = json.loads(response.body)
        articles = response_json['result']['items']
        
        for article in articles:
            article_dict = {}
            article_dict['country'] = 'KOR'    
            article_dict['title'] = article['title']
            
            # 이미지가 없는 뉴스 기사도 있어서 try catch로 처리
            try:
                article_dict['image_link'] = article['files'][0]['path']
            except IndexError:
                article_dict['image_link'] = ''
                
            article_dict['source'] = '이코노미스트'
            article_dict['content'] = article['content'].replace("rdquo", '"').replace("ldquo", '"')
            article_dict['published_at'] = article['firstPublishDate']
            article_dict['link'] = 'https://economist.co.kr/article/view/' + article['aid']
            
            yield article_dict