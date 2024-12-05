import os
import json
from abc import ABC
import mysql.connector
from mysql.connector import Error
from dotenv import load_dotenv

from logs.logger import Logger


class BaseRepository(ABC):

    BATCH_SIZE = 200

    def __init__(self):
        load_dotenv()
        self.database = os.getenv("MYSQL_DATABASE")
        self.host = os.getenv("MYSQL_HOST")
        self.port = os.getenv("MYSQL_PORT")
        self.user = os.getenv("MYSQL_USER")
        self.password = os.getenv("MYSQL_PASSWORD")
        self.logger = Logger(self.__class__.__name__)
        self.connection = None

    def connect(self) -> None:
        try:
            self.connection = mysql.connector.connect(
                database=self.database,
                host=self.host,
                port=self.port,
                user=self.user,
                password=self.password,
            )
            self.logger.log_info("Connected to MySQL Database.")
        except Error as sql_e:
            self.connection = None
            raise Error(f"Error connecting to MySQL Database. Reason: {sql_e}")

    def disconnect(self) -> None:
        if self.connection and self.connection.is_connected():
            self.connection.close()
            self.logger.log_info("Disconnected from MySQL Database.")
        else:
            raise Error(
                "Error disconnecting from MySQL Database. Check if connection is made."
            )

    def execute_query(self, query: str, values, batch: bool = False) -> None:
        if not values:
            self.logger.log_warning("There are no records to insert.")

        try:
            if self.connection == None or not self.connection.is_connected():
                self.connect()

            cursor = self.connection.cursor()
            if batch:
                for idx in range(0, len(values), self.BATCH_SIZE):
                    chunk = values[idx : idx + self.BATCH_SIZE]
                    cursor.executemany(query, chunk)
                    self.connection.commit()

                    self.logger.log_info("Inserting/Updating records by batch.")
            else:
                cursor.execute(query, values)
                self.connection.commit()
                self.last_row_id = cursor.lastrowid

                self.logger.log_info("Inserting/Updating record.")
        except Error as sql_e:
            raise Error(f"Error executing query. Reason: {sql_e}")
        finally:
            cursor.close()
            if self.connection and self.connection.is_connected():
                self.disconnect()

    def fetch_results(self, query: str, all: bool = True) -> list:
        try:
            self.connect()
            cursor = self.connection.cursor(dictionary=True)
            if all:
                cursor.execute(query)
                rows = cursor.fetchall()
                if not rows:
                    self.logger.log_warning("No records have been fetched.")
                else:
                    self.logger.log_info("Fetching records.")
                return rows

            else:
                row = cursor.execute(query)
                if not row:
                    self.logger.log_warning("No record has been fetched.")
                else:
                    self.logger.log_info("Fetching record.")
                    self.logger.log_info(row)
                return row
        except Error as sql_e:
            raise Error(f"Error fetching results. Reason: {sql_e}")
        finally:
            cursor.close()
            self.disconnect()
