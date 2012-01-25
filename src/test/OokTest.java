package test;

import java.io.File;

import org.faabtech.brainfuck.impl.OokEngine;


public class OokTest {

	public static void main(String[] args) throws Exception {
		new OokEngine(30000).interpret(new File(
				"samples/hello_world.ook"));
	}

}