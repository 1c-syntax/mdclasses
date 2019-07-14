package org.github._1c_syntax.mdclasses.metadata.additional;

import org.github._1c_syntax.mdclasses.metadata.Configuration;

public class ConfigurationVersion {

    private int major = 8;
    private int minor = 0;
    private int patch = 0;

    private final String DONT_USE = "DontUse";

    public ConfigurationVersion (String value){

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

    public ConfigurationVersion(int major, int minor, int patch){
        this.major = major;
        this.minor = minor;
        this.patch = patch;
    }

    public ConfigurationVersion(){

    }

    public int compareTo(ConfigurationVersion versionB){

        // TODO: переделать в цикл
        if (major == versionB.major) {
            if (minor == versionB.minor) {
                if (patch == versionB.patch){
                    return 0;
                }
                else if (patch >= versionB.patch){
                    return -1;
                }
                else {
                    return 1;
                }
            }
            else if (minor >= versionB.minor) {
                return -1;
            }
            else {
                return 1;
            }
        }
        else if (major >= versionB.major){
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
