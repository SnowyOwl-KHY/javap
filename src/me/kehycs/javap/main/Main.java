package me.kehycs.javap.main;

import me.kehycs.javap.exception.ClassFileParseException;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClassFileParseException {
        Scanner in = new Scanner(System.in);
        String filePath = in.nextLine();
        File file = new File(filePath);
        Parser parser = new Parser(file);
        System.out.println(parser.parse());
        parser.close();
        in.close();
    }

}
