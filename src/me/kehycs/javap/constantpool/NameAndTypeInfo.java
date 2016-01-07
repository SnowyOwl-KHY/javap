package me.kehycs.javap.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

public class NameAndTypeInfo extends ConstantInfo {

    private int nameIndex;

    private int descriptorIndex;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException {
        nameIndex = dataInputStream.readUnsignedShort();
        descriptorIndex = dataInputStream.readUnsignedShort();
    }

    @Override
    public String getTypeName() {
        return "NameAndType";
    }

    @Override
    public String getContent() {
        return "#" + nameIndex + ":#" + descriptorIndex;
    }

    @Override
    public String getRealContent() {
        return "\"" + constantPoolSource.getConstantInfo(nameIndex).getRealContent()
                + "\":" + constantPoolSource.getConstantInfo(descriptorIndex).getRealContent();
    }
}
