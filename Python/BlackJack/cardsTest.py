#cit 590
#HW6
#Authors: Weixi Ma and Kexin Huang

from cards import *
import unittest

class testCard(unittest.TestCase):

    def setUp(self):
        '''set up a Card class for unittesting'''
        self.card = Card(13, 'H')

    def testCreation(self):
        '''check the creation of the self.card class'''
        self.assertEqual(self.card.rank, 13)
        self.assertEqual(self.card.suit, 'H')

    def teststr(self):
        '''check the str function of the Card class'''
        self.assertEqual(str(self.card), 'KH')

class testDeck(unittest.TestCase):

    def setUp(self):
        '''Set up a Deck class for unittesting'''
        self.deck = Deck()

    def testCreation(self):
        '''check the creation of the Deck class'''
        #Check that the deck has 52 cards
        self.assertEqual(len(self.deck.get_deck()), 52)
        self.assertTrue('JC' in str(self.deck))
        self.assertTrue('AH' in str(self.deck))
        self.assertTrue('QS' in str(self.deck))
        self.assertTrue('KD' in str(self.deck))
        self.assertTrue('9C' in str(self.deck))
        
    def testdeal(self):
        '''check the self.deal function'''
        self.assertEqual(str(self.deck.deal()), 'KD')
        self.assertEqual(str(self.deck.deal()), 'QC')

unittest.main()
