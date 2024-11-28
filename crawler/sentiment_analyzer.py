import os
import requests
import json  
from time import sleep
from dotenv import load_dotenv

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

        { "sentiment" : "positive" / "negative" / "neutral"}
        
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
        
        sleep(5) # Too Many Requests 에러를 막기 위한 강제 sleep
        
        with requests.post(self.host, headers = headers, json = request_data, stream=True) as response:
            for line in response.iter_lines():
                    
                # 결과 값 도출
                if line and 'sentiment' in line.decode('utf-8'):
                    json_str = line.decode('utf-8')[5:]
                    parsed_data = json.loads(json_str)
                    content_str = parsed_data["message"]["content"]
                    content_data = json.loads(content_str)
                    return content_data["sentiment"]
                
                # 서버에서 에러가 날 경우 (Too Many Requests)
                elif line and 'event:error' in line.decode('utf-8'):
                    return "Code: 42901. Too many requests - rate exceeded"
                    


    
    

