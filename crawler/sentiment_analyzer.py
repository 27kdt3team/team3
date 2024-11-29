import requests
import json  
from dotenv import load_dotenv
import os

import re
import pandas as pd
from html import unescape
from time import sleep

class SentimentAnalyzer:
    
    def __init__(self):
        load_dotenv()
        self.host = os.getenv('CLOVA_HOST')
        self.api_key = os.getenv('CLOVA_SENTIMENT_API_KEY')
        self.api_key_primary_val = os.getenv('CLOVA_SENTIMENT_PRIMARY_VAL')
        self.request_id = os.getenv('CLOVA_SENTIMENT_REQUEST_ID')
    
    def get_headers(self):
        return {
            'X-NCP-CLOVASTUDIO-API-KEY': self.api_key,
            'X-NCP-APIGW-API-KEY': self.api_key_primary_val,
            'X-NCP-CLOVASTUDIO-REQUEST-ID': self.request_id,
            'Content-Type': 'application/json; charset=utf-8',
            'Accept': 'text/event-stream'
        }
    
    def get_request_data(self, article):
        
        prompt = '''
        다음은 뉴스 기사입니다. 기사의 전반적인 감정(긍정적, 부정적, 중립적)을 분석하고, 결과만 출력하세요.
        \n결과는 다음 JSON 형식으로 작성하세요:

        { 'sentiment' : 'positive' / 'negative' / 'neutral'}
        
        다음은 분석해야 할 뉴스 기사입니다:
        '''
        
        preset_text = [
            {'role': 'system', 'content' : prompt},
            {'role': 'user', 'content' : article}
        ]

        return {
            'messages': preset_text,
            'topP': 0.6,
            'topK': 0,
            'maxTokens': 700,
            'temperature': 0.1,
            'repeatPenalty': 1.2,
            'stopBefore': [],
            'includeAiFilters': False,
            'seed': 0
        }

    def analyze(self, article):
        headers = self.get_headers()
        request_data = self.get_request_data(article)

        with requests.post(self.host, headers = headers, json = request_data, stream=True) as response:
            for line in response.iter_lines():
                if line:
                    print(line.decode('utf-8'))
                    
                if line and 'sentiment' in line.decode('utf-8'):
                    line = line.decode('utf-8')[5:]
                    print(line)
                    # if 'positive' in line:
                    #     return 'positive'
                    # elif 'negative' in line:
                    #     return 'negative'
                    # else:
                    #     return 'neutral'
                    
analyzer = SentimentAnalyzer()
df = pd.read_csv('crawl_samples/maeil_sample.csv')

def sanitize(text):
    text = unescape(text)
    text = re.sub(r"사진=[^\s]*\s", "", text) # 사진= 으로 시작하는 텍스트 제거
    text = re.sub(r"[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}", "", text) # 이메일 제거
    text = re.sub(r"http[s]?://[^\s]+", "", text) # URL 제거
    text = re.sub(r"[^a-zA-Z가-힣0-9.,?!\s]", "", text) # 한글, 영어, 숫자, 기본 문자(.,?!)를 제외한 모든 문자 제거
    text = re.sub(r"\s+", " ", text).strip() # 긴 공백 문자 제거
    return text

results = []
for article in df['content']:
    sleep(10)
    
    print("Article: ")
    print(article)
    
    sentiment = analyzer.analyze(sanitize(article))
    results.append(sentiment)
    
    print("Sentiment: ")
    print(sentiment)

# Add the sentiment results as a new column to the DataFrame
df['sentiment'] = results

# Save the updated DataFrame to a new CSV file (optional)
df.to_csv('crawl_samples/maeil_sample_with_sentiment.csv', index=False)

    
    

