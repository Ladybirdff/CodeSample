#cit 590
#HW6
#Authors: Weixi Ma and Kexin Huang

from cards import *
from SolBlackjack import *
import unittest

class testBlackjack(unittest.TestCase):

    def setUp(self):
        '''set up initial variables for unittest'''
        '''create a full blackjack table'''
        self.deck = Deck()
        self.blackjack1 = Blackjack()
        self.blackjack1.occupancy = set([6])
        self.blackjack1.table = {'row1':['AC',2,3,4,5], 'row2':[6,7,8,'1H',10], 'row3':[11,12,13], 'row4':[14,15,16]}
        self.blackjack1.discardList = [17,'10H',19,20]
        self.blackjack1.round = 3
        self.blackjack2 = Blackjack()
        self.blackjack2.table = {'row1':[Card(1,'D'),Card(6,'D'),Card(4,'D'),Card(6,'C'),Card(10,'S')],
                                 'row2':[Card(13,'H'),Card(5,'C'),Card(2,'H'),Card(3,'S'),Card(1,'C')],
                                 'row3':[Card(9,'C'),Card(6,'H'),Card(8,'H')],
                                 'row4':[Card(13,'D'),Card(7,'H'),Card(8,'C')]}
        self.blackjack2.discardList = [Card(9,'S'),Card(13,'C'),Card(9,'H'),Card(10,'D')]
    def testCreation(self):
        '''test the table was successfully created'''
        self.assertEqual(self.blackjack2.table['row1'][0].rank, 1)
        self.assertEqual(self.blackjack2.table['row4'][1].suit, 'H')

    def testscore(self):
        '''test score of a list excluding an Ace'''
        self.assertEqual(self.blackjack2.score(33),0)
        self.assertEqual(self.blackjack2.score(15),1)

    def testrecurr_ace(self):
        '''test the function used to deal with list containing Ace'''
        self.assertEqual(self.blackjack2.recurr_ace(35, 1), 0) #we assume ace scoring 11 first, and minus 10 each time
        self.assertEqual(self.blackjack2.recurr_ace(31, 1), 7) # a list scoring 21, but not a blackjack

    def testsingle_score(self):
        '''test the score of a list input'''
        lst1 = self.blackjack2.table['row1']
        lst2 = self.blackjack2.table['row2']
        lst3 = [Card(1,'S'),Card(13,'H')] #test Blackjack
        self.assertEqual(self.blackjack2.single_score(lst1),0)
        self.assertEqual(self.blackjack2.single_score(lst2),7)
        self.assertEqual(self.blackjack2.single_score(lst3),10)#black jack

    def testmake_column(self):
        '''test function converting to vertical column'''
        self.assertEqual(self.blackjack2.make_column()[0][0].rank,1)
        self.assertEqual(self.blackjack2.make_column()[0][0].suit,'D')#test first column contains an Ace of Diamond
        self.assertEqual(self.blackjack2.make_column()[3][2].rank,8)
        self.assertEqual(self.blackjack2.make_column()[3][2].suit,'H') #test forth column contains an 8 of Heart

    def testtotal_score(self):
        '''test the total score of the table'''
        self.assertEqual(self.blackjack2.total_score(), 31)

    def teststr(self):
        '''test str'''
        card1 =str(self.blackjack2.table['row3'][2]) #test card in the table
        discard1 = str(self.blackjack2.discardList[0])
        discard4 = str(self.blackjack2.discardList[3])       
        self.assertTrue(discard1 in str(self.blackjack2))
        self.assertTrue(discard4 in str(self.blackjack2))
        self.assertTrue(card1 in str(self.blackjack2))

        
    def testerrorCheck(self):
        '''check for the available place for putting card in'''
        self.assertEqual(self.blackjack1.errorCheck(0), False)
        self.assertEqual(self.blackjack1.errorCheck(21), False)
        self.assertEqual(self.blackjack1.errorCheck(10), True)
        self.assertEqual(self.blackjack1.errorCheck(6), False)

    def testmoveCard(self):
        '''check the self.table after moving the card in'''
        self.blackjack1.moveCard(2, '9C')
        self.assertTrue(self.blackjack1.table == {'row1':['AC','9C',3,4,5], 'row2':[6,7,8,'1H',10], 'row3':[11,12,13], 'row4':[14,15,16]})
        self.blackjack1.moveCard(17, '7S')
        self.assertTrue(self.blackjack1.discardList == ['7S','10H',19,20])

    def testshowDealedCard(self):
        '''check the dealed card from the deck'''
        self.assertEqual(str(self.blackjack1.showDealedCard()), 'KD')
        
    def testcheckFullTable(self):
        '''check whether the table is full or not'''
        self.assertEqual(self.blackjack1.checkFullTable(), False)

    def testhighScore(self):
        '''check the file highScore.txt for writting the highest score'''
        '''clean the file first'''
        self.f = open('highScore.txt', 'r+')
        line = self.f.readline()
        self.f.seek(0)
        self.f.truncate()
        self.f.close()
        
        self.blackjack1.highScore(15)
        self.f = open('highScore.txt', 'r')
        self.assertEqual(int(self.f.readline()), 15)
        self.f.close()
        
        self.blackjack1.highScore(21)
        self.f = open('highScore.txt', 'r')
        self.assertEqual(int(self.f.readline()), 21)
        self.f.close()

        self.blackjack1.highScore(11)
        self.f = open('highScore.txt', 'r')
        self.assertEqual(int(self.f.readline()), 21)
        self.f.close()

        #reset the file
        self.f = open('highScore.txt', 'w')
        self.f.write(line)
        self.f.close()       

        
        
        


unittest.main()
