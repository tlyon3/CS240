package spell;
import java.io.*;
import java.util.Scanner;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
public class SpellCorrector implements ISpellCorrector {
	private Dictionary myDictionary;
	SpellCorrector(){
		myDictionary = new Dictionary();
	}
	public static void main(String[] args) throws NoSimilarWordFoundException{
		SpellCorrector sc = new SpellCorrector();
		try{
			sc.useDictionary(args[0]);
			System.out.println("Dictionary:\n"+sc.myDictionary.toString());
		}
		catch(IOException ex){
			System.out.println("Error reading file: '"+args[0]+"'");
		}
	}
	
	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		try{
			java.io.File temp = new File(dictionaryFileName);
			java.io.FileReader fileReader = new FileReader(temp);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			Scanner myScanner = new Scanner (temp);
			while(myScanner.hasNext()){
				myDictionary.add(myScanner.next());
			}
			myScanner.close();
			bufferedReader.close();
		}
		catch(java.io.FileNotFoundException ex){
			System.out.println("Could not open file: '"+ dictionaryFileName + "'");
		}
	}

	@Override
	public String suggestSimilarWord(String inputWord)
			throws NoSimilarWordFoundException {
//		Set<String> modifiedWords = myDictionary.modifyOne(inputWord);
//		for(String word : modifiedWords){
//			System.out.println(word);
//		}
		inputWord = inputWord.toLowerCase();
		if(myDictionary.find(inputWord) == null){
			Set<String> modifiedWords = myDictionary.modifyOne(inputWord);
			Set<String> modifiedInDictionary = new HashSet<String>();
			boolean found = false;
			for(String word : modifiedWords){
				if(myDictionary.find(word)!=null){ //word exists in dictionary
					found = true;
					modifiedInDictionary.add(word);
				}
			}
			if(!found){//no modified one words exist in dictionary
				Set<String> modifiedTwoWords = myDictionary.modifyTwo(modifiedWords);
				for(String word : modifiedTwoWords){
					if(myDictionary.find(word)!=null){
						found = true;
						modifiedInDictionary.add(word);
					}
				}
			}
			else{//modified one word exists in dictionary. Find max count and return
				//TODO
			}
			if(!found){
				NoSimilarWordFoundException ex = new NoSimilarWordFoundException();
				throw ex;
			}
			else{//modified two word exists in dictionary. Find max count and return
				//TODO
			}
		}
		else{
			System.out.println("Word spelled correctly!");
		}
		return null;
	}

}