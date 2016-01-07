package me.kehycs.javap.attribute;

import me.kehycs.javap.exception.ClassFileParseException;
import me.kehycs.javap.util.ConvertTool;
import me.kehycs.javap.util.Pair;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LineNumberTableAttribute extends AttributeInfo {

    private List<Pair<Integer, Integer>> lineNumberInfoList = new ArrayList<>();

    @Override
    public void readData(DataInputStream dataInputStream) throws IOException, ClassFileParseException {

        int lineNumberInfoListLength = dataInputStream.readUnsignedShort();
        for (int i = 0; i < lineNumberInfoListLength; ++i) {
            int startPC = dataInputStream.readUnsignedShort();
            int sourceLineNumber = dataInputStream.readUnsignedShort();
            lineNumberInfoList.add(new Pair<>(startPC, sourceLineNumber));
        }
    }

    @Override
    public String describe(int blankNumber) {
        StringBuilder result = new StringBuilder();

        result.append(ConvertTool.getBlank(blankNumber)).append("LineNumberTable:\n");

        for (Pair<Integer, Integer> lineNumberInfo : lineNumberInfoList) {
            int sourceLineNumber = lineNumberInfo.second;
            int startPC = lineNumberInfo.first;
            result.append(ConvertTool.getBlank(blankNumber + 2)).append("line ").append(sourceLineNumber).append(": ").append(startPC).append('\n');
        }
        return result.toString();
    }
}
