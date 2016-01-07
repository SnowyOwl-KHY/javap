package me.kehycs.javap.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

public class ClassConstant extends ConstantInfo {

    private int nameIndex;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException {
        nameIndex = dataInputStream.readUnsignedShort();
    }

    @Override
    public String getTypeName() {
        return "Class";
    }

    @Override
    public String getContent() {
        return "#" + nameIndex;
    }

    @Override
    public String getRealContent() {
        return constantInfoProvider.getConstantInfo(nameIndex).getRealContent();
    }
}
