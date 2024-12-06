from apscheduler.schedulers.background import BackgroundScheduler
        
def say_hello():
    print("Hello!")
    
def main():
    scheduler = BackgroundScheduler(timezone='Asia/Seoul')
    scheduler.add_job(say_hello, 'interval', seconds=10, id='test')
    scheduler.start() 
    
    # main loop
    try:
        while True:
            pass
    except (KeyboardInterrupt, SystemExit):
        scheduler.shutdown()

if __name__ == "__main__":
    main()
    
