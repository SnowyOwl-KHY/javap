package me.kehycs.javap.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

public class LongConstant extends ConstantInfo {

    private long value;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException {
        value = dataInputStream.readLong();
    }

    @Override
    public String getTypeName() {
        return "Long";
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
