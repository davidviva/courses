#!/usr/bin/python

from tkinter import *
from tkinter import messagebox
import sys 
import os

path = os.path.split(os.path.realpath(sys.argv[0]))[0]

def analyze():
	radioValue = analysis.get()
	if radioValue == "Analysis1":
		prop = parameter1.get()
		# tkMessageBox.showinfo('Message', "You chose, %s" % carrier)
		os.system("python " + path + "/Analysis1.py " + prop)
	elif radioValue == "Analysis2":
		prop = parameter1.get()
		# tkMessageBox.showinfo('Message', "You chose, %s" % radioValue % city)
		os.system("python " + path + "/Analysis2.py " + prop)
	elif radioValue == "Analysis3":
		size = parameter1.get()
		top = parameter2.get()
		# tkMessageBox.showinfo('Message', "You chose, %s" % radioValue % month % day)
		os.system("python " + path + "/Analysis3.py " + size + " " + top)
	elif radioValue == "Analysis4":
		genre = parameter1.get()
		age_group = parameter2.get()
		# tkMessageBox.showinfo('Message', "You chose, %s" % radioValue % month % carrier)
		os.system("python " + path + "/Analysis4.py " + genre + " " + age_group)
	elif radioValue == "Analysis5":
		gender = parameter1.get()
		age = parameter2.get()
		occup = parameter3.get()
		topK = parameter4.get()
		os.system("python " + path + "/Analysis5.py " + gender + " " + age + " " + occup + " " + topK)

def insert1():
	yourParameter1.delete(0,END)
	yourParameter2.delete(0,END)
	yourParameter3.delete(0,END)
	yourParameter4.delete(0,END)

def insert2():
	yourParameter1.delete(0,END)
	yourParameter2.delete(0,END)
	yourParameter3.delete(0,END)
	yourParameter4.delete(0,END)

def insert3():
	yourParameter1.delete(0,END)
	yourParameter2.delete(0,END)
	yourParameter3.delete(0,END)
	yourParameter4.delete(0,END)

def insert4():
	yourParameter1.delete(0,END)
	yourParameter2.delete(0,END)
	yourParameter3.delete(0,END)
	yourParameter4.delete(0,END)

def insert5():
	yourParameter1.delete(0,END)
	yourParameter2.delete(0,END)
	yourParameter3.delete(0,END)
	yourParameter4.delete(0,END)

app = Tk()
app.title("Movie rating information")
app.geometry('600x600+500+500')
app.configure(background='lightblue')

labelText = StringVar()
labelText.set("Please choose the information to analyze and input the parameter")
label1 = Label(app, textvariable = labelText, height = 4)
label1.configure(background='lightblue')
label1.pack()

analysis = StringVar()
analysis.set(None)
radio1 = Radiobutton(app, text = "Average ratings (Please input age, or gender, or occupation, or all)", value = "Analysis1", variable = analysis, command = insert1)
radio2 = Radiobutton(app, text = "Sample users ratio (Please input age, or gender, or occupation, or all)", value = "Analysis2", variable = analysis, command = insert2)
radio3 = Radiobutton(app, text = "Rating disagreement between different genders (Please input the threshold and topk)", value = "Analysis3", variable = analysis, command = insert3)
radio4 = Radiobutton(app, text = "The change of taste of genre (Please input genre and age_group)", value = "Analysis4", variable = analysis, command = insert4)
radio5 = Radiobutton(app, text = "Recommendations(Please input your information gender, age, occupation, topk)", value = "Analysis5", variable = analysis, command = insert5)
radio1.configure(background='lightblue')
radio2.configure(background='lightblue')
radio3.configure(background='lightblue')
radio4.configure(background='lightblue')
radio5.configure(background='lightblue')
radio1.pack()
radio2.pack()
radio3.pack()
radio4.pack()
radio5.pack()

text1 = StringVar()
text1.set("parameter 1: ")
label2 = Label(app, textvariable = text1, height = 4)
label2.configure(background='lightblue')
label2.pack()

parameter1 = StringVar(None)
yourParameter1 = Entry(app, textvariable = parameter1)
yourParameter1.pack()

text2 = StringVar()
text2.set("parameter 2: ")
label3 = Label(app, textvariable = text2, height = 4)
label3.configure(background='lightblue')
label3.pack()

parameter2 = StringVar(None)
yourParameter2 = Entry(app, textvariable = parameter2)
yourParameter2.pack()

text3 = StringVar()
text3.set("parameter 3: ")
label4 = Label(app, textvariable = text3, height = 4)
label4.configure(background='lightblue')
label4.pack()

parameter3 = StringVar(None)
yourParameter3 = Entry(app, textvariable = parameter3)
yourParameter3.pack()

text4 = StringVar()
text4.set("parameter 4: ")
label5 = Label(app, textvariable = text4, height = 4)
label5.configure(background='lightblue')
label5.pack()

parameter4 = StringVar(None)
yourParameter4 = Entry(app, textvariable = parameter4)
yourParameter4.pack()

button = Button(app, text = "Submit", width = 20, command = analyze)
button.pack(side = 'bottom', padx = 15, pady = 15)

app.mainloop()
