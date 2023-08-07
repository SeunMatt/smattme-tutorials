package com.smattme.vavr;

import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class VavrUnitTests {

	private final Logger logger = LoggerFactory.getLogger(VavrUnitTests.class);

	@Test
	void givenValidFilePathAndTryCatchBlock_whenReadFile_thenReturnFileContent() {
		String expectedFileContent = """
				Fortune favours the prepared!
				Are you prepared?
				""".trim();
		//before Vavr's Try Monad
		String filePath = "demo_file.txt";
		String fileContent = null;
		try {
			fileContent =  DemoService.readFile(filePath);
		} catch (Exception ex) {
			logger.error("Error reading file", ex);
		}
		assertNotNull(fileContent);
		assertEquals(expectedFileContent, fileContent);
	}

	@Test
	void givenValidFilePath_whenReadFile_thenReturnFileContent() {
		String expectedFileContent = """
				Fortune favours the prepared!
				Are you prepared?
				""".trim();


		//with Vavr's Try Monad
		var path = "demo_file.txt";
		var content = Try.of(() -> DemoService.readFile(path)).getOrNull();

		assertNotNull(content);
		assertEquals(expectedFileContent, content);
	}

	@Test
	void givenValidFilePathAndException_whenReadFile_thenLogExAndReturnNull() {
		var errorMsg = "Error reading file";

		/*
			if DemoService.readFile raise an exception,
			return null and log stack trace
		 */
		var path = "invalid_demo_file.txt";
		var content = Try.of(() -> DemoService.readFile(path))
				.onFailure(ex -> logger.error(errorMsg, ex))
				.getOrNull();

		assertNull(content);
	}

	@Test
	void givenValidFilePathAndException_whenReadFile_thenLogExAndReturnDefault() {


		var errorMsg = "Error reading file";
		/*
			if DemoService.readFile raise an exception,
			log error and then
			return defaultContent
		 */
		var defaultContent = "Java rules";
		var path = "invalid_demo_file.txt";
		var content = Try.of(() -> DemoService.readFile(path))
				.onFailure(ex -> logger.error(errorMsg, ex))
				.getOrElse(defaultContent);

		assertNotNull(content);
		assertEquals(defaultContent, content);
	}









}
