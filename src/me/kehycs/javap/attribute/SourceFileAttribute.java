package me.kehycs.javap.attribute;

import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;

import java.io.DataInputStream;
import java.io.IOException;

public class SourceFileAttribute extends AttributeInfo {

    private String sourceFile;

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException, ClassFileParseException {
        int sourceFileIndex = dataInputStream.readUnsignedShort();
        sourceFile = constantInfoProvider.getConstantInfo(sourceFileIndex).getRealContent();
    }

    @Override
    public String describe(int blankNumber) {
        return ConvertTool.getBlank(blankNumber) + "SourceFile: " + sourceFile + "\n";
    }
}
