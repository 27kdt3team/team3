from repositories.base_repository import BaseRepository
from models.article import Article
from logs.logger import Logger
from mysql.connector import Error

class SentimentAnalyzerRepository(BaseRepository):
        
    def fetch_unprocessed_data(self) -> list[Article]:
        # 실제 테이블용 쿼리
        # query = '''
        # SELECT
        #     DISTINCT ken.raw_news_id,
        #     CASE
        #         WHEN rn.country = 'KOR' THEN rn.content
        #         ELSE tn.content
        #     END AS content
        # FROM
        #     raw_news rn
        # JOIN
        #     keyword_extracted_news ken ON ken.raw_news_id = rn.raw_news_id
        # JOIN
        #     translated_news tn ON tn.raw_news_id = rn.raw_news_id
        # JOIN
        #     news_process_logs npl ON npl.raw_news_id = rn.raw_news_id
        # WHERE
        #     ken.news_type = 'stock'
        #     AND npl.sentiment_analyzed_at IS NULL
        #     AND npl.process_status != 'FAILED'
        #     AND npl.completed_at IS NULL;
        # '''
        
        query = '''
        SELECT 
            DISTINCT tken.raw_news_id, 
            CASE
                WHEN tn.country = 'KOR' THEN tn.content
                ELSE ttn.content
            END AS content
        FROM 
            test_news tn
        JOIN 
            test_keyword_extracted_news tken ON tken.raw_news_id = tn.raw_news_id
        JOIN
            test_logs tl ON tl.raw_news_id = tn.raw_news_id
        LEFT JOIN
            test_translated_news ttn ON ttn.raw_news_id = tn.raw_news_id
        WHERE
            tken.news_type = 'stock'
            AND tl.sentiment_analyzed_at IS NULL
            AND tl.process_status != 'FAILED'
            AND tl.completed_at IS NULL;
        '''
        
        try:
            rows = self.fetch_results(query = query)
        except Error as sql_e:
            self.logger.log_error('Error fetching unanalyzed(sentiment) articles.')
            self.logger.log_error(sql_e)
        
        return [Article.from_dict(row) for row in rows]        
            
    def save_processed_data(self, articles: list[Article]) -> None:
        # # 실제 테이블 쿼리문
        # query = '''
        # INSERT INTO 
        #     sentiment_analyzed_news(raw_news_id, sentiment)
        # VALUES
        #     (%s, %s)
        # '''

        # 테스트용
        query = '''
        INSERT INTO
            test_sentiment_analyzed_news(raw_news_id, sentiment)
        VALUES
            (%s, %s)
        '''
        values = [
            (
                article.raw_news_id, 
                article.sentiment
            ) 
            for article in articles
        ]
        
        try:
            self.execute_query(query = query, values = values, batch = True)
        except Error as sql_e:
            self.logger.log_error('Error inserting sentiment analyzed articles.')
            self.logger.log_error(sql_e)
        
        