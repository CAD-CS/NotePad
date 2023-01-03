package ui;

import model.Note;
import model.Pad;
import model.PadManager;

import java.util.Scanner;

public class CLI {

    private boolean run;
    private String input;
    private String cmd;
    private Scanner scanner = new Scanner(System.in);

    public CLI() {
        initialize();
        while (run) {
            System.out.println("**********");
            dispayInventory();
            System.out.println("**********");
            processCommands();
        }
    }

    private void dispayInventory() {
        System.out.println("Selected Pad");
        if (PadManager.getInstance().getSelectedPad() == null) {
            System.out.println("null");
        } else {
            System.out.println(PadManager.getInstance().getSelectedPad().getPadTitle());
            System.out.println("\t List Of Notes");
            for (Note note : PadManager.getInstance().getSelectedPad().getListOfNotes()) {
                System.out.println("\t \t" + note.getNoteTitle());
            }
            System.out.println("Selected Note");
            if (PadManager.getInstance().getSelectedPad().getSelectedNote() == null) {
                System.out.println("null");
            } else {
                System.out.println(PadManager.getInstance().getSelectedPad().getSelectedNote().getNoteTitle());
                System.out.println("Text");
                System.out.println(PadManager.getInstance().getSelectedPad().getSelectedNote().getText());
            }
        }
        System.out.println("List Of Pads");
        for (Pad pad : PadManager.getInstance().getListOfPad()) {
            System.out.println("\t" + pad.getPadTitle());
        }
    }

    private void initialize() {
        run = true;
    }

    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void processCommands() {
        System.out.println("Enter command: \n");
        cmd = scanner.nextLine();
        System.out.println("Enter input:");
        input = scanner.nextLine();
        switch (cmd) {
            case "np":
                PadManager.getInstance().newPad(input);
                break;
            case "nn":
                PadManager.getInstance().newNote(input);
                break;
            case "rp" :
                PadManager.getInstance().removePad(input);
                break;
            case "rn":
                PadManager.getInstance().removeNote(input);
                break;
            case "cp":
                PadManager.getInstance().changePadTitle(input);
                break;
            case "cn":
                PadManager.getInstance().changeNoteTitle(input);
                break;
            case "t":
                PadManager.getInstance().changeNoteText(input);
                break;
            case "sp":
                PadManager.getInstance().selectPad(input);
                break;
            case "sn":
                PadManager.getInstance().selectNote(input);
                break;
            case "dsp":
                PadManager.getInstance().deselectPad();
                break;
            case "dsn":
                PadManager.getInstance().deselectNote();
                break;
            default:
                run = false;
                break;
        }
    }


}
