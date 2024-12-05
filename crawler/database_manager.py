import logging
import mysql.connector
from mysql.connector import Error
from dotenv import load_dotenv
import os

class DatabaseManager:
    
    # 클래스 초기화. env 파일에서 환경 변수를 가져옴
    def __init__(self):
        load_dotenv()
        self.database = os.getenv('MYSQL_DATABASE')
        self.host = os.getenv('MYSQL_HOST')
        self.port = os.getenv('MYSQL_PORT')
        self.user = os.getenv('MYSQL_USER')
        self.password = os.getenv('MYSQL_PASSWORD')
        self.connection = None
        
    # 네이버 클라우드 MySQL 서버에 연결
    def connect(self) -> None:
        try:
            self.connection = mysql.connector.connect(
                database = self.database,
                host = self.host,
                port = self.port,
                user = self.user,
                password = self.password
            )
        except Error as sql_e:
            print(f'MySQL Connection Error: {sql_e}')
            self.connection = None
        
    # 네이버 클라우드 MySQL 서버에 연결 끊기
    def disconnect(self) -> None:    
        if self.connection and self.connection.is_connected():
            self.connection.close()
            print("Disconnected from MySQL Database.")
        
    # 뉴스 기사 원본을 데이터베이스에 저장    
    def insert_raw_articles(self, item) -> None:
        # 실제 넣을 테이블 쿼리
        # query = '''
        # INSERT INTO raw_news(country, title, image_link, source, content, published_at, link) 
        # VALUES (%s, %s, %s, %s, %s, %s, %s)
        # '''
        
        # 테스트 테이블용 쿼리
        query = '''
        INSERT INTO test_news(country, title, image_link, source, content, published_at, link) 
        VALUES (%s, %s, %s, %s, %s, %s, %s)
        '''
        
        # Scrapy에서 크롤링한 기사 정보
        values = (
            item['country'],
            item['title'],
            item['image_link'],
            item['source'],
            item['content'],
            item['published_at'],
            item['link']
        )
        
        try:
            cursor = self.connection.cursor()
            cursor.execute(query, values)
            self.connection.commit()
            self.last_row_id = cursor.lastrowid # 로그 생성을 위해 마지막으로 넣은 PK 저장
        except Error as sql_e:
            print('Error inserting query...')
            print('Function : insert_raw_articles')
            print(f'Query: {query}')
            print(f'Values: {str(item)}')
            print(f'Error: {sql_e}')
        finally:
            cursor.close()
        
    # 텍스트 가공 로그 생성
    def insert_news_process_logs(self, link) -> None:
        # 실제 테이블용 쿼리
        # query = """
        # INSERT INTO news_process_logs(raw_news_id, crawled_at, log_msg) 
        # VALUES (%s, NOW(), %s)
        # """
        
        # 테스트 테이블용 쿼리
        query = """
        INSERT INTO test_logs(raw_news_id, crawled_at, log_msg, link) 
        VALUES (%s, NOW(), %s, %s)
        """
        
        values = (self.last_row_id, "Successfully crawled article", link)
        
        try:
            cursor = self.connection.cursor()
            cursor.execute(query, values)
            cursor.exec
            self.connection.commit()
        except Error as sql_e:
            print('Error inserting query...')
            print('Function : insert_news_process_logs')
            print(f'Query: {query}')
            print(f'Error: {sql_e}')
            
            logging.info('Error inserting query...')
            logging.info('Function : insert_news_process_logs')
            logging.info(f'Query: {query}')
            logging.info(f'Error: {sql_e}')
        finally:
            cursor.close()
        
    # 시장 지표 데이터 삽입    
    def insert_index(self, item) -> None:
        # 실제 넣을 테이블 쿼리
        # query = '''
        # INSERT INTO indices (title, current_value, change_value, change_percent)
        # VALUES ('KOSPI', 100, -1.2, -1.2)
        # ON DUPLICATE KEY UPDATE
        #   current_value = VALUES(current_value),
        #   change_value = VALUES(change_value),
        #   change_percent = VALUES(change_percent);
        # '''
        
        # 테스트 테이블용 쿼리
        query = '''
        INSERT INTO indices (title, current_value, change_value, change_percent) 
        VALUES (%s, %s, %s, %s)
        ON DUPLICATE KEY UPDATE
            current_value = VALUES(current_value),
            change_value = VALUES(change_value),
            change_percent = VALUES(change_percent);
        '''
        
        # Scrapy에서 크롤링한 기사 정보
        values = (
            item['title'],
            item['current_value'],
            item['change_value'],
            item['change_percent']
        )
        
        try:
            cursor = self.connection.cursor()
            cursor.execute(query, values)
            self.connection.commit()
            self.last_row_id = cursor.lastrowid # 로그 생성을 위해 마지막으로 넣은 PK 저장
        except Error as sql_e:
            print('Error inserting query...')
            print('Function : insert_raw_articles')
            print(f'Query: {query}')
            print(f'Values: {str(item)}')
            print(f'Error: {sql_e}')
        finally:
            cursor.close()
            
    # 시장 지표 데이터 삽입    
    def insert_forex(self, item) -> None:
        # 실제 넣을 테이블 쿼리
        # query = '''
        # INSERT INTO forex (forex_name, rate, change_value, change_percent, last_updated) 
        # VALUES (%s, %s, %s, %s, NOW())
        # ON DUPLICATE KEY UPDATE
        #   rate = VALUES(rate),
        #   change_value = VALUES(change_value),
        #   change_percent = VALUES(change_percent);
        # '''
        
        # 테스트 테이블용 쿼리
        query = '''
        INSERT INTO forex (forex_name, rate, change_value, change_percent, last_updated) 
        VALUES (%s, %s, %s, %s, NOW())
        ON DUPLICATE KEY UPDATE
            rate = VALUES(rate),
            change_value = VALUES(change_value),
            change_percent = VALUES(change_percent),
            last_updated = VALUES(last_updated);
        '''
        
        # Scrapy에서 크롤링한 기사 정보
        values = (
            item['title'],
            item['current_value'],
            item['change_value'],
            item['change_percent']
        )
        
        try:
            cursor = self.connection.cursor()
            cursor.execute(query, values)
            self.connection.commit()
            self.last_row_id = cursor.lastrowid # 로그 생성을 위해 마지막으로 넣은 PK 저장
        except Error as sql_e:
            print('Error inserting query...')
            print('Function : insert_raw_articles')
            print(f'Query: {query}')
            print(f'Values: {str(item)}')
            print(f'Error: {sql_e}')
        finally:
            cursor.close()

        