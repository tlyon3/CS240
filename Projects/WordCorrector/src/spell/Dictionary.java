package spell;
import java.util.HashSet;
import java.util.Set;
public class Dictionary implements ITrie{
	char[] alphabet = new char[26];
	Dictionary(){
		root = new Node();
		nodeCount = 0;
		wordCount = 0;
		alphabet[0] ='a';
		alphabet[1] = 'b';
		alphabet[2] = 'c';
		alphabet[3] = 'd';
		alphabet[4] = 'e';
		alphabet[5] = 'f';
		alphabet[6] = 'g';
		alphabet[7] = 'h';
		alphabet[8] = 'i';
		alphabet[9]= 'j';
		alphabet[10] = 'k';
		alphabet[11] = 'l';
		alphabet[12] = 'm';
		alphabet[13] = 'n';
		alphabet[14] = 'o';
		alphabet[15] = 'p';
		alphabet[16] = 'q';
		alphabet[17] = 'r';
		alphabet[18] = 's';
		alphabet[19] = 't';
		alphabet[20] = 'u';
		alphabet[21] = 'v';
		alphabet[22] = 'w';
		alphabet[23] = 'x';
		alphabet[24] = 'y';
		alphabet[25] = 'z';
	}
	
	private Node root;
	private int nodeCount;
	private int wordCount;
	@Override
	public void add(String word) {
		word = word.toLowerCase();
		System.out.println("Adding word: " + word);
		word = word.toLowerCase();
		Node currentNode = new Node();
		for(int i=0;i<word.length();i++){
			int index = word.charAt(i)-'a';
			if(currentNode.nodes[index] == null){
				currentNode.nodes[index] = new Node();
				nodeCount++;
				System.out.println("Node count = " + nodeCount);
			}
			currentNode = currentNode.nodes[index];
			if(i == word.length() - 1){
				if(currentNode.getValue() == 0)
					wordCount++;
				currentNode.increaseCount();
				System.out.println("\tWord count = " +wordCount);
			}
		}
		//insertReflexive(root, word, 0, word.length());	
	}
	
//	private void insertReflexive(Node n, String s, int i, int max){
//		if(i==max){
//			if(n.getValue() == 0)
//				wordCount++;
//			n.increaseCount();
//			System.out.println("  Added word: "+s);
//			return;
//		}
//		if(n.nodes[s.charAt(i) - 'a'] == null){
//			n.nodes[s.charAt(i) - 'a'] = new Node();
//			nodeCount++;
//			insertReflexive(n.nodes[s.charAt(i) - 'a'],s,++i,max);
//		}
//	}

	@Override
	public INode find(String word) {
		return findReflexive(root, word, 0,word.length());
		
	}
	
	private INode findReflexive(Node n, String s, int i, int max){
		if(n.nodes[s.charAt(i) - 'a'] == null){
			return null;
		}
		if(i==max){
			return n.nodes[s.charAt(i) - 'a'];
		}
		else{
			return findReflexive(n.nodes[s.charAt(i)-'a'],s,++i,max);
		}
		
	}
	
	@Override
	public String toString(){
		StringBuilder word = new StringBuilder();
		StringBuilder output = new StringBuilder();
		toStringHelper(root, word, output);
		return output.toString();
	}
	private void toStringHelper(Node n, StringBuilder word, StringBuilder output){
		if(n==null)
			return;
		if(n.getValue() > 0){
			output.append(word.toString() + "\n");
		}
		else{
			for(int i=0;i<26;i++){
				word.append('a'+i);
				toStringHelper(n.nodes[i],word,output);
				word.setLength(word.length()-1);
			}
		}
	}
//	public boolean equals(ITrie trie){
//		if(wordCount != trie.getWordCount() || nodeCount!=trie.getNodeCount())
//			return false;
//		else return equalsReflexive(root, trie.root);
//	}
//	private boolean equalsReflexive(Node n1, Node n2){
//		for(int i=0;i<26;i++){
//			if(n1.nodes[i]!=n2.nodes[i])
//				return false;
//			else if(n1.nodes[i].getValue()!=n2.nodes[i].getValue())
//				return false;
//			else return equalsReflexive(n1.nodes[i],n2.nodes[i]);
//		}
//		return true;
//	}
	
	@Override
	public int getWordCount() {
		return wordCount;
	}

	@Override
	public int getNodeCount() {
		return nodeCount;
	}
	
	public Set<String> modifyOne(String word){
		Set<String> modifiedWords = new HashSet<String>();//avoid duplications
		for(String modWord : deletion(word))
			modifiedWords.add(modWord);
		for(String modWord : insertion(word))
			modifiedWords.add(modWord);
		for(String modWord : alteration(word))
			modifiedWords.add(modWord);
		for(String modWord : transposition(word))
			modifiedWords.add(modWord);
		return modifiedWords;
	}
	
	public Set<String> modifyTwo(Set<String> words){
		Set<String> doubleModifiedWords = new HashSet<String>();
		for(String word : words){
			for(String modWord : deletion(word))
				doubleModifiedWords.add(modWord);
			for(String modWord : insertion(word))
				doubleModifiedWords.add(modWord);
			
		}
		return doubleModifiedWords;
	}
	
	public String[] deletion(String word){
		String[] modifiedWords = new String[word.length()];
		for(int i=0;i<modifiedWords.length;i++){
			StringBuilder sb = new StringBuilder();
			//skip index i
			sb.append(word.substring(0,i));//get everything before index i
			sb.append(word.substring(i+1));//get everything after index i
			modifiedWords[i] = sb.toString();
		}
		return modifiedWords;
	}
	
	public String[] insertion(String word){
		String[] modifiedWords = new String[(word.length()+1)*26];
		for(int i=0;i<word.length()+1;i++){
			int j=0;
			for(int k=0;k<26;k++){
				StringBuilder sb = new StringBuilder();
				sb.append(word.substring(0,i));//everything before insertion index
				sb.append(alphabet[k]);			//insert at insertion index
				sb.append(word.substring(i));	//everything after insertion index
				modifiedWords[i*26 + j++] = sb.toString();
			}
		}
		return modifiedWords;
	}
	
	public String[] transposition(String word){
		String[] modifiedWords = new String[word.length()-1];
		for(int i=0;i<word.length()-1;i++){
			StringBuilder sb = new StringBuilder();
			sb.append(word.substring(0,i));//everything before transposition index
			sb.append(word.charAt(i+1));//char at transposition index+1
			sb.append(word.charAt(i));//char at transposition index
			sb.append(word.substring(i+2));//everything after transposition index+2
			modifiedWords[i] = sb.toString();
		}
		return modifiedWords;
	}
	
	public String[] alteration(String word){
		String[] modifiedWords = new String[word.length()*25];
		for(int i=0;i<word.length();i++){
			int j=0;
			char currentLetter = word.charAt(i);
			for(int k=0;k<26;k++){
				if(currentLetter == alphabet[k])
					continue;
			StringBuilder sb = new StringBuilder();
			sb.append(word.substring(0, i));//get everything before alteration index
			sb.append(alphabet[k]);//change char at alteration index
			sb.append(word.substring(i+1));//get everything after alteration index
			modifiedWords[i*25+(j++)] = sb.toString();
			}
		}
		return modifiedWords;
	}
}