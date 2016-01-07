package me.kehycs.javap.attribute;

import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CodeAttribute extends AttributeInfo {

    private int maxStack;

    private int maxLocals;

    private byte[] code;

    private List<ExceptionInfo> exceptionTable = new ArrayList<>();

    private List<AttributeInfo> attributeInfoList = new ArrayList<>();

    @Override
    public void readData(InputStream inputStream) throws IOException, ClassFileParseException {
        byte[] tempData = new byte[4];

        inputStream.read(tempData);
        maxStack = (int) ConvertTool.parseNumber(tempData, 0, 2);
        maxLocals = (int) ConvertTool.parseNumber(tempData, 2, 4);

        inputStream.read(tempData);
        int codeLength = (int) ConvertTool.parseNumber(tempData);
        code = new byte[codeLength];
        inputStream.read(code);

        inputStream.read(tempData, 0, 2);
        int exceptionTableLength = (int) ConvertTool.parseNumber(tempData);
        for (int i = 0; i < exceptionTableLength; ++i) {
            ExceptionInfo exceptionInfo = new ExceptionInfo(inputStream, constantPoolSource);
            exceptionTable.add(exceptionInfo);
        }

        inputStream.read(tempData, 0, 2);
        int attributeCount = (int) ConvertTool.parseNumber(tempData);
        for (int i = 0; i < attributeCount; ++i) {
            AttributeInfo attributeInfo = AttributeInfo.newAttributeInfo(inputStream, constantPoolSource);
            attributeInfoList.add(attributeInfo);
        }
    }
}
