package ui;

import exception.ExistingTitleException;
import exception.NotFoundException;
import model.Note;
import model.Pad;
import model.VimPad;

import java.io.IOException;
import java.util.Scanner;

// Class responsible for running and displaying in console
// ATTRIBUTION[1]:  CLI was Roughly modeled off of TellerApp class
//              in given instructions for Phase 1 with changed implementation and names for readability
// Attribution[2]: processJson, saveSelectedPad, loadPad were modelled with respect to the methods in
//                 "JsonSerializationDemo"
public class CLI {

    // Scanner
    Scanner userInput = new Scanner(System.in);

    // Fields
    private VimPad vm;
    private String input;
    private boolean run;

    // Runs CLI application
    public CLI() {
        runCLI();
    }

    public String getInput() {
        return this.input;
    }

    public boolean getRun() {
        return this.run;
    }

    // EFFECTS: Displays the notes/pads and allows for modifying the fields
    private void runCLI() {
        initialize();
        displayCommands();
        while (run) {
            System.out.println("\nEnter: \n");
            this.input = userInput.nextLine();
            System.out.println("************* \n");
            try {
                preprocessMain(input);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            displayInventory();
            System.out.println("************* \n");
        }

    }


    // MODIFIES: this
    // EFFECTS: initializes the program with blank note, pad, input, and list of pad
    private void initialize() {
        this.vm = new VimPad();
        this.input = "";
        this.run = true;
    }

    // Methods
    private void displayCommands() {
        System.out.println("Legend:");
        System.out.println("\t Quit = q"); // exit
        System.out.println("\t Commands = d"); // implicit
        System.out.println("\t New note = n"); // Menu -> New Note
        System.out.println("\t New pad = p"); // Menu -> New Pad
        System.out.println("\t Remove pad = rp"); // Menu -> Close Current Pad
        System.out.println("\t Remove note = rn"); // Menu -> Close Selected Note
        System.out.println("\t Select pad = sp"); // Click on top row
        System.out.println("\t Select note = sn"); // click on left column
        System.out.println("\t Modify note = m"); // click and modify text in center
        System.out.println("\t Change name of pad = cp"); // Done when creating new note
        System.out.println("\t Change name of note = cn"); // Done when creating new note
        System.out.println("\t Display note text = t"); // implicit
        System.out.println("\t Save selected pad = s"); // Menu -> Save Pad
        System.out.println("\t Load pad = l"); // Menu -> Load Pad // Also Menu -> Rename Pad/Note
    }


    // MODIFIES: this
    // EFFECTS: calls processMain in vm with respective input
    public void preprocessMain(String cmd) throws ExistingTitleException, NotFoundException, IOException {
        if (cmd.equals("n") || cmd.equals("p")) {
            vm.processMain(cmd, processOutAndInput("Enter title:"));
        } else if (cmd.equals("rn") || cmd.equals("rp")) {
            vm.processMain(cmd, "");
        } else if (cmd.equals("sn") || cmd.equals("sp")) {
            vm.processMain(cmd, processOutAndInput("Select:"));
        } else if (cmd.equals("m")) {
            vm.processMain(cmd, processOutAndInput("Enter new text: "));
        } else if (cmd.equals("cn") || cmd.equals("cp")) {
            vm.processMain(cmd, processOutAndInput("Enter new title:"));
        } else if (cmd.equals("l")) {
            vm.processMain(cmd, processOutAndInput("Load... :"));
        } else if (cmd.equals("s")) {
            vm.processMain(cmd, "");
        } else {
            preprocessRest(cmd);
        }
    }

    // EFFECTS: this
    // MODIFIES: processes the rest of the commands
    public void preprocessRest(String cmd) {
        switch (cmd) {
            case "q":
                this.run = false;
                break;
            case "d":
                displayCommands();
                break;
            default:
                break;
        }
    }

    // EFFECTS: ask user for their input given "instruction text" and stores their answer in input
    private String processOutAndInput(String x) throws NullPointerException {
        System.out.println("\n " + x);
        String result = userInput.nextLine();
        if (result == null || result.equals("")) {
            throw new NullPointerException();
        }
        this.input = result;
        return this.input;
    }

    // EFFECTS: displays current selected items, and list of pad
    private void displayInventory() {
        if (vm.getSelectedPad() == null && vm.getListOfPad() != null) {
            System.out.println("\n--Selected Pad-- ");
            System.out.println("--Selected Note-- ");
            System.out.println("--List of pads-- ");
            for (Pad p : vm.getListOfPad()) {
                System.out.println("\t" + p.getPadTitle());
            }
            System.out.println("List of Notes in Pad: ");
        } else if (vm.getSelectedPad() == null && vm.getSelectedNote() == null) {
            System.out.println("\n--Selected Pad-- ");
            System.out.println("--Selected Note-- ");
            System.out.println("--List of pads-- ");
            System.out.println("List of Notes in Pad: ");
        } else {
            formatDisplayInventory();
        }
    }

    //  EFFECTS: formats output string for displayInventory
    private void formatDisplayInventory() {
        System.out.println("\n--Selected Pad-- " + vm.getSelectedPad().getPadTitle());
        System.out.println("--Selected Note-- " + vm.getSelectedNote().getNoteTitle());
        System.out.println("--List of pads-- ");
        for (Pad p : vm.getListOfPad()) {
            System.out.println("\t" + p.getPadTitle());
        }
        System.out.println("List of Notes in Pad: ");
        for (Note n : vm.getSelectedPad().getListOfNotes()) {
            System.out.println("\t" + n.getNoteTitle());
        }
    }

}
