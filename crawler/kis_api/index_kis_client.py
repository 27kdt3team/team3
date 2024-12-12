import asyncio
from datetime import datetime
from typing import List, Dict

from kis_api.base_kis_client import BaseKisClient
from models.index import Index


class IndexKisClient(BaseKisClient):
    
    async def _fetch_kor_index(self, title: str, fid: str) -> Index:
        end_point = f"{self.DOMAIN_URL}/uapi/domestic-stock/v1/quotations/inquire-index-price"
        headers = {
            "content-type": "application/json; charset=utf-8",
            "authorization": f"Bearer {self.access_token}",
            "appkey": self.app_key,
            "appsecret": self.app_secret,
            "tr_id": "FHPUP02100000",
            "custtype": "P"
        }
        
        params = {
            "FID_COND_MRKT_DIV_CODE": "U",
            "FID_INPUT_ISCD": fid
        }

        response_json = await self.make_request(url = end_point, headers = headers, params = params)
        index = Index.from_dict(
            {
                "title" : title,
                "current_value" : response_json["output"]["bstp_nmix_prpr"],
                "change_value" : response_json["output"]["bstp_nmix_prdy_vrss"],
                "change_percent" : response_json["output"]["bstp_nmix_prdy_ctrt"]
            }            
        )
        
        self.logger.log_info(index)
        
        return index
        
    async def _fetch_usa_index(self, title: str, iscd: str) -> Index:
        end_point = f"{self.DOMAIN_URL}/uapi/overseas-price/v1/quotations/inquire-daily-chartprice"
        
        today_date = datetime.now().strftime("%Y%m%d")
        
        headers = {
            "content-type": "application/json",
            "authorization": f"Bearer {self.access_token}",
            "appkey": self.app_key,
            "appsecret": self.app_secret,
            "tr_id": "FHKST03030100"
        }
        
        params = {
            "FID_COND_MRKT_DIV_CODE": "N",
            "FID_INPUT_ISCD": iscd,
            "FID_INPUT_DATE_1": today_date,
            "FID_INPUT_DATE_2": today_date,
            "FID_PERIOD_DIV_CODE": "D"
        }

        response_json = await self.make_request(url = end_point, headers = headers, params = params)
        index = Index.from_dict(
            {
                "title" : title,
                "current_value" : response_json["output1"]["ovrs_nmix_prpr"],
                "change_value" : response_json["output1"]["ovrs_nmix_prdy_vrss"],
                "change_percent" : response_json["output1"]["prdy_ctrt"]
            }            
        ) 
        
        self.logger.log_info(index)
        
        return index
        
    async def get_kor_indices(self, indices: List[Dict[str, str]]) -> List[Index]:
        tasks = [self._fetch_kor_index(index.get("title"), index.get("fid")) for index in indices]
        return await asyncio.gather(*tasks)
    
    async def get_usa_indices(self, indices: List[Dict[str, str]]) -> List[Index]:
        tasks = [self._fetch_usa_index(index.get("title"), index.get("iscd")) for index in indices]
        return await asyncio.gather(*tasks)
            
    
            
        