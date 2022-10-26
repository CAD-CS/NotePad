package ui;

import java.io.FileNotFoundException;

// Attribution[1]: The try-catch was modified with respect to the main function in "JsonSerializationDemo"
public class Main {
    public static void main(String[] args) {
        try {
            new VimPad();
        } catch (FileNotFoundException e) {
            System.out.println("### *Beep-Boop* File Not Found ###");
        }
    }
}
