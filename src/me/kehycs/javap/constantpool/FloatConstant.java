package me.kehycs.javap.constantpool;

import java.io.DataInputStream;
import java.io.IOException;

public class FloatConstant extends ConstantInfo {

    private float value;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException {
        value = dataInputStream.readFloat();
    }

    @Override
    public String getTypeName() {
        return "Float";
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
