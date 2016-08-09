package net.marcus;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MadLib {

	private Scanner scanner;
	private int successfullyPromptedTimes = 0;
	private static final Pattern WORD_PATTERN = Pattern.compile("[a-zA-Z]+(?:\\-[a-zA-Z]+)?");
	private String words[] = new String [4];
	private static final String WORDS[] = {"noun", "verb", "adjective", "adverb"}; 

	public MadLib(InputStream inputStream) {
		scanner = new Scanner(inputStream);
	}


	public void playMadLib() {
		retrieveInputs();
	}

	private void retrieveInputs() {
		for(int i = 0; i < 4; i++){
			words[i] = getWord(i); 
		}
	}

	private String getWord(int num) {
		String word;
		do {
			System.out.println("Type a "+WORDS[num]+": ");
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
