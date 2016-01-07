package me.kehycs.javap.attribute;

import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;
import me.kehycs.javap.util.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LineNumberTableAttribute extends AttributeInfo {

    private List<Pair<Integer, Integer>> lineNumberInfoList = new ArrayList<>();

    @Override
    public void readData(InputStream inputStream) throws IOException, ClassFileParseException {
        byte[] tempData = new byte[2];

        inputStream.read(tempData);
        int lineNumberInfoListLength = (int) ConvertTool.parseNumber(tempData);
        for (int i = 0; i < lineNumberInfoListLength; ++i) {
            inputStream.read(tempData);
            int startPC = (int) ConvertTool.parseNumber(tempData);
            inputStream.read(tempData);
            int lineNumber = (int) ConvertTool.parseNumber(tempData);
            lineNumberInfoList.add(new Pair<>(startPC, lineNumber));
        }
    }
}
