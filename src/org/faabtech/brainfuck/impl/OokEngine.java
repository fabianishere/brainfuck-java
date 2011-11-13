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
	 * The {@link Token} class contains tokens of <code>Ook!</code>.
	 * 
	 * @author Fabian M.
	 */
	protected static enum Token {
		NEXT("Ook. Ook?"),
		PREVIOUS("Ook? Ook."),
		PLUS("Ook. Ook."),
		MINUS("Ook! Ook!"),
		OUTPUT("Ook! Ook."),
		INPUT("Ook. Ook!"),
		BRACKET_LEFT("Ook! Ook?"),
		BRACKET_RIGHT("Ook? Ook!");
		
		String value;
		
		/**
		 * Constructs a new token.
		 * 
		 * @param value The value of the token.
		 */
		Token(String value) {
			this.value = value;
		}
		
		/**
		 * Get the value of the token.
		 * 
		 * @return the value.
		 */
		public String getValue() {
			return value;
		}
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
		List<Token> tokens = new ArrayList<Token>();
		// It fine that all Ook! tokens are 6 characters long :)
		// So we aren't going to loop through all characters..
		for (; charPointer < str.length(); ) {
			String token = "";
			if (charPointer + defaultTokenLength <= str.length())
				// The string we found.
				token = str.substring(charPointer, charPointer + defaultTokenLength);
			else
				token = str.substring(charPointer, charPointer
						+ (str.length() - charPointer));
			
			boolean b = false;
			
			for (Token tokenCheck : Token.values()) {
				if (tokenCheck.getValue().equals(token)) {
					tokens.add(tokenCheck);
					charPointer += defaultTokenLength;
					b = true;
					break;
				}
			}
			
			// If the token was invalid, b is false. 
			if (!b)
				if (charPointer + defaultTokenLength > str.length()) 
					charPointer += (str.length() - charPointer);
				else 
					charPointer++;
				
				
		} 
		
		// Loop through all tokens.
		for (int tokenPointer = 0; tokenPointer < tokens.size(); ) {
<<<<<<< HEAD
			Token token = tokens.get(tokenPointer);
			switch(token) {
			case NEXT:
=======
			String token = tokens.get(tokenPointer);
			if (token.equals(Token.NEXT)) {
>>>>>>> 8723f8655b1c5e2155a86b8bcc3b763ee484ab29
				// increment the data pointer (to point to the next cell
				// to the
				// right).
				dataPointer = (dataPointer == data.length - 1 ? 0 : dataPointer + 1);
<<<<<<< HEAD
				break;
			case PREVIOUS:
=======
			} 
			if (token.equals(Token.PREVIOUS)) {
>>>>>>> 8723f8655b1c5e2155a86b8bcc3b763ee484ab29
				// decrement the data pointer (to point to the next cell
				// to the
				// left).
				dataPointer = (dataPointer == 0 ? data.length - 1 : dataPointer - 1);
<<<<<<< HEAD
				break;
			case PLUS:
				// increment (increase by one) the byte at the data
				// pointer.
				data[dataPointer]++;
				break;
			case MINUS:
				// decrement (decrease by one) the byte at the data
				// pointer.
				data[dataPointer]--;
				break;
			case OUTPUT:
				// Output the byte at the current index in a character.
				outWriter.write((char) data[dataPointer]);
				// Flush the outputstream.
				outWriter.flush();
				break;
			case INPUT:
				// accept one byte of input, storing its value in the
				// byte at the data pointer.
				data[dataPointer] = (byte) consoleReader.read();
				break;
			case BRACKET_LEFT:
=======
			} 
			if (token.equals(Token.PLUS)) {
				// increment (increase by one) the byte at the data
				// pointer.
				data[dataPointer]++;
			} 
			if (token.equals(Token.MINUS)) {
				// decrement (decrease by one) the byte at the data
				// pointer.
				data[dataPointer]--;
			}
			if (token.equals(Token.OUTPUT)) {
				// Output the byte at the current index in a character.
				outWriter.write((char) data[dataPointer]);
			} 
			if (token.equals(Token.INPUT)) {
				// accept one byte of input, storing its value in the
				// byte at the data pointer.
				data[dataPointer] = (byte) consoleReader.read();
			} 
			if (token.equals(Token.BRACKET_LEFT)) {
>>>>>>> 8723f8655b1c5e2155a86b8bcc3b763ee484ab29
				if (data[dataPointer] == 0) {
					int level = 1;
					while (level > 0) {	
						tokenPointer++;
						
						if (tokens.get(tokenPointer).equals(Token.BRACKET_LEFT)) 
							level++;
						else if (tokens.get(tokenPointer).equals(Token.BRACKET_RIGHT))
							level--;
					}
				}
<<<<<<< HEAD
				break;
			case BRACKET_RIGHT:
=======
			}
			if (token.equals(Token.BRACKET_RIGHT)) {
>>>>>>> 8723f8655b1c5e2155a86b8bcc3b763ee484ab29
				if (data[dataPointer] != 0) {
					int level = 1;
					while (level > 0) {
						tokenPointer--;
						
						if (tokens.get(tokenPointer).equals(Token.BRACKET_LEFT)) 
							level--;
						else if (tokens.get(tokenPointer).equals(Token.BRACKET_RIGHT))
							level++;
					}
				}
				break;
			}
			
			tokenPointer++;
		}
		// Clear all data.
		initate(data.length);
	}
	
<<<<<<< HEAD

=======
	/**
	 * Is the given token a valid <code>Ook!</code> token.
	 * 
	 * @param token
	 *            The token to check.
	 * @return <code>true</code> if the given token is a valid
	 *         <code>Ook!</code> token, <code>false</code> otherwise.
	 */
	protected boolean isValidToken(String token) {
		if (token.equals(Token.NEXT)
				|| token.equals(Token.PREVIOUS) || token.equals(Token.PLUS)
				|| token.equals(Token.MINUS) || token.equals(Token.OUTPUT)
				|| token.equals(Token.INPUT)
				|| token.equals(Token.BRACKET_LEFT)
				|| token.equals(Token.BRACKET_RIGHT)) {
			return true;
		}
		return false;
	}
>>>>>>> 8723f8655b1c5e2155a86b8bcc3b763ee484ab29
}
