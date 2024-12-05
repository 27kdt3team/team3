from mysql.connector import Error
from repositories.base_repository import BaseRepository
from enums.log_msg import LogMsg


class CrawlerRepository(BaseRepository):

    def save_crawled_articles(self, item) -> None:
        if not self.connection and not self.connection.is_connected():
            raise Error("Error connecting to Database. Check if connection is initialized")

        if not item:
            self.logger.log_warning("There are no records to insert.")

        # 실제 넣을 테이블 쿼리
        # query = '''
        # INSERT INTO raw_news(country, title, image_link, source, content, published_at, link)
        # VALUES (%s, %s, %s, %s, %s, %s, %s)
        # '''

        # 테스트 테이블용 쿼리
        query = """
        INSERT INTO 
            test_news(country, title, image_link, source, content, published_at, link) 
        VALUES 
            (%s, %s, %s, %s, %s, %s, %s)
        """
        # Scrapy에서 크롤링한 기사 정보
        values = (
            item["country"],
            item["title"],
            item["image_link"],
            item["source"],
            item["content"],
            item["published_at"],
            item["link"],
        )

        try:
            cursor = self.connection.cursor()
            cursor.execute(query, values)
            self.connection.commit()
            self.last_row_id = cursor.lastrowid
        except Error as sql_e:
            self.logger.log_error("Error inserting crawled article.")
            self.logger.log_error(sql_e)
        finally:
            cursor.close()

    def create_log(self) -> None:
        if not self.connection and not self.connection.is_connected():
            raise Error("Error connecting to Database. Check if connection is initialized.")

        # 실제 테이블용 쿼리
        # query = """
        # INSERT INTO news_process_logs(raw_news_id, crawled_at, log_msg)
        # VALUES (%s, NOW(), %s)
        # """
        # values = (self.last_row_id, LogMsg.CRAWLED_SUCCESS.value)

        # 테스트 테이블용 쿼리
        query = """
        INSERT INTO 
            test_logs(raw_news_id, crawled_at, log_msg) 
        VALUES 
            (%s, NOW(), %s)
        """

        values = (self.last_row_id, LogMsg.CRAWLED_SUCCESS.value)

        try:
            cursor = self.connection.cursor()
            cursor.execute(query, values)
            self.connection.commit()
        except Error as sql_e:
            self.logger.log_error(f"Error creating News Process Log. raw_news_id #{self.last_row_id}")
            self.logger.log_error(sql_e)
        finally:
            cursor.close()
