#cit 590
#HW6
#Authors: Weixi Ma, Kexin Huang

from cards import *

class Blackjack(object):
    
    def __init__(self):
        '''make initial variables'''
        self.newDeck = Deck()
        self.table = {'row1':[1,2,3,4,5], 'row2':[6,7,8,9,10], 'row3':[11,12,13], 'row4':[14,15,16]}
        self.discardList = [17,18,19,20]
        self.round = 0 #used to trace how many cards are in the table
        self.occupancy = set([]) # used to remember whether the position in the table is occupied or not


    def askUser(self):
        '''Ask user to input a int'''
        while True:
            try:
                position = input('Which position do you want to place the card in?')
                break
            except (NameError, SyntaxError):
                print 'Ops, please enter a number.'
        return position
    
    def userMove(self):
        '''Ask user to make a move by typing a number'''
        position = self.askUser()
        while self.errorCheck(position) == False:
            position = self.askUser()
        self.occupancy.add(position)
        return position
                   
    def errorCheck(self, position): # check out of range and check occupancy
        '''Check input number in range from 1 to 20 or not'''
        if position not in range(1,21):
            print 'Ohhhh, I can\'t do that! Number should be from 1 to 20'
            return False
        if position in self.occupancy:
            print 'Ops, this position already has a card on it, please relocate it'
            return False
        else: return True
            
    def moveCard(self, position, deal):
        '''according to user's decision, move the card to the table'''
        if position <= 16:
            for row in self.table:
                for x in self.table[row]:
                    if x == position:
                        self.table[row][self.table[row].index(position)] = deal
                        self.round += 1
                        break
        else:
            for x in self.discardList:
                self.discardList[self.discardList.index(position)] = deal
                break
                
    def showDealedCard(self):
        '''Deal a card'''
        dealedCard = self.newDeck.deal()
        print 'New dealed card is', dealedCard
        return dealedCard

    def checkFullTable(self):
        '''Check whether the table is full or not'''
        if self.round < 16:
            return False
        else: return True

    def highScore(self, score):
        '''record high score'''
        try:
            f = open('highScore.txt', 'r+') # try the .txt file exist or not
        except IOError:
            f = open('highScore.txt', 'a+')
        lines = f.readlines()
        if not lines:
            f.write(str(score))
            print 'You are a genius! You achieved the highest score!'
        else:
            if int(lines[0]) < score:
                print 'You are a genius! You achieved the highest score!'
                f.seek(0)
                f.write(str(score))
            else: pass
        f.close()

    def score(self, total):
        '''calculate the score for every row or column except blackjack'''
        if total > 21:
            return 0
        elif total == 21:
            return 7
        elif total == 20:
            return 5
        elif total == 19:
            return 4
        elif total == 18:
            return 3
        elif total == 17:
            return 2
        elif total <= 16:
            return 1

    def recurr_ace(self, total, n):
        '''calculate the score which approaching 21 most for ACE showing up for n time'''
        if total <= 21 or n == 0:
            return self.score(total)
        else:
            for i in range (1,n+1):
                newTotal = total - 10 * i
                return self.recurr_ace(newTotal, n-i)
        

    def single_score(self, lst):
        '''calculate the score for every row or column'''
        total = 0
        mark = False
        ace_counter = 0
        counter = 0
        for card in lst:
            if card.rank >= 11:
                '''J, Q and K count for 10 points'''
                total += 10
            elif card.rank == 1:
                '''assume Ace counts for 11 points first'''
                total += 11
                mark = True
                ace_counter += 1
            else:
                total += card.rank
            counter += 1
        if counter == 2 and total == 21:
            '''Blackjack'''
            return 10
        else:
            if mark:
                return self.recurr_ace(total, ace_counter)
            elif not mark:
                return self.score(total)

    def make_column(self):
        '''return 5 list of 5 column indivadually'''
        column1 = []
        column1.append(self.table['row1'][0])
        column1.append(self.table['row2'][0])
        column2 = []
        column2.append(self.table['row1'][1])
        column2.append(self.table['row2'][1])
        column2.append(self.table['row3'][0])
        column2.append(self.table['row4'][0])
        column3 = []
        column3.append(self.table['row1'][2])
        column3.append(self.table['row2'][2])
        column3.append(self.table['row3'][1])
        column3.append(self.table['row4'][1])
        column4 = []
        column4.append(self.table['row1'][3])
        column4.append(self.table['row2'][3])
        column4.append(self.table['row3'][2])
        column4.append(self.table['row4'][2])
        column5 = []
        column5.append(self.table['row1'][4])
        column5.append(self.table['row2'][4])
        return column1, column2, column3, column4, column5

    def total_score(self):
        '''return the total score for the hand'''
        row1_score = self.single_score(self.table['row1'])
        row2_score = self.single_score(self.table['row2'])
        row3_score = self.single_score(self.table['row3'])
        row4_score = self.single_score(self.table['row4'])
        total_row = row1_score + row2_score + row3_score + row4_score
    
        column1, column2, column3, column4, column5 = self.make_column()
        column1_score = self.single_score(column1)
        column2_score = self.single_score(column2)
        column3_score = self.single_score(column3)
        column4_score = self.single_score(column4)
        column5_score = self.single_score(column5)
        total_column = column1_score + column2_score + column3_score + column4_score + column5_score

        return total_row + total_column
    
    def __str__(self):
        '''str, return the str of table'''
        table = '-------------------------\nThe state of the game is:\n\n'
        
        for row in self.table:
            rowCopy = self.table[row][:]
            for i in rowCopy:
                if type(i) == int:
                    if i < 10:
                        rowCopy[rowCopy.index(i)] = '  '+ str(i)
                    else:
                        rowCopy[rowCopy.index(i)] = ' ' + str(i)
                else:
                    if len(str(i)) == 2:
                        rowCopy[rowCopy.index(i)] = ' ' + str(i)
                    else:
                        rowCopy[rowCopy.index(i)] = str(i)
            if row == 'row3' or row == 'row4':
                rowCopy.insert(0, '   ')
                rowCopy.insert(5, '   ')
            rowCopy = str(rowCopy) + '\n'
            table += rowCopy

        listCopy = self.discardList[:]
        for i in listCopy:
            listCopy[listCopy.index(i)] = str(i)
        table += 'Discard list:\n' + str(listCopy)

        return table
        
        
    def play(self):
        '''play the game once'''
        print'Welcome to Blackjack Game:'
        print self
        self.newDeck.shuffle()
        while self.checkFullTable() == False: 
            deal = self.showDealedCard()
            move = self.userMove()
            self.moveCard(move, deal)
            print self
        score = self.total_score()
        print 'Wow, you finished this round.\nTotal score is:', score
        self.highScore(score)
        choice = raw_input('Would you want to play again? Press any key and enter to continue\nOr press n and enter to quit')
        if choice != 'n':
            main()

def main():
        bj_solitaire = Blackjack()
        bj_solitaire.play()

if __name__ == '__main__':
    main()
        
            

        
        



