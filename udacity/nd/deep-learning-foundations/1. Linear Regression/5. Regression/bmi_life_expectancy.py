import pandas as pd 
from sklearn.linear_model import LinearRegression

#read data
bmi_life_expectancy = pd.read_csv("bmi_life_expectancy.csv")
bmi = bmi_life_expectancy[['BMI']]
life_expectancy = bmi_life_expectancy[['Life expectancy']]

#train model on data
bmi_life_model = LinearRegression()
bmi_life_model.fit(bmi, life_expectancy)

#Make a prediction using the model
print(bmi_life_model.predict([[21.07931]]))