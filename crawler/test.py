import re
from html import unescape

from datetime import datetime, timedelta

def calc(timestamp) -> bool:
    if isinstance(timestamp, str):
        timestamp = datetime.fromisoformat(timestamp)
        
    three_days_ago = datetime.now() - timedelta(3)
    return timestamp.date() == three_days_ago.date()

economist_str = '2024-11-23 18:14:51'
hankyung_str = '2024.11.22 09:42'
maeil_str = '2024-11-22 17:59:05'

hankyung_str = hankyung_str.replace('.','-')
hankyung_datetime = datetime.fromisoformat(hankyung_str)
print(hankyung_datetime)

