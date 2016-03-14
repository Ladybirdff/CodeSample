# numberTheory
# cit 590 HW2
# Weixi Ma
# Study Group: Weixi Ma, Kexin Huang, Man Hua, Jiaqi Luo

def numfactor(x):
    '''calculate the number of factors of the given number x'''
    counter = 0
    y = 1
    while x >= y:
        if x%y == 0:
            counter = counter + 1
        y = y + 1
    return counter

def sumfactor(x):
    '''calculate the sum of all factors(except x itself) of the given number x'''
    sumfactor = 0
    y = 1
    while x > y:
        if x%y == 0:
            sumfactor = sumfactor + y
        y = y + 1
    return sumfactor
        

def isPrime(x):
    '''returns whether or not the given number x is prime.'''
    if x == 1:
        '''1 is not prime.'''
        return False
    elif numfactor(x) == 2:
        '''prime number is a number with exactly 2 factors'''
        return True
    else:
        return False

            
        
def isComposite(x):
    '''returns whether or not the given number x is compsite.'''
    if x == 1:
        return False           #1 is neither prime nor composite
    else:
        if isPrime(x):
            return False
        else:
            return True
 

def isPerfect(x):
    '''returns wheher or not the given number x is perfect.'''
    if isComposite(x):
        '''a perfect number has to be composite first.'''
        if sumfactor(x) == x:
            return True
        else:
            return False
    else:
        False
        

def isAbundant(x):
    '''returns wheher or not the given number x is abundant.'''
    if isComposite(x):
        '''an abundant number has to be composite first.'''
        if sumfactor(x) > x:
            return True
        else:
            return False
    else:
        return False
  
def isInt(x):
    '''check if x is an integer or not'''
    return x == int(x)

def quadratic(a,b,c):
    '''check for integer solution of a quadratic equation with coefficients a, b and c.'''
    delta = b*b-4*a*c
    if delta < 0:
        return False
    else:
        x1 = (-b+delta**(0.5))/(2*a)
        x2 = (-b-delta**(0.5))/(2*a)
        if (isInt(x1) and x1 > 0) or (isInt(x2) and x2 > 0):
            return True
        else:
            return False
        
def isTriangular(x):
    '''return whether or not the given number x is triangular.'''
    '''x is triangular when x = n*(n+1)/2,n is integer begin from 1'''
    if quadratic(1,1,-2*x):
        return True
    else:
        return False


def isPentagonal(x):
    '''return whether or not the given number x is pentagonal.'''
    if isTriangular(3*x):
        '''3x has to be triangular first when it is pentagonal'''
        if quadratic(9, -3, -6*x):
            return True
        else:
            return False

    else:
        return False


def isHexagonal(x):
    '''return whether or not the given number x is hexagnoal.'''
    '''x is triangular when x = 2*n*n-n,n is integer begin from 1'''
    if quadratic(2,-1,-x):
        return True
    else:
        return False

def main():
    continue_choice = True
    while continue_choice:
        '''keep the loop running'''
        x = input('Input a number between 1 and 10000(input -1 to quit)')
        if x == -1:
            break
        elif x > 10000 or x < 1 or x != int(x):
            print 'Error! You should input an integer between 1 and 10000.'
            
        else:
            if isPrime(x):
                prime = 'is prime,'
            else:
                prime = 'is not prime,'
            if isComposite(x):
                composite = 'is composite,'
            else:
                composite = 'is not composit,'
            if isPerfect(x):
                perfect = 'is perfect,'
            else:
                perfect = 'is not perfect,'
            if isAbundant(x):
                abundant = 'is abundant,'
            else:
                abundant = 'is not abundnat,'
            if isTriangular(x):
                triangular = 'is triangular,'
            else:
                triangular = 'is not triangular,'
            if isPentagonal(x):
                pentagonal = 'is pentagonal,'
            else:
                pentagonal = 'is not pentagonal,'
            if isHexagonal(x):
                hexagonal = 'is hexagonal.'
            else:
                hexagonal = 'is not hexagonal.'
            print x, prime, composite, perfect, abundant, triangular, pentagonal, hexagonal
        
if __name__ == "__main__":
    main()
    
    
    

    
 












            
    
