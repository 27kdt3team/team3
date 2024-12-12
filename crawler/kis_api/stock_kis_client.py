import asyncio
from kis_api.base_kis_client import BaseKisClient
from models.ticker import Ticker
from models.kor_stock import KORStock
from models.usa_stock import USAStock
from typing import List


class StockKisClient(BaseKisClient):
    
    async def fetch_kor_stock(self, kor_ticker: Ticker) -> KORStock:
        end_point = f"{self.DOMAIN_URL}/uapi/domestic-stock/v1/quotations/inquire-price-2"
        headers = {
            "content-type": "application/json; charset=utf-8",
            "authorization": f"Bearer {self.access_token}",
            "appkey": self.app_key,
            "appsecret": self.app_secret,
            "tr_id": "FHPST01010000",
            "custtype" : "P"
        }
        params = {
            "fid_cond_mrkt_div_code" : "J",
            "fid_input_iscd" : kor_ticker.symbol
        }
        
        response_json = await self.make_request(url = end_point, headers = headers, params = params)
        kor_stock_quote = KORStock.from_dict(
            {
                "ticker_id" : kor_ticker.ticker_id,
                "open" : response_json["output"]["stck_oprc"],
                "day_high" : response_json["output"]["stck_hgpr"],
                "day_low" : response_json["output"]["stck_lwpr"],
                "volume" : response_json["output"]["acml_vol"],
                "close" : response_json["output"]["stck_prdy_clpr"],
                "current_price" : response_json["output"]["stck_prpr"],
            }
        )
        self.logger.log_info(kor_stock_quote)
        
        return kor_stock_quote
    
    async def fetch_usa_stock(self, usa_ticker: Ticker) -> USAStock:
        end_point = f"{self.DOMAIN_URL}/uapi/overseas-price/v1/quotations/price-detail"
        headers = {
            "content-type": "application/json; charset=utf-8",
            "authorization": f"Bearer {self.access_token}",
            "appkey": self.app_key,
            "appsecret": self.app_secret,
            "tr_id": "HHDFS76200200",
        }
        params = {
            "AUTH" : "",
            "EXCD" : usa_ticker.exchange[:3],
            "SYMB" : usa_ticker.symbol
        }
        
        response_json = await self.make_request(url = end_point, headers = headers, params = params)
        usa_stock_quote = USAStock.from_dict(
            {
                "ticker_id" : usa_ticker.ticker_id,
                "open" : response_json["output"]["open"],
                "day_high" : response_json["output"]["high"],
                "day_low" : response_json["output"]["low"],
                "volume" : response_json["output"]["tvol"],
                "close" : response_json["output"]["base"],
                "current_price" : response_json["output"]["last"],
            }
        )
        
        self.logger.log_info(usa_stock_quote)
        
        return usa_stock_quote
    
    async def get_kor_stock_quotes(self, kor_tickers: List[Ticker]) -> List[KORStock]:    
        tasks = [self.fetch_kor_stock(kor_ticker) for kor_ticker in kor_tickers]
        return await asyncio.gather(*tasks)    
    
    async def get_usa_stock_quotes(self, usa_tickers: List[Ticker]) -> List[USAStock]:
        tasks = [self.fetch_usa_stock(usa_ticker) for usa_ticker in usa_tickers]
        return await asyncio.gather(*tasks)