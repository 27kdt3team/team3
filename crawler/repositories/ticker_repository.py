from mysql.connector import Error
from typing import Dict
from repositories.base_repository import BaseRepository

class TickerRepository(BaseRepository):
    
    # 주식 티커 정보를 dict안에 넣는다
    # company(key) => ticker_id(value) 
    def fetch_tickers(self) -> Dict[str, str]:
        query = '''
        SELECT 
            company, ticker_id 
        FROM 
            tickers
        '''
        
        try:
            rows = self.fetch_results(query = query)
        except Error as sql_e:
            self.logger.log_error('Error fetching tickers')    
            self.logger.log_error(sql_e)
        
        return {row['company'] : row['ticker_id'] for row in rows}
        