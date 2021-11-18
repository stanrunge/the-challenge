import tkinter as tk

window = tk.Tk()
window.title("AquaPure Login")

usernamelabel = tk.Label(text="Enter username:")
usernameentry = tk.Entry()

passwordlabel = tk.Label(text="Enter password:")
passwordentry = tk.Entry(show="*")

submitbutton = tk.Button(text="Submit")

usernamelabel.pack()
usernameentry.pack()
passwordlabel.pack()
passwordentry.pack()
submitbutton.pack()

window.mainloop()