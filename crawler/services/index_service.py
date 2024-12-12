from typing import List
import asyncio
from models.index import Index
from kis_api.index_kis_client import IndexKisClient
from repositories.index_repository import IndexRepository

class IndexService:
    
    def __init__(self):
        self.index_kis_client = IndexKisClient()
        self.index_repository = IndexRepository()
    
    def upsert_indices(self):
        kor_index_infos = [
            {
                "title" : "KOSPI",
                "fid" : "0001"
            },
            {
                "title" : "KOSDAQ",
                "fid" : "1001"
            }
        ]
        
        usa_index_infos = [
            {
                "title" : "NASDAQ",
                "iscd" : "COMP"
            },
            {
                "title" : "S&P500",
                "iscd" : "SPX"
            }
        ]
        
        indices = []
        
        kor_indices: List[Index] = asyncio.run(self.index_kis_client.get_kor_indices(kor_index_infos))
        usa_indices: List[Index] = asyncio.run(self.index_kis_client.get_usa_indices(usa_index_infos))
        
        indices.extend(kor_indices)
        indices.extend(usa_indices)
        
        self.index_repository.upsert_index(indices)
        
        
        
        
        