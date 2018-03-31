import numpy as np
from sklearn import linear_model
from random import randint
'''
DATASET for POWER USAGE
'''
from Dataset import x0,y0
'''
Fit Dataset into Mulivariant Linear Regression Model
'''
clf = linear_model.LinearRegression()
clf.fit(x0,y0)

def predict(t,m):
    '''
    Pridicts Power Usage wrt to time
    '''
    return clf.predict([[t,m]])

class Classifiers:
    def __init__(self):
        pass
    '''
        Classifier  for finding  ac mode from give parameters
    '''
    def get_acmode_from(self,temp=25,humidity=50,isLoadHigh=False):
        if isLoadHigh:
            return "power saver"
        elif humidity > 50:
            return "dry"
        elif temp < 18:
            return "fan"
        else:
            return "cool"
    '''
    Predicts Power Usage from time ,month given
    '''
    def predict_Power_from_date(self,time,month):
        return predict(time,month)

