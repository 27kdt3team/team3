from pykis import PyKis, KisAuth, KisQuote
from decimal import Decimal
import os, sys

sys.path.append(os.path.dirname(os.path.dirname(os.path.abspath(__file__))))

from database_manager import DatabaseManager

class KisStockApiService:
    def __init__(self):
        self.kis = None
        self.app_key = None
        self.app_secret = None
        self.access_token = None

    def get_access_token(self):
        self.kis = PyKis(KisAuth.load("secret.json"), keep_token=True)

        self.app_key = self.kis.appkey.appkey
        self.app_secret = self.kis.appkey.secretkey
        self.access_token = self.kis.token.token

        print("APPKEY: ", self.app_key)
        print("APPSECRET: ", self.app_secret)
        print("TOKEN: ", self.access_token)
        
    def get_korea_stock(self, ticker):
        quote: KisQuote = self.kis.stock(ticker).quote()
        
        print(quote)
        print(
            f"""
            종목코드: {quote.symbol}
            종목명: {quote.name}
            종목시장: {quote.market}

            업종명: {quote.sector_name}

            현재가: {quote.price}
            거래량: {quote.volume}
            거래대금: {quote.amount}
            시가총액: {quote.market_cap}
            대비부호: {quote.sign}
            위험도: {quote.risk}
            거래정지: {quote.halt}
            단기과열구분: {quote.overbought}

            전일종가: {quote.prev_price}
            전일거래량: {quote.prev_volume}
            전일대비: {quote.change}

            상한가: {quote.high_limit}
            하한가: {quote.low_limit}
            거래단위: {quote.unit}
            호가단위: {quote.tick}
            소수점 자리수: {quote.decimal_places}

            통화코드: {quote.currency}
            당일환율: {quote.exchange_rate}

            당일시가: {quote.open}
            당일고가: {quote.high}
            당일저가: {quote.low}

            등락율: {quote.rate}
            대비부호명: {quote.sign_name}

            ==== 종목 지표 ====

            EPS (주당순이익): {quote.indicator.eps}
            BPS (주당순자산): {quote.indicator.bps}
            PER (주가수익비율): {quote.indicator.per}
            PBR (주가순자산비율): {quote.indicator.pbr}

            52주 최고가: {quote.indicator.week52_high}
            52주 최저가: {quote.indicator.week52_low}
            52주 최고가 날짜: {quote.indicator.week52_high_date.strftime("%Y-%m-%d")}
            52주 최저가 날짜: {quote.indicator.week52_low_date.strftime("%Y-%m-%d")}
            """
        )
    
if __name__ == "__main__":
    kis_stock_service = KisStockApiService()
    kis_stock_service.get_access_token()
    kis_stock_service.get_korea_stock("030200")
        
        
