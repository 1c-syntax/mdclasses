package org.github._1c_syntax.mdclasses.metadata.additional;

public class CompatibilityMode {

    private int major = 8;
    private int minor = 0;
    private int patch = 0;

    private final String DONT_USE = "DontUse";

    public CompatibilityMode(String value){

        if (value.toUpperCase().equals((DONT_USE.toUpperCase()))){
            major = 8;
            minor = 3;
            patch = 99;
            return;
        }

        // парсим версию, например Version_8_3_10
        String newValue = value.toUpperCase().replace("VERSION_", "");

        String[] array = newValue.split("_");
        major = Integer.parseInt(array[0]);
        minor = Integer.parseInt(array[1]);
        patch = Integer.parseInt(array[2]);

    }

    public CompatibilityMode(int major, int minor, int patch){
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public static int compareTo(CompatibilityMode versionA, CompatibilityMode versionB){

        // TODO: переделать в цикл
        if (versionA.major == versionB.major) {
            if (versionA.minor == versionB.minor) {
                if (versionA.patch == versionB.patch){
                    return 0;
                }
                else if (versionA.patch >= versionB.patch){
                    return -1;
                }
                else {
                    return 1;
                }
            }
            else if (versionA.minor >= versionB.minor) {
                return -1;
            }
            else {
                return 1;
            }
        }
        else if (versionA.major >= versionB.major){
            return -1;
        }
        else {
            return  1;
        }

    }

    public int getMajor(){
        return major;
    }

    public int getMinor(){
        return minor;
    }
    public int getPatch(){
        return patch;
    }



}
