#19120484_Tram Huu Duc
import numpy as np
from cmath import sqrt


def BFS(matrix, start, end):
    """
    BFS algorithm:
    Parameters:
    ---------------------------
    matrix: np array 
        The graph's adjacency matrix
    start: integer
        starting node
    end: integer
        ending node
    
    Returns
    ---------------------
    visited
        The dictionary contains visited nodes, each key is a visited node,
        each value is the adjacent node visited before it.
    path: list
        Founded path
    """
    # TODO: 
    path = []
    visited = {}
    visited[start] = None

    b = [False] * (len(matrix))
    queue = []

    current_node = start
    b[start] = True
    queue.append(start)

    while len(queue) > 0:
        current_node = queue[0]
        queue.remove(current_node)
        # print(current_node)

        if current_node == end:
            path.append(current_node)
            while current_node != start:
                current_node = visited[current_node]
                path.append(current_node)

            # print(visited)
            # print(path)
            path.reverse()
            return visited, path

        for i in range(len(matrix)):
            if matrix[current_node][i] != 0:
                if b[i] == False:
                    b[i] = True
                    queue.append(i)
                    visited[i] = current_node
                visited[i] = current_node
        
    return {}, []


def DFS(matrix, start, end):
    """
    DFS algorithm
     Parameters:
    ---------------------------
    matrix: np array 
        The graph's adjacency matrix
    start: integer 
        starting node
    end: integer
        ending node
    
    Returns
    ---------------------
    visited 
        The dictionary contains visited nodes: each key is a visited node, 
        each value is the key's adjacent node which is visited before key.
    path: list
        Founded path
    """

    # TODO:     
    path = []
    visited = {}
    visited[start] = None

    stack = []
    b = [False] * (len(matrix))
    b[start] = True
    stack.append(start)
    current_node = start

    while len(stack) != 0:
        current_node = stack.pop()
        # print(current_node)

        if current_node == end:
            path.append(current_node)

            while current_node != start:
                current_node = visited[current_node]
                path.append(current_node)

            
            path.reverse()
            for i in stack:
                visited.pop(i)     
            print(visited)   
            return visited, path

        for i in range(len(matrix)):
            j = len(matrix) - i - 1
            if(b[j] == False and matrix[current_node][j] != 0):
                b[j] = True
                stack.append(j)
                visited[j] = current_node

    return {}, []

def UCS(matrix, start, end):
    """
    Uniform Cost Search algorithm
     Parameters:visited
    ---------------------------
    matrix: np array 
        The graph's adjacency matrix
    start: integer 
        starting node
    end: integer
        ending node
    
    Returns
    ---------------------
    visited
        The dictionary contains visited nodes: each key is a visited node, 
        each value is the key's adjacent node which is visited before key.
    path: list
        Founded path
    """
    # TODO:  
    path = []
    visited = {}
    visited[start] = None

    b = [False] * (len(matrix))
    queue = {}
    queue[start] = 0

    b[start] = True

    while len(queue) > 0:
        current_node = min(queue, key = queue.get)# replace for priority queue

        if current_node == end:
            path.append(current_node)

            while current_node != start:
                current_node = visited[current_node]
                path.append(current_node)

            
            path.reverse()
            print(visited)   
            print(path)
            return visited, path
        
        k = queue[current_node]
        queue.pop(current_node)


        for i in range(len(matrix)):
            val = k + matrix[current_node][i]
            if matrix[current_node][i] != 0:
                if i in queue and queue[i] > val:
                    queue[i] = val
                    visited[i] = current_node
                    continue

                if b[i] == False:
                    queue[i] = val
                    visited[i] = current_node
                    b[i] = True
        

        
    return {}, []


def GBFS(matrix, start, end):
    """
    Greedy Best First Search algorithm
     Parameters:
    ---------------------------
    matrix: np array 
        The graph's adjacency matrix
    start: integer 
        starting node
    end: integer
        ending node
   
    Returns
    ---------------------
    visited
        The dictionary contains visited nodes: each key is a visited node, 
        each value is the key's adjacent node which is visited before key.
    path: list
        Founded path
    """
    # TODO: 
    path = []
    visited = {}

    b = [False] * (len(matrix))
    queue = {}
    queue[start] = 0
    visited[start] = None

    b[start] = True

    while len(queue) > 0:
        current_node = min(queue, key = queue.get)# replace for priority queue
        queue.pop(current_node)

        if current_node == end:
            path.append(current_node)

            while current_node != start:
                current_node = visited[current_node]
                path.append(current_node)

            
            path.reverse()
            print(visited)   
            print(path)
            return visited, path # RETURN RESULT

        for i in range(len(matrix)):
            val = matrix[current_node][i]
            if matrix[current_node][i] != 0 and b[i] == False:
                queue[i] = val
                visited[i] = current_node
                b[i] = True

    return {}, [] # RETURN EMPTY PATH IF CANNOT FIND THE PATH

def Astar(matrix, start, end, pos):
    """
    A* Search algorithm
     Parameters:
    ---------------------------
    matrix: np array UCS
        The graph's adjacency matrix
    start: integer 
        starting node
    end: integer
        ending node
    pos: dictionary. keys are nodes, values are positions
        positions of graph nodes
    Returns
    ---------------------
    visited
        The dictionary contains visited nodes: each key is a visited node, 
        each value is the key's adjacent node which is visited before key.
    path: list
        Founded path
    """
    # TODO: 
    path=[]
    visited={}

    queue = {}
    g = {}
    queue[start] = 0
    g[start] = 0


    b = [False]*len(matrix)    
    visited[start] = None
    b[start] = True

    while len(queue) > 0:
        q = min(queue, key = queue.get)
        fq = queue[q]
        queue.pop(q)
        print(q)

        if q == end:
            path.append(q)

            while q != start:
                q = visited[q]
                path.append(q)
            
            path.reverse()
            print(visited)   
            print(path)
            return visited, path # RETURN RESULT

        for i in range(len(matrix)):
            if matrix[q][i] != 0 and b[i] == False:
                visited[i] = q
                g[i] = g[q] + matrix[q][i]
                h =  matrix[q][i] + sqrt((pos[end][0] - pos[i][0])**2 + (pos[end][1] - pos[i][1])**2)
                queue[i] = g[i] + h
                b[i] = True

    return {}, []



