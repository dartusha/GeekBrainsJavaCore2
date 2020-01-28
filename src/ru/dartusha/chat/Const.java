package ru.dartusha.chat;

import java.util.regex.Pattern;

public class Const {
    final static String CHAT="Чат";
    final static String AUTH_HEADER="Авторизация";
    final static String CMD_CLOSED="$CLOSED$";
    final static String LOCAL_HOST="localhost";
    final static int    PORT=7777;
    final static String SERVER="server";
    static final Pattern MESSAGE_PATTERN = Pattern.compile("^/w (\\w+) (.+)", Pattern.MULTILINE);
}
