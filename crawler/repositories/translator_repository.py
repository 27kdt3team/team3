from mysql.connector import Error
from repositories.base_repository import BaseRepository
from models.article import Article
from logs.logger import Logger

class TranslatorRepository(BaseRepository):
     
    def fetch_unprocessed_data(self) -> list[Article]:
        # 실제 테이블용 쿼리
        # query = '''
        # SELECT
        #     rn.raw_news_id,
        #     rn.title,
        #     rn.content
        # FROM 
        #     raw_news rn
        # JOIN
        #     news_process_logs npl ON npl.raw_news_id = rn.raw_news_id
        # WHERE
        #     npl.translated_at IS NULL
        #     AND npl.completed_at IS NULL
        #     AND tl.process_status != 'FAILED'
        #     AND rn.country = 'USA'
        # '''
        
        # 테스트용
        query = '''
        SELECT 
            tn.raw_news_id, 
            tn.title, 
            tn.content
        FROM
            test_news tn
        JOIN
            test_logs tl ON tl.raw_news_id = tn.raw_news_id
        WHERE
            tl.translated_at IS NULL
            AND tl.completed_at IS NULL
            AND tl.process_status != 'FAILED'
            AND tn.country = 'USA'
        '''
        
        try:
            rows = self.fetch_results(query = query)
            return [Article.from_dict(row) for row in rows]
        except Error as sql_e:
            self.logger.log_error('Error fetching untranslated articles.')
            self.logger.log_error(sql_e)
            return []
            
    def save_processed_data(self, articles: list[Article]) -> None:
        # 실제 테이블용 쿼리
        # query = '''
        # INSERT INTO
        #     translated_news(raw_news_id, title, content)
        # VALUES
        #     (%s, %s, %s)
        # '''
        
        # 테스트용 쿼리
        query = '''
        INSERT INTO 
            test_translated_news(raw_news_id, title, content)
        VALUES 
            (%s, %s, %s)
        '''
        values = [
            (
                article.raw_news_id, 
                article.title, 
                article.content
            ) 
            for article in articles
        ]
        
        try:
            self.execute_query(query = query, values = values, batch = True)
        except Error as sql_e:
            self.logger.log_error('Error inserting translated articles.')
            self.logger.log_error(sql_e)
        
                
            
        
    
    
        
    
