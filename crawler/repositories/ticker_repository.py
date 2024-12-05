from mysql.connector import Error
from repositories.base_repository import BaseRepository

class TickerRepository(BaseRepository):
    
    def fetch_tickers(self) -> dict[str, str]:
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
        