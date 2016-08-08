package net.marcus;

import java.io.InputStream;
import java.util.Scanner;

public class MadLib {

	private Scanner scanner;

	public MadLib(InputStream inputStream) {
		scanner = new Scanner(inputStream);
	}

	public int getSuccessfullyPromptedTimes() {
		int i;
		String text;
		for(i = 0; i < 4; i++){
			text = scanner.next();
		}
		return i;
	}

}
