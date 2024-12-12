from services.stock_service import StockService
from services.stock_quote_service import StockQuoteService
from services.index_service import IndexService
from services.forex_service import ForexService
from logs.logger import Logger

class StockManager:
    
    @staticmethod    
    def upsert_stock_info():
        stock_service = StockService()    
        stock_service.get_kor_stock_info()
        stock_service.get_usa_stock_info()
    
    @staticmethod
    def update_stock_quotes():
        stock_quote_service = StockQuoteService()
        stock_quote_service.update_kor_stock_quotes()
        stock_quote_service.update_usa_stock_quotes()
        
    @staticmethod
    def upsert_indices():
        index_service = IndexService()    
        index_service.upsert_indices()
        
    @staticmethod
    def upsert_forex():
        forex_service = ForexService()
        forex_service.upsert_forex()
    
    