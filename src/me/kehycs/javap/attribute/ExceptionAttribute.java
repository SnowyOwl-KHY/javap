package me.kehycs.javap.attribute;

import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExceptionAttribute extends AttributeInfo {

    private List<String> exceptionList = new ArrayList<>();

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException, ClassFileParseException {

        int exceptionNumber = dataInputStream.readUnsignedShort();
        for (int i = 0; i < exceptionNumber; ++i) {
            int exceptionIndex = dataInputStream.readUnsignedShort();
            String exceptionClassName = constantInfoProvider.getConstantInfo(exceptionIndex).getRealContent();
            exceptionList.add(exceptionClassName);
        }
    }

    @Override
    public String describe(int blankNumber) {
        StringBuilder result = new StringBuilder();

        result.append(ConvertTool.getBlank(blankNumber)).append("throws ");

        for (int i = 0; i < exceptionList.size(); ++i) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(exceptionList.get(i));
        }
        result.append("\n");
        return result.toString();
    }
}
