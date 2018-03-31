import numpy.random as rand

class PowerUsage:
    def __init__(self):
        self.isTurenedOn = rand.randint(0,5)
    def get_acmode_from(self,temp=25,humidity=50,isLoadHigh=False):
        if isLoadHigh:
            return "3"
        elif humidity > 50:
            return "2"
        elif temp < 18:
            return "1"
        else:
            return "0"
    def getData(self):
        return self.isTurenedOn