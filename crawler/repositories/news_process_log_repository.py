from mysql.connector import Error
from models.news_process_log import NewsProcessLog
from repositories.base_repository import BaseRepository
from enums.process import Process


class NewsProcessLogRepository(BaseRepository):

    def update_logs(self, process: Process, logs: list[NewsProcessLog]) -> None:
        if not logs:
            self.logger.log_warning("There are no logs to update.")
            return

        query = None
        values = None

        process_to_column = {
            Process.TRANSLATION: "translated_at",
            Process.KEYWORD_EXTRACTION: "keyword_extracted_at",
            Process.SENTIMENT_ANALYSIS: "sentiment_analyzed_at",
            Process.COMPLETED: "completed_at",
        }

        if process not in process_to_column:
            self.logger.log_error(f"Process value not in enum class Process: {process}")
            return

        column_name = process_to_column.get(process)

        # 실제 테이블용 쿼리문
        # query = f'''
        # UPDATE
        #     news_process_logs
        # SET
        #     {column_name} = NOW(),
        #     completed_at = CASE
        #         WHEN %s = 'FAILED' THEN NOW()
        #         ELSE completed_at
        #     END,
        #     process_status = %s,
        #     log_msg = %s,
        # WHERE
        #     raw_news_id = %s
        # '''

        # 테스트용
        query = f"""
        UPDATE
            test_logs
        SET
            {column_name} = NOW(),
            completed_at = CASE
                WHEN %s = 'FAILED' THEN NOW()
                ELSE completed_at
            END,
            process_status = %s,
            log_msg = %s
        WHERE
            raw_news_id = %s
        """
        values = [
            (log.status, log.status, log.log_msg, log.raw_news_id) for log in logs
        ]

        try:
            self.execute_query(query=query, values=values, batch=True)
        except Error as sql_e:
            self.logger.log_error("Error updating news process logs.")
            self.logger.log_error(sql_e)
