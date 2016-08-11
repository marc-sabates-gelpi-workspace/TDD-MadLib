package net.marcus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class MadLibTest {

	private static final String FAKE_ADVERB = "fake-adverb";
	private static final String FAKE_ADJECTIVE = "fake-adjective";
	private static final String FAKE_VERB = "fake-verb";
	private static final String FAKE_NOUN = "fake-noun";
	private static final int TIMEOUT = 7000;

	@Test
	public void shouldTestUserPromptedSuccessfully4Times() {
		try (PipedInputStream pipedInputStream = new PipedInputStream();
				PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				PrintStream outputStream = new PrintStream(arrayOutputStream)) {
			
			pipedOutputStream.write(String.format("%s %s %s %s\n", FAKE_NOUN, FAKE_VERB, FAKE_ADJECTIVE, FAKE_ADVERB).getBytes());
			
			MadLib madLib = new MadLib(pipedInputStream, outputStream);
			madLib.playMadLib();
			
			String output = arrayOutputStream.toString(); 
			assertThat(output, containsString(FAKE_NOUN));
			assertThat(output, containsString(FAKE_VERB));
			assertThat(output, containsString(FAKE_ADJECTIVE));
			assertThat(output, containsString(FAKE_ADVERB));
			assertEquals(4, madLib.getSuccessfullyPromptedTimes());
			
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void shouldBlockWhenUserInputed3Times() {
		runWithInputAndExpectNTimesBlocking(String.format("%s %s %s\n", FAKE_NOUN, FAKE_VERB, FAKE_ADJECTIVE), 3);
	}

	@Test
	public void shouldBlockWhenUserInputedNilTimes() throws Exception {
		runWithInputAndExpectNTimesBlocking("\n", 0);
	}
	
	@Test
	public void shouldBlockWhenFirstInputNotAWord() throws Exception {
		runWithInputAndExpectNTimesBlocking("-1\n", 0);
	}
	
	@Test
	public void shouldBlockWhenSecondInputNotAWord() throws Exception {
		runWithInputAndExpectNTimesBlocking(String.format("%s -666\n", FAKE_NOUN), 1);
	}
	
	@Test
	public void shouldBlockWhenThirdInputNotAWord() throws Exception {
		runWithInputAndExpectNTimesBlocking(String.format("%s %s +567\n",FAKE_NOUN, FAKE_VERB), 2);
	}
	
	@Test
	public void shouldBlockWhenFourthInputNotAWord() throws Exception {
		runWithInputAndExpectNTimesBlocking(String.format("%s %s %s 9090\n", FAKE_NOUN, FAKE_VERB, FAKE_ADJECTIVE), 3);
	}

	private void runWithInputAndExpectNTimesBlocking(String input, int expectedPromptedTimes) {
		try (PipedInputStream pipedInputStream = new PipedInputStream();
				PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
				ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
				PrintStream outputStream = new PrintStream(arrayOutputStream)) {
			
			pipedOutputStream.write(input.getBytes());
			
			MadLib madLib = new MadLib(pipedInputStream, outputStream);
			Thread t = new Thread( () -> madLib.playMadLib() );
			long startTime = System.currentTimeMillis();
			t.start();
			t.join(TIMEOUT);
			
			assertEquals(true, System.currentTimeMillis() - startTime >= TIMEOUT);
			assertEquals(true, t.isAlive());
			assertEquals(expectedPromptedTimes, madLib.getSuccessfullyPromptedTimes());
			
			t.interrupt();
			t.join();
		} catch (IOException | InterruptedException e) {
			fail(e.getMessage());
		}
	}
}
