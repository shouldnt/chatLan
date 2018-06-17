import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Server_Start extends JFrame {
    public Server_Start() {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void button1MouseClicked(MouseEvent e) {
        String name = txtName.getText();
        Server_prog se = new Server_prog(name);
        se.setVisible(true);
        Thread t = new Thread(se);
        t.start();
        this.setVisible(false);
    }

    private void button2MouseClicked(MouseEvent e) {
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Son Luu
        txtName = new JTextField();
        button1 = new JButton();
        button2 = new JButton();

        //======== this ========
        setTitle("Server_Start");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(txtName);
        txtName.setBounds(5, 5, 374, 26);

        //---- button1 ----
        button1.setText("Start");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(310, 35, 70, 27);

        //---- button2 ----
        button2.setText("Exit");
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button2MouseClicked(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(5, 35, 70, 27);

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
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    public static void main (String []args)
    {
        Server_Start main = new Server_Start();
        main.setVisible(true);
    }
}
