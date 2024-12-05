from mysql.connector import Error
from repositories.base_repository import BaseRepository
from models.article import Article


class KeywordExtractorRepository(BaseRepository):

    def fetch_unprocessed_data(self) -> list[Article]:
        # 실제 테이블용 쿼리
        # query = '''
        # SELECT
        #     rn.raw_news_id,
        #     CASE
        #         WHEN rn.country = 'KOR' THEN rn.title
        #         ELSE tn.title
        #     END AS title,
        #     CASE
        #         WHEN rn.country = 'KOR' THEN rn.content
        #         ELSE tn.content
        #     END AS content
        # FROM
        #     raw_news rn
        # JOIN
        #     news_process_logs npl ON npl.raw_news_id = rn.raw_news_id
        # LEFT JOIN
        #     translated_news tn ON tn.raw_news_id = rn.raw_news_id
        # WHERE
        #     npl.keyword_extracted_at IS NULL
        #     AND npl.process_status != 'FAILED'
        #     AND npl.completed_at IS NULL
        # '''

        query = """
        SELECT
            tn.raw_news_id,
            CASE
                WHEN tn.country = 'KOR' THEN tn.title
                ELSE ttn.title
            END AS title,
            CASE
                WHEN tn.country = 'KOR' THEN tn.content
                ELSE ttn.content
            END AS content
        FROM 
            test_news tn
        JOIN
            test_logs tl ON tl.raw_news_id = tn.raw_news_id
        LEFT JOIN
            test_translated_news ttn ON tn.raw_news_id = ttn.raw_news_id
        WHERE
            tl.keyword_extracted_at IS NULL
            AND tl.process_status != 'FAILED'
            AND tl.completed_at IS NULL;
        """

        try:
            rows = self.fetch_results(query=query)
            return [Article.from_dict(row) for row in rows]
        except Error as sql_e:
            self.logger.log_error("Error fetching unextracted(keyword) articles.")
            self.logger.log_error(sql_e)
            return []

    def save_processed_data(self, articles: list[Article]) -> None:
        # 실제 테이블용
        # query = '''
        # INSERT INTO
        #     keyword_extracted_news(raw_news_id, news_type, ticker_id)
        # VALUES
        #     (%s, %s, %s)
        # '''

        # 테스트용
        query = """
        INSERT INTO 
            test_keyword_extracted_news(raw_news_id, news_type, ticker_id)
        VALUES
            (%s, %s, %s)
        """
        values = [
            (article.raw_news_id, article.news_type, article.ticker_id)
            for article in articles
        ]

        try:
            self.execute_query(query=query, values=values, batch=True)
        except Error as sql_e:
            self.logger.log_error("Error inserting sentiment analyzed articles.")
            self.logger.log_error(sql_e)
