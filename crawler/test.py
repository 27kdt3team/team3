from repositories.crawler_repository import CrawlerRepository

repo = CrawlerRepository()

latest_published_dates = repo.get_latest_published_dates_by_website()
result = {key: value for publish_date in latest_published_dates for key, value in publish_date.items()}
# print(result)

print(result.get('이코노미스트'))

print(result.get('한국경제'))

print(result.get('매일경제'))

print(result.get('Business Insider'))

print(result.get('The Motley Fool'))

print(result.get('Zack\'s'))






# list_of_dicts = [{'a': 1}, {'b': 2}, {'c': 3}]

# # Using dictionary comprehension
# result = {key: value for d in list_of_dicts for key, value in d.items()}
# print(result)

# # Using dict.update()
# result = {}
# for d in list_of_dicts:
#     result.update(d)
# print(result)