package me.kehycs.javap.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

public class MethodTypeConstant extends ConstantInfo {

    private int descriptorIndex;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException {
        descriptorIndex = dataInputStream.readUnsignedShort();
    }

    @Override
    public String getTypeName() {
        return "MethodType";
    }

    @Override
    public String getContent() {
        return String.valueOf(descriptorIndex);
    }

    @Override
    public String getRealContent() {
        return constantInfoProvider.getConstantInfo(descriptorIndex).getRealContent();
    }
}
