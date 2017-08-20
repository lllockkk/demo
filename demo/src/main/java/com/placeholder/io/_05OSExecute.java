package com.placeholder.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by L on 2017/8/17.
 */
public class _05OSExecute {
    public static void command(String command) {
        boolean err = false;
        try {
            Process process = new ProcessBuilder(command.split(" ")).start();
            BufferedReader results = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String s;
            while ((s = results.readLine()) != null)
                System.out.println(s);

            BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream(), "GBK"));
            while ((s = errors.readLine()) != null) {
                System.out.println(s);
                err = true;
            }
        } catch (IOException e) {
            if (!command.startsWith("CMD /C"))
                command("CMD /C " + command);
            else
                throw new RuntimeException(e);
        }
        if (err)
            throw new OSExecuteException("Errors executing " + command);
    }

    public static void main(String[] args) {
        _05OSExecute.command("dir");
    }
}

class OSExecuteException extends RuntimeException {
    public OSExecuteException(String message) {
        super(message);
    }
}