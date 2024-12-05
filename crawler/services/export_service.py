from services.base_service import BaseService
from repositories.export_repository import ExportRepository
from models.news_process_log import NewsProcessLog
from models.article import Article
from logs.logger import Logger
from enums.status import Status
from enums.log_msg import LogMsg
from typing import List

class ExportService(BaseService):
    
    def __init__(self):
        self.logger = Logger(self.__class__.__name__)
        self.repository = ExportRepository()
        
    def process(self) -> List[NewsProcessLog]:
        self.logger.log_info('Exporting articles to corresponding tables.')
        
        articles: list[Article] = self.repository.fetch_processed_data()
        
        process_logs = []
        
        kor_econ_news = []
        usa_econ_news = []
        kor_stock_news = []
        usa_stock_news = []
        for article in articles:
            if article.news_type == 'economy':
                if article.country == 'KOR':
                    kor_econ_news.append(article)
                elif article.country == 'USA':
                    usa_econ_news.append(article)
                    
            elif article.news_type == 'stock':
                if article.country == 'KOR':
                    kor_stock_news.append(article)
                elif article.country == 'USA':
                    usa_stock_news.append(article)
                    
            process_logs.append(
                NewsProcessLog(
                    raw_news_id = article.raw_news_id,
                    status = Status.SUCCESS.value,
                    log_msg = LogMsg.PROCESS_COMPLETED.value
                )
            )
        
        if kor_econ_news:
            self.repository.insert_econ_news(country = 'KOR', econ_articles = kor_econ_news)
        if usa_econ_news:
            self.repository.insert_econ_news(country = 'USA', econ_articles = usa_econ_news)
        if kor_stock_news:
            self.repository.insert_stock_news(country = 'KOR', stock_articles = kor_stock_news)
        if usa_stock_news:
            self.repository.insert_stock_news(country = 'USA', stock_articles = usa_stock_news)
            
        return process_logs