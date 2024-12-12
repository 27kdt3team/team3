import asyncio
from typing import List, Dict
from datetime import datetime
from kis_api.base_kis_client import BaseKisClient
from models.forex import Forex

class ForexKisClient(BaseKisClient):
    
    async def _fetch_forex(self, title: str, iscd: str) -> Forex:
        end_point = f"{self.DOMAIN_URL}/uapi/overseas-price/v1/quotations/inquire-daily-chartprice"
        
        today_date = datetime.now().strftime("%Y%m%d")
        
        headers = {
            "content-type": "application/json; charset=utf-8",
            "authorization": f"Bearer {self.access_token}",
            "appkey": self.app_key,
            "appsecret": self.app_secret,
            "tr_id": "FHKST03030100"
        }
        
        params = {
            "FID_COND_MRKT_DIV_CODE": "X",
            "FID_INPUT_ISCD": iscd,
            "FID_INPUT_DATE_1": today_date,
            "FID_INPUT_DATE_2": today_date,
            "FID_PERIOD_DIV_CODE": "D"
        }

        response_json = await self.make_request(url = end_point, headers = headers, params = params)
        forex = Forex.from_dict(
            {
                "forex_name" : title,
                "rate" : response_json["output1"]["ovrs_nmix_prpr"],
                "change_value" : response_json["output1"]["ovrs_nmix_prdy_vrss"],
                "change_percent" : response_json["output1"]["prdy_ctrt"]
            }        
        ) 
        
        self.logger.log_info(forex)
        
        return forex
    
    async def get_forex(self, forex_infos: List[Dict[str, str]]) -> List[Forex]:
        tasks = [self._fetch_forex(forex_info.get("title"), forex_info.get("iscd")) for forex_info in forex_infos]
        return await asyncio.gather(*tasks)
    