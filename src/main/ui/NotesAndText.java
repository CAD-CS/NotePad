package ui;

import java.awt.LayoutManager;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;

import model.Note;

public class NotesAndText extends JPanel {
    
    private Set<Note> listOfNote;
    private JTextArea text;

   public NotesAndText() {
    super(new BorderLayout());
   } 
    

}
