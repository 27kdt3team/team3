import scrapy
from korean_news_crawler.user_agents import get_random_user_agent

class MaeilSpider(scrapy.Spider):
    name = 'maeil'
    
    # "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Mobile Safari/537.36",
    headers = {
        "Accept": "*/*",
        "Accept-Encoding": "gzip, deflate, br, zstd",
        "Accept-Language": "en-US,en;q=0.9,ko;q=0.8,fr;q=0.7",
        "Connection": "keep-alive",
        "DNT": "1",
        "Host": "www.mk.co.kr",
        "Referer": "https://www.mk.co.kr/news/business/latest/",
        "Sec-Fetch-Dest": "empty",
        "Sec-Fetch-Mode": "cors",
        "Sec-Fetch-Site": "same-origin",
        "User-Agent": get_random_user_agent()['User-Agent'],
        "X-Requested-With": "XMLHttpRequest",
        "sec-ch-ua": '"Google Chrome";v="131", "Chromium";v="131", "Not_A Brand";v="24"',
        "sec-ch-ua-mobile": "?1",
        "sec-ch-ua-platform": '"Android"',
    }
    
    cookies = {
        'PCID' : '17302645381415665605320',
        'SCOUTER' : 'z46vklbeoaffpv',
        '_sas_id.01.646b' : 'e307cdb4cb517f86.1730264538.',
        'MK_total_search_history' : '%5B%22%ED%98%84%EB%8C%80%EC%B0%A8%22%5D',
        '_sas_ses.01.646b' : '1'
    }
    
    def start_requests(self):
        # 10 articles per page
        for i in range(1, 3):
            
            # 'https://www.mk.co.kr/_CP/42?page=3&lang=null&lcode=economy&scode=latest&date=null&category=null&mediaCode=null&sort=null&userNo=null&ga_category=data-category_1depth%3D%7C%EB%89%B4%EC%8A%A4%7C%20data-category_2depth%3D%7C%EA%B2%BD%EC%A0%9C%7C%20data-category_3depth%3D%7C%ED%99%88%7C%20%20data-section%3D%7C%EC%B5%9C%EC%8B%A0%EA%B8%B0%EC%82%AC%7C'
            # 'https://www.mk.co.kr/_CP/42?page=3&lang=null&lcode=business&scode=latest&date=null&category=null&mediaCode=null&sort=null&userNo=null&ga_category=data-category_1depth%3D%7C%EB%89%B4%EC%8A%A4%7C%20data-category_2depth%3D%7C%EA%B8%B0%EC%97%85%7C%20data-category_3depth%3D%7C%EC%B5%9C%EC%8B%A0%EB%89%B4%EC%8A%A4%7C%20%20data-section%3D%7C%EC%B5%9C%EC%8B%A0%EA%B8%B0%EC%82%AC%7C'
            
            yield scrapy.Request(url = f'https://www.mk.co.kr/_CP/42?page={i}&lang=null&lcode=business&scode=latest&date=null&category=null&mediaCode=null&sort=null&userNo=null&ga_category=data-category_1depth%3D%7C%EB%89%B4%EC%8A%A4%7C%20data-category_2depth%3D%7C%EA%B8%B0%EC%97%85%7C%20data-category_3depth%3D%7C%EC%B5%9C%EC%8B%A0%EB%89%B4%EC%8A%A4%7C%20%20data-section%3D%7C%EC%B5%9C%EC%8B%A0%EA%B8%B0%EC%82%AC%7C',
                                 headers = self.headers,
                                 cookies = self.cookies
                                )
        
    def parse(self, response):
        article_urls = response.xpath("//li[@class='news_node']/a[@class='news_item']/@href").getall()
        
        for article_url in article_urls:
            random_header = get_random_user_agent()
            yield response.follow(url = article_url, headers = random_header, callback = self.parse_article)
            
    def parse_article(self, response):
        article_dict = {}
        
        article_dict['country'] = 'Korea'
        article_dict['title'] = response.xpath("normalize-space(//h2[@class='news_ttl'])").get()
        article_dict['source'] = '매일경제'
        article_dict['image_link'] = response.xpath("//div[@class='thumb']/img/@src").get()
        
        content_str = ''.join(response.xpath("//div[@itemprop='articleBody']/p/text()").getall())
        if content_str == '':
            article_dict['content'] = ''.join(response.xpath("//div[@itemprop='articleBody']/text()").getall())
        else:
            article_dict['content'] = content_str
            
        article_dict['published_at'] = response.xpath("//dl[@class='registration']/dd/text()").get()
        article_dict['link'] = response.url
        
        yield article_dict
        