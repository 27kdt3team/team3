import scrapy
from korean_news_crawler.user_agents import get_random_user_agent

class HankyungSpider(scrapy.Spider):
    name = 'hankyung' # Spider 이름 명시
    
    def start_requests(self):
        # 20 articles per page
        for i in range(1, 3):
            headers = get_random_user_agent() # 랜덤 User-Agent를 가져옴
            yield scrapy.Request(f'https://www.hankyung.com/koreamarket/news/all-news?page={i}', headers = headers)
            
    # 각 뉴스 목록 페이지에 있는 뉴스 기사 링크들을 가져온다.
    # 그 다음 가져온 링크들을 크롤링할 수 있는 parse_article 함수를 호출한다.
    def parse(self, response):
        article_urls = response.css('h2.news-tit > a::attr(href)').getall()
        for article_url in article_urls:
            headers = get_random_user_agent() # 랜덤 User-Agent를 가져옴
            yield response.follow(url = article_url, headers = headers, callback = self.parse_article)
            
    # 각 뉴스 기사 페이지를 크롤링한다
    def parse_article(self, response):
        # 뉴스 기사 정보를 dict 형태로 저장한다
        article_dict = {}
        article_dict['country'] = 'KOR'
        article_dict['title'] = response.xpath("normalize-space(//h1[@class='headline']/text())").get()
        
        img_ret = response.xpath("//div[@class='figure-img']/img/@src").get()
        article_dict['image_link'] =  img_ret if img_ret is not None else ''
        
        article_dict['source'] ='한국경제'
        article_dict['content'] = response.xpath("normalize-space(//div[@class='article-body'])").get()
        article_dict['published_at'] = response.xpath("//span[@class='txt-date']/text()").get().replace('.','-')
        article_dict['link'] = response.url
        
        yield article_dict
    



