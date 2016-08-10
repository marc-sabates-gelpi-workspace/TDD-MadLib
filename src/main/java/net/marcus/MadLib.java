package net.marcus;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MadLib {

	private Scanner scanner;
	private int successfullyPromptedTimes = 0;
	private static final Pattern WORD_PATTERN = Pattern.compile("[a-zA-Z]+(?:\\-[a-zA-Z]+)?");
	private String words[] = new String [4];
	private static final String WORDS[] = {"noun", "verb", "adjective", "adverb"};
	private static final String MAD_PHRASE = "Do you %s your %s %s %s? That's hilarious!";
	
	public static void main(String args[]){
		MadLib madlib = new MadLib(System.in);
		madlib.playMadLib();
	}

	public MadLib(InputStream inputStream) {
		scanner = new Scanner(inputStream);
	}


	public void playMadLib() {
		retrieveInputs();
		printMadPhrase();
	}

	private void printMadPhrase() {
		System.out.println(String.format(MAD_PHRASE, words[1], words[2], words[0], words[3]));
		
	}

	private void retrieveInputs() {
		for(int i = 0; i < 4; i++){
			words[i] = getWord(i); 
		}
	}

	private String getWord(int num) {
		String word;
		do {
			System.out.print("Type a "+WORDS[num]+": ");
			word = scanner.next();
		} while(isNotAWord(word));
		
		successfullyPromptedTimes++;
		return word;
	}


	private boolean isNotAWord(String text) {
		return !WORD_PATTERN.matcher(text).matches();
	}

	public int getSuccessfullyPromptedTimes() {
		return successfullyPromptedTimes;
	}
	
	public String[] getWords(){
		return words;
	}
}
