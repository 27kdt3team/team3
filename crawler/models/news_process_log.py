from enums.status import Status
from enums.log_msg import LogMsg

class NewsProcessLog:
    
    def __init__(self, raw_news_id: int, status: str, log_msg: str):
        self.raw_news_id = raw_news_id
        self.status = status
        self.log_msg = log_msg
    
    def __repr__(self) -> str:
        return f'''
    NewsProcessLog(
        raw_news_id = {self.raw_news_id},
        status = {self.status},
        log_msg = {self.log_msg}
    )
    '''
    
    @classmethod
    def from_dict(cls, data: dict) -> 'NewsProcessLog':
        return cls(
            raw_news_id = data.get('raw_news_id'),
            status = data.get('status'),
            log_msg =data.get('log_msg')
        )