import mysql.connector

database = mysql.connector.connect(
    host="localhost",
    user="root",
    password="\\2u8.rYDA$Rff{JK",
    database="the-challenge"
)

cursor = database.cursor()

sql = "INSERT INTO users (idusers, username, password) VALUES (%s, %s, %s)"
val = ("2", "rhowan", "password2")

cursor.execute(sql, val)

database.commit()
