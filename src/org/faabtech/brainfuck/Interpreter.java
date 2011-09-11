package org.faabtech.brainfuck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * This interpreter interprets the Brainfuck code.
 * 
 * @author Fabian M.
 */
public class Interpreter {


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
	 * Constructs a new Interpreter.
	 * 
	 * @param cells
	 *            The amount of memory cells.
	 */
	public Interpreter(int cells) {
		data = new byte[cells];
		dataPointer = 0;
		charPointer = 0;
		consoleReader = new InputStreamReader(System.in);
	}
	
	/**
	 * Interprets the given file.
	 * 
	 * @param file
	 * 			  The file to interpret.
	 * @throws Exception 
	 */
	public void interpret(File file) throws Exception {	
		fileReader = new BufferedReader(new FileReader(file));
		String content = "";
		String line = "";
		while((line = fileReader.readLine()) != null) {
			content += line;
		}
		
		interpret(content);
	}

	
	/**
	 * Interprets the given string.
	 * 
	 * @param str
	 *            The string to interpret.
	 * @throws Exception
	 */
	public void interpret(String str) throws Exception {
		char[] chars = str.toCharArray();

		for (; charPointer < chars.length; charPointer++) {
			interpret(chars[charPointer], chars);
		}
	}

	/**
	 * Interprets the given char
	 * 
	 * @param c
	 *            The char to interpret.
	 * @throws Exception
	 */
	public void interpret(char c, char[] chars) throws Exception {
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
