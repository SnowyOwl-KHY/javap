package me.kehycs.javap.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

public class UTF8Constant extends ConstantInfo {

    private String string;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException {
        int length = dataInputStream.readUnsignedShort();
        byte[] temp = new byte[length];
        dataInputStream.read(temp);
        string = new String(temp, "UTF-8");
    }

    @Override
    public String getTypeName() {
        return "Utf8";
    }

    @Override
    public String getContent() {
        return string;
    }

    @Override
    public String getRealContent() {
        return string;
    }
}
