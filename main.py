__author__ = 'sirin'
'''
Monte Carlo simulation for deployment of a WSN.
deployment area 100m by 100m square 2D region
so total area is 10000 m2 and using disk sensing model.
The program to find out the necessary sensor number
for 99% sensing coverage.
The radius = 5 m
'''
import random
import math

def sensor_location(n):
  coordinates = []
  for i in range(n):
    x = random.uniform(0,100)
    y = random.uniform(0,100)
    coordinates.append((x,y))
  return coordinates
  
def coverage_ratio(sample_count,sensor,radius):
  count = 0
  location = sensor_location(sensor)
  for i in range(sample_count):
    xi = random.uniform(0,100)
    yi = random.uniform(0,100)
    sensed = False
    for j in location:
      d = math.sqrt(math.pow((xi-j[0]),2) + math.pow((yi-j[1]),2))
      if d <= radius:
        sensed = True
        break
    if sensed == False:
      count += 1
  coverage = ((count/float(sample_count))*10000)
  return 100-(coverage/100)
  
def simulation(sample,sensor,radius):
  print "running for radius = %d" % radius
  while True:
    ratio = coverage_ratio(sample,sensor,radius)
    if ratio >= 99.0:
      print "number of required sensor: %d" % sensor
      print "coverage: %f" % ratio
      break
    else:
      sensor += 5
  return ratio
  
def main():
  for i in range(15):
    simulation(10000,10,5)
    
if __name__ == "__main__":
  main()
  
