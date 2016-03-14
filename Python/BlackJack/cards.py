#cit 590
#HW6
#Authors: Weixi Ma and Kexin Huang

import random  # needed for shuffling a Deck

class Card(object):
    #the card has a suit which is one of 'S','C','H' or 'D'
    #the card has a rank 
    def __init__(self, rank, suit):
        #implement
        #where r is the rank, s is suit
        self.rank = rank #1-13
        self.suit = suit #str
        
    def __str__(self):
        '''return the suit and the real rank of a card according to its rank's number'''
        if self.rank in range(2,11):
            return str(self.rank) + self.suit
        if self.rank == 1:
            return 'A' + self.suit
        if self.rank == 11:
            return 'J' + self.suit
        if self.rank == 12:
            return 'Q' + self.suit
        if self.rank == 13:
            return 'K' + self.suit
        
    def get_rank(self):#int
        '''return a card's rank'''
        return self.rank

    def get_suit(self):#string
        '''return a card's suit'''
        return self.suit


SUITS = ['S','H','C','D']

class Deck():#list of Card
    """Denote a deck to play cards with"""
    
    def __init__(self):
        """Initialize deck as a list of all 52 cards:
           13 cards in each of 4 suits"""
        #correct the code below
        self.__deck = []
        for i in range(0,52):
            card = Card(i%13+1, SUITS[i%4])
            self.__deck.append(card)

    def shuffle(self):
        """Shuffle the deck"""
        random.shuffle(self.__deck)

    def get_deck(self):
        '''return the self.__deck list'''
        return self.__deck
    
    def deal(self):
        # get the last card in the deck
        # simulates a pile of cards and getting the top one
        return self.__deck.pop()
    
    def __str__(self):
        """Represent the whole deck as a string for printing -- very useful during code development"""
        result = ""
        for s in self.__deck:
            result = result + str(s) + '\n'
        return result

            
        

