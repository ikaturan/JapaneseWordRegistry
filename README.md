# My Personal Project: Japanese Word Registry

## Improve your Japanese

This is an application for **recording Japanese words.** 
It will allow users to record a Japanese word and store the word, 
a picture of the kanji and the definition of the word. It will also allow users to search
existing words in the registry. Anyone interested in learning Japanese can use this application 
to store Japanese words. This project is of interest to me because I want to use this application
to improve my Japanese vocabulary in preparation for the *Japanese Language Proficiency Exam.*
Also, I want to create an interface where it is possible to search a Japanese word in romaji. 
Surprisingly, many online Japanese word search websites do not allow for searching Japanese words
in romaji; you have to type the hiragana, katakana or kanji to search. Finally, having my own 
registry will be extremely useful for me as it allows me to concentrate on specific Japanese words
that I find useful, but are hard to remember.

## User Stories
- As a user, I want to be able to add a new Japanese word to my favourites word list and specify the definition 
and image of the kanji (if applicable).
- As a user, I want to be able to view the favourites word list in my registry.
- As a user, I want to be able to select a Japanese word and view the word in detail (definition, kanji, etc).
- As a user, I want to be able to remove a word from my favourites word list.
- As a user, I want to be able to see how many words are in my favourites word list. 
- As a user, I want to be able to save my Japanese Word Registry to file (if I so choose)
- As a user, I want to be able to load my Japanese Word Registry from file (if I so choose)

## Instructions for grader
- You can generate the first required action related to the user story "adding multiple words to a word list" 
by entering the romaji, definition and image in the respective three text boxes and
click the add button.
- You can generate the second required action related to the user story "removing words from a word list"
by selecting the word from the table and hitting the remove button.
- You can generate the third action related to the user story "see how many words in the list" 
by pressing the update count button.
- You can locate my visual component by selecting a word from the table and clicking the display image button.
- You can save the state of my application by clicking the save button.
- You can reload the state of my application by clicking the reload button.

## Phase 4: Task 2
Note: the application initializes with two words added and the words in wordlist counted once.
- run wl.addWord(jisho)
  - Added jisho to wordlist
- run wl.addWord(kamen)
  - Added kamen to wordlist
- run wl.countWords()
  - Counted words in wordlist
- run wl.removeWords(kamen)
  - Removed kamen from wordlist

## Phase 4: Task 3
- If I had more time to work on my code, I would certainly refactor the private classes. There are way too many
private classes in JapaneseAppUI. The private classes do not do much by themselves; a lot of them are there to
make the application look nicer. Many private classes in JapaneseAppUI simply exist to implement one button, or
to do one action. I would love to refactor these classes so that they are one button that runs one method
instead. Doing so would make the diagram a lot cleaner. 
- Another aspect I would like to refactor is, instead of having the image of the kanji being displayed via a 
new popup window, I would like the image displayed somewhere on the screen when I press the display image 
button. I chose to have the popup window because it was the easiest to implement in my opinion. However, 
having the image displayed on the main screen of the application would look a lot cleaner. 
