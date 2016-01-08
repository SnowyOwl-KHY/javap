package me.kehycs.javap.attribute;

import me.kehycs.javap.exception.ClassFileParseException;

import java.io.DataInputStream;
import java.io.IOException;

public class ConstantValueAttribute extends AttributeInfo {

    private int constantValueIndex;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException, ClassFileParseException {
        constantValueIndex = dataInputStream.readUnsignedShort();
    }

    @Override
    public String describe(int blankNumber) {
        return null;
    }
}
