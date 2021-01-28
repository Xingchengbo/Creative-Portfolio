# CS 4412 Project 6
# !/usr/bin/python3

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
from copy import copy, deepcopy


# Static helper methods

# Used for greedy algorithm step 2
# Returns a list of cities sorted by cost from inputted city - O(n)
def sortCityCosts(city, cities):
    cost = {}
    for i in cities:
        cost[i] = city.costTo(i)

    sorted_cities = sorted(cost.items(), key=lambda x: x[1])
    return sorted_cities


# Creates a matrix with original costs to start_cities from dest_cities
# Time - O(n^2) : Space - 0(n^2)
def createMatrix(cities):
    matrix = np.full((len(cities), len(cities)), fill_value=np.inf)
    for i in range(len(cities)):
        for j in range(len(cities)):
            if i != j:
                dist = cities[i].costTo(cities[j])
                matrix[i][j] = dist
    return matrix


# Takes a full matrix and reduces it until each row/col has a 0
# Returns a reduced matrix
# Time : O(n) - Space : O(n^2)
def reduceMatrix(matrix):
    # Iterating over rows, finding minimum values (Ignoring infinite values)
    for row in range(matrix.shape[0]):
        minimum_row = np.min(matrix[row, :])
        if minimum_row != np.inf:
            # Subtracting minimum value from the row over the entire row
            matrix[row, :] -= minimum_row

    # Iterating over cols, finding minimum values (Ignoring infinite values)
    for col in range(matrix.shape[1]):
        minimum_col = np.min(matrix[:, col])
        if minimum_col != np.inf:
            # Subtracting minimum value from the col over the entire col
            matrix[:, col] -= minimum_col

    return matrix


# Checks if a tour is impossible/plausible/possible/optimal
# Time: Worst Case O(n!) / Best Case O(n) -- Space: O(n)
# Sorts the row into a heap (smaller costs first), and then maps that list into a tuple: (cost, index)
def find_tour(reduced_matrix, row, min_value, path, length, num_cities, time_, time_allowance):
    sorted_row = list(map(lambda x: (x[1], x[0]), enumerate(list(reduced_matrix[row, :]))))
    heapq.heapify(sorted_row)

    while sorted_row and time_ < time_allowance:
        city = heapq.heappop(sorted_row)
        # If n == number of cities and the index value == 0
        if city[0] <= min_value:
            if city[1] == 0 and length == num_cities:
                return True
            # Add the city to the path, increments the length
            elif city[1] not in path:
                path.append(city[1])
                length += 1
                # If the city added doesn't form a path, deletes the city and decrements the length
                if not find_tour(reduced_matrix, city[1], min_value, path, length, num_cities, time_, time_allowance):
                    del path[-1]
                    length -= 1
                # Tour Found!
                else:
                    return True
    # No Tour Found!
    return False


class TSPSolver:
    def __init__(self, gui_view):
        self._scenario = None

    def setupWithScenario(self, scenario):
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

    def defaultRandomTour(self, time_allowance=60.0):
        results = {}
        cities = self._scenario.getCities()
        ncities = len(cities)
        foundTour = False
        count = 0
        bssf = None
        start_time = time.time()
        while not foundTour and time.time() - start_time < time_allowance:
            # create a random permutation
            perm = np.random.permutation(ncities)
            route = []
            # Now build the route using the random permutation
            for i in range(ncities):
                route.append(cities[perm[i]])
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

    # Greedy algorithm: Time - O(n^3) : Space - O(n)
    # 1.) Start at a City
    # 2.) Go to the next closest unvisited city
    # 3.) Repeat 2 until all cities have been visited
    # 4.) Go to step 1 at a different city
    # 5.) Repeat 1-4 until all possibilities have been exhausted and return the shortest path
    def greedy(self, time_allowance=60.0):
        results = {}
        cities = self._scenario.getCities()
        ncities = len(cities)
        foundTour = False
        bssf = self.defaultRandomTour(time_allowance=time_allowance)['soln']
        start_time = time.time()
        # Ends when all city paths have been checked or time runs out
        while not foundTour and time.time() - start_time < time_allowance:
            # For loop to check paths for each city
            for i in range(ncities):
                city = cities[i]
                # Initializing a route with the city chosen for this iteration
                route = [city]
                # Copying cities that need to be visited from the original list
                to_visit = copy(cities)
                # Deleting this iterations starting city
                del to_visit[i]
                # Continue until there are no more cities to visit
                while len(to_visit):
                    # Sorts the cities by their distance from this iterations chosen city
                    cities_sorted = sortCityCosts(city, to_visit)
                    # Creates indices for the unvisited cities (sorted)
                    sorted_index = to_visit.index(cities_sorted[0][0])
                    # Declares the next closest city being visited
                    closest_city = to_visit[sorted_index]
                    # Deletes the city just visited
                    del to_visit[sorted_index]
                    # Appends the newly visited city to route
                    route.append(closest_city)
                    # Changes the city being sorted at the beginning of the loop to the new city
                    city = closest_city
                # Creates Solution for previous iteration
                new_route = TSPSolution(route)
                # Checks if previous solution is better than the last iteration
                if new_route.cost < bssf.cost:
                    bssf = new_route
            # Mark foundTour to True once all possibilities have been exhausted
            foundTour = True
        end_time = time.time()
        results['cost'] = bssf.cost
        results['time'] = end_time - start_time
        results['count'] = ncities
        results['soln'] = bssf
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

    def branchAndBound(self, time_allowance=60.0):
        pass

    ''' <summary>
        This is the entry point for the algorithm you'll write for your group project.
        </summary>
        <returns>results dictionary for GUI that contains three ints: cost of best solution, 
        time spent to find best solution, total number of solutions found during search, the 
        best solution found.  You may use the other three field however you like.
        algorithm</returns> 
    '''

    # Hungarian method
    # 1.) Get reduced matrix - (Save original matrix for final costs)
    # 2.) 'Draw lines' through the rows and columns of the matrix
    # 3.) The number of 'lines drawn' should equal n, if not Find the smallest entry NOT covered by our 'lines'
    # Subtract that value for each uncovered Row and Add it to each Column
    # Repeat until the 'lines drawn' == 'number of cities'
    # 4.) Reference the original Matrix values with the marked values and discover the optimal path
    def fancy(self, time_allowance=60.0):
        results = {}
        optimal_solution = None
        min_value = 0
        path = [0]
        num_tours = 0
        cities = self._scenario.getCities()
        num_cities = len(cities)
        original_matrix = createMatrix(cities)
        reduced_matrix = reduceMatrix(original_matrix)
        bssf = self.defaultRandomTour(time_allowance=time_allowance)['soln']
        start_time = time.time()
        # Using row and column scan helper function on reduced matrix
        # Time: O(n^3)
        solution_matrix, covered_rows, covered_cols, covered_count = self.row_col_scan(reduced_matrix)

        time_ = time.time() - start_time
        # While loop Time - O(n)
        # Runs row_col_scan() Time - O(n^3)
        # Total Time: O(n^4)
        while covered_count < num_cities and time_ < time_allowance:
            # Gets the minimum value of the remaining cells in the solution matrix
            subMatrix_min = np.min(solution_matrix)
            # Time O(n^2)
            # If the row or column is not covered, subtract the minimum value of the remaining cells from rows
            for row in range(reduced_matrix.shape[0]):
                for col in range(reduced_matrix.shape[1]):
                    if row not in covered_rows:
                        reduced_matrix[row][col] -= subMatrix_min
                    if col in covered_cols:
                        reduced_matrix[row][col] += subMatrix_min

            solution_matrix, covered_rows, covered_cols, covered_count = self.row_col_scan(reduced_matrix)
            time_ = time.time() - start_time

        # Worst case Time: O(n!) -- Best O(n^2)
        # Repeats find_tour until the optimal is found
        # Else: Finds the next smallest value and updates the min_value in find_tour()
        while not optimal_solution and time_ < time_allowance:

            if find_tour(reduced_matrix, 0, min_value, path, 1, original_matrix.shape[0], time_, time_allowance):
                # Sets a solution found by find_tour()
                optimal_solution = list(map(lambda x: cities[x], path))
            else:
                lowest = np.inf
                for row in range(original_matrix.shape[0]):
                    for col in range(original_matrix.shape[1]):
                        if lowest > reduced_matrix[row][col] and reduced_matrix[row][col] > min_value:
                            lowest = reduced_matrix[row][col]
                min_value = lowest
                time_ = time.time() - start_time

        # Updates best solution and number of solutions
        if optimal_solution:
            bssf = TSPSolution(optimal_solution)
            num_tours += 1

        end_time = time.time()
        results['cost'] = bssf.cost
        results['time'] = end_time - start_time
        results['count'] = num_tours
        results['soln'] = bssf
        results['max'] = None
        results['total'] = None
        results['pruned'] = None
        return results

    # Row Scan - Column Scan
    # 'Covering' the rows and columns that contain a single zero by turning them to np.inf and storing their indices
    # Parameters: full matrix, Dynamic--Number of rows, Number of Columns
    # Returns: Tuple - The solution matrix, a list of the covered rows, a list of covered columns, length of combined covers
    # Time: O(n^3) - Space: O(n^2)
    def row_col_scan(self, matrix):
        covered_col_index = []
        covered_row_index = []
        next_matrix = deepcopy(matrix)

        while np.isin(0, next_matrix):
            # Row scan to find a single 0, if no zeros, or more than one zeros are found, go to next row
            # If a single zero is found, Save that city and its indices
            for row in range(matrix.shape[0]):
                zero_count = 0
                zero_index = 0
                for col in range(matrix.shape[1]):
                    if next_matrix[row][col] == 0:
                        zero_count += 1
                        zero_index = col
                if zero_count == 1:
                    covered_col_index.append(zero_index)
                    next_matrix[:, zero_index] = np.inf

            # Column scan to find a single 0, if no zeros, or more than one zeros are found, go to next column
            # If a single zero is found, Save that city and its indices
            for col in range(matrix.shape[1] ):
                zero_count = 0
                zero_index = 0
                for row in range(matrix.shape[0]):
                    if next_matrix[row][col] == 0:
                        zero_count += 1
                        zero_index = row

                if zero_count == 1:
                    covered_row_index.append(zero_index)
                    next_matrix[zero_index, :] = np.inf

        covered_count = len(covered_row_index) + len(covered_col_index)
        return next_matrix, covered_row_index, covered_col_index, covered_count
