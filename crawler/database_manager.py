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
    
    def print_error(self, function_name, error_msg) -> None:
        print('Error executing query...')
        print(f'Function : {function_name}')
        print(f'Error: {error_msg}')
    
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
            self.print_error('insert_raw_articles', sql_e)
        finally:
            cursor.close()
        
    # 텍스트 가공 로그 생성
    def insert_news_process_logs(self, link) -> None:
        # 실제 테이블용 쿼리
        # query = """
        # INSERT INTO news_process_logs(raw_news_id, crawled_at, log_msg) 
        # VALUES (%s, NOW(), %s)
        # """
        # values = (self.last_row_id, "Successfully crawled article")
        
        # 테스트 테이블용 쿼리
        query = """
        INSERT INTO test_logs(raw_news_id, crawled_at, log_msg, link) 
        VALUES (%s, NOW(), %s, %s)
        """
        # 테스트 values()
        values = (self.last_row_id, "Successfully crawled article", link)
        
        try:
            cursor = self.connection.cursor()
            cursor.execute(query, values)
            self.connection.commit()
        except Error as sql_e:
            self.print_error('insert_news_process_logs', sql_e)
        finally:
            cursor.close()
            
    def insert_translated_articles(self, item) -> None:
        query = '''
        INSERT INTO 
        '''
        
        values = (
            item['translated_title'],
            item['translated_content']
        )
        
        try:
            cursor = self.connection.cursor()
            cursor.execute(query, values)
            self.connection.commit()
        except Error as sql_e:
            self.print_error('insert_translated_articles', sql_e)
        finally:
            cursor.close()
            
    def fetch_all_companies(self) -> set:
        query = '''
        SELECT company FROM tickers
        '''
        
        try:
            cursor = self.connection.cursor()
            cursor.execute(query)
            companies = cursor.fetchall()
            return set(company[0] for company in companies)
        except Error as sql_e:
            self.print_error('fetch_all_companies', sql_e)
        finally:
            cursor.close()
    
        
    def fetch_untranslated_articles(self) -> list:
        query = '''
        SELECT 
            tn.title, tn.content
        FROM
            test_news tn
        JOIN
            test_logs tl ON tl.raw_news_id = tn.raw_news_id
        WHERE
            tl.crawled_at IS NOT NULL
            AND tl.completed_at IS NULL
            AND tn.country = 'USA'
        '''
        
        try:
            cursor = self.connection.cursor()
            cursor.execute(query)
            articles = cursor.fetchall()
            return articles
        except Error as sql_e:
            self.print_error('fetch_untranslated_articles', sql_e)
        finally:
            cursor.close()
            
    def update_translation_logs(self, status: str, articles) -> None:
        query = ''
        if status == 'success':
            query = '''
            
            '''
        elif status == 'fail':
            query = '''
            '''
        
        try:
            cursor = self.connection.cursor()
            cursor.execute(query)
        except Error as sql_e:
            self.print_error('update_translation_logs', sql_e)
        finally:
            cursor.close()
            
    def test_many(self, items):
        query = '''
        INSERT INTO test_table (col)
        VALUES (%s)
        '''
        
        values = items
        
        try:
            cursor = self.connection.cursor()
            cursor.executemany(values)
            cursor.close()
        except Error as sql_e:
            pass
        finally:
            cursor.close()

        