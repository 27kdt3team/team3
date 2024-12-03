import re, os, sys
from html import unescape
import logging
from itemadapter import ItemAdapter
sys.path.append(os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__)))))

from database_manager import DatabaseManager

class KoreanNewsCrawlerPipeline:

    def open_spider(self, spider): 
        spider.db = DatabaseManager()
        spider.db.connect()
    
    def close_spider(self, spider):
        if hasattr(spider, 'db'):
            spider.db.disconnect()
    
    # 텍스트 전처리
    def sanitize(self, text):
        text = unescape(text)
        text = re.sub(r"사진=[^\s]*\s", "", text) # 사진= 으로 시작하는 텍스트 제거
        text = re.sub(r"[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}", "", text) # 이메일 제거
        text = re.sub(r"http[s]?://[^\s]+", "", text) # URL 제거
        text = re.sub(r"[^a-zA-Z가-힣0-9.,?!\s]", "", text) # 한글, 영어, 숫자, 기본 문자(.,?!)를 제외한 모든 문자 제거
        text = re.sub(r"\s+", " ", text).strip() # 긴 공백 문자 제거
        return text

    def process_item(self, item, spider):
        logging.info("Inserting item into database.")
        logging.info(f"Item: {str(item)}")
        logging.info(f"Spider: {str(spider)}")
        
        item['content'] = self.sanitize(item['content'])
        
        if hasattr(spider, 'db'):
            spider.db.insert_raw_articles(item)
            spider.db.insert_news_process_logs(item['link'])
                    
        return item
    
    
