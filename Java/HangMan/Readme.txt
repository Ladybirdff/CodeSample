Please run Main.java to start the game.

At first I partition all words in the dictionary text file to a HashMap by their lengths, by doing so, I can know all the available or valid lengths for user to guess. After the user chose a length, I can easily chosen those words in the HashMap by using the length as the key. And store them in an ArrayList for further partition when the user play the game.

I also use a HashMap for creating new word families, the key is the occurrences and the position of guessed letters, and then choose the biggest family as remaining words.
