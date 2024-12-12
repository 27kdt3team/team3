import os
import json
import aiohttp
from aiolimiter import AsyncLimiter
from abc import ABC
from dotenv import load_dotenv
from typing import Dict

from logs.logger import Logger
from kis_api.kis_token_manager import KisTokenManager


class BaseKisClient(ABC):
    
    DOMAIN_URL = "https://openapi.koreainvestment.com:9443"
    
    def __init__(self):
        load_dotenv()
        self.app_key = os.getenv("KIS_APP_KEY")
        self.app_secret = os.getenv("KIS_APP_SECRET")
        self.token_manager = KisTokenManager()
        self.access_token = self.token_manager.get_token()
        self.logger = Logger(self.__class__.__name__)
        
    async def make_request(self, url: str, headers: Dict[str, str], params: Dict[str, str]) -> json:
        limiter = AsyncLimiter(max_rate=19, time_period=1)
        async with limiter:
            async with aiohttp.ClientSession() as session:
                try:
                    async with session.get(url=url, headers=headers, params=params) as response:
                        response.raise_for_status()
                        response_json = await response.json()
                        return response_json
                except aiohttp.ClientError as aio_ce:
                    self.logger.log_error("Error requesting KIS API.")
                    self.logger.log_error(f"URL: {url}")
                    self.logger.log_error(aio_ce)
    