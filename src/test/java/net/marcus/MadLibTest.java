package net.marcus;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

public class MadLibTest {

	private MadLib madLib;
	private PipedInputStream pipedInputStream;
	private PipedOutputStream pipedOutputStream;
	
	@Before
	public void setUp() throws Exception {
		pipedInputStream = new PipedInputStream();
		pipedOutputStream = new PipedOutputStream(pipedInputStream);
		madLib = new MadLib(pipedInputStream);
	}

	@Test
	public void shouldTestUserPromptedSuccessfully4Times() {
		try {
			pipedOutputStream.write("fake.noun fake.verb fake.adjective fake.adverb\n".getBytes());
			assertEquals(4, madLib.getSuccessfullyPromptedTimes());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	@Ignore
	public void shouldTestUserPromptedSuccessfully3Times() {
		try {
			pipedOutputStream.write("fake.noun fake.verb fake.adjective\n".getBytes());
			assertEquals(3, madLib.getSuccessfullyPromptedTimes());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Ignore 
	public void shouldTestUserPromptedSuccessfullyNilTimes() throws Exception {
		try {
			pipedOutputStream.write("\n".getBytes());
			assertEquals(0, madLib.getSuccessfullyPromptedTimes());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
