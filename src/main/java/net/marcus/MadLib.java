package net.marcus;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MadLib {

	private Scanner scanner;
	private PrintStream outputStream;
	private int successfullyPromptedTimes = 0;
	private static final Pattern WORD_PATTERN = Pattern.compile("[a-zA-Z]+(?:\\-[a-zA-Z]+)?");
	private String words[] = new String [4];
	private static final String WORDS[] = {"noun", "verb", "adjective", "adverb"};
	private static final String MAD_PHRASE = "Do you %s your %s %s %s? That's hilarious!";
	
	public static void main(String args[]){
		MadLib madlib = new MadLib();
		madlib.playMadLib();
	}


	public MadLib() {
		scanner = new Scanner(System.in);
		outputStream = System.out;
	}
	
	public MadLib(InputStream inputStream, PrintStream outputStream) {
		scanner = new Scanner(inputStream);
		this.outputStream = outputStream;
	}

	public void playMadLib() {
		retrieveInputs();
		printMadPhrase();
	}
	
	public int getSuccessfullyPromptedTimes() {
		return successfullyPromptedTimes;
	}
	
	private void printMadPhrase() {
		outputStream.println(String.format(MAD_PHRASE, words[1], words[2], words[0], words[3]));
	}

	private void retrieveInputs() {
		for(int i = 0; i < 4; i++){
			words[i] = getWord(i); 
		}
	}

	private String getWord(int num) {
		String word = null;
		try{
			do {
				outputStream.print("Type a "+WORDS[num]+": ");
				word = scanner.next();
			} while(isNotAWord(word));
		} catch (NoSuchElementException e){
			//We just want to catch NoSuchElementException to prevent console pollution on testing
			//for we get the exception when the thread is blocked and it gets interrupted
		}
		
		successfullyPromptedTimes++;
		return word;
	}

	private boolean isNotAWord(String text) {
		return !WORD_PATTERN.matcher(text).matches();
	}
}
