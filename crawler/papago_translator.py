import os
import json
import requests
import urllib.request
from dotenv import load_dotenv
                
class PapagoTranslator:
    
    API_URL = 'https://naveropenapi.apigw.ntruss.com/nmt/v1/translation'
    
    # API에 필요한 환경 변수 설정
    def __init__(self) -> None:
        load_dotenv()
        self.client_id = os.getenv('PAPAGO_CLIENT_ID')
        self.client_secret = os.getenv('PAPAGO_CLIENT_SECRET')
        
    # 번역할 변수 설정
    def get_payload(self, text):
        return {
            'source': 'en', # 원본의 언어 - 영어
            'target': 'ko', # 번역할 언어 - 한국어
            'honorific' : 'true', # 높임말 - true
            'text': text # 번역할 텍스트
        }
    
    # API 호출에 필요한 client_id와 client_secret 설정
    def get_headers(self):
        return {
            "X-NCP-APIGW-API-KEY-ID": self.client_id,
            "X-NCP-APIGW-API-KEY": self.client_secret,
        }
        
    # 영어에서 한국어로 번역
    def translate(self, text) -> str:    
        # POST로 API 호출
        payload = self.get_payload(text)
        headers = self.get_headers()
        response = requests.post(self.API_URL, headers=headers, data=payload)
        
        # API 응답 코드 확인
        if response.status_code == 200: # 200 OK
            response_json = response.json()
            return response_json['message']['result']['translatedText']
        
        else:
            raise Exception(f"Error from Papago API: {response.status_code} - {response.text}")

        
        
        