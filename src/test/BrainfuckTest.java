package test;

import java.io.File;

import org.faabtech.brainfuck.BrainfuckEngine;


public class BrainfuckTest {
	
	public static void main(String[] args) throws Exception {
		new BrainfuckEngine(30000).interpret(new File(
				"test/hello_world.bf"));
	}

}
