from repositories.base_repository import BaseRepository
from models.article import Article
from mysql.connector import Error
from typing import List


class ExportRepository(BaseRepository):

    # 텍스트 가공 단계를 다 거친 뉴스 기사들을 데이터베이스에서 가져온다
    def fetch_processed_data(self) -> List[Article]:
        # 실제 테이블용 쿼리
        # query = '''
        # SELECT
        #     rn.raw_news_id,
        #     rn.country,
        #     CASE
        #         WHEN rn.country = 'KOR' THEN rn.title
        #         ELSE tn.title
        #     END AS title,
        #     rn.image_link,
        #     rn.source,
        #     CASE
        #         WHEN rn.country = 'KOR' THEN rn.content
        #         ELSE tn.content
        #     END AS content,
        #     rn.published_at,
        #     rn.link,
        #     ken.news_type,
        #     ken.ticker_id,
        #     san.sentiment,
        #     tn.title as translated_title,
        #     tn.content as translated_content
        # FROM
        #     raw_news rn
        # JOIN
        #     keyword_extracted_news ken ON ken.raw_news_id = rn.raw_news_id
        # JOIN
        #     news_process_logs npl ON npl.raw_news_id = rn.raw_news_id
        # LEFT JOIN
        #     sentiment_analyzed_news san ON san.raw_news_id = rn.raw_news_id            
        # LEFT JOIN
        #     translated_news tn ON tn.raw_news_id = rn.raw_news_id
        # WHERE
        #     npl.completed_at IS NULL
        #     AND npl.process_status = 'SUCCESS';
        # '''

        query = """
        SELECT 
            tn.raw_news_id,
            tn.country,
            CASE
                WHEN tn.country = 'KOR' THEN tn.title
                ELSE ttn.title
            END AS title,
            tn.image_link,
            tn.source,
            CASE
                WHEN tn.country = 'KOR' THEN tn.content
                ELSE ttn.content
            END AS content,
            tn.published_at,
            tn.link,
            tken.news_type,
            tken.ticker_id,
            tsan.sentiment
        FROM
            test_news tn
        JOIN
            test_keyword_extracted_news tken ON tken.raw_news_id = tn.raw_news_id
        JOIN 
            test_logs tl ON tl.raw_news_id = tn.raw_news_id
        LEFT JOIN
            test_sentiment_analyzed_news tsan ON tsan.raw_news_id = tn.raw_news_id
        LEFT JOIN
            test_translated_news ttn ON ttn.raw_news_id = tn.raw_news_id
        WHERE
            tl.completed_at IS NULL
            AND tl.process_status = 'SUCCESS';
        """

        try:
            rows = self.fetch_results(query=query)
        except Error as sql_e:
            self.logger.log_error("Error fetching completed articles")
            self.logger.log_error(sql_e)
        return [Article.from_dict(row) for row in rows]

    # 경제 뉴스를 데이터베이스에 입력
    def insert_econ_news(self, country: str, econ_articles: List[Article]) -> None:
        # 나라와 테이블 매핑
        country_to_table = {
            'KOR' : 'kor_econ_news',
            'USA' : 'usa_econ_news'
        }
        
        table = country_to_table.get(country) # 해당 나라에 맞는 테이블명
        if not table: # 매핑이 안된 country일 경우 조기 반환
            self.logger.log_error(f"Not a valid country: {country}")
            return

        query = f"""
        INSERT INTO
            {table}(title, source, image_link, content, published_at, link)
        VALUES
            (%s, %s, %s, %s, %s, %s)
        """

        values = [
            (
                article.title,
                article.source,
                article.image_link,
                article.content,
                article.published_at,
                article.link,
            )
            for article in econ_articles
        ]

        try:
            self.execute_query(query=query, values=values, batch=True)
        except Error as sql_e:
            self.logger.log_error("Error inserting econ news(KOR/USA).")
            self.logger.log_error(sql_e)

    # 주식 뉴스를 데이터베이스에 입력
    def insert_stock_news(self, country: str, stock_articles: list[Article]) -> None:
        
        # 나라와 테이블 매핑
        country_to_table = {
            "KOR" : "kor_stock_news",
            "USA" : "usa_stock_news"
        }
        
        table = country_to_table.get(country) # 해당 나라에 맞는 테이블명
        if not table: # 매핑이 안된 country일 경우 조기 반환
            self.logger.log_error(f"Not a valid country: {country}")
            return
        
        query = f"""
        INSERT INTO
            {table} (title, source, image_link, content, published_at, link, ticker_id, sentiment)
        VALUES
            (%s, %s, %s, %s, %s, %s, %s, %s)
        """

        values = [
            (
                article.title,
                article.source,
                article.image_link,
                article.content,
                article.published_at,
                article.link,
                article.ticker_id,
                article.sentiment,
            )
            for article in stock_articles
        ]

        try:
            self.execute_query(query=query, values=values, batch=True)
        except Error as sql_e:
            self.logger.log_error("Error inserting stock news(KOR/USA)")
            self.logger.log_error(sql_e)
