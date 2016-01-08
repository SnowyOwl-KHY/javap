package me.kehycs.javap.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

public class DoubleConstant extends ConstantInfo {

    private double value;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException {
        value = dataInputStream.readDouble();
    }

    @Override
    public String getTypeName() {
        return "Double";
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
