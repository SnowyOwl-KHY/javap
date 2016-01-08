package me.kehycs.javap.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

public class MethodHandleConstant extends ConstantInfo {

    private int referenceKind;

    private int referenceIndex;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException {
        referenceKind = dataInputStream.readUnsignedByte();
        referenceIndex = dataInputStream.readUnsignedShort();
    }

    @Override
    public String getTypeName() {
        return "MethodHandle";
    }

    @Override
    public String getContent() {
        return referenceKind + "," + referenceIndex;
    }

    @Override
    public String getRealContent() {
        return referenceKind + "," + constantInfoProvider.getConstantInfo(referenceIndex).getRealContent();
    }
}
