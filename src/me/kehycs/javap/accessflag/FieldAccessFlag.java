package me.kehycs.javap.accessflag;

import me.kehycs.javap.exception.ClassFileParseException;

/**
 * Created by kehanyang on 12/30/15.
 */
public class FieldAccessFlag extends AccessFlag {

    public FieldAccessFlag(int flags) throws ClassFileParseException {
        super(flags);
    }

}
