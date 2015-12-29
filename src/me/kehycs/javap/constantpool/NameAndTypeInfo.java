package me.kehycs.javap.constantpool;

import me.kehycs.javap.util.ConvertTool;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kehanyang on 12/29/15.
 */
public class NameAndTypeInfo extends ConstantInfo {

    private int nameIndex;

    private int descriptorIndex;

    @Override
    public void readData(InputStream inputStream) throws IOException {
        byte[] temp = new byte[2];
        inputStream.read(temp);
        nameIndex = (int) ConvertTool.parseNumber(temp);
        inputStream.read(temp);
        descriptorIndex = (int) ConvertTool.parseNumber(temp);
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
        return constantPoolSource.getConstantInfo(nameIndex).getRealContent()
                + ":" + constantPoolSource.getConstantInfo(descriptorIndex).getRealContent();
    }
}
