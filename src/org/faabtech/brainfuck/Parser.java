package org.faabtech.brainfuck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * This parser parses <code>Brainfuck</code> code.
 * 
 * @author Fabian M.
 */
public class Parser {


	/**
	 * The data.
	 */
	private byte[] data;

	/**
	 * The data pointer.
	 */
	private int dataPointer = 0;

	/**
	 * The character pointer.
	 */
	private int charPointer = 0;
	
	/**
	 * BufferedReader to read from file.
	 */
	private BufferedReader fileReader;
	
	/**
	 * InputStreamReader to read from console.
	 */
	private InputStreamReader consoleReader;


	/**
	 * Constructs a new Parser.
	 * 
	 * @param cells
	 *            The amount of memory cells.
	 */
	public Parser(int cells) {
		data = new byte[cells];
		dataPointer = 0;
		charPointer = 0;
		consoleReader = new InputStreamReader(System.in);
	}
	
	/**
	 * Parses the given file.
	 * 
	 * @param file
	 * 			  The file to parse.
	 * @throws Exception 
	 */
	public void parse(File file) throws Exception {
		if (!file.exists()) {
			throw new Exception("File " + file.getPath() + " doesn't exists.");
		} 
		if (file.isDirectory()) {
			throw new Exception("File " + file.getPath() + " is directory.");
		}
		
		fileReader = new BufferedReader(new FileReader(file));
		String content = "";
		String line = "";
		while((line = fileReader.readLine()) != null) {
			content += line;
		}
		
		parse(content);
	}

	
	/**
	 * Parses the given string.
	 * 
	 * @param str
	 *            The string to parse.
	 * @throws Exception
	 */
	public void parse(String str) throws Exception {
		char[] chars = str.toCharArray();

		for (; charPointer < chars.length; charPointer++) {
			parse(chars[charPointer], chars);
		}
	}

	/**
	 * Parses the given char
	 * 
	 * @param c
	 *            The char to parse.
	 * @throws Exception
	 */
	public void parse(char c, char[] chars) throws Exception {
		switch (c) {
		case '>':
			// increment the data pointer (to point to the next cell to the
			// right).
			if ((dataPointer + 1) > data.length) {
				throw new Exception("Data pointer (" + dataPointer
						+ ") on postion " + charPointer + "" + " out of range.");
			}
			dataPointer++;
			break;
		case '<':
			// decrement the data pointer (to point to the next cell to the
			// left).
			if ((dataPointer - 1) < 0) {
				throw new Exception("Data pointer (" + dataPointer
						+ ") on postion " + charPointer + " " + "negative.");
			}
			dataPointer--;
			break;
		case '+':
			
			// increment (increase by one) the byte at the data pointer.
			if ((((int) data[dataPointer]) + 1) > Integer.MAX_VALUE) {
				throw new Exception("Byte value at data pointer ("
						+ dataPointer + ") " + " on postion " + charPointer
						+ " higher than byte max value.");
			}
			data[dataPointer]++;
			break;
		case '-':
			
			// decrement (decrease by one) the byte at the data pointer.
			/*if ((data[dataPointer] - 1) < 0) {
				throw new Exception("At data pointer " + dataPointer
						+ " on postion " + charPointer
						+ ": Value can not be lower than zero.");
			}*/
			data[dataPointer]--;
			break;
		case '.':
			System.out.print((char) data[dataPointer]);
			break;
		case ',':
			// accept one byte of input, storing its value in the byte at the data pointer.
			data[dataPointer] = (byte) consoleReader.read();
			break;
		case '[':
			if (data[dataPointer] == 0) {
				int i = 1;
				while (i > 0) {
					char c2 = chars[++charPointer];
					if (c2 == '[')
						i++;
					else if (c2 == ']')
						i--;
				}
			}
			break;
		case ']':
			int i = 1;
			while (i > 0) {
				char c2 = chars[--charPointer];
				if (c2 == '[')
					i--;
				else if (c2 == ']')
					i++;
			}
			charPointer--;
			break;
		}

	}
}
