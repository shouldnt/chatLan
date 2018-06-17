import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Client_Start extends JFrame {
    String filePath="";
    public Client_Start() {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void button1MouseClicked(MouseEvent e) {
        String name = txtName.getText();
        Client_prog client = new Client_prog(name);
        client.setVisible(true);
        client.setFilePath(filePath);
        Thread t = new Thread(client);
        t.start();
        this.setVisible(false);
    }

    private void button2MouseClicked(MouseEvent e) {
        LoadFile dialog = new LoadFile(this);
        filePath=dialog.getFilePath();
        lbPath.setText(filePath);
    }

    private void button3MouseClicked(MouseEvent e) {
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Son Luu
        txtName = new JTextField();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        lbPath = new JLabel();

        //======== this ========
        setTitle("Client Start");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(txtName);
        txtName.setBounds(10, 5, 350, txtName.getPreferredSize().height);

        //---- button1 ----
        button1.setText("Start");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(new Rectangle(new Point(10, 40), button1.getPreferredSize()));

        //---- button2 ----
        button2.setText("Browse");
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button2MouseClicked(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(new Rectangle(new Point(155, 40), button2.getPreferredSize()));

        //---- button3 ----
        button3.setText("Exit");
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button3MouseClicked(e);
            }
        });
        contentPane.add(button3);
        button3.setBounds(new Rectangle(new Point(305, 40), button3.getPreferredSize()));

        //---- lbPath ----
        lbPath.setText("file Path ..");
        contentPane.add(lbPath);
        lbPath.setBounds(20, 75, 345, lbPath.getPreferredSize().height);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Son Luu
    private JTextField txtName;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel lbPath;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    public static void main(String []args)
    {
        Client_Start main = new Client_Start();
        main.setVisible(true);
    }
}
