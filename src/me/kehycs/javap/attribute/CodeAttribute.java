package me.kehycs.javap.attribute;

import me.kehycs.javap.constantpool.ConstantInfoProvider;
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
            ExceptionInfo exceptionInfo = new ExceptionInfo(dataInputStream, constantInfoProvider);
            exceptionTable.add(exceptionInfo);
        }

        int attributeCount = dataInputStream.readUnsignedShort();
        for (int i = 0; i < attributeCount; ++i) {
            AttributeInfo attributeInfo = AttributeInfo.newAttributeInfo(dataInputStream, constantInfoProvider);
            attributeInfoList.add(attributeInfo);
        }
    }

    @Override
    public String describe(int blankNumber) {
        StringBuilder result = new StringBuilder();

        result.append(ConvertTool.getBlank(blankNumber)).append("Code:\n");

        result.append(ConvertTool.getBlank(blankNumber + 2)).append("stack=").append(maxStack).append(", locals=").append(maxLocals).append('\n');

        for (int i = 0; i < attributeInfoList.size(); ++i) {
            result.append(attributeInfoList.get(i).describe(blankNumber + 2));
        }
        return result.toString();
    }

    public static class ExceptionInfo {

        private int startPC;

        private int endPC;

        private int handlerPC;

        private String catchType;

        public ExceptionInfo(DataInputStream dataInputStream, ConstantInfoProvider constantInfoProvider) throws IOException {

            startPC = dataInputStream.readUnsignedShort();
            endPC = dataInputStream.readUnsignedShort();
            handlerPC = dataInputStream.readUnsignedShort();
            int catchTypeIndex = dataInputStream.readUnsignedShort();
            if (catchTypeIndex == 0) {
                catchType = "any";
            } else {
                catchType = constantInfoProvider.getConstantInfo(catchTypeIndex).getRealContent();
            }
        }
    }
}
