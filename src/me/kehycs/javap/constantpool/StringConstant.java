package me.kehycs.javap.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

public class StringConstant extends ConstantInfo {

    private int valueIndex;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException {
        valueIndex = dataInputStream.readUnsignedShort();
    }

    @Override
    public String getTypeName() {
        return "String";
    }

    @Override
    public String getContent() {
        return String.valueOf(valueIndex);
    }

    @Override
    public String getRealContent() {
        return constantInfoProvider.getConstantInfo(valueIndex).getRealContent();
    }
}
