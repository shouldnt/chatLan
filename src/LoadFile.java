import javax.swing.*;
import java.awt.*;
//This class is used to Show the Open File Dialog for choosing file. Return the File that was chosen path
public class LoadFile {
    String filename="";
    public LoadFile(Component parent)
    {

        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(parent);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filename=fc.getSelectedFile().getPath();
        }
    }
    public String getFilePath()
    {
        return this.filename;
    }
}
