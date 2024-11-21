from pykis import PyKis, KisAuth
import requests
from datetime import datetime

class KisIndexApiService:
    def __init__(self, base_url):
        self.base_url = base_url
        self.app_key = None
        self.app_secret = None
        self.access_token = None

    def get_access_token(self):
        kis = PyKis(KisAuth.load("secret.json"), keep_token=True)

        self.app_key = kis.appkey.appkey
        self.app_secret = kis.appkey.secretkey
        self.access_token = kis.token.token

        print("APPKEY: ", self.app_key)
        print("APPSECRET: ", self.app_secret)
        print("TOKEN: ", self.access_token)
        

    def revoke_access_token(self):
        if not self.access_token:
            print("No access token to revoke.")
            return

        url = f"{self.base_url}/oauth2/revokeP"
        headers = {"Content-Type": "application/json"}
        payload = {
            "appkey": self.app_key,
            "appsecret": self.app_secret,
            "token": self.access_token
        }
        
        response = requests.post(url, headers=headers, json=payload)
        if response.status_code == 200:
            response_json = response.json()
            if response_json.get("code") == "200":
                print("KIS API access token destroyed.")
        else:
            print(f"Failed to revoke access token: {response.status_code}, {response.text}")

    def get_korea_index(self, fid):
        url = f"{self.base_url}/uapi/domestic-stock/v1/quotations/inquire-index-price"
        headers = {
            "content-type": "application/json",
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

        response = requests.get(url, headers=headers, params=params)
        if response.status_code == 200:
            return response.json()
        else:
            print(f"Failed to get Korea index: {response.status_code}, {response.text}")
            return None

    def get_usa_index(self, market_code, iscd):
        url = f"{self.base_url}/uapi/overseas-price/v1/quotations/inquire-daily-chartprice"
        today_date = datetime.now().strftime("%Y%m%d")
        headers = {
            "content-type": "application/json",
            "authorization": f"Bearer {self.access_token}",
            "appkey": self.app_key,
            "appsecret": self.app_secret,
            "tr_id": "FHKST03030100"
        }
        params = {
            "FID_COND_MRKT_DIV_CODE": market_code,
            "FID_INPUT_ISCD": iscd,
            "FID_INPUT_DATE_1": today_date,
            "FID_INPUT_DATE_2": today_date,
            "FID_PERIOD_DIV_CODE": "D"
        }

        response = requests.get(url, headers=headers, params=params)
        if response.status_code == 200:
            return response.json()
        else:
            print(f"Failed to get USA index: {response.status_code}, {response.text}")
            return None
        
    def print_index_info(self, index_title, current_value, increase_value, increase_percent):
        print(index_title)
        print("Current Value : ", current_value)
        print("Change Value : ", increase_value)
        print("Change Percent : ", increase_percent)
        print()

# Example usage:
if __name__ == "__main__":
    kis_index_api_service = KisIndexApiService(base_url="https://openapi.koreainvestment.com:9443")
    kis_index_api_service.get_access_token()
    
    KOSPI = kis_index_api_service.get_korea_index("0001")
    #print("KOSPI Index:", KOSPI)
    kis_index_api_service.print_index_info("KOSPI", 
                                 KOSPI["output"]["bstp_nmix_prpr"],
                                 KOSPI["output"]["bstp_nmix_prdy_vrss"], 
                                 KOSPI["output"]["bstp_nmix_prdy_ctrt"])
    
    KOSDAQ = kis_index_api_service.get_korea_index("1001")
    #print("KOSDAQ Index:", KOSDAQ)
    kis_index_api_service.print_index_info("KOSDAQ", 
                                 KOSDAQ["output"]["bstp_nmix_prpr"],
                                 KOSDAQ["output"]["bstp_nmix_prdy_vrss"], 
                                 KOSDAQ["output"]["bstp_nmix_prdy_ctrt"])
    
    NASDAQ = kis_index_api_service.get_usa_index("N", "COMP")
    # print("NASDAQ Index:", NASDAQ)
    kis_index_api_service.print_index_info("NASDAQ", 
                                 NASDAQ["output1"]["ovrs_nmix_prpr"],
                                 NASDAQ["output1"]["ovrs_nmix_prdy_vrss"], 
                                 NASDAQ["output1"]["prdy_ctrt"])
    
    SNP500 = kis_index_api_service.get_usa_index("N", "SPX")
    # print("SNP500 Index:", SNP500)
    kis_index_api_service.print_index_info("SNP500", 
                                 SNP500["output1"]["ovrs_nmix_prpr"],
                                 SNP500["output1"]["ovrs_nmix_prdy_vrss"], 
                                 SNP500["output1"]["prdy_ctrt"])
    
    FOREX = kis_index_api_service.get_usa_index("X", "FX@KRW")
    # print("FOREX Index:", FOREX)
    kis_index_api_service.print_index_info("FOREX", 
                                 FOREX["output1"]["ovrs_nmix_prpr"],
                                 FOREX["output1"]["ovrs_nmix_prdy_vrss"], 
                                 FOREX["output1"]["prdy_ctrt"])

    #ksi_service.revoke_access_token()


