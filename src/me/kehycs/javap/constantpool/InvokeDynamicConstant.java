package me.kehycs.javap.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

public class InvokeDynamicConstant extends ConstantInfo {

    private int bootstrapMethodAttrIndex;

    private int nameAndTypeIndex;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException {
        bootstrapMethodAttrIndex = dataInputStream.readUnsignedShort();
        nameAndTypeIndex = dataInputStream.readUnsignedShort();
    }

    @Override
    public String getTypeName() {
        return "InvokeDynamic";
    }

    @Override
    public String getContent() {
        return bootstrapMethodAttrIndex + "," + nameAndTypeIndex;
    }

    @Override
    public String getRealContent() {
        return bootstrapMethodAttrIndex + "," + constantInfoProvider.getConstantInfo(nameAndTypeIndex).getRealContent();
    }
}
