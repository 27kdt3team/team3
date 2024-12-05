from news_crawler.spider_manager import SpiderManager
from services.translation_service import TranslationService
from services.keyword_extraction_service import KeywordExtractionService
from services.sentiment_analysis_service import SentimentAnalysisService
from repositories.news_process_log_repository import NewsProcessLogRepository
from services.export_service import ExportService
from enums.process import Process
from logs.logger import Logger


# 작업 Controller
class ProcessManager:

    def __init__(self) -> None:
        self.logger = Logger(self.__class__.__name__)
        self.log_repo = NewsProcessLogRepository()

    def crawl(self) -> None:
        self.logger.log_info("Crawling articles from the web.")

        spider_manager = SpiderManager()
        spider_manager.run_spiders()

    def translate(self) -> None:
        translation_service = TranslationService()
        process_logs = translation_service.process()

        self.log_repo.update_logs(process=Process.TRANSLATION, logs=process_logs)

    def extract_keywords(self) -> None:
        keyword_service = KeywordExtractionService()
        process_logs = keyword_service.process()

        self.log_repo.update_logs(process=Process.KEYWORD_EXTRACTION, logs=process_logs)

    def analyze_sentiment(self) -> None:
        sentiment_service = SentimentAnalysisService()
        process_logs = sentiment_service.process()

        self.log_repo.update_logs(process=Process.SENTIMENT_ANALYSIS, logs=process_logs)

    def export(self) -> None:
        export_service = ExportService()
        process_logs = export_service.process()
        self.log_repo.update_logs(process=Process.COMPLETED, logs=process_logs)

    def run(self) -> None:
        self.crawl()
        self.translate()
        self.extract_keywords()
        self.analyze_sentiment()
        self.export()
