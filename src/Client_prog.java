import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import javax.sound.midi.Receiver;
import javax.swing.*;

public class Client_prog extends JFrame implements Runnable {
    Socket conn;
    String serverName="";
    int port = 1102;

    String name="Client";

    String filePath = "SERVER.txt";
    FileTransfer fileTrans;
    FileTransfer keyTrans ;
    AlgorithmRSA rsa;

    BigInteger KeyE=null, KeyN=null;

    Boolean isGetKey=false;
    Boolean isConnect=false;

    //Constructor
    public Client_prog() {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //invoke the RSA Algorithm to use for encrypt and decrypt the message
        rsa = new AlgorithmRSA(256);
        //Write Public Key to a file named "CLIENT_KEY.txt"
        CreateKey key = new CreateKey(rsa.getE(),rsa.getN(),"CLIENT_KEY.txt");
        key.write();
    }

    //Do the same as Constructor below
    public Client_prog(String name) {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.name=name;

        rsa = new AlgorithmRSA(256);
        CreateKey key = new CreateKey(rsa.getE(),rsa.getN(),"CLIENT_KEY.txt");
        key.write();
    }
    @Override
    public void run()
    {
        connect();
        update();
    }
    //Send message button action
    private void SendMouseClicked(MouseEvent e) {
        if(!isGetKey)
            content.setText(content.getText()+"\n"+"No Public Key for Decryption. Please Click Receive Key Button First!");
        else
            send();
    }
    //Receive File button action
    private void button2MouseClicked(MouseEvent e) {
        fileTrans = new FileTransfer(this.serverName,9201);
        try {
            fileTrans.receiveFile("download.txt");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        content.setText(content.getText()+"\n"+fileTrans.getMessage());
        Thread t = new Thread(fileTrans);
        t.start();
        content.setText(content.getText()+"\n"+fileTrans.getMessage());
    }
    //Send File button action
    private void button1MouseClicked(MouseEvent e) {
        fileTrans = new FileTransfer(this.serverName,9201);
        LoadFile file = new LoadFile(this);
        try {
            fileTrans.sendFile(file.getFilePath());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        content.setText(content.getText()+"\n"+fileTrans.getMessage());
    }
    //Receive Public Key from Server action
    private void button3MouseClicked(MouseEvent e) {
        keyTrans = new FileTransfer(this.serverName,9999);
        try {
            keyTrans.receiveFile("KEY_FROM_SERVER.txt");
            isGetKey=true;
            content.setText(content.getText()+"\n"+"Public Key from  Received");
        } catch (IOException e1) {
            isGetKey=false;
            content.setText(content.getText()+"\n"+"Public Key failed!");
        }

        GetKey k = new GetKey("KEY_FROM_SERVER.txt");
        k.read();
        this.KeyE=k.getE();
        this.KeyN=k.getN();
        System.out.println("Key e: "+this.KeyE);
        System.out.println("Key n: "+this.KeyN);
    }
    //Send Client's Public Key to Server
    private void button4MouseClicked(MouseEvent e) {
        keyTrans = new FileTransfer(this.serverName,9999);
        try {
            keyTrans.sendFile("CLIENT_KEY.txt");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Son Luu
        scrollPane1 = new JScrollPane();
        content = new JTextArea();
        message = new JTextField();
        Send = new JButton();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();

        //======== this ========
        setTitle("Client");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(content);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(5, 10, 460, 245);
        contentPane.add(message);
        message.setBounds(5, 270, 460, message.getPreferredSize().height);

        //---- Send ----
        Send.setText("Send");
        Send.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SendMouseClicked(e);
            }
        });
        contentPane.add(Send);
        Send.setBounds(480, 270, 105, Send.getPreferredSize().height);

        //---- button1 ----
        button1.setText("Send File");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(480, 10, 105, button1.getPreferredSize().height);

        //---- button2 ----
        button2.setText("Receive File");
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button2MouseClicked(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(480, 60, 105, button2.getPreferredSize().height);

        //---- button3 ----
        button3.setText("Get Key");
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button3MouseClicked(e);
            }
        });
        contentPane.add(button3);
        button3.setBounds(480, 160, 105, button3.getPreferredSize().height);

        //---- button4 ----
        button4.setText("Send Key");
        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button4MouseClicked(e);
            }
        });
        contentPane.add(button4);
        button4.setBounds(480, 195, 105, button4.getPreferredSize().height);

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

    //set the SERVER.txt path for client to get Server's ip and port number
    //if filePath is empty, set the Default path
    public void setFilePath(String t)
    {
        if(t!="")
            this.filePath=t;
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Son Luu
    private JScrollPane scrollPane1;
    private JTextArea content;
    private JTextField message;
    private JButton Send;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    public void getServerInfo()
    {
        ReadFile readInfo=new ReadFile(filePath);
        readInfo.read();
        this.serverName=readInfo.getName();
        this.port=readInfo.getPort();

    }
    //Connect to the Server
    public void connect()
    {
        //Read Server's ip and Server's port from the file
        getServerInfo();

        try {
            conn = new Socket(this.serverName, this.port);
            content.setText("Connected to Server: "+ serverName + " at Port: " + port +"\n");
        }
        catch (IOException er)
        {

        }
    }
    //Read continuely message that was sent from server
    public void update()
    {
        while (true) {
            try {
                //stream to receive the message from socket
                DataInputStream dis = new DataInputStream(conn.getInputStream());
                String string = dis.readUTF();
                //Decrypt the message
                String sec_message=rsa.decrypt(string);
                content.setText(content.getText() + '\n' + sec_message);
            } catch (Exception e1) {
                content.setText(content.getText() + '\n' + "Message sending fail:Network Error. Please reconnect to the server \n ");
                try {
                    Thread.sleep(3000);
                    System.exit(0);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    //Send message to SERVER
    public void send()
    {
        content.setText(content.getText() + '\n' + name +": " + message.getText());
        try {
            //Encrypt the messge using SERVER's Public key
            String sec_message=rsa.encrypt(name+": " + message.getText(),this.KeyN,this.KeyE);
            //Stream to Send message throught Socket
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeUTF(sec_message);
        } catch (Exception e1) {
            content.setText(content.getText() + '\n' + "Message sending fail:Network Error. Please reconnect to the servern \n");
            try {
                Thread.sleep(3000);
                System.exit(0);
            } catch (InterruptedException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
        }
        message.setText("");
    }
}
