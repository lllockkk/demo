package com.placeholder;

import java.io.*;

public class App {

}

class BasicFileOutput {
    public static void main(String[] args) throws IOException {
        String src = "hello\nworld";
        BufferedReader br = new BufferedReader(new StringReader(src));
        String line;
        int counter = 1;

        while ((line = br.readLine()) != null) {
            System.out.println(counter++ + ": " + line);
        }
    }
}


