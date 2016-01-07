package me.kehycs.javap.attribute;

import me.kehycs.javap.constantpool.ConstantPoolSource;
import me.kehycs.javap.util.ConvertTool;

import java.io.IOException;
import java.io.InputStream;

public class ExceptionInfo {

    private int startPC;

    private int endPC;

    private int handlerPC;

    private String catchType;

    public ExceptionInfo(InputStream inputStream, ConstantPoolSource constantPoolSource) throws IOException {
        byte[] tempData = new byte[2];

        inputStream.read(tempData);
        startPC = (int) ConvertTool.parseNumber(tempData);
        inputStream.read(tempData);
        endPC = (int) ConvertTool.parseNumber(tempData);
        inputStream.read(tempData);
        handlerPC = (int) ConvertTool.parseNumber(tempData);
        inputStream.read(tempData);
        int catchTypeIndex = (int) ConvertTool.parseNumber(tempData);
        if (catchTypeIndex == 0) {
            catchType = "any";
        } else {
            catchType = constantPoolSource.getConstantInfo(catchTypeIndex).getRealContent();
        }
    }
}
