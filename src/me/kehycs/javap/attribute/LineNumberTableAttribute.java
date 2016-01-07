package me.kehycs.javap.attribute;

import me.kehycs.javap.exception.ClassFileParseException;
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
            int lineNumber = dataInputStream.readUnsignedShort();
            lineNumberInfoList.add(new Pair<>(startPC, lineNumber));
        }
    }
}
