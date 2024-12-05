import os
import requests
import json  
from json.decoder import JSONDecodeError
from time import sleep
from dotenv import load_dotenv

from models.article import Article
from enums.status import Status
from enums.log_msg import LogMsg
from logs.logger import Logger

class SentimentAnalyzer:
    
    def __init__(self) -> None:
        load_dotenv()
        self.host = os.getenv('CLOVA_HOST')
        self.api_key = os.getenv('CLOVA_SENTIMENT_API_KEY')
        self.api_key_primary_val = os.getenv('CLOVA_SENTIMENT_PRIMARY_VAL')
        self.request_id = os.getenv('CLOVA_SENTIMENT_REQUEST_ID')
        self.logger = Logger(self.__class__.__name__)
    
    def get_headers(self) -> dict[str, str]:
        return {
            'X-NCP-CLOVASTUDIO-API-KEY': self.api_key,
            'X-NCP-APIGW-API-KEY': self.api_key_primary_val,
            'X-NCP-CLOVASTUDIO-REQUEST-ID': self.request_id,
            'Content-Type': 'application/json; charset=utf-8',
            'Accept': 'text/event-stream'
        }
    
    def get_request_data(self, text: str) -> dict:
        
        prompt = '''
        다음은 뉴스 기사입니다. 기사의 전반적인 감정(긍정적, 부정적, 중립적)을 분석하고, 결과만 출력하세요.
        \n결과는 다음 JSON 형식으로 작성하세요:

        { "sentiment" : "positive" / "negative" / "neutral"}
        
        다음은 분석해야 할 뉴스 기사입니다:
        '''
        
        preset_text = [
            {'role': 'system', 'content' : prompt},
            {'role': 'user', 'content' : text}
        ]

        return {
            'messages': preset_text,
            'topP': 0.8,
            'topK': 0,
            'maxTokens': 150,
            'temperature': 0.03,
            'repeatPenalty': 1.2,
            'stopBefore': [],
            'includeAiFilters': False,
            'seed': 0
        }
    
    def format_output(self, result_str: str) -> str:
        try:
            response_json = json.loads(result_str)
            content_json = json.loads(response_json['message']['content'])
            sentiment = content_json['sentiment']
            return sentiment
        except JSONDecodeError as je:
            self.logger.log_error(f'Error decoding JSON: {je}')
            self.logger.log_error(f'API Result: {result_str}')
            if 'positive' in result_str:
                return 'positive'
            elif 'negative' in result_str:
                return 'negative'
            elif 'neutral' in result_str:
                return 'neutral'
                
            return None
            
    def format_error(self, error_str: str) -> list:
        response_json = json.loads(error_str)
        return [response_json['status']['code'], response_json['status']['message']]

    def analyze(self, article: Article) -> dict[str, str]:
        content = article.content
        if len(content) > 4990:
            content = content[:4990]
            
        headers = self.get_headers()
        request_data = self.get_request_data(content)
        
        sleep(1.5) # Too Many Requests 에러를 막기 위한 강제 sleep
        
        result = None
        with requests.post(self.host, headers = headers, json = request_data) as response:
            if response.status_code == 200:
                response_text = response.text.split('\n\n')
                for text in response_text:
                    if 'event:result' in text:
                        idx = text.find("data:")
                        result_str = text[idx + len("data:"):].strip()
                        sentiment = self.format_output(result_str)
                        if not sentiment:
                            failed_reason = f"Clova Studio threw unexpected result: {result_str}"
                            result = {
                                'status' : Status.FAILED.value,
                                'log_msg' : LogMsg.SENTIMENT_ANALYSIS_FAILED.format_failed_msg(article_id = article.raw_news_id, reason = failed_reason),
                                'sentiment' : sentiment
                            }
                        else:
                            result = {
                                'status' : Status.SUCCESS.value,
                                'log_msg' : LogMsg.SENTIMENT_ANALYSIS_SUCCESS.value,
                                'sentiment' : sentiment
                            }
                            
                        self.logger.log_info(json.dumps(result, indent = 2))
                        
                        return result
                    
                    elif 'event:error' in text:
                        idx = text.find('data:')
                        error_str = text[idx + len('data:'):].strip()
                        error_details = self.format_error(error_str)
                        failed_reason = 'Status: ' + error_details[0] + '\n Reason: ' + error_details[1]
                        
                        result = {
                            'status' : Status.FAILED.value,
                            'log_msg' : LogMsg.SENTIMENT_ANALYSIS_FAILED.format_failed_msg(article_id = article.raw_news_id, reason = failed_reason),
                            'sentiment' : ''
                        }
                        
                        self.logger.log_info(json.dumps(result, indent = 2))
                        
                        return result
                    
            else:
                failed_reason = "Http Status: " + str(response.status_code) + "\nMessage: " + response.text
                
                result = {
                    'status' : Status.FAILED.value,
                    'log_msg' : LogMsg.SENTIMENT_ANALYSIS_FAILED.format_failed_msg(article_id = article.raw_news_id, reason = failed_reason),
                    'sentiment' : ''
                }
                
                self.logger.log_info(json.dumps(result, indent = 2))
                
                return result
                
