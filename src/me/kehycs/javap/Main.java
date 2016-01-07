package me.kehycs.javap;

import me.kehycs.javap.exception.ClassFileParseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) throws IOException, ClassFileParseException {
        InputStream inputStream = new FileInputStream("/Users/kehanyang/Test/Java/HelloWorld.class");
        Parser parser = new Parser(inputStream);
        System.out.println(parser.parse());
    }

}
