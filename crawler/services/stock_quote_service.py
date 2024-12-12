import asyncio
from kis_api.stock_kis_client import StockKisClient
from models.ticker import Ticker
from models.kor_stock import KORStock
from models.usa_stock import USAStock
from repositories.ticker_repository import TickerRepository
from repositories.stock_repository import StockRepository
from typing import List

from time import sleep
import time

class StockQuoteService:

    def __init__(self):
        self.stock_kis_client = StockKisClient()
        self.ticker_repository = TickerRepository()
        self.stock_respository = StockRepository()

    def update_kor_stock_quotes(self) -> None:        
        kor_tickers: List[Ticker] = self.ticker_repository.fetch_kor_tickers()
        kor_stock_quotes: List[KORStock] = asyncio.run(self.stock_kis_client.get_kor_stock_quotes(kor_tickers))
        self.stock_respository.update_stock_quotes(country = "KOR", stock_quotes = kor_stock_quotes)
        
    def update_usa_stock_quotes(self) -> None:
        usa_tickers: List[Ticker] = self.ticker_repository.fetch_usa_tickers()    
        usa_stock_quotes: List[USAStock] = asyncio.run(self.stock_kis_client.get_usa_stock_quotes(usa_tickers))
        self.stock_respository.update_stock_quotes(country = "USA", stock_quotes = usa_stock_quotes)
        
        