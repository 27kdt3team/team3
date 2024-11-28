from korean_news_crawler.spider_manager import SpiderManager
from database_manager import DatabaseManager
from papago_translator import PapagoTranslator
from keyword_extractor import KeywordExtractor
from sentiment_analyzer import SentimentAnalyzer

# 작업 Controller
class ProcessManager:
    
    def __init__(self):
        self.spider_manager = SpiderManager()
        self.db = DatabaseManager()
        self.translator = PapagoTranslator()
        self.keyword_extractor = KeywordExtractor()
        self.sentiment_analyzer = SentimentAnalyzer()
        
    def crawl(self):
        self.spider_manager.run_spiders()
        
    def translate(self):
        self.translator.translate()
    
    def extract_keywords(self, articles: dict):
        self.keyword_extractor.extract()
    
    def analyze_sentiment(self):
        self.sentiment_analyzer.analyze()
    
    def update_logs(self):
        pass
    
    def run(self):
        # Crawling
        self.crawl()
        
        # Translation
        self.db.connect()
        untranslated_articles = self.db.fetch_untranslated_articles()
        self.translate()
        self.db.disconnect()
        
        # Keyword Extraction
        self.db.connect()
        articles_to_extract = self.db.fetch_unextracted_articles()
        self.db.disconnect()
        
        self.extract_keywords()
        
        
        
        # Sentiment Analysis
        self.db.connect()
        self.analyze_sentiment()
        self.db.disconnect()
        
        
        
        
            
        
        
        
    