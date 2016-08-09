package net.marcus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.junit.Test;

public class MadLibTest {

	private static final int TIMEOUT = 5000;

	@Test
	public void shouldTestUserPromptedSuccessfully4Times() {
		MadLib madLib;
		try (PipedInputStream pipedInputStream = new PipedInputStream();
				PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream)) {
			madLib = new MadLib(pipedInputStream);
			
			pipedOutputStream.write("fake-noun fake-verb fake-adjective fake-adverb\n".getBytes());
			
			madLib.playMadLib();
			assertEquals(4, madLib.getSuccessfullyPromptedTimes());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void shouldBlockWhenUserInputed3Times() {
		MadLib madLib;
		try (PipedInputStream pipedInputStream = new PipedInputStream();
				PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream)) {
			madLib = new MadLib(pipedInputStream);
			
			pipedOutputStream.write("fake-noun fake-verb fake-adjective\n".getBytes());
			
//			Thread t = new Thread(new Runnable() {
//			    public void run() {
//		    		madLib.playMadLib();
//			    }
//			});
			Thread t = new Thread( () -> madLib.playMadLib() );
			
			long startTime = System.currentTimeMillis();
			t.start();
			t.join(TIMEOUT);
			
			assertEquals(true, System.currentTimeMillis() - startTime >= TIMEOUT);
			assertEquals(true, t.isAlive());
			assertEquals(3, madLib.getSuccessfullyPromptedTimes());

			t.interrupt();
			t.join();
		} catch (IOException | InterruptedException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void shouldBlockWhenUserInputedNilTimes() throws Exception {
		MadLib madLib;
		try (PipedInputStream pipedInputStream = new PipedInputStream();
				PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream)) {
			madLib = new MadLib(pipedInputStream);
			
			pipedOutputStream.write("\n".getBytes());
			
			Thread t = new Thread( () -> madLib.playMadLib() );
			
			long startTime = System.currentTimeMillis();
			t.start();
			t.join(TIMEOUT);
			
			assertEquals(true, System.currentTimeMillis() - startTime >= TIMEOUT);
			assertEquals(true, t.isAlive());
			assertEquals(0, madLib.getSuccessfullyPromptedTimes());

			t.interrupt();
			t.join();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void shouldBlockWhenFirstInputNotAString() throws Exception {
		MadLib madLib;
		try (PipedInputStream pipedInputStream = new PipedInputStream();
				PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream)) {
			madLib = new MadLib(pipedInputStream);
			
			pipedOutputStream.write("-1\n".getBytes());
			
			Thread t = new Thread( () -> madLib.playMadLib() );
			
			long startTime = System.currentTimeMillis();
			t.start();
			t.join(TIMEOUT);
			
			assertEquals(true, System.currentTimeMillis() - startTime >= TIMEOUT);
			assertEquals(true, t.isAlive());
			assertEquals(0, madLib.getSuccessfullyPromptedTimes());

			t.interrupt();
			t.join();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void shouldBlockWhenSecondInputNotAString() throws Exception {
		MadLib madLib;
		try (PipedInputStream pipedInputStream = new PipedInputStream();
				PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream)) {
			madLib = new MadLib(pipedInputStream);
			
			pipedOutputStream.write("fake-noun -666\n".getBytes());
			
			Thread t = new Thread( () -> madLib.playMadLib() );
			
			long startTime = System.currentTimeMillis();
			t.start();
			t.join(TIMEOUT);
			
			assertEquals(true, System.currentTimeMillis() - startTime >= TIMEOUT);
			assertEquals(true, t.isAlive());
			assertEquals(1, madLib.getSuccessfullyPromptedTimes());

			t.interrupt();
			t.join();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void shouldBlockWhenThirdInputNotAString() throws Exception {
		MadLib madLib;
		try (PipedInputStream pipedInputStream = new PipedInputStream();
				PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream)) {
			madLib = new MadLib(pipedInputStream);
			
			pipedOutputStream.write("fake-noun fake-verb +567\n".getBytes());
			
			Thread t = new Thread( () -> madLib.playMadLib() );
			
			long startTime = System.currentTimeMillis();
			t.start();
			t.join(TIMEOUT);
			
			assertEquals(true, System.currentTimeMillis() - startTime >= TIMEOUT);
			assertEquals(true, t.isAlive());
			assertEquals(2, madLib.getSuccessfullyPromptedTimes());

			t.interrupt();
			t.join();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void shouldBlockWhenFourthInputNotAString() throws Exception {
		MadLib madLib;
		try (PipedInputStream pipedInputStream = new PipedInputStream();
				PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream)) {
			madLib = new MadLib(pipedInputStream);
			
			pipedOutputStream.write("fake-noun fake-verb fake-adjective 9090\n".getBytes());
			
			Thread t = new Thread( () -> madLib.playMadLib() );
			
			long startTime = System.currentTimeMillis();
			t.start();
			t.join(TIMEOUT);
			
			assertEquals(true, System.currentTimeMillis() - startTime >= TIMEOUT);
			assertEquals(true, t.isAlive());
			assertEquals(3, madLib.getSuccessfullyPromptedTimes());

			t.interrupt();
			t.join();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
}
