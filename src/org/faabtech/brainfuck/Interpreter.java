package org.faabtech.brainfuck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * The {@link Interpreter} class interpret the given <code>BrainFuck</code> code.
 * 
 * @author Fabian M.
 */
public class Interpreter {


	/**
	 * The memory thats available for this brainfuck program.
	 */
	private byte[] data;

	/**
	 * The data pointer that points to the current index in the {@link Interpreter#data} memory array.
	 */
	private int dataPointer = 0;

	/**
	 * The character pointer that points to the current index of the character array
	 * 	of value of its file or string.
	 */
	private int charPointer = 0;
	
	/**
	 * The {@link Interpreter#fileReader} allows use to read from a file if one is specified.
	 */
	private BufferedReader fileReader;
	
	/**
	 * The {@link Interpreter#consoleReader} allows us to read from the console for the '.' keyword.
	 */
	private InputStreamReader consoleReader;
	
	/**
	 * The {@link Interpreter#outWriter} allows us to write to the console.
	 */
	private OutputStream outWriter;
	
	/**
	 * The current line we are at.
	 */
	private int lineCount = 0;
	
	/**
	 * The current collumn we are at.
	 */
	private int collumnCount = 0;

	/**
	 * Constructs a new Interpreter.
	 * 
	 * @param cells
	 *            The amount of memory cells.
	 */
	public Interpreter(int cells) {
		this(cells, new PrintStream(System.out), System.in);
	}
	
	/**
	 * Constructs a new Interpreter.
	 * 
	 * @param cells
	 *            The amount of memory cells.
	 * @param out The outputstream of this program.
	 */
	public Interpreter(int cells, OutputStream out) {
		this(cells, out, System.in);
	}
	
	/**
	 * Constructs a new Interpreter.
	 * 
	 * @param cells
	 *            The amount of memory cells.
	 * @param out The printstream of this program.
	 * @param in The outputstream of this program.
	 */
	public Interpreter(int cells, OutputStream out, InputStream in) {
		initate(cells);
		outWriter = out;
		consoleReader = new InputStreamReader(in);
	}
	
	/**
	 * Initiate this instance.
	 * 
	 * @param cells 
	 * 	     The amount of memory cells.
	 */
	public void initate(int cells) {
		data = new byte[cells];
		dataPointer = 0;
		charPointer = 0;
		lineCount = 0;
		collumnCount = 0;
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
			lineCount++;
			
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
		for (; charPointer < str.length(); charPointer++) 
			interpret(str.chatAt(charPointer), chars);
		initate(cells);
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
				throw new Exception("Error on line " + lineCount + ", collumn " + collumnCount + ":" 
						+ "data pointer (" + dataPointer
						+ ") on postion " + charPointer + "" + " out of range.");
			}
			dataPointer++;
			break;
		case '<':
			// decrement the data pointer (to point to the next cell to the
			// left).
			if ((dataPointer - 1) < 0) {
				throw new Exception("Error on line " + lineCount + ", collumn " + collumnCount + ":" 
						+ "data pointer (" + dataPointer
						+ ") on postion " + charPointer + " " + "negative.");
			}
			dataPointer--;
			break;
		case '+':
			
			// increment (increase by one) the byte at the data pointer.
			if ((((int) data[dataPointer]) + 1) > Integer.MAX_VALUE) {
				throw new Exception("Error on line " + lineCount + ", collumn " + collumnCount + ":" 
						+ "byte value at data pointer ("
						+ dataPointer + ") " + " on postion " + charPointer
						+ " higher than byte max value.");
			}
			data[dataPointer]++;
			break;
		case '-':
			// decrement (decrease by one) the byte at the data pointer.
			/*if ((data[dataPointer] - 1) < 0) {
				throw new Exception("Error on line " + lineCount + ", collumn " + collumnCount + ":" 
						+ "at data pointer " + dataPointer
						+ " on postion " + charPointer
						+ ": Value can not be lower than zero.");
			}*/
			data[dataPointer]--;
			break;
		case '.':
			// Output the byte at the current index in a character.
			outWriter.print((char) data[dataPointer]);
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
		collumnCount++;
	}
}
