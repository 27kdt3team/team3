# import re
# from html import unescape

# from datetime import datetime, timedelta

# def calc(timestamp) -> bool:
#     if isinstance(timestamp, str):
#         timestamp = datetime.fromisoformat(timestamp)
        
#     three_days_ago = datetime.now() - timedelta(3)
#     return timestamp.date() == three_days_ago.date()

# economist_str = '2024-11-23 18:14:51'
# hankyung_str = '2024.11.22 09:42'
# maeil_str = '2024-11-22 17:59:05'

# hankyung_str = hankyung_str.replace('.','-')
# hankyung_datetime = datetime.fromisoformat(hankyung_str)
# print(hankyung_datetime)

import mysql.connector
from mysql.connector import Error
from database_manager import DatabaseManager

def sentiment_articles(self,analyzer):
    
    # 크롤링 끝난 후 영어 번역할 기사들 가져오기
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
    
    query = '''
    UPDATE 
        test_logs
    SET 
        translated_at = NOW(),
        log_msg = %s,
        
    WHERE
        raw_news_id = %s
    '''
    
    
    
    #원본 쿼리문
    query = """
        select raw_news_id, content
        from vw_Korean_news
    """

        #테스트 쿼리문
    query = """
    select raw_news_id, content
    from test_news
    """
    
    try:
        cursor = self.connection.cursor(dictionary=True)
        cursor.execute(query)
        articles = cursor.fetchall()

        for article in articles:
            raw_news_id = article['raw_news_id']
            content = article['content']

            sentiment = analyzer.analyze(content)
            #원본
            # insert_query = """
            #     INSERT INTO sentiment_analyzed_news(raw_news_id,sentiment)
            #     VALUES (%s, %s)
            # """

            #테스트
            insert_query = """
                INSERT INTO test_sentiment_analyzed_news(raw_news_id,sentiment)
                VALUES (%s, %s)
            """
            values = (raw_news_id,sentiment)
            #원본
            # update_log_query = """
            # UPDATE news_process_logs
            # SET sentiment_analyzed_at = NOW(), log_msg = %s
            # WHERE raw_news_id = %s
            # """
            
            #테스트
            update_log_query = """
            UPDATE test_news_process_logs
            SET sentiment_analyzed_at = NOW(), log_msg = %s
            WHERE raw_news_id = %s
            """

            log_values = ("Sucessfully analyzed", raw_news_id)

            try:
                cursor.execute(insert_query, values)
                self.connection.commit()
                
                cursor.execute(update_log_query,log_values)
                self.connection.commit()

            except Error as sql_e:
                print('Error inserting or Updating query...')
                print('Function :insert_translate_news')
                print(f'Query: {insert_query}')
                print('Function : update_news_process_logs')
                print(f'Query: {update_log_query}')
                print(f'Error: {sql_e}')
                
    except Error as sql_e:
        print('Error selecting query...')
        print('Function : select_raw_news')
        print(f'Query: {query}')
        print(f'Error: {sql_e}')
    finally:
        cursor.close() 
            
db = DatabaseManager()
db.connect()
ret = db.fetch_all_companies()
db.disconnect()

print(ret)