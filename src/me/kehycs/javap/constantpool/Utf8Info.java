package me.kehycs.javap.constantpool;

import me.kehycs.javap.util.ConvertTool;

import java.io.IOException;
import java.io.InputStream;

public class Utf8Info extends ConstantInfo {

    private String string;

    @Override
    public void readData(InputStream inputStream) throws IOException {
        byte[] temp = new byte[2];
        inputStream.read(temp);
        int length = (int) ConvertTool.parseNumber(temp);
        temp = new byte[length];
        inputStream.read(temp);
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
