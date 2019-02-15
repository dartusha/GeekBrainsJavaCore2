package ru.dartusha.chat;

public class DataProcess {
    public static Network network;
    public static String curuser;

    public DataProcess(Network network) {

    }

    public static void setNetwork(Network netw) {
        network = netw;
    }

    public static void setCuruser(String cur) {
        curuser = cur;
    }

    public static String getCuruser() {
        return curuser;
    }

    public  static Network getNetwork(){
        return network;
    }
}
