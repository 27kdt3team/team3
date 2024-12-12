import asyncio
from kis_api.forex_kis_client import ForexKisClient
from repositories.forex_repository import ForexRepository


class ForexService:
    
    def __init__(self):
        self.forex_kis_client = ForexKisClient()
        self.forex_repository = ForexRepository()
        
    def upsert_forex(self):
        forex_infos = [
            {
                "title" : "KRW/USD",
                "iscd" : "FX@KRW"
            }
        ]
        
        forexes = asyncio.run(self.forex_kis_client.get_forex(forex_infos))
        
        print(forexes)
        self.forex_repository.upsert_forex(forexes)
        