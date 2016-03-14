#Hammurabi
#cit 590 HW1
#Weixi Ma
#Study Group: Weixi Ma, Man Hua, Jiaqi Luo, Kexin Huang

import random

def print_intro():
    print'''Congrats, you are the newest ruler of ancient Samaria, elected for a ten year term of office. Your duties are to distribute food, direct farming, and buy and sell land as needed to support your people. Watch out for rat infestations and the resultant plague! Grain is the general currency, measured in bushels. The following will help you in your decisions:
* Each person needs at least 20 bushels of grain per year to survive.
* Each person can farm at most 10 acres of land.
* It takes 2 bushels of grain to farm an acre of land.
* The market price for land fluctuates yearly.
Rule wisely and you will be showered with appreciation at the end of your term. Rule poorly and you will be kicked out of office!'''

def Hammurabi():
    starved = 0
    immigrants = 5
    population = 100
    harvest = 3000         #total bushels harvested
    bushels_per_acre = 3   #amount harvested for each acre planted
    rats_ate = 200         #bushels destroyed by rats
    bushels_in_storage = 2800
    acres_owned = 1000
    cost_per_acre = 19     #each acre costs this many bushels
    plague_deaths = 0
    print_intro()

    counter = 1
    totalstarving = 0
    totalplague = 0
    
    while counter <= 10:
        print'O great Hammurabi!'
        print'You are in year',counter,'of your ten year rule.'
        print'In the previous year',starved,'people starved to death.'
        print'In the previous year',immigrants,'people entered the kingdom.'
        print'The population is now',population,'.'
        print'We harvested',harvest,'bushels at',bushels_per_acre,'bushels per acer.'
        print'Rats destroyed',rats_ate,'bushels, leaving',bushels_in_storage,'bushels in storage.'
        print'The city owns',acres_owned,'acres of land.'
        print'Land is currently worth',cost_per_acre,'bushels per acre.'
        print'There were',plague_deaths,'deaths from the plague.'

        plague_deaths = 0  # reset the plague_deaths to be zero
        rat_probability = random.randint(0,10)/10.0 # probability of rat infest

        choice=raw_input('Do you want to buy or sell lands?(type in "buy" or "sell")')  # Ask the user to buy or sell lands
        if choice == 'buy':
            acres_to_buy = ask_to_buy_land(bushels_in_storage,cost_per_acre)
            acres_owned = acres_owned + acres_to_buy
            bushels_in_storage = bushels_in_storage - cost_per_acre * acres_to_buy
        elif choice == 'sell':
            acres_to_sell = ask_to_sell_land(acres_owned)
            acres_owned = acres_owned - acres_to_sell
            bushels_in_storage = bushels_in_storage + cost_per_acre * acres_to_sell
        else:
            print'error'
            
        bushels_feed = ask_to_feed(bushels_in_storage)
        bushels_in_storage = bushels_in_storage - bushels_feed
        
        acres_cultivate = ask_to_cultivate(acres_owned,population,bushels_in_storage)
        bushels_in_storage = bushels_in_storage - acres_cultivate * 2

        starved = numStarving(population,bushels_feed)
        if starved > population * 0.45:   # test whether the starving people is too much
            print'There are',starved,'people starving, you are thrown out of office. Game over!'
            break

        totalstarving = totalstarving + starved

        population = population - starved
        immigrants = numImmigrants(acres_owned,bushels_in_storage,population,starved)

        if isPlague():
            plague_deaths = population / 2
            population = plague_deaths + immigrants
        else:
            population = population + immigrants

        totalplague = totalplague + plague_deaths

        bushels_per_acre = getHarvest()
        harvest = acres_cultivate * bushels_per_acre

        if rat_probability < 0.4:
            rats_ate = int(effectOfRats() * bushels_in_storage)
        else:
            rats_ate = 0
        
        bushels_in_storage = bushels_in_storage-rats_ate + harvest
        cost_per_acre = priceOfLand()
        
        counter = counter + 1

        if counter > 10:
            summary(acres_owned,starved,totalstarving,totalplague)
            break
        
def ask_to_buy_land(bushels,cost):
    '''Ask user how many bushels to spend buying land.'''
    acres = input('How many acres will you buy?')
    while acres * cost > bushels:
        print'O great Hammurabi, we have but',bushels,'bushels of grain!'
        acres = input('How many acres will you buy?')
    return acres

def ask_to_sell_land(acres):
    '''Ask user hwo many bushels they want to sell.'''
    acres_to_sell = input('How many acres will you sell?')
    while acres_to_sell > acres:
        print'O great Hammurabi, we have but',acres,'acres of land!'
        acres_to_sell = input('How many acres will you sell?')
    return acres_to_sell

def ask_to_feed(bushels):
    '''Ask user how many bushels they want to use for feeding.'''
    bushels_to_feed = input('How many bushels will you feed your people?')
    while bushels_to_feed > bushels:
        print'O great Hammurabi, we have but',bushels,'bushels of grain!'
        bushels_to_feed = input('How many bushels will you feed your people?')
    return bushels_to_feed

def ask_to_cultivate(acres,population,bushels):
    '''Ask user how much land they want plant seed in'''
    lands_to_seed = input('How many land will you seed?')
    while lands_to_seed > acres:
        print'O great Hammurabi, we have but',acres,'acres of land!'
        lands_to_seed = input('How many land will you seed?')

    while lands_to_seed / 10 > population:
        print'O great Hammurabi, we have but',population,'people!'
        lands_to_seed = input('How many land will you seed?')

    while lands_to_seed * 2 > bushels:
        print'O great Hammurabi, we have but',bushels,'bushels of grain!'
        lands_to_seed = input('How many land will you seed?')
    return lands_to_seed

def isPlague():
    '''the possibility of having Plague.'''
    if random.randint(1,100)>85:
        return True
    else:
        return False

def numStarving(population,bushels):
    '''return the number of starving people.'''
    numFeeding = int(bushels / 20)
    return population - numFeeding

def numImmigrants(land,grainInStorage,population,numStarving):
    '''return the number of immigrants.'''
    if numStarving > 0:
        return 0
    else:
        return (20 * land + grainInStorage)/(100 * population + 1)

def getHarvest():
    '''return a random number between 1 and 8 for amount harvested for each acre planted'''
    return random.randint(1,8)

def effectOfRats():
    '''return a possibility of damage of rats infest'''
    return random.randint(10,30)/100.0

def priceOfLand():
    '''return a random number between 16 and 22 for the price for each acre'''
    return random.randint(16,22)

def summary(acres,numStarving,totalstarving,totalplague):   #final summary
    print'Congradulation! you do a great job in the previous decade, ending up with',numStarving,'people starving and owning',acres,'acres of land.'
    print'In the last 10 years,',totalstarving,'people dead because of starving and',totalplague,'people dead because of plague.'

if __name__=="__main__":
    Hammurabi()
