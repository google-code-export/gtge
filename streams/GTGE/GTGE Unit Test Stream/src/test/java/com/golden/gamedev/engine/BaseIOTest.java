package com.golden.gamedev.engine;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import com.golden.gamedev.Game;
import com.golden.gamedev.engine.resource.ClassBasedResourceLoader;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * The {@link BaseIOTest} class provides a {@link TestCase} to verify the
 * behavior of the {@link BaseIOTest} class.
 * @author MetroidFan2002
 * @version 1.0
 * @since 0.2.4
 * @see BaseIO
 * @see TestCase
 * 
 */
public class BaseIOTest extends TestCase {
	
	/**
	 * The {@link BaseIO} instance under test.
	 */
	private BaseIO base;
	
	protected void setUp() throws Exception {
		this.base = new BaseIO(String.class);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#BaseIO(java.lang.Class, int)}.
	 */
	public final void testBaseIOClassInt() {
		this.base = new BaseIO(String.class, -42);
		assertNotNull(this.base);
		assertEquals(-42, this.base.getMode());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#BaseIO(java.lang.Class)}.
	 */
	public final void testBaseIOClass() {
		Assert.assertNotNull(this.base);
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#getURL(java.lang.String, int)}.
	 */
	public final void testGetURLStringInt() {
		this.base.setBase(Game.class);
		URL url = this.base.getURL("Game.dat", BaseIO.CLASS_URL);
		assertNotNull(url);
		url = this.base.getURL(
		        "src/main/resources/com/golden/gamedev/Game.dat",
		        BaseIO.WORKING_DIRECTORY);
		assertNotNull(url);
		url = this.base.getURL("com/golden/gamedev/Game.dat",
		        BaseIO.CLASS_LOADER);
		assertNotNull(url);
		url = this.base.getURL("com/golden/gamedev/Game.dat",
		        BaseIO.SYSTEM_LOADER);
		assertNotNull(url);
		try {
			url = this.base.getURL("Gameeeee.dat", BaseIO.CLASS_URL);
			fail("Expected RuntimeException - no resource exists for the given path.");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
		
		this.base.setBase(String.class);
		try {
			this.base.getURL("Game.dat", BaseIO.CLASS_URL);
			fail("Expected RuntimeException - cannot load with Java library class loader.");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#getURL(java.lang.String)}.
	 */
	public final void testGetURLString() {
		this.base.setBase(Game.class);
		this.base.setMode(BaseIO.CLASS_URL);
		assertNotNull(this.base.getURL("Game.dat"));
		
		this.base.setBase(String.class);
		try {
			this.base.getURL("Game.dat");
			fail("Expected RuntimeException - bad base class");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
		this.base.setBase(Game.class);
		this.base.setMode(300);
		assertNotNull(this.base.getURL("Game.dat"));
		
		assertEquals(BaseIO.CLASS_URL, this.base.getMode());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#getStream(java.lang.String, int)}
	 * .
	 */
	public final void testGetStreamStringInt() {
		this.base.setBase(Game.class);
		InputStream stream = this.base.getStream("Game.dat", BaseIO.CLASS_URL);
		assertNotNull(stream);
		stream = this.base.getStream(
		        "src/main/resources/com/golden/gamedev/Game.dat",
		        BaseIO.WORKING_DIRECTORY);
		assertNotNull(stream);
		stream = this.base.getStream("com/golden/gamedev/Game.dat",
		        BaseIO.CLASS_LOADER);
		assertNotNull(stream);
		stream = this.base.getStream("com/golden/gamedev/Game.dat",
		        BaseIO.SYSTEM_LOADER);
		assertNotNull(stream);
		try {
			stream = this.base.getStream("Gameeeee.dat", BaseIO.CLASS_URL);
			fail("Expected RuntimeException - no resource exists for the given path.");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
		
		this.base.setBase(String.class);
		try {
			this.base.getStream("Game.dat", BaseIO.CLASS_URL);
			fail("Expected RuntimeException - cannot load with Java library class loader.");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
		
		try {
			this.base.getStream(null, BaseIO.CLASS_URL);
			fail("Expected RuntimeException - cannot retrieve a null resource.");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#getStream(java.lang.String)}.
	 */
	public final void testGetStreamString() {
		this.base.setBase(Game.class);
		this.base.setMode(BaseIO.CLASS_URL);
		assertNotNull(this.base.getStream("Game.dat"));
		
		this.base.setBase(String.class);
		try {
			this.base.getStream("Game.dat");
			fail("Expected RuntimeException - bad base class");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
		this.base.setBase(Game.class);
		this.base.setMode(300);
		assertNotNull(this.base.getStream("Game.dat"));
		assertEquals(BaseIO.CLASS_URL, this.base.getMode());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#getInputStream(java.lang.String)}
	 * .
	 */
	public final void testGetInputStreamString() {
		this.base.setBase(Game.class);
		this.base.setMode(BaseIO.CLASS_URL);
		assertNotNull(this.base.getInputStream("Game.dat"));
		
		this.base.setBase(String.class);
		try {
			this.base.getInputStream("Game.dat");
			fail("Expected RuntimeException - bad base class");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
		this.base.setBase(Game.class);
		this.base.setMode(300);
		assertNotNull(this.base.getInputStream("Game.dat"));
		assertEquals(BaseIO.CLASS_URL, this.base.getMode());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#getFile(java.lang.String, int)}.
	 */
	public final void testGetFileStringInt() {
		this.base.setBase(Game.class);
		File file = this.base.getFile("Game.dat", BaseIO.CLASS_URL);
		assertNotNull(file);
		file = this.base.getFile(
		        "src/main/resources/com/golden/gamedev/Game.dat",
		        BaseIO.WORKING_DIRECTORY);
		assertNotNull(file);
		file = this.base.getFile("com/golden/gamedev/Game.dat",
		        BaseIO.CLASS_LOADER);
		assertNotNull(file);
		file = this.base.getFile("com/golden/gamedev/Game.dat",
		        BaseIO.SYSTEM_LOADER);
		assertNotNull(file);
		try {
			file = this.base.getFile("Gameeeee.dat", BaseIO.CLASS_URL);
			fail("Expected RuntimeException - no resource exists for the given path.");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
		
		this.base.setBase(String.class);
		try {
			this.base.getFile("Game.dat", BaseIO.CLASS_URL);
			fail("Expected RuntimeException - cannot load with Java library class loader.");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
		
		try {
			this.base.getFile(null, BaseIO.CLASS_URL);
			fail("Expected RuntimeException - cannot retrieve a null resource.");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#getFile(java.lang.String)}.
	 */
	public final void testGetFileString() {
		this.base.setBase(Game.class);
		this.base.setMode(BaseIO.CLASS_URL);
		assertNotNull(this.base.getFile("Game.dat"));
		
		this.base.setBase(Game.class);
		this.base.setMode(300);
		assertNotNull(this.base.getFile("Game.dat"));
		assertEquals(BaseIO.CLASS_URL, this.base.getMode());
		
		try {
			this.base.getFile(null);
			fail("Expected RuntimeException - cannot resolve null filename.");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#setFile(java.lang.String, int)}.
	 */
	public final void testSetFileStringInt() {
		this.base.setBase(Game.class);
		File fileToCompare = new File(Game.class.getResource("").getFile()
		        .replaceAll("%20", " ")
		        + File.separator + "blah");
		File file = this.base.setFile("blah", BaseIO.CLASS_URL);
		assertEquals(fileToCompare, file);
		fileToCompare = new File("blah");
		file = this.base.setFile("blah", BaseIO.WORKING_DIRECTORY);
		assertEquals(fileToCompare, file);
		fileToCompare = new File(Game.class.getClassLoader().getResource("")
		        .getFile().replaceAll("%20", " ")
		        + File.separator + "blah");
		file = this.base.setFile("blah", BaseIO.CLASS_LOADER);
		assertEquals(fileToCompare, file);
		fileToCompare = new File(ClassLoader.getSystemResource("").getFile()
		        .replaceAll("%20", " ")
		        + File.separator + "blah");
		file = this.base.setFile("blah", BaseIO.SYSTEM_LOADER);
		assertEquals(fileToCompare, file);
		
		// Exception cases
		try {
			this.base.setFile("blah", 54334);
			fail("Expected RuntimeException - Unrecognized Mode.");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
		
		try {
			this.base.setFile(null, BaseIO.WORKING_DIRECTORY);
			fail("Expected RuntimeException - Null Argument");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#setFile(java.lang.String)}.
	 */
	public final void testSetFileString() {
		this.base.setBase(Game.class);
		this.base.setMode(BaseIO.CLASS_URL);
		File fileToCompare = new File(Game.class.getResource("").getFile()
		        .replaceAll("%20", " ")
		        + File.separator + "blah");
		File file = this.base.setFile("blah");
		assertEquals(fileToCompare, file);
		fileToCompare = new File("blah");
		this.base.setMode(BaseIO.WORKING_DIRECTORY);
		file = this.base.setFile("blah", BaseIO.WORKING_DIRECTORY);
		assertEquals(fileToCompare, file);
		fileToCompare = new File(Game.class.getClassLoader().getResource("")
		        .getFile().replaceAll("%20", " ")
		        + File.separator + "blah");
		this.base.setMode(BaseIO.CLASS_LOADER);
		file = this.base.setFile("blah", BaseIO.CLASS_LOADER);
		assertEquals(fileToCompare, file);
		fileToCompare = new File(ClassLoader.getSystemResource("").getFile()
		        .replaceAll("%20", " ")
		        + File.separator + "blah");
		this.base.setMode(BaseIO.SYSTEM_LOADER);
		file = this.base.setFile("blah", BaseIO.SYSTEM_LOADER);
		assertEquals(fileToCompare, file);
		
		// If a mode is not specified, the SYSTEM_LOADER will load if the class
		// was a java lang class...
		this.base.setBase(String.class);
		this.base.setMode(-234);
		file = this.base.setFile(null);
		assertNotNull(file);
		assertEquals(BaseIO.SYSTEM_LOADER, this.base.getMode());
		
		// If it is a user's class, it will be the first choice.
		this.base.setBase(Game.class);
		this.base.setMode(-234);
		file = this.base.setFile(null);
		assertNotNull(file);
		assertEquals(BaseIO.CLASS_URL, this.base.getMode());
		
		this.base.setBase(BaseGraphics.class);
		this.base.setMode(-234);
		file = this.base.setFile(null);
		assertNotNull(file);
		assertEquals(BaseIO.CLASS_URL, this.base.getMode());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#createFile(java.lang.String)}.
	 */
	public final void testCreateFile() {
		this.base.setBase(Game.class);
		File fileToCompare = new File(Game.class.getResource("").getFile()
		        .replaceAll("%20", " ")
		        + File.separator + "blah");
		File file = this.base.createFile("blah");
		assertEquals(fileToCompare, file);
		fileToCompare = new File("blah");
		this.base.setMode(BaseIO.WORKING_DIRECTORY);
		file = this.base.createFile("blah");
		assertEquals(fileToCompare, file);
		fileToCompare = new File(Game.class.getClassLoader().getResource("")
		        .getFile().replaceAll("%20", " ")
		        + File.separator + "blah");
		this.base.setMode(BaseIO.CLASS_LOADER);
		file = this.base.createFile("blah");
		assertEquals(fileToCompare, file);
		fileToCompare = new File(ClassLoader.getSystemResource("").getFile()
		        .replaceAll("%20", " ")
		        + File.separator + "blah");
		this.base.setMode(BaseIO.SYSTEM_LOADER);
		file = this.base.createFile("blah");
		assertEquals(fileToCompare, file);
		
		// Exception cases
		try {
			this.base.setMode(-24333);
			this.base.createFile("blah");
			fail("Expected RuntimeException - Unrecognized Mode.");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
		
		try {
			this.base.createFile(null);
			fail("Expected RuntimeException - Null Argument");
		}
		catch (RuntimeException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseIO#getRootPath(int)}
	 * .
	 */
	public final void testGetRootPath() {
		this.base.setBase(Game.class);
		assertEquals(Game.class.getResource("").toString(),
		        this.base.getRootPath(BaseIO.CLASS_URL));
		assertEquals(System.getProperty("user.dir") + File.separator,
		        this.base.getRootPath(BaseIO.WORKING_DIRECTORY));
		assertEquals(Game.class.getClassLoader().getResource("").toString(),
		        this.base.getRootPath(BaseIO.CLASS_LOADER));
		assertEquals(ClassLoader.getSystemResource("").toString(),
		        this.base.getRootPath(BaseIO.SYSTEM_LOADER));
		assertEquals("[UNKNOWN-MODE]", this.base.getRootPath(-223432));
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#getModeString(int)}.
	 */
	public final void testGetModeString() {
		assertEquals("Class-URL", this.base.getModeString(BaseIO.CLASS_URL));
		assertEquals("Working-Directory",
		        this.base.getModeString(BaseIO.WORKING_DIRECTORY));
		assertEquals("Class-Loader",
		        this.base.getModeString(BaseIO.CLASS_LOADER));
		assertEquals("System-Loader",
		        this.base.getModeString(BaseIO.SYSTEM_LOADER));
		assertEquals("[UNKNOWN-MODE]", this.base.getModeString(-223432));
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseIO#getMode()}.
	 */
	public final void testGetMode() {
		assertEquals(BaseIO.CLASS_URL, this.base.getMode());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseIO#setMode(int)}.
	 */
	public final void testSetMode() {
		this.base.setMode(-232);
		assertEquals(-232, this.base.getMode());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#setBase(java.lang.Class)}.
	 */
	public final void testSetBase() {
		this.base.setBase(Game.class);
		assertEquals(Game.class, this.base.getBase());
		try {
			this.base.setBase(null);
			fail("Expected NullPointerException - class may not be null as a classloader is required.");
		}
		catch (NullPointerException e) {
			// Intentionally blank
		}
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseIO#getBase()}.
	 */
	public final void testGetBase() {
		assertEquals(String.class, this.base.getBase());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseIO#getLoader()}.
	 */
	public final void testGetLoader() {
		assertEquals(String.class.getClassLoader(), this.base.getLoader());
	}
	
	/**
	 * Test method for {@link com.golden.gamedev.engine.BaseIO#toString()}.
	 */
	public final void testToString() {
		assertNotNull(this.base.toString());
	}
	
	/**
	 * Test method for
	 * {@link com.golden.gamedev.engine.BaseIO#getSelectedResourceLoader()}.
	 */
	public void testGetSelectedResourceLoader() throws Exception {
		assertTrue(this.base.getSelectedResourceLoader() instanceof ClassBasedResourceLoader);
		this.base.setMode(23232);
		assertEquals(base, this.base.getSelectedResourceLoader());
	}
}
