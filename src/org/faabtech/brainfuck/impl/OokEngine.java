package org.faabtech.brainfuck.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.faabtech.brainfuck.impl.TrollScriptEngine.Token;

/**
 * The {@link OokEngine} is an implementation for the
 * <code>brainfuck<code> dialect
 * 	<code>Ook!</code>.
 * 
 * @author Fabian M.
 */
public class OokEngine extends TrollScriptEngine {

	/**
	 * The default length of a token.
	 */
	protected int defaultTokenLength = 9;
	/**
	 * The {@link Token} class contains tokens in <code>Ook!</code>.
	 * 
	 * @author Fabian M.
	 */
	protected static class Token {
		public final static String NEXT = "Ook. Ook?";
		public final static String PREVIOUS = "Ook? Ook.";
		public final static String PLUS = "Ook. Ook.";
		public final static String MINUS = "Ook! Ook!";
		public final static String OUTPUT = "Ook! Ook.";
		public final static String INPUT = "Ook. Ook!";
		public final static String BRACKET_LEFT = "Ook! Ook?";
		public final static String BRACKET_RIGHT = "Ook? Ook!";
	}
	
	/**
	 * Constructs a new {@link OokEngine} instance.
	 * 
	 * @param cells
	 *            The amount of memory cells.
	 */
	public OokEngine(int cells) {
		this(cells, new PrintStream(System.out), System.in);
	}

	/**
	 * Constructs a new {@link OokEngine} instance.
	 * 
	 * @param cells
	 *            The amount of memory cells.
	 * @param out
	 *            The outputstream of this program.
	 */
	public OokEngine(int cells, OutputStream out) {
		this(cells, out, System.in);
	}

	/**
	 * Constructs a new {@link OokEngine} instance.
	 * 
	 * @param cells
	 *            The amount of memory cells.
	 * @param out
	 *            The printstream of this program.
	 * @param in
	 *            The outputstream of this program.
	 */
	public OokEngine(int cells, OutputStream out, InputStream in) {
		super(cells, out, in);
	}
	
	/**
	 * Interprets the given string.
	 * 
	 * @param str
	 *            The string to interpret.
	 * @throws Exception
	 */
	@Override
	public void interpret(String str) throws Exception {
		// List with tokens.defaultTokenLenght
		List<String> tokens = new ArrayList<String>();
		// It fine that all TrollScript tokens are 3 characters long :)
		// So we aren't going to loop through all tokens.
		for (; charPointer < str.length(); ) {
			String token = "";
			if (charPointer + defaultTokenLength <= str.length())
				// The string we found.
				token = str.substring(charPointer, charPointer + defaultTokenLength);
			else
				token = str.substring(charPointer, charPointer
						+ (str.length() - charPointer));
			// Is it a token?
			if (isValidToken(token)) {
				tokens.add(token);
				charPointer += defaultTokenLength;
			} else if (charPointer + defaultTokenLength > str.length()) {
				charPointer += (str.length() - charPointer);
			} else {
				charPointer++;
			}
		} 
		
		// Loop through all tokens.
		for (int tokenPointer = 0; tokenPointer < tokens.size(); ) {
			String token = tokens.get(tokenPointer);
			if (token.equalsIgnoreCase(Token.NEXT)) {
				// increment the data pointer (to point to the next cell
				// to the
				// right).
				dataPointer = (dataPointer == data.length - 1 ? 0 : dataPointer + 1);
			} 
			if (token.equalsIgnoreCase(Token.PREVIOUS)) {
				// decrement the data pointer (to point to the next cell
				// to the
				// left).
				dataPointer = (dataPointer == 0 ? data.length - 1 : dataPointer - 1);
			} 
			if (token.equalsIgnoreCase(Token.PLUS)) {
				// increment (increase by one) the byte at the data
				// pointer.
				data[dataPointer]++;
			} 
			if (token.equalsIgnoreCase(Token.MINUS)) {
				// decrement (decrease by one) the byte at the data
				// pointer.
				data[dataPointer]--;
			}
			if (token.equalsIgnoreCase(Token.OUTPUT)) {
				// Output the byte at the current index in a character.
				outWriter.write((char) data[dataPointer]);
			} 
			if (token.equalsIgnoreCase(Token.INPUT)) {
				// accept one byte of input, storing its value in the
				// byte at the data pointer.
				data[dataPointer] = (byte) consoleReader.read();
			} 
			if (token.equalsIgnoreCase(Token.BRACKET_LEFT)) {
				if (data[dataPointer] == 0) {
					int level = 1;
					while (level > 0) {	
						tokenPointer++;
						
						if (tokens.get(tokenPointer).equalsIgnoreCase(Token.BRACKET_LEFT)) 
							level++;
						else if (tokens.get(tokenPointer).equalsIgnoreCase(Token.BRACKET_RIGHT))
							level--;
					}
				}
			}
			if (token.equalsIgnoreCase(Token.BRACKET_RIGHT)) {
				if (data[dataPointer] != 0) {
					int level = 1;
					while (level > 0) {
						tokenPointer--;
						
						if (tokens.get(tokenPointer).equalsIgnoreCase(Token.BRACKET_LEFT)) 
							level--;
						else if (tokens.get(tokenPointer).equalsIgnoreCase(Token.BRACKET_RIGHT))
							level++;
					}
				}
			}
			
			tokenPointer++;
		}
		// Flush the outputstream.
		outWriter.flush();
		// Clear all data.
		initate(data.length);
	}
	
	/**
	 * Is the given token a valid <code>Ook!</code> token.
	 * 
	 * @param token
	 *            The token to check.
	 * @return <code>true</code> if the given token is a valid
	 *         <code>Ook!</code> token, <code>false</code> otherwise.
	 */
	protected boolean isValidToken(String token) {
		if (token.equalsIgnoreCase(Token.NEXT)
				|| token.equalsIgnoreCase(Token.PREVIOUS) || token.equalsIgnoreCase(Token.PLUS)
				|| token.equalsIgnoreCase(Token.MINUS) || token.equalsIgnoreCase(Token.OUTPUT)
				|| token.equalsIgnoreCase(Token.INPUT)
				|| token.equalsIgnoreCase(Token.BRACKET_LEFT)
				|| token.equalsIgnoreCase(Token.BRACKET_RIGHT)) {
			return true;
		}
		return false;
	}
}
