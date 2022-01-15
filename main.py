import tkinter as tk
import json
import os
import mysql.connector

cnx = mysql.connector.connect(user="stan", password="zs!e3pqiFSxNSLM", host="the-challenge-database.mysql.database.azure.com", port=3306, database="the-challenge")
mycursor = cnx.cursor()

savedSet=set()
mypath="sensordata"




class Login:

    def __init__(self):
        # Window
        window = tk.Tk()
        window.title("AquaPure Login")
        icon = tk.Image("photo", file="assets/logo with text.png")
        window.iconphoto(True, icon)

        # Frames
        frm_top = tk.Frame()
        frm_top.pack()

        frm_bottom = tk.Frame()
        frm_bottom.pack()

        # Elements
        lbl_email = tk.Label(master=frm_top, text="E-mail:")
        lbl_email.pack()

        usernameentry = tk.Entry(master=frm_top)
        usernameentry.pack()

        lbl_password = tk.Label(master=frm_top, text="Wachtwoord:")
        lbl_password.pack()

        passwordentry = tk.Entry(master=frm_top, show="*")
        passwordentry.pack()

        # Submit Info Button
        def submitinfo():
            credentials = {
                "username": usernameentry.get(),
                "password": passwordentry.get()
            }

            with open("config.json", "w") as config:
                json.dump(credentials, config)

            window.destroy()
            if os.name == "nt":
                os.system('python dashboard.py')
            else:
                os.system('python3 dashboard.py')

        submitbutton = tk.Button(master=frm_top, text="Verstuur", command=submitinfo)
        submitbutton.pack()

        # Change Password Button
        button_changePassword = tk.Button(master=frm_bottom, text="Wachtwoord vergeten?")
        button_changePassword.pack()

        # Create Account Button
        button_createAccount = tk.Button(master=frm_bottom, text="Maak een account")
        button_createAccount.pack()

        window.mainloop()


class Dashboard:

    def __init__(self):
        config = json.load(open("config.json"))

        window = tk.Tk()
        window.title("AquaPure Login")
        icon = tk.Image("photo", file="assets/logo with text.png")
        window.iconphoto(True, icon)

        # Top bar
        frame_top = tk.Frame()
        lbl_title = tk.Label(master=frame_top, text="AquaPure Dashboard")
        lbl_user = tk.Label(master=frame_top, text="Welkom " + config['username'])
        frame_top.pack()
        lbl_title.grid(column=0, row=0)
        lbl_user.grid(column=2, row=0)

        # Devices frame
        frame_devices = tk.Frame()
        frame_devices.pack()

        # Devices
        device1 = Device(1, "OK")
        device2 = Device(2, "ERROR")
        device3 = Device(3, "OK")
        device4 = Device(4, "ERROR")

        # Bottom bar
        frame_bottom = tk.Frame()
        button_report = tk.Button(master=frame_bottom, text="Rapporteer foute metingen")
        frame_bottom.pack()
        button_report.pack()


class Device:

    def __init__(self, deviceid, status):
        from app.dashboard import frame_devices

        name = "Apparaat"
        temperature = 10
        ph = 7
        conductivity = 10
        turbidity = 1

        if status == "OK":
            statuscolor = "#9dff73"
        else:
            statuscolor = "#ff5757"

        frame_device = tk.Frame(master=frame_devices, bg=statuscolor)
        lbl_device = tk.Label(master=frame_device, text=name, bg=statuscolor)
        lbl_status = tk.Label(master=frame_device, text=status, bg=statuscolor)
        lbl_reading1 = tk.Label(master=frame_device, text="Temperatuur: " + str(temperature) + "°C", bg=statuscolor)
        lbl_reading2 = tk.Label(master=frame_device, text="pH-niveau: " + str(ph), bg=statuscolor)
        lbl_reading3 = tk.Label(master=frame_device, text="Geleidbaarheid: " + str(conductivity) + "ms/cm",
                                bg=statuscolor)
        lbl_reading4 = tk.Label(master=frame_device, text="Troebelheid: " + str(turbidity) + " NTU", bg=statuscolor)

        frame_device.grid(column=deviceid, row=0)
        lbl_device.grid(column=0, row=0)
        lbl_status.grid(column=1, row=0)
        lbl_reading1.grid(column=0, row=1, columnspan=2)
        lbl_reading2.grid(column=0, row=2, columnspan=2)
        lbl_reading3.grid(column=0, row=3, columnspan=2)
        lbl_reading4.grid(column=0, row=4, columnspan=2)


Login()
