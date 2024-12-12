import multiprocessing
from apscheduler.schedulers.blocking import BlockingScheduler
from apscheduler.executors.pool import ThreadPoolExecutor
from stock_manager import StockManager
from logs.logger import Logger
import threading

class StockDataScheduler:
    def __init__(self):
        self.stock_data_lock = threading.Lock()
        executors = {
            'default': ThreadPoolExecutor(
                max_workers=multiprocessing.cpu_count()
            )
        }
        self.scheduler = BlockingScheduler(
            executors=executors,
            job_defaults={
                'max_instances': 1,  # Ensure only one instance of each job
                'misfire_grace_time': 60,  # 5-minute grace period
                'coalesce': True  # Combine missed runs
            }
        )
        
        self.logger = Logger(self.__class__.__name__)
        

    # Acquire lock for stock data-related tasks
    def safe_stock_data_task_wrapper(self, task_method):
        with self.stock_data_lock:
            try:
                task_method()
            except Exception as e:
                self.logger.log_error(f"Error in stock data task: {e}")

    def schedule_tasks(self):
        try:
            self.scheduler.add_job(
                lambda: self.safe_stock_data_task_wrapper(StockManager.update_stock_quotes), 
                'interval', 
                minutes=1,
                id='update_stock_quotes_job'
            )

            self.scheduler.add_job(
                StockManager.upsert_indices, 
                'interval', 
                minutes=1,
                id='upsert_indices_job'
            )

            self.scheduler.add_job(
                StockManager.upsert_forex, 
                'interval', 
                minutes=1,
                id='upsert_forex_job'
            )
            
            # Run quarterly (Every 3 months)
            self.scheduler.add_job(
                lambda: self.safe_stock_data_task_wrapper(StockManager.upsert_stock_info), 
                'cron',  # Use cron-style scheduling
                month='1,4,7,10',  # January, April, July, October
                day='1',  # First day of the specified months
                hour='0',  # At midnight
                id='upsert_stock_info_job'
            )

        except Exception as e:
            self.logger.log_error(f"Error scheduling tasks: {e}")

    def start(self):
        try:
            self.logger.log_info("Starting stock data scheduler")
            self.schedule_tasks()
            self.scheduler.start()
        except (KeyboardInterrupt, SystemExit):
            self.logger.log_info("Scheduler shutting down")
            self.scheduler.shutdown()
        except Exception as e:
            self.logger.log_error(f"Unexpected error in scheduler: {e}")

def main():
    stock_data_scheduler = StockDataScheduler()
    stock_data_scheduler.start()

if __name__ == "__main__":
    main()