package me.kehycs.javap.attribute;

import me.kehycs.javap.constantpool.ConstantPoolSource;
import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExceptionAttribute extends AttributeInfo {

    private List<String> exceptionList = new ArrayList<>();

    @Override
    public void readData(InputStream inputStream) throws IOException, ClassFileParseException {
        byte[] tempData = new byte[2];

        inputStream.read(tempData);
        int exceptionNumber = (int) ConvertTool.parseNumber(tempData);
        for (int i = 0; i < exceptionNumber; ++i) {
            inputStream.read(tempData);
            int exceptionIndex = (int) ConvertTool.parseNumber(tempData);
            String exceptionClassName = constantPoolSource.getConstantInfo(exceptionIndex).getRealContent();
            exceptionList.add(exceptionClassName);
        }
    }
}
