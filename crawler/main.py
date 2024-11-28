from apscheduler.schedulers.background import BackgroundScheduler
        
def say_hello():
    print("Hello!")
    
def main():
    sched = BackgroundScheduler(timezone='Asia/Seoul')
    sched.add_job(say_hello, 'interval', seconds=10, id='test')
    sched.start() 
    
    # main loop
    try:
        while True:
            pass
    except (KeyboardInterrupt, SystemExit):
        sched.shutdown()

if __name__ == "__main__":
    main()
    
