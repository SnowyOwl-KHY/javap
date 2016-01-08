package me.kehycs.javap.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

public class IntegerConstant extends ConstantInfo {

    private int value;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException {
        value = dataInputStream.readInt();
    }

    @Override
    public String getTypeName() {
        return "Integer";
    }

    @Override
    public String getContent() {
        return String.valueOf(value);
    }

    @Override
    public String getRealContent() {
        return String.valueOf(value);
    }
}
