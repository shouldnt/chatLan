import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import javax.swing.*;
import java.net.InetSocketAddress;

public class Server_prog extends JFrame implements Runnable{
    ServerSocket server;
    Socket conn;

    String serverName="";
    String clientName="";
    int port = 1102;
    String name="Server";

    FileTransfer fileTrans;
    FileTransfer keyTrans;
    AlgorithmRSA rsa;
    BigInteger KeyE=null, KeyN=null;

    boolean isGetKey=false;
    boolean isStart=false;

    //Constructor
    public Server_prog(String name) {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Random rand = new Random();
        this.port=rand.nextInt(9999-1000)+1001;
        try {
            this.serverName=InetAddress.getLocalHost().getHostAddress();
            serverIP.setText(serverName);
            serverPort.setText(Integer.toString(this.port));
        }
        catch (IOException e) {
        }
        this.name=name;
        //Invoke the RSA Algorithm to use for encrypt and decrypt the message
        rsa = new AlgorithmRSA(256);

        //Write the public key to a File name "SERVER_KEY.txt"
        CreateKey key = new CreateKey(rsa.getE(),rsa.getN(),"SERVER_KEY.txt");
        key.write();
    }

    //Constructor. Do the same as Constructor below
    public Server_prog() {
        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Random rand = new Random();
        this.port=rand.nextInt(9999-1000)+1001;
        try {
            this.serverName=InetAddress.getLocalHost().getHostAddress();
            serverIP.setText(serverName);
            serverPort.setText(Integer.toString(this.port));
        }
        catch (IOException e) {
        }
        keyTrans = new FileTransfer(this.serverName,9999);
        rsa = new AlgorithmRSA(256);
        CreateKey key = new CreateKey(rsa.getE(),rsa.getN(),"SERVER_KEY.txt");
        key.write();
    }

    @Override
    public void run()
    {
        start();
        update();
    }

    //Send message button action
    private void SendMouseClicked(MouseEvent e) {
        if(!isGetKey)
            content.setText(content.getText()+"\n"+"No Public Key for Decryption. Please Click Receive Key Button First!");
        else
            send();
    }
    //Send File button Operation
    private void button1MouseClicked(MouseEvent e)  {
        fileTrans = new FileTransfer(this.clientName,9201);
        LoadFile file = new LoadFile(this);
        try {
            fileTrans.sendFile(file.getFilePath());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        content.setText(content.getText()+"\n"+fileTrans.getMessage());
    }

    //Receive File button operation
    private void button2MouseClicked(MouseEvent e) {
        fileTrans = new FileTransfer(this.clientName,9201);
        try {
            fileTrans.receiveFile("download.txt");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        content.setText(content.getText()+"\n"+fileTrans.getMessage());

    }

    //Receive Public Key from Client Operation
    private void button3MouseClicked(MouseEvent e) {
        keyTrans = new FileTransfer(this.clientName,9999);
        try {
            keyTrans.receiveFile("KEY_FROM_CLIENT.txt");
            isGetKey=true;
            content.setText(content.getText()+"\n"+"Public Key from Client Received \n");
        } catch (IOException e1) {
            isGetKey=false;
            content.setText(content.getText()+"\n"+"Public Key Failed! \n");
        }
        GetKey k = new GetKey("KEY_FROM_CLIENT.txt");
        k.read();
        this.KeyE=k.getE();
        this.KeyN=k.getN();
        System.out.println("Key e: "+this.KeyE);
        System.out.println("Key n: "+this.KeyN);
    }

    //Send Server's Public Key to Client button action
    private void button4MouseClicked(MouseEvent e) {
        keyTrans = new FileTransfer(this.clientName,9999);
        try {
            keyTrans.sendFile("SERVER_KEY.txt");
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
        serverIP = new JLabel();
        serverPort = new JLabel();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();

        //======== this ========
        setTitle("Server");
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(content);
        }
        contentPane.add(scrollPane1);
        scrollPane1.setBounds(10, 15, 490, 260);
        contentPane.add(message);
        message.setBounds(5, 295, 495, message.getPreferredSize().height);

        //---- Send ----
        Send.setText("Send");
        Send.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SendMouseClicked(e);
            }
        });
        contentPane.add(Send);
        Send.setBounds(505, 295, 100, Send.getPreferredSize().height);
        contentPane.add(serverIP);
        serverIP.setBounds(110, 10, 225, serverIP.getPreferredSize().height);
        contentPane.add(serverPort);
        serverPort.setBounds(110, 35, 45, serverPort.getPreferredSize().height);

        //---- button1 ----
        button1.setText("Send File");
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button1MouseClicked(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(505, 15, 100, button1.getPreferredSize().height);

        //---- button2 ----
        button2.setText("Receive File");
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button2MouseClicked(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(505, 55, 100, button2.getPreferredSize().height);

        //---- button3 ----
        button3.setText("Get Key");
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button3MouseClicked(e);
            }
        });
        contentPane.add(button3);
        button3.setBounds(510, 190, 100, button3.getPreferredSize().height);

        //---- button4 ----
        button4.setText("Send Key");
        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                button4MouseClicked(e);
            }
        });
        contentPane.add(button4);
        button4.setBounds(510, 230, 100, button4.getPreferredSize().height);

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
    private JScrollPane scrollPane1;
    private JTextArea content;
    private JTextField message;
    private JButton Send;
    private JLabel serverIP;
    private JLabel serverPort;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables


    //Start the Server
    public void start()
    {
        //create a Text file "SERVER.txt" that contains server 's ip and its port number
        WriteFile file = new WriteFile(serverName,port);
        file.write();

        isStart=true;
        try {
            //create server Socket
            server = new ServerSocket(port, 1, InetAddress.getLocalHost());
            content.setText("Server: "+ serverName + " at Port: " + port);
            //listen for client's socket appcepting
            conn = server.accept();
            //get client's socket ip
            this.clientName=(((InetSocketAddress) conn.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
            content.setText(content.getText() + "\n" + "Client Found at: "+this.clientName+"\n");
        }
        catch (IOException er) {
        }

    }
    public void update()
    {
        //listen continuely for message that was send from client
        while (true) {
            try {
                //Stream to read the message sent from client (message was encrypted)
                DataInputStream dis = new DataInputStream(conn.getInputStream());
                String string = dis.readUTF();
                //Decryptng the message
                String sec_message=rsa.decrypt(string);
                content.setText(content.getText() + '\n' + sec_message);
            } catch (Exception e1) {
                content.setText(content.getText() + '\n' + "Message sending fail:Network Error. Please Restart the Server");
                try {
                    Thread.sleep(3000);
                    System.exit(0);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }
    }

    //send message to client
    public void send()
    {
        content.setText(content.getText() + '\n' + name +": " + message.getText());
        try {
            //Encrypting the message using Client's Public Key
            String sec_message=rsa.encrypt(name+": " + message.getText(),this.KeyN,this.KeyE);

            //Stream to send the message to Client throught socket
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeUTF(sec_message);
        } catch (Exception e1) {
            content.setText(content.getText() + '\n' + "Message sending fail:Network Error. Please reconnect to the server");
            try {
                Thread.sleep(3000);
                System.exit(0);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        message.setText("");
    }
}
