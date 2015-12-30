package me.kehycs.javap;

import me.kehycs.javap.accessflag.ClassAccessFlag;
import me.kehycs.javap.constantpool.ConstantInfo;
import me.kehycs.javap.constantpool.ConstantPoolSource;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kehanyang on 12/29/15.
 */
public class Parser implements ConstantPoolSource {

    private static final byte[] MAGIC_NUMBER = {(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};

    private InputStream inputStream;

    private Version version;

    private List<ConstantInfo> constantPool = new ArrayList<>();

    private ClassAccessFlag classAccessFlag;

    private String className;

    private String superClassName;

    private List<String> interfaceNames = new ArrayList<>();

    private List<FieldInfo> fieldInfoList = new ArrayList<>();

    public Parser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String parse() throws IOException, ClassFileParseException {

        readMagicNumber();

        readVersion();

        readConstantPool();

        readAccessFlag();

        readClassInfo();

        readFieldInfo();

        return describe();
    }

    private boolean isMagicNumber(byte[] bytes) {
        if (bytes.length != MAGIC_NUMBER.length) {
            return false;
        }
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != MAGIC_NUMBER[i]) {
                return false;
            }
        }
        return true;
    }

    private void readMagicNumber() throws IOException, ClassFileParseException {
        byte[] tempData = new byte[4];
        inputStream.read(tempData);
        if (!isMagicNumber(tempData)) {
            throw new ClassFileParseException("Magic number error.");
        }
    }

    private void readVersion() throws IOException {
        byte[] tempData = new byte[4];
        inputStream.read(tempData);
        int majorVersionNumber = (int) ConvertTool.parseNumber(tempData, 2, 4);
        int minorVersionNumber = (int) ConvertTool.parseNumber(tempData, 0, 2);
        version = new Version(majorVersionNumber, minorVersionNumber);
    }

    private void readConstantPool() throws IOException, ClassFileParseException {
        byte[] tempData = new byte[2];
        inputStream.read(tempData);
        int constantPoolCount = (int) ConvertTool.parseNumber(tempData) - 1;

        for (int i = 0; i < constantPoolCount; ++i) {
            ConstantInfo constantInfo = ConstantInfo.newConstantInfo(inputStream, this);
            constantPool.add(constantInfo);
        }
    }

    private void readAccessFlag() throws IOException, ClassFileParseException {
        byte[] tempData = new byte[2];
        inputStream.read(tempData, 0, 2);
        int accessFlags = (int) ConvertTool.parseNumber(tempData, 0, 2);
        classAccessFlag = new ClassAccessFlag(accessFlags);
    }

    private void readClassInfo() throws IOException {
        byte[] tempData = new byte[2];

        inputStream.read(tempData);
        int classIndex = (int) ConvertTool.parseNumber(tempData);
        className = getConstantInfo(classIndex).getRealContent();

        inputStream.read(tempData);
        int superClassIndex = (int) ConvertTool.parseNumber(tempData);
        superClassName = getConstantInfo(superClassIndex).getRealContent();

        inputStream.read(tempData);
        int interfaceNumber = (int) ConvertTool.parseNumber(tempData);
        for (int i = 0; i < interfaceNumber; ++i) {
            inputStream.read(tempData);
            int interfaceIndex = (int) ConvertTool.parseNumber(tempData);
            interfaceNames.add(getConstantInfo(interfaceIndex).getRealContent());
        }
    }

    private void readFieldInfo() throws IOException, ClassFileParseException {
        byte[] tempData = new byte[2];

        inputStream.read(tempData);
        int fieldInfoCount = (int) ConvertTool.parseNumber(tempData);
        for (int i = 0; i < fieldInfoCount; i++) {
            FieldInfo fieldInfo = new FieldInfo(inputStream, this);
            fieldInfoList.add(fieldInfo);
        }
    }

    public ConstantInfo getConstantInfo(int index) {
        return constantPool.get(index - 1);     // 常量池索引从1开始
    }

    private String describe() {
        StringBuilder result = new StringBuilder();

        describeClass(result);

        result.append(version).append("\n");
        result.append("  ").append(classAccessFlag).append("\n");

        describeConstantPool(result);

        result.append("{\n");

        describeFields(result);

        return result.toString();
    }

    private void describeClass(StringBuilder result) {
        result.append(classAccessFlag.getClassModifiers()).append(className);
        if (!superClassName.equals("java/lang/Object")) {
            result.append(" extends ").append(superClassName);
        }
        if (interfaceNames.size() > 0) {
            result.append(" implements ");
            for (int i = 0; i < interfaceNames.size(); ++i) {
                if (i > 0) {
                    result.append(", ");
                }
                result.append(interfaceNames.get(i));
            }
        }
        result.append("\n");
    }

    private void describeConstantPool(StringBuilder result) {
        result.append("Constant pool:\n");
        int maxIndexLength = String.valueOf(constantPool.size()).length();
        for (int i = 1; i <= constantPool.size(); i++) {
            ConstantInfo constantInfo = getConstantInfo(i);
            result.append("  ");
            int indexLengthDiff = maxIndexLength - String.valueOf(i).length();
            while (indexLengthDiff-- > 0) {
                result.append(" ");
            }
            result.append("#").append(i).append(" = ").append(constantInfo).append("\n");
        }
    }

    private void describeFields(StringBuilder result) {
        for (FieldInfo fieldInfo : fieldInfoList) {
            result.append("  ").append(fieldInfo).append('\n');
            result.append("    descriptor: ").append(fieldInfo.getDescriptor()).append('\n');
            result.append("    ").append(fieldInfo.getAccessFlag()).append("\n\n");
        }
    }
    
}
