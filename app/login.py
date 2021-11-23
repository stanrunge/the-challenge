import tkinter as tk
import json
import os

window = tk.Tk()
window.title("AquaPure Login")
icon = tk.Image("photo", file="app/assets/logo with text.png")
window.iconphoto(True, icon)

frm_top = tk.Frame()
frm_top.pack()

frm_bottom = tk.Frame()
frm_bottom.pack()

lbl_email = tk.Label(master=frm_top, text="E-mail:")
lbl_email.pack()

usernameentry = tk.Entry(master=frm_top)
usernameentry.pack()

lbl_password = tk.Label(master=frm_top, text="Wachtwoord:")
lbl_password.pack()

passwordentry = tk.Entry(master=frm_top, show="*")
passwordentry.pack()

def submitinfo():
    credentials = {
        "username": usernameentry.get(),
        "password": passwordentry.get()
    }
    
    with open("app/config.json", "w") as config:
        json.dump(credentials, config)

    window.destroy()

submitbutton = tk.Button(master=frm_top, text="Verstuur", command=submitinfo)
submitbutton.pack()

button_changePassword = tk.Button(master=frm_bottom, text="Wachtwoord vergeten?")
button_changePassword.pack()

button_createAccount = tk.Button(master=frm_bottom, text="Maak een account")
button_createAccount.pack()

window.mainloop()
exec(open("app/dashboard.py").read())