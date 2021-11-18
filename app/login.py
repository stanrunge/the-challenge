import tkinter as tk
import json

window = tk.Tk()
window.title("AquaPure Login")

usernamelabel = tk.Label(text="Enter username:")
usernameentry = tk.Entry()

passwordlabel = tk.Label(text="Enter password:")
passwordentry = tk.Entry(show="*")

def submitinfo():
    data = {}
    data['credentials'] = []
    data["credentials"].append({
        'username': usernameentry.get(),
        'password': passwordentry.get()
    })
    
    with open("app/config.json", "w") as config:
        json.dump(data, config)

submitbutton = tk.Button(text="Submit", command=submitinfo)

usernamelabel.pack()
usernameentry.pack()
passwordlabel.pack()
passwordentry.pack()
submitbutton.pack()

window.mainloop()