import yfinance as yf

# 주식 심볼을 사용하여 Ticker 객체 생성
# Ticker ID 예: Apple Inc의 주식 심볼 AAPL
#               삼성전자의 주식 심볼 005930.KS
#               펄어비스 주식 심볼 263750.KQ
class YfIndexApiService:
    def __init__(self):
        pass
    
    def check_market(self, ticker):
        # KOSPI 검사
        kospi_ticker = ticker + ".KS"
        kospi_info = yf.Ticker(kospi_ticker)
        if kospi_info.info is not None:
            print("KOSPI")
            return ".KS"

        # KOSDAQ 검사
        kosdaq_ticker = ticker + ".KQ"
        kosdaq_info = yf.Ticker(kosdaq_ticker)
        if kospi_info.info is not None:
            print("KOSDAQ")
            return ".KQ"

        return ""
    
    def test(self, ticker_name):
        ticker = "005930" + self.check_market(ticker_name)
        yf_ticker = yf.Ticker(ticker)  
        info = yf_ticker.info

        # 출력해보기
        current_price = info.get('currentPrice')
        close = info.get('close')
        open = info.get('open')
        volume = info.get('volume')
        fiftytwo_week_low = info.get('fiftyTwoWeekLow')
        fiftytwo_week_high = info.get('fiftyTwoWeekHigh')
        day_low = info.get('dayLow')
        day_high = info.get('dayHigh')
        return_on_assets = info.get('returnOnAssets')
        return_on_equity = info.get('returnOnEquity')
        enterprice_value = info.get('enterpriseValue')
        enterprice_to_EBITDA = info.get('enterpriseToEbitda')
        price_to_book = info.get('priceToBook')
        price_to_sales = info.get('priceToSalsesTrailing12Months')
        earnings_per_share = info.get('trailingEps')
        current_ratio = info.get('currentRatio')
        debt_to_equity = info.get('deptToEquity')

        print(f"current_price: {current_price}")
        print(f"close: {close}")
        print(f"open: {open}")
        print(f"volume: {volume}")
        print(f"fiftytwo_week_low: {fiftytwo_week_low}")
        print(f"fiftytwo_week_high: {fiftytwo_week_high}")
        print(f"day_low: {day_low}")
        print(f"day_high : {day_high}")
        print(f"return_on_assets: {return_on_assets}")
        print(f"return_on_equity: {return_on_equity}")
        print(f"enterprice_value: {enterprice_value}")
        print(f"enterprice_to_EBITDA: {enterprice_to_EBITDA}")
        print(f"price_to_book : {price_to_book}")
        print(f"return_on_assets: {return_on_assets}")
        print(f"price_to_sales: {price_to_sales}")
        print(f"earnings_per_share : {earnings_per_share}")
        print(f"current_ratio: {current_ratio}")
        print(f"debt_to_equity: {debt_to_equity}")

# Example usage:
if __name__ == "__main__":
    yfinance = YfIndexApiService()
    yfinance.test("005930")