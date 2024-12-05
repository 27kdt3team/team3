from services.base_service import BaseService
from processors.keyword_extractor import KeywordExtractor
from repositories.keyword_extractor_respository import KeywordExtractorRepository
from repositories.ticker_repository import TickerRepository
from models.news_process_log import NewsProcessLog
from models.article import Article
from logs.logger import Logger
from typing import List

class KeywordExtractionService(BaseService):
    
    def __init__(self) -> None:
        self.logger = Logger(self.__class__.__name__)
        self.repository = KeywordExtractorRepository()
        self.processor = KeywordExtractor()
        
    def process(self) -> List[NewsProcessLog]:
        self.logger.log_info('Extracting keyword from articles.')
        
        # 키워드 티커 정보를 KeywordExtractor에 주입
        ticker_repository = TickerRepository()        
        ticker_dict = ticker_repository.fetch_tickers()
        self.processor.get_tickers(ticker_dict)
        
        # 데이터베이스에서 아직 키우드 추출 안된 기사들을 가져오기
        articles: list[Article] = self.repository.fetch_unprocessed_data() # Article 객체 배열
        # 데이터베이스에서 주식 티커 정보 가져오기
        
        process_logs = []
        extracted_articles = []
        
        for article in articles:
            result = self.processor.extract(article) 
            
            # 키워드 추출은 API에 의존하지 않기 때문에 Status 여부는 모두 성공(SUCCESS)이다
            news_type = result.get('news_type')
            companies = result.get('companies')
            
            if news_type == 'stock':
                ticker_ids = [ticker_dict.get(company) for company in companies]
                extracted_articles.extend(
                    Article(
                        raw_news_id = article.raw_news_id,
                        news_type = news_type,
                        ticker_id = ticker_id
                    ) for ticker_id in ticker_ids
                )
                
            else:
                article.news_type = news_type
                extracted_articles.append(article)
                
            process_logs.append(
                NewsProcessLog(
                    raw_news_id = article.raw_news_id,
                    status = result.get('status'),
                    log_msg = result.get('log_msg')
                )
            )
        
        if extracted_articles:
            self.repository.save_processed_data(extracted_articles)
            
        return process_logs