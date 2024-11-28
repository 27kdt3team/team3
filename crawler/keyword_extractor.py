import re
from database_manager import DatabaseManager

class KeywordExtractor:
    
    def __init__(self) -> None:
        self.db = DatabaseManager()
        self.companies = self.get_all_companies() # set
        
    def remove_noise(self, article) -> str:
        result = re.sub(r'(와|과|은|는|이|가|을|를|에|의|로|으로|도|만|까지|부터|밖에)\b', ' ', article)
        result = re.sub(r'(^[^가-힣a-zA-Z]+|[^가-힣a-zA-Z]+$)', '', result)
        result = result.replace("네이버", "NAVER")
    
        return result
    
    def get_all_companies(self) -> set:
        self.db.connect()
        companies = self.db.fetch_all_companies()
        self.db.disconnect()
        return companies
        
    def extract(self, article) -> dict:
        cleaned_article = self.remove_noise(article)
        mentioned_companies = set(cleaned_article.split(' ')).intersection(self.companies)
        
        if mentioned_companies:
            return {
                'type' : 'stock',
                'companies' : list(mentioned_companies)    
            } 
            
        return {
            'type' : 'economy',
            'companies' : []
        }
        

