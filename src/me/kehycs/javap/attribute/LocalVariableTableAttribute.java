package me.kehycs.javap.attribute;

import me.kehycs.javap.constantpool.ConstantPoolSource;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LocalVariableTableAttribute extends AttributeInfo {

    List<LocalVariableInfo> localVariableInfoList = new ArrayList<>();

    @Override
    public void readData(InputStream inputStream) throws IOException, ClassFileParseException {
        byte[] tempData = new byte[2];

        inputStream.read(tempData);
        int localVariableInfoListLength = (int) ConvertTool.parseNumber(tempData);
        for (int i = 0; i < localVariableInfoListLength; ++i) {
            LocalVariableInfo localVariableInfo = new LocalVariableInfo(inputStream, constantPoolSource);
            localVariableInfoList.add(localVariableInfo);
        }
    }

    public static class LocalVariableInfo {

        private int startPC;

        private int length;

        private String name;

        private String descriptor;

        private int index;

        public LocalVariableInfo(InputStream inputStream, ConstantPoolSource constantPoolSource) throws IOException {
            byte[] tempData = new byte[2];

            inputStream.read(tempData);
            startPC = (int) ConvertTool.parseNumber(tempData);
            inputStream.read(tempData);
            length = (int) ConvertTool.parseNumber(tempData);
            inputStream.read(tempData);
            int nameIndex = (int) ConvertTool.parseNumber(tempData);
            name = constantPoolSource.getConstantInfo(nameIndex).getRealContent();
            inputStream.read(tempData);
            int descriptorIndex = (int) ConvertTool.parseNumber(tempData);
            descriptor = ConvertTool.parseSingleDescriptor(constantPoolSource.getConstantInfo(descriptorIndex).getRealContent());
            inputStream.read(tempData);
            index = (int) ConvertTool.parseNumber(tempData);
        }
    }
}
