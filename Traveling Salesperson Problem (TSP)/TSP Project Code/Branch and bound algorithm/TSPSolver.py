#!/usr/bin/python3

from which_pyqt import PYQT_VER
if PYQT_VER == 'PYQT5':
	from PyQt5.QtCore import QLineF, QPointF
elif PYQT_VER == 'PYQT4':
	from PyQt4.QtCore import QLineF, QPointF
else:
	raise Exception('Unsupported Version of PyQt: {}'.format(PYQT_VER))




import time
import numpy as np
from TSPClasses import *
import heapq
import itertools



class TSPSolver:
	def __init__( self, gui_view ):
		self._scenario = None

	def setupWithScenario( self, scenario ):
		self._scenario = scenario


	''' <summary>
		This is the entry point for the default solver
		which just finds a valid random tour.  Note this could be used to find your
		initial BSSF.
		</summary>
		<returns>results dictionary for GUI that contains three ints: cost of solution, 
		time spent to find solution, number of permutations tried during search, the 
		solution found, and three null values for fields not used for this 
		algorithm</returns> 
	'''
	
	def defaultRandomTour( self, time_allowance=60.0 ):
		results = {}
		cities = self._scenario.getCities()
		ncities = len(cities)
		foundTour = False
		count = 0
		bssf = None
		start_time = time.time()
		while not foundTour and time.time()-start_time < time_allowance:
			# create a random permutation
			perm = np.random.permutation( ncities )
			route = []
			# Now build the route using the random permutation
			for i in range( ncities ):
				route.append( cities[ perm[i] ] )
			bssf = TSPSolution(route)
			count += 1
			if bssf.cost < np.inf:
				# Found a valid route
				foundTour = True
		end_time = time.time()
		results['cost'] = bssf.cost if foundTour else math.inf
		results['time'] = end_time - start_time
		results['count'] = count
		results['soln'] = bssf
		results['max'] = None
		results['total'] = None
		results['pruned'] = None
		return results


	''' <summary>
		This is the entry point for the greedy solver, which you must implement for 
		the group project (but it is probably a good idea to just do it for the branch-and
		bound project as a way to get your feet wet).  Note this could be used to find your
		initial BSSF.
		</summary>
		<returns>results dictionary for GUI that contains three ints: cost of best solution, 
		time spent to find best solution, total number of solutions found, the best
		solution found, and three null values for fields not used for this 
		algorithm</returns> 
	'''

	def greedy( self,time_allowance=60.0 ):
		results = {}
		cities = self._scenario.getCities()
		ncities = len(cities)
		bestSol = None
		bestSolCost = math.inf
		#loop with each city as potential starting point
		start_time = time.time()
		for n in range(ncities):
			tour = []
			tour.append(n)
			unvisited = list(range(ncities))
			current = cities[n]
			continued = True
			while unvisited and continued:
				continued = False
				minPath = math.inf
				minCity = None
				for i in unvisited:
					cost = current.costTo(cities[i])
					if cost < minPath:
						minPath = cost
						minCity = i
				if minCity is not None:
					unvisited.remove(minCity)
					tour.append(minCity)
					current = cities[minCity]
					continued = True
			if tour[0] == tour[-1] and not unvisited:
				sol = TSPSolution(self.makeCityList(tour[:-1],cities))
				solCost = sol._costOfRoute()
				if solCost < bestSolCost:
					bestSol = sol
					bestSolCost = solCost
		end_time = time.time()
		results['cost'] = bestSolCost
		results['time'] = end_time - start_time
		results['count'] = 0
		results['soln'] = bestSol
		results['max'] = None
		results['total'] = None
		results['pruned'] = None
		return results


	
	
	
	''' <summary>
		This is the entry point for the branch-and-bound algorithm that you will implement
		</summary>
		<returns>results dictionary for GUI that contains three ints: cost of best solution, 
		time spent to find best solution, total number solutions found during search (does
		not include the initial BSSF), the best solution found, and three more ints: 
		max queue size, total number of states created, and number of pruned states.</returns> 
	'''
		
	def branchAndBound( self, time_allowance=60.0 ):
		cities = self._scenario.getCities()
		ncities = len(cities)

		start_time = time.time()
		#construct matrix
		matrix = np.zeros((ncities,ncities))
		for i in range(ncities):
			for j in range(ncities):
				matrix[i,j] = cities[i].costTo(cities[j])

		bound = self.reduceMatrix(matrix)
		tour = [0]
		rowIndices = np.arange(ncities)
		colIndices = np.arange(ncities)

		queue = DepthBoundQueue()
		#queue = DepthCostQueue(cities)
		#queue = BoundQueue()
		#queue = CostQueue(cities)
		#queue = Stack()
		queue.insert(((matrix, bound, tour, (rowIndices, colIndices)),0))

		greedySol = self.greedy(time_allowance=60.0)
		bssf = []
		if greedySol['soln'] is not None:
			for city in greedySol['soln'].route:
				bssf.append(city._index)
			bssf.append(bssf[0])
		#bssfCost = math.inf
		bssfCost = greedySol['cost']

		count = 0
		pruned = 0
		total = 1
		maxQueueSize = len(queue)
		while len(queue) > 0 and time.time()-start_time < time_allowance:
			maxQueueSize = max(len(queue),maxQueueSize)
			#problem = queue.pop()
			#maxDepth = problem[1]
			problem = queue.delete()
			

			if problem[0][1] < bssfCost:
				subproblems = self.expand(problem[0])
				for subproblem in subproblems:
					total += 1
					if len(subproblem[2]) == ncities + 1:
						count += 1
						solCost = getCost(subproblem[2],cities)
						#compare with bssf
						if solCost < bssfCost:
							bssf = subproblem[2]
							bssfCost = solCost
					elif subproblem[2][-1] == 0:
						pass #must catch when tour goes back to start before visiting all.
					elif subproblem[1] < bssfCost: 
						queue.insert((subproblem,problem[1] + 1))
					else:
						pruned += 1
			else:
				pruned += 1
		end_time = time.time()

		if bssf is not None:
			bssfSol = TSPSolution(self.makeCityList(bssf[:-1],cities))
		else:
			bssfSol = None
		results = {}
		results['cost'] = bssfCost 
		results['time'] = end_time - start_time
		results['count'] = count
		results['soln'] = bssfSol
		results['max'] = maxQueueSize
		results['total'] = total
		results['pruned'] = pruned
		return results





	# subproblems are tuples with entries matrix, bound, tour, matrix index info
	# matrix is a numpy array of floating point numbers
	# bound is a normal python number
	# tours stored as list of city numbers.
	#   a complete tour will be appended with the city returned to.
	# matrix index info is so that we can drop rows and columns of the table over time
	#   a tuple of first the row indices then the column.
	#	specifically stored as a numpy array of the same length as matrix dimensions.
	#	each entry tells what node the corresponding row/column is for.
	
	####################################################################################################
	# Expands a problem (as a tuple) into its possible subproblems and returns a list of them.
	####################################################################################################
	def expand(self, problem):
		subproblems = []
		matrix, bound, tour, indices = problem
		rowIndices, colIndices = indices
		endingCity = tour[-1]

		# linear search for endingCity row
		endingCityRow = -1
		n = 0
		while endingCityRow == -1 and n < len(rowIndices):
			if rowIndices[n] == endingCity:
				endingCityRow = n
			n += 1

		# loop over each cell in the row
		i = endingCityRow
		for j in range(len(rowIndices)):
			element = matrix[endingCityRow,j]
			if element != math.inf:
				#remove row of i
				subMat = np.delete(matrix, i, 0)
				#remove column of j
				subMat = np.delete(subMat, j, 1)
				subBound = bound + self.reduceMatrix(subMat) + element
				if subBound != math.inf:
					subTour = tour.copy()
					subTour.append(colIndices[j])
					subIndices = (np.delete(rowIndices, i), np.delete(colIndices, j))
					subproblems.append((subMat, subBound, subTour, subIndices))
		return subproblems



	####################################################################################################
	# reduces the matrix given by reference, and returns the bound retrieved from this action.
	#
	# Matrices should be square numpy 2D arrays.
	#
	# If matrix has an all infinte row or column, this will return infinity.
	####################################################################################################
	#TODO exit early on infinites.
	def reduceMatrix(self, matrix):
		bound = 0
		#rows
		for i in range(matrix.shape[0]):
			#get row min
			rowMin = matrix[i,:].min()
			if (rowMin != math.inf):
				#subtract from every cell in row
				matrix[i,:] -= rowMin
			#add to bound
			bound += rowMin
		#columns
		for i in range(matrix.shape[0]):
			#get col min
			colMin = matrix[:,i].min()
			if (colMin != math.inf):
				#subtract from every cell in col
				matrix[:,i] -= colMin
			#add to bound
			bound += colMin
		return bound



	####################################################################################################
	# given a list of cities indices, create a list of the cities
	####################################################################################################
	def makeCityList(self, tour, cities):
		cityList = []
		for city in tour:
			cityList.append(cities[city])
		return cityList



	''' <summary>
		This is the entry point for the algorithm you'll write for your group project.
		</summary>
		<returns>results dictionary for GUI that contains three ints: cost of best solution, 
		time spent to find best solution, total number of solutions found during search, the 
		best solution found.  You may use the other three field however you like.
		algorithm</returns> 
	'''
		
	def fancy( self,time_allowance=60.0 ):
		pass
		


####################################################################################################
# given a partial or complete tour, find it's cost
####################################################################################################
def getCost(tour, cities):
	cost = 0
	last = cities[0]
	for city in tour[1:]:
		cost += last.costTo(cities[city])
		last = cities[city]
	return cost

class DepthBoundQueue:
	def __init__(self):
		self.list = []
	
	def insert(self, element):
		self.list.append(element)

	def delete(self):
		maxDepth = 0
		minBound = math.inf
		selected = None
		for i in range(len(self.list)):
			problem = self.list[i][0]
			depth = self.list[i][1]
			bound = problem[1]
			if depth > maxDepth:
				maxDepth = depth
				minBound = bound
				selected = i
			elif depth == maxDepth and bound < minBound: #and cost < minCost:
				minBound = bound
				selected = i
		return self.list.pop(selected)
	
	def __len__(self):
		return len(self.list)

class DepthCostQueue:
	def __init__(self,cities):
		self.list = []
		self.cities = cities
	
	def insert(self, element):
		self.list.append(element)

	def delete(self):
		maxDepth = 0
		minCost = math.inf
		selected = None
		for i in range(len(self.list)):
			problem = self.list[i][0]
			depth = self.list[i][1]
			cost = getCost(problem[2],self.cities)
			if depth > maxDepth:
				maxDepth = depth
				minCost = cost
				selected = i
			elif depth == maxDepth and cost < minCost:
				minCost = cost
				selected = i
		return self.list.pop(selected)

	def __len__(self):
		return len(self.list)

class BoundQueue:
	def __init__(self):
		self.list = []
	
	def insert(self, element):
		self.list.append(element)

	def delete(self):
		minBound = math.inf
		selected = None
		for i in range(len(self.list)):
			problem = self.list[i][0]
			bound = problem[1]
			if bound < minBound:
				minBound = bound
				selected = i
		return self.list.pop(selected)
	
	def __len__(self):
		return len(self.list)

class CostQueue:
	def __init__(self,cities):
		self.list = []
		self.cities = cities
	
	def insert(self, element):
		self.list.append(element)

	def delete(self):
		minCost = math.inf
		selected = None
		for i in range(len(self.list)):
			problem = self.list[i][0]
			cost = getCost(problem[2],self.cities)
			if cost < minCost:
				minCost = cost
				selected = i
		return self.list.pop(selected)

	def __len__(self):
		return len(self.list)


class Stack:
	def __init__(self):
		self.list = []
	
	def insert(self, element):
		self.list.append(element)

	def delete(self):
		return self.list.pop()

	def __len__(self):
		return len(self.list)
