package Hangmanstuff;

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

class Hangman{
	
	public static final int RAND_MIN = 2;
	public static final int RAND_MAX = 13;
	public static final int MAXNUMGUESSES = 7;
	
// returns list of all words of random length
	
	public static ArrayList<String> adjustedList(int length, ArrayList<String> orgWordList){
		
		ArrayList<String> newWordList = new ArrayList<String>();
		for(String word: orgWordList) {
			if(word.length() == length) {
				newWordList.add(word);
			}
		}
		
		return newWordList;
	}
	
// cheating part, removes all words containing the user's guess until the list cannot be limited anymore, and returns that new limited list
	
	public static ArrayList<Character> lastRemoval(ArrayList<String> sizeList, int len){

		    ArrayList<Character> guessList = new ArrayList<Character>();
		    int guessCount = 0;

		    Scanner cInput = new Scanner(System.in);
		    
		    for(int i = 0; i < len; i++) {
	    		System.out.print("_ ");
	    	}
		    System.out.println();

		    while (!sizeList.isEmpty() && guessCount < MAXNUMGUESSES) {
		    	
		    	
		        System.out.println("What is your guess?");
		        Character cheatGuess = cInput.nextLine().charAt(0);
		        guessCount++;

		        guessList.add(cheatGuess);

		        ArrayList<String> elementsToRemove = new ArrayList<String>();

		        for (String s : sizeList) {

		            if (s.contains(cheatGuess.toString())) {
		                elementsToRemove.add(s);
		            }
		        }

		        sizeList.removeAll(elementsToRemove);
		        
		        for(int i = 0; i < len; i++) {
		    		System.out.print("_ ");
		    	}
		        System.out.println();
		        
		    }
		    if(guessCount == MAXNUMGUESSES) {
		    	System.out.println("You have not guessed the word incorrectly :(");
		    	return null;
		    }
		    else {
//		    cInput.close();
		    return guessList;
		    }
		}

	public static ArrayList<String> realList(ArrayList<String> tmpList, ArrayList<Character> gList){
		
		ArrayList<String> elementToRemove = new ArrayList<String>();
		
//		int gLastIndex = gList.size();
//		gList.remove(gLastIndex - 1);
		
		for(int i = 0; i < gList.size() - 1; i++) {
			for (String t : tmpList) {
				if (t.contains(gList.get(i).toString())) {
					elementToRemove.add(t);
				}
			}
		}
		
		tmpList.removeAll(elementToRemove);
	
		return tmpList;
	}
   
    public static void realGame(ArrayList<Character> guessList, ArrayList<String> finalWordList) {


    	int listSize = finalWordList.size();
    	int randNum = 0;
    	
    	if(listSize == 1) {
    		randNum = 0;
    	}
    	else {
    		randNum = (int)(Math.random() * listSize);
    	}
    	
    	String finalWord = finalWordList.get(randNum);
    	int finalWordLength = finalWord.length();
    	char splitWord[] = finalWord.toCharArray();
    	
    	boolean isLetter[] = new boolean[finalWordLength];
    	for(int i = 0; i < finalWordLength; i++) {
			if (guessList.contains(finalWord.charAt(i))) {
				isLetter[i] = true;
			} else {
				isLetter[i] = false;
			}
    	}
    	guessList.remove(guessList.size() - 1);
    	ArrayList<Character> wrongGuesses = guessList; // all previous wrongGuesses
    	
    	boolean isNotDone = true;

		Scanner in = new Scanner(System.in);
    	
		while(isNotDone && (wrongGuesses.size() < MAXNUMGUESSES)) {
			playerView(finalWord, isLetter, splitWord, wrongGuesses);

			System.out.println("What is your guess?");

			String finalGuess = in.nextLine();

			if(finalWord.equals(finalGuess)) {
				System.out.println("Wow! You guessed it! The word was " + finalWord);
			}
			else if(finalWord.contains(finalGuess)){
				for(int i = 0; i < finalWordLength; i++) {
					if(splitWord[i] == finalGuess.charAt(0)) {
						isLetter[i] = true;
					}
				}
			}
			else {
				System.out.println("What is your guess?");
				wrongGuesses.add(finalGuess.charAt(0));
			}
    	
			//printing out results
			//while word has not been figured out yet
			for(int i = 0; i < finalWordLength; i++) {
				if(isLetter[i] == false) {
					isNotDone = true;
					break;
				}
				else {
					isNotDone = false;
				}	
	    	}
		}
		
		if(wrongGuesses.size() == MAXNUMGUESSES) {
			System.out.println("You have not guessed the word correctly :(");
		}
		else if (wrongGuesses.size() < MAXNUMGUESSES) {
			System.out.println("You have guessed the word correctly!! :D ");
		}
                                                                   
    }
    
    
// prints out lines for the game of hangman, reveals letters when they're guessed correctly, and prints out wrong guesses
    
    public static void playerView(String chosenWord1, boolean []rightGuesses, char []charWord, ArrayList<Character> wrongGuess) {
    	for(int i = 0; i < chosenWord1.length(); i++) {
    		if(rightGuesses[i] == false) {
    			System.out.print("_ ");
    		}
    		else if(rightGuesses[i] == true) {
    			System.out.print(charWord[i] + " ");
    		}
    		
    	}
    	System.out.println();
		/*System.out.print("Wrong guesses are: ");
		for(int i = 0; i < wrongGuess.size(); i++) {
			System.out.print(wrongGuess.get(i) + ", ");
		}
		System.out.println();*/
    }

    
	public static void main(String[] args) throws FileNotFoundException{
		
	// scans for file and puts all words 
		Scanner list = new Scanner(new File("C:\\Users\\Melody Lam\\Desktop\\wordList.txt"));
		ArrayList<String> wordList = new ArrayList<String>();
		while(list.hasNext()) {
			wordList.add(list.next());
		}
		
		int wordLength = (int)(Math.random() * 14) + 1;
		
		System.out.println("Welcome to hangman!");
		System.out.println("You will be prompted to make guesses. If you get it wrong, you will be prompted again and no letters will appear.");
		System.out.println("If you make the correct guess, you will see letters appear and be prompted to make more guesses!");
	// limitedList is list of words with random wordlength
		
		ArrayList<String> sizedList = new ArrayList<String>();
		sizedList = adjustedList(wordLength, wordList);
		
		ArrayList<String> tempList = new ArrayList<String>();
		tempList = adjustedList(wordLength, wordList);
		
		ArrayList<Character> guessList = new ArrayList<Character>();
		guessList = lastRemoval(sizedList, wordLength);
		if(guessList == null) {
			return;
		}
		
		ArrayList<String> finalList = new ArrayList<String>();
		finalList = realList(tempList, guessList);

		// after while loop this sized list is as limited as it can be with guesses, we have passed the cheater portion
		realGame(guessList, finalList);
		
		
	
	}	
}