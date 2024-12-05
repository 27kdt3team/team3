from mysql.connector import Error
from repositories.base_repository import BaseRepository
from enums.log_msg import LogMsg
from typing import List

class CrawlerRepository(BaseRepository):

    def save_crawled_articles(self, item: List) -> None:
        # 데이터베이스 연결이 안된 경우
        if not self.connection and not self.connection.is_connected(): 
            raise Error("Error connecting to Database. Check if connection is initialized")

        # 데이터베이스에 입력할 기사가 없을 경우
        if not item: 
            self.logger.log_warning("There are no records to insert.")

        query = """
        INSERT INTO 
            raw_news(country, title, image_link, source, content, published_at, link) 
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
            self.last_row_id = cursor.lastrowid # 가공 로그 생성을 위해 PK값은 클래스 변수로 저장
        except Error as sql_e:
            self.logger.log_error("Error inserting crawled article.")
            self.logger.log_error(sql_e)
        finally:
            cursor.close()

    def create_log(self) -> None:
        # 데이터베이스에 연결이 안된 경우
        if not self.connection and not self.connection.is_connected():
            raise Error("Error connecting to Database. Check if connection is initialized.")

        query = """
        INSERT INTO 
            news_process_logs(raw_news_id, crawled_at, log_msg) 
        VALUES 
            (%s, NOW(), %s)
        """

        # 뉴스 기사 원본을 삽입하고 받은 PK값을 넣는다 (self.last_row_id)
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
