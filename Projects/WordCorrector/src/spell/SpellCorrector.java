package spell;
import java.io.*;
import java.util.Scanner;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.Map;
public class SpellCorrector implements ISpellCorrector {
	private Dictionary myDictionary;
	public Dictionary getDict(){
		return myDictionary;
	}
	public SpellCorrector(){
		myDictionary = new Dictionary();
	}
	public static void main(String[] args) throws NoSimilarWordFoundException{
		SpellCorrector sc = new SpellCorrector();
		SpellCorrector sc2 = new SpellCorrector();
		try{
			
			sc.useDictionary(args[0]);
			sc2.useDictionary(args[1]);
		
			System.out.println(sc.myDictionary.equals(sc2.getDict()));
			System.out.println("Dict1:\n" + sc.myDictionary.toStringWithValues());
			System.out.println("Dict2:\n" + sc2.getDict().toStringWithValues());
		}
		catch(IOException ex){
			System.out.println("Error reading file '"+args[0]+"'");
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
		if(inputWord.equals("")){
			throw new NoSimilarWordFoundException();
		}
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
				TreeMap<Integer,String> wordValueMap = new TreeMap<Integer, String>();
				for(String word : modifiedInDictionary){
					wordValueMap.put(myDictionary.findWordCount(word),word);
				}
//				System.out.println("Printing map");
//				for(Map.Entry<Integer, String> entry : wordValueMap.entrySet()){
//					System.out.println(entry.getKey()+": "+entry.getValue());
//				}
				//will always return word with greatest value and first alphabetical
				return wordValueMap.lastEntry().getValue();
			}
			if(!found){
				NoSimilarWordFoundException ex = new NoSimilarWordFoundException();
				throw ex;
			}
			else{//modified two word exists in dictionary. Find max count and return
				TreeMap<Integer,String> wordValueMap = new TreeMap<Integer, String>();
				for(String word : modifiedInDictionary){
					wordValueMap.put(myDictionary.findWordCount(word),word);
				}
				return wordValueMap.lastEntry().getValue();
			}
		}
		else{
//			System.out.println("'"+inputWord+"' spelled correctly!");
			return inputWord;
		}
	}

}
