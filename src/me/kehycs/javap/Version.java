package me.kehycs.javap;

/**
 * Created by kehanyang on 12/29/15.
 */
public class Version {

    private int major;

    private int minor;

    public Version(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    @Override
    public String toString() {
        return "  minor version: " + minor + "\n  major version: " + major;
    }
}
