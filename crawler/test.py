from services.forex_service import ForexService
import time

service = ForexService()
st = time.time()
service.upsert_forex()
et = time.time()

print("Execution: ", et - st)