package me.kehycs.javap.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

public class MethodRefInfo extends ConstantInfo {

    private int classIndex;

    private int nameAndTypeIndex;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException {
        classIndex = dataInputStream.readUnsignedShort();
        nameAndTypeIndex = dataInputStream.readUnsignedShort();
    }

    @Override
    public String getTypeName() {
        return "Methodref";
    }

    @Override
    public String getContent() {
        return "#" + classIndex + ".#" + nameAndTypeIndex;
    }

    @Override
    public String getRealContent() {
        return constantPoolSource.getConstantInfo(classIndex).getRealContent()
                + "." + constantPoolSource.getConstantInfo(nameAndTypeIndex).getRealContent();
    }
}
