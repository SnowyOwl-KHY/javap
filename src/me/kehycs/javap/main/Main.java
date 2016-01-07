package me.kehycs.javap.main;

import me.kehycs.javap.exception.ClassFileParseException;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassFileParseException {
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream("/Users/kehanyang/Test/Java/HelloWorld.class"));
        Parser parser = new Parser(dataInputStream);
        System.out.println(parser.parse());
        parser.close();
    }

}
