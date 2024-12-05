# 한국 뉴스 크롤러
from news_crawler.news_crawler.spiders.economist_spider import EconomistSpider
from news_crawler.news_crawler.spiders.hankyung_spider import HankyungSpider
from news_crawler.news_crawler.spiders.maeil_spider import MaeilSpider
# 미국 뉴스 크롤러
from news_crawler.news_crawler.spiders.businessinsider_spider import BusinessInsiderSpider
from news_crawler.news_crawler.spiders.motleyfool_spider import MotelyFoolSpider
from news_crawler.news_crawler.spiders.zacks_spider import ZacksSpider

from scrapy.crawler import CrawlerProcess

class SpiderManager:
    
    crawler_settings = {
        'ROBOTSTXT_OBEY' : False, 
        'DOWNLOAD_DELAY' : 1.5, 
        'RANDOMIZE_DOWNLOAD_DELAY' : True,
        'AUTOTHROTTLE_ENABLED' : True,
        'AUTOTHROTTLE_START_DELAY' : 1,  
        'AUTOTHROTTLE_MAX_DELAY' : 8,  
        'AUTOTHROTTLE_TARGET_CONCURRENCY' : 1.0,  
        'AUTOTHROTTLE_DEBUG' : True,
        'ITEM_PIPELINES': {'news_crawler.news_crawler.pipelines.NewsCrawlerPipeline' : 300}
    }
    
    def __init__(self):
        self.process = CrawlerProcess(self.crawler_settings)
        
    def run_spiders(self):
        self.process.crawl(MaeilSpider)
        self.process.crawl(HankyungSpider)
        self.process.crawl(EconomistSpider)
        
        self.process.crawl(BusinessInsiderSpider)
        self.process.crawl(MotelyFoolSpider)
        self.process.crawl(ZacksSpider)
        
        self.process.start()