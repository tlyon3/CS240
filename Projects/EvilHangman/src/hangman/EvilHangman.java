package hangman;

import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.io.*;
import java.util.Scanner;
import java.util.Map;
import java.util.Iterator;

public class EvilHangman implements IEvilHangmanGame {
	EvilHangman(){
		madeGuesses = new HashSet<Character>();
		dict = new Dictionary();
		correctGuesses = new HashSet<Character>();
	}
	Dictionary dict;
	Set<Character> correctGuesses;
	public static void main(String[] args){
		try{
			String dictionary = new String();
			dictionary = args[0];
			java.io.File temp = new File(dictionary);
			int wordLength = Integer.parseInt(args[1]);
			int guesses = Integer.parseInt(args[2]);
			EvilHangman myGame = new EvilHangman();
			myGame.startGame(temp, wordLength);
			StringBuilder tempSB = new StringBuilder();
			for(int i=0;i<wordLength;i++){
				tempSB.append("-");
			}
			String wordPattern = tempSB.toString();
			int i=0;
			Scanner userInput = new Scanner(System.in);
			while(i!=guesses){
//				System.out.print("Words in dictionary: ");
//				myGame.dict.printDictionary();
				int tempCount = 0;
				for(int k=0;k<wordLength;k++){
					if(wordPattern.charAt(k) != '-')
						tempCount++;
					else
						break;
				}
				if(tempCount == wordLength){
					System.out.println("You won! The word was " + wordPattern);
					return;
				}
				System.out.println("You have " + (guesses-i) + " guesses remaining");
				System.out.print("Previous guesses: ");
				for(char ch:myGame.madeGuesses){
					System.out.print(ch+" ");
				}
				System.out.println();
				System.out.println("Word: " + wordPattern);
				System.out.print("Make a guess: ");
				//try catch block for GuessAlreadyMadeException
				try{
					String guess = new String();
					while(true){
						guess = userInput.next();
						if(guess.length() != 1){
							System.out.println("Please enter a single letter");
						}
						else if(!Character.isLetter(guess.charAt(0))){
							System.out.print("Please enter a single letter: ");
						}
						else
							break;
					}
					char charGuess = guess.charAt(0);
					if(myGame.madeGuesses.contains(charGuess)){
						throw new GuessAlreadyMadeException();
					}
					Set<String> newWords = myGame.makeGuess(charGuess);
					if(newWords.isEmpty()){//shouldn't ever get here
						System.out.println("SET IS EMPTY");
						System.out.println("Sorry! There is no '"+charGuess+"' in the word");
					}
					else{
						myGame.dict.setWords(newWords);
						wordPattern = myGame.getPattern(newWords.iterator().next(),myGame.correctGuesses,charGuess);
						int count = 0;
						//System.out.println("Checking pattern " + wordPattern + " for "+charGuess);
						for(int j=0;j<wordPattern.length();j++){
							if(wordPattern.charAt(j) == charGuess){
//								System.out.println("Char matches ("+charGuess+") at index "+j);
								count++;
							}
						}
						if(count==0){
							System.out.println("Sorry! There is no '"+charGuess+"' in the word");
							System.out.println();
						}
						else{
							System.out.println("Yes there are "+count+" "+charGuess+"'s in the word!");
							System.out.println();
							myGame.correctGuesses.add(charGuess);
						}
					}
					//increment guess counter
					i++;
				}
				catch(GuessAlreadyMadeException ex){
					System.out.println("You have already guessed that letter!");
					System.out.println();
				}
			}
			userInput.close();
			//if they guess the word, say they one
			//else, say they lost and output the word
			int tempCount = 0;
			for(int k=0;k<wordLength;k++){
				if(wordPattern.charAt(k) == '-'){
					System.out.println("Sorry you lost! The word was: "+myGame.dict.getWord());
					return;
				}
			}
			System.out.println("You won! The word was " + wordPattern);
		}
		catch(ArrayIndexOutOfBoundsException ex){
			System.out.println("Number of arguments invalid.");
		}
	}
	private HashSet<Character> madeGuesses;
	 
	@Override
	public void startGame(File dictionary, int wordLength) {
		// TODO Auto-generated method stub
		try{
			FileReader myReader = new FileReader(dictionary);
			Scanner myScanner = new Scanner(dictionary);
			while(myScanner.hasNext()){
				String next = myScanner.next();//possible problem with memory?
				if(next.length() == wordLength)
					dict.addWord(next);
			}
			myReader.close();
			myScanner.close();
		}
		catch(IOException ex){
			System.out.println("Error reading file '" + dictionary.toString() + "'");
		}
	}

	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
		// TODO Auto-generated method stub
		madeGuesses.add(guess);
		Map<String,Set<String>> patternMap = dict.makePatternSet(guess, correctGuesses);
		Iterator it = patternMap.entrySet().iterator();
		Set<String> maxSet = new TreeSet<String>();
		String maxPattern = new String();
		//find max set
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			Set<String> temp = (Set<String>)pair.getValue();
//			System.out.println("Checking '" + pair.getKey()+"'("+temp.size()+")" + " against max: '"+maxPattern+"'("+maxSet.size()+")");
			if(temp.size() > maxSet.size()){
				maxSet = temp;
				maxPattern = (String)pair.getKey();
			}
			else if(temp.size() == maxSet.size()){
				//resolve tie breaker
				//1.Choose the group in which the letter does not appear at all
				//2.If each group has the guess letter, choose the one with the fewest
				System.out.println("Tie breaker");
				String patternCurrent = (String)pair.getKey();
				int countCurrent = 0;
				int countMax = 0;
					//find out how many times letter appears in pattern
				for(int i=0;i<patternCurrent.length();i++){
					if(patternCurrent.charAt(i) == guess){
						countCurrent++;
					}
					else if(maxPattern.charAt(i) == guess)
						countMax++;
				}
				if(countCurrent < countMax){
					maxSet = temp;
					maxPattern = patternCurrent;
				}
				//3. If this still has not resolved the issue, choose the one with the rightmost guessed letter
				/*4. If there is still more than one group, choose the one with the next rightmost letter
				 * Repeat this step (step 4) until a group is chosen*/
				else if(countCurrent == countMax){
//					System.out.println("\tCountCurrent = "+countCurrent);
//					System.out.println("\tCountmax = "+ countMax);
//					System.out.println("\tpatternCurrent = " + patternCurrent);
//					System.out.println("\tmaxPattern = "+maxPattern);
					for(int i=patternCurrent.length()-1;i >= 0;i--){
						System.out.println("i = "+i);
						if(patternCurrent.charAt(i) != '-' && maxPattern.charAt(i) == '-'){
							maxSet = temp;
							maxPattern = patternCurrent;
							break;
						}
					}
				}
			}
		}
//		System.out.println("Max pattern: " + maxPattern);
//		System.out.println("Words in set:");
//		for(String w:maxSet){
//			System.out.println(w);
//		}
		return maxSet;
	}
	public String getPattern(String word,Set<Character> correctGuesses,char guess){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<word.length();i++){
			boolean exsistingChar = false;
			if(word.charAt(i)==guess){
				exsistingChar = true;
				sb.append(guess);
			}
			else{
				for(char c:correctGuesses){
					if(c == word.charAt(i)){
						sb.append(c);
						exsistingChar = true;
						break;
					}
				}
			}
			if(!exsistingChar){
				sb.append("-");
			}
		}
		return sb.toString();
	}
}
