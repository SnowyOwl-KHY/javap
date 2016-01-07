package me.kehycs.javap.constantpool;

import me.kehycs.javap.util.ConvertTool;

import java.io.IOException;
import java.io.InputStream;

public class MethodRefInfo extends ConstantInfo {

    private int classIndex;

    private int nameAndTypeIndex;

    @Override
    public void readData(InputStream inputStream) throws IOException {
        byte[] temp = new byte[2];
        inputStream.read(temp);
        classIndex = (int) ConvertTool.parseNumber(temp);
        inputStream.read(temp);
        nameAndTypeIndex = (int) ConvertTool.parseNumber(temp);
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
