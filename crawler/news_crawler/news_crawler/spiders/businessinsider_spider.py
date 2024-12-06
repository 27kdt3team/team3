from collections.abc import Iterable
import scrapy
from scrapy import Request
from news_crawler.news_crawler.user_agents import get_random_user_agent

class BusinessInsiderSpider(scrapy.Spider):
    name = 'businessinsider'
    
    def start_requests(self) -> Iterable[Request]:
        for i in range(1, 101):
            random_header = get_random_user_agent()
            yield scrapy.Request(url = f'https://markets.businessinsider.com/news?p={i}', headers = random_header)

    def parse(self, response):
        articles = response.xpath("//div[@class='latest-news__story']")
        for article in articles:
            source = article.css('span.latest-news__source::text').get()
            if source == 'Business Insider':
                link = article.css('a.latest-news__link').attrib['href']
                random_header = get_random_user_agent()
                yield response.follow(
                    url = link,
                    headers = random_header,
                    callback = self.parse_article
                )
    
    def parse_article(self, response):
        article_dict = {}

        article_dict['country'] = 'USA'
        article_dict['title'] = response.css('h1.post-headline::text').get()
        article_dict['image_link'] = response.css('figure.figure.image-figure-image div > img').attrib['src']
        article_dict['source'] = 'Business Insider'    
        
        content_str = response.xpath("normalize-space(//div[@class='content-lock-content'])").get()
        article_dict['content'] = content_str.replace('Advertisement','')
        
        timestamp_str = response.xpath("//time/@data-timestamp").get()
        article_dict['published_at'] = self.convert_timestamp(timestamp_str)
        
        article_dict['link'] = response.url
        
        yield article_dict
    
    def convert_timestamp(self, timestamp_str: str) -> str:
        return timestamp_str.replace('T',' ').replace('Z','')
    