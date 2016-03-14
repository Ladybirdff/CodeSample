#Racko.py
#CIT 590 HW3
#authors -  Weixi Ma and Shu-Yu Lai

deck = range(1,61)
discard = []


import random

def shuffle():
    '''shuffle the deck or the discard pile when the deck is run out'''
    global deck
    global discard
    if len(deck) >= 60:
        random.shuffle(deck)
    elif len(deck) <= 0:
        random.shuffle(discard)
        deck = discard[:]
        discard = []
        discard.append(deck.pop())
    
def check_racko(rack):
    '''check whether the rack is in ascending order or not'''
    i = 0
    while i < (len(rack) - 1):
        if rack[i] < rack[i + 1]:
            return False
        else:
            i = i + 1
    return True

def deal_card():
    '''get the top card(the last element) from the deck'''
    global deck
    return deck.pop()

def deal_initial_hands():
    '''deal two hands of 10 cards each'''
    computer_hand = []
    human_hand = []
    counter = 1
    while counter <= 10:
        x = deal_card()
        computer_hand.append(x)
        y = deal_card()
        human_hand.append(y)
        counter = counter + 1
    return computer_hand, human_hand

def does_user_begin():
    '''decide whether the ueser begin first or not'''
    if random.randint(1,100)>50:
        return True
    else:
        return False

def print_top_to_bottom(rack):
    '''print a list out from top tp bottom'''
    for i in range(0,len(rack)):
        print rack[i]

def find_and_replace(newCard, cardToBeReplaced, hand):
    '''replace a card in the hand with newCard'''
    index = hand.index(cardToBeReplaced)
    hand[index] = newCard

def add_card_to_discard(card):
    '''add the card to the top of the discard pile'''
    global discard
    discard.append(card)

def computer_play(hand):
    '''computer's strategy to win the game'''
    '''my strategy is that the computer always take the card from the deck'''
    '''and put it into certain position according to its value'''
    global deck
    global discard
    newcard = deck.pop()
    if newcard in range(55,61):
        discard.append(hand[0])
        hand[0] = newcard
    elif newcard in range(49,55):
        discard.append(hand[1])
        hand[1] = newcard
    elif newcard in range(43,49):
        discard.append(hand[2])
        hand[2] = newcard
    elif newcard in range(37,43):
        discard.append(hand[3])
        hand[3] = newcard
    elif newcard in range(31,37):
        discard.append(hand[4])
        hand[4] = newcard
    elif newcard in range(25,31):
        discard.append(hand[5])
        hand[5] = newcard
    elif newcard in range(19,25):
        discard.append(hand[6])
        hand[6] = newcard
    elif newcard in range(13,19):
        discard.append(hand[7])
        hand[7] = newcard
    elif newcard in range(7,13):
        discard.append(hand[8])
        hand[8] = newcard
    elif newcard in range(1,7):
        discard.append(hand[9])
        hand[9] = newcard
    return hand

def use_discard():
    '''Determin whether the user use to discard pile or not'''
    while True:
        choice = raw_input('Do you use the discard pile?(yes or no)')
        if choice == 'yes':
            return True
        elif choice == 'no':
            return False
        else:
            print'Error'

def use_deck():
    '''Determin whether the user use the card from the deck or not'''
    while True:
        choice = raw_input('Do you use the card from the deck?(yes or no)')
        if choice == 'yes':
            return True
        elif choice == 'no':
            return False
        else:
            print'Error'
    
def replace_card(hand):
    '''return the card which will be replaced in the hand'''
    while True:
        oldcard = raw_input('Choose a card which you want to replace in your hand.')
        for i in range(0,len(hand)):
            if oldcard == str(hand[i]):
                return int(oldcard)

        print 'Error, you should choose one card from your hand to be replaced.'


def main():
    print'Game starts!'
    global deck
    global discard
    shuffle()
    #shuffle the deck to start the game and deal each player's hand
    computer_hand, human_hand = deal_initial_hands()
    print 'Your current hand is'
    print_top_to_bottom(human_hand)
    discard.append(deck.pop())
    #put the first card of the deck into discard pile
    #determin which one to start first
    if does_user_begin():
        print 'You go first.'
        while not check_racko(computer_hand) and not check_racko(human_hand):
            
            print 'The top card on the discard pile is', discard[-1]
            #show ueser the top in discard pile, choose whether use the discard pile or not
            if use_discard():
                #replace a card from user's hand with a discard
                oldcard = replace_card(human_hand)
                find_and_replace(discard.pop(), oldcard, human_hand)
                discard.append(oldcard)
                print 'Your current hand is'
                print_top_to_bottom(human_hand)
            else:
                
                newcard = deck.pop()
                #deal a card from the deck
                print 'The card you get from the deck is', newcard
                #choose whether use the card from deck or not
                if use_deck():
                    #replace the card in the hand and put it into discard pile
                    oldcard = replace_card(human_hand)
                    find_and_replace(newcard, oldcard, human_hand)
                    discard.append(oldcard)
                else:
                    #discard the top card from deck
                    discard.append(newcard)
                print 'Your current hand is'
                print_top_to_bottom(human_hand)

            shuffle()
            #check the length of the deck and decide whether to shuffle it
            computer_hand = computer_play(computer_hand)
            #the turn for computer to move
            
        #check if anyone get racko
        if check_racko(human_hand):
            print'You get Rack-O!'
            print_top_to_bottom(human_hand)

        elif check_racko(computer_hand):
            print'The computer gets Rack-O! Its hand is'
            print_top_to_bottom(computer_hand)
            
    else:
        print 'The computer goes first.'
        while not check_racko(computer_hand) and not check_racko(human_hand):
            #the computer moves first
            computer_hand = computer_play(computer_hand)
            #check the length of the deck and decide whether have to shuffle it
            shuffle()
            
            print 'The top card on the discard pile is', discard[-1]
            #show ueser the top in discard pile, choose whether use the discard pile or not
            if use_discard():
                #use the discard to replace a card in the hand and put the replaced card into discard pile
                oldcard = replace_card(human_hand)
                find_and_replace(discard.pop(), oldcard, human_hand)
                discard.append(oldcard)
                print 'Your current hand is'
                print_top_to_bottom(human_hand)
            else:
                
                newcard = deck.pop()
                print 'The card you get from the deck is', newcard
                #deal a card from deck and choose whether to use it
                if use_deck():
                    #replace the card in the hand and put it into discard pile
                    oldcard = replace_card(human_hand)
                    find_and_replace(newcard, oldcard, human_hand)
                    discard.append(oldcard)
                else:
                    #discard the top card from deck
                    discard.append(newcard)
                print 'Your current hand is'
                print_top_to_bottom(human_hand)

            
        #check if anyone get racko           
        if check_racko(computer_hand):
            print'The computer gets Rack-O! Its hand is'
            print_top_to_bottom(computer_hand)

        elif check_racko(human_hand):
            print'You get Rack-O!'
            print_top_to_bottom(human_hand)


if __name__ == '__main__':
    main()

    



    
    

