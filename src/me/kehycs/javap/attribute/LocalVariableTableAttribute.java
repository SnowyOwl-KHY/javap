package me.kehycs.javap.attribute;

import me.kehycs.javap.constantpool.ConstantPoolSource;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalVariableTableAttribute extends AttributeInfo {

    List<LocalVariableInfo> localVariableInfoList = new ArrayList<>();

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException, ClassFileParseException {

        int localVariableInfoListLength = dataInputStream.readUnsignedShort();
        for (int i = 0; i < localVariableInfoListLength; ++i) {
            LocalVariableInfo localVariableInfo = new LocalVariableInfo(dataInputStream, constantPoolSource);
            localVariableInfoList.add(localVariableInfo);
        }
    }

    public static class LocalVariableInfo {

        private int startPC;

        private int length;

        private String name;

        private String descriptor;

        private int index;

        public LocalVariableInfo(DataInputStream dataInputStream, ConstantPoolSource constantPoolSource) throws IOException {
            startPC = dataInputStream.readUnsignedShort();

            length = dataInputStream.readUnsignedShort();

            int nameIndex = dataInputStream.readUnsignedShort();
            name = constantPoolSource.getConstantInfo(nameIndex).getRealContent();

            int descriptorIndex = dataInputStream.readUnsignedShort();
            descriptor = ConvertTool.parseSingleDescriptor(constantPoolSource.getConstantInfo(descriptorIndex).getRealContent());

            index = dataInputStream.readUnsignedShort();
        }
    }
}
