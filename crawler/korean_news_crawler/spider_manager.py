from korean_news_crawler.spiders.economist_crawler import EconomistSpider
from korean_news_crawler.spiders.hankyung_crawler import HankyungSpider
from korean_news_crawler.spiders.maeil_crawler import MaeilSpider
from scrapy.crawler import CrawlerProcess

class SpiderManager:
    
    crawler_settings = {
        'ROBOTSTXT_OBEY' : False,
        'DOWNLOAD_DELAY' : 1.5, 
        'AUTOTHROTTLE_ENABLED' : True,
        'AUTOTHROTTLE_START_DELAY' : 1,  
        'AUTOTHROTTLE_MAX_DELAY' : 10,  
        'AUTOTHROTTLE_TARGET_CONCURRENCY' : 1.0,  
        'AUTOTHROTTLE_DEBUG' : True,
        'ITEM_PIPELINES': {'korean_news_crawler.pipelines.KoreanNewsCrawlerPipeline' : 300}
    }
    
    def __init__(self):
        self.process = CrawlerProcess(self.crawler_settings)
        
    def run_spiders(self):
        self.process.crawl(MaeilSpider)
        self.process.crawl(HankyungSpider)
        self.process.crawl(EconomistSpider)
        self.process.start()
        
sm = SpiderManager()
sm.run_spiders()
    
    
