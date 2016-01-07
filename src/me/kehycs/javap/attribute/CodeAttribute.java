package me.kehycs.javap.attribute;

import me.kehycs.javap.constantpool.ConstantPoolSource;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CodeAttribute extends AttributeInfo {

    private int maxStack;

    private int maxLocals;

    private byte[] code;

    private List<ExceptionInfo> exceptionTable = new ArrayList<>();

    private List<AttributeInfo> attributeInfoList = new ArrayList<>();

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException, ClassFileParseException {

        maxStack = dataInputStream.readUnsignedShort();
        maxLocals = dataInputStream.readUnsignedShort();

        int codeLength = dataInputStream.readInt();
        code = new byte[codeLength];
        dataInputStream.read(code);

        int exceptionTableLength = dataInputStream.readUnsignedShort();
        for (int i = 0; i < exceptionTableLength; ++i) {
            ExceptionInfo exceptionInfo = new ExceptionInfo(dataInputStream, constantPoolSource);
            exceptionTable.add(exceptionInfo);
        }

        int attributeCount = dataInputStream.readUnsignedShort();
        for (int i = 0; i < attributeCount; ++i) {
            AttributeInfo attributeInfo = AttributeInfo.newAttributeInfo(dataInputStream, constantPoolSource);
            attributeInfoList.add(attributeInfo);
        }
    }

    public static class ExceptionInfo {

        private int startPC;

        private int endPC;

        private int handlerPC;

        private String catchType;

        public ExceptionInfo(DataInputStream dataInputStream, ConstantPoolSource constantPoolSource) throws IOException {

            startPC = dataInputStream.readUnsignedShort();
            endPC = dataInputStream.readUnsignedShort();
            handlerPC = dataInputStream.readUnsignedShort();
            int catchTypeIndex = dataInputStream.readUnsignedShort();
            if (catchTypeIndex == 0) {
                catchType = "any";
            } else {
                catchType = constantPoolSource.getConstantInfo(catchTypeIndex).getRealContent();
            }
        }
    }
}
