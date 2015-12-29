package me.kehycs.javap.constantpool;

import me.kehycs.javap.util.ConvertTool;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kehanyang on 12/29/15.
 */
public class ClassInfo extends ConstantInfo {

    private int nameIndex;

    @Override
    public void readData(InputStream inputStream) throws IOException {
        byte[] data = new byte[2];
        inputStream.read(data);
        nameIndex = (int) ConvertTool.parseNumber(data);
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
        return constantPoolSource.getConstantInfo(nameIndex).getRealContent();
    }
}
