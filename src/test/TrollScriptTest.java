package test;

import java.io.File;

import org.faabtech.brainfuck.impl.TrollScriptEngine;

public class TrollScriptTest {

	public static void main(String[] args) throws Exception {
		new TrollScriptEngine(30000).interpret(new File(
				"test/hello_world.troll"));
	}

}
