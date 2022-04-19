import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class client implements ActionListener{
  String username;
  JButton send_button;
  JTextField message_field;
  JTextPane messages_screen;
  Socket s;
  DataInputStream din;
  DataOutputStream dout;
  public client() throws Exception{

    //creating the main window
    JFrame main_window = new JFrame("client");
    main_window.setSize(500,515); 
    main_window.setResizable(false); main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    main_window.setLayout(null);
    //the messages font for the app 
    Font messages_font= new Font("SansSerif",Font.PLAIN, 15);
    
    //creating the JTextPane to save the messages there 
    messages_screen = new JTextPane();
    messages_screen.setFont(messages_font);
    messages_screen.setBounds(0,0,500,450);
    messages_screen.setEditable(false);
    messages_screen.setBackground(Color.decode("#232323"));
    messages_screen.setForeground(Color.WHITE);
    main_window.add(messages_screen);

    // creating the new jtextfield  
    message_field= new JTextField("");
    message_field.setFont(messages_font);
    message_field.setBounds(0,450,450,40);
    message_field.setBackground(Color.decode("#232323"));
    message_field.setForeground(Color.WHITE);
    main_window.add(message_field);

    ImageIcon send_icon = new ImageIcon("send.png");
    send_button = new JButton(send_icon);
    send_button.setBackground(Color.BLACK);
    send_button.setBounds(450,450,50,50);
    Border emptyBorder = BorderFactory.createEmptyBorder();
    send_button.setBorder(emptyBorder);
    send_button.addActionListener(this);
    main_window.add(send_button);

    //making the window visible
    main_window.setVisible(true);
    
    //networking
    s = new Socket("localhost",9999);
    din = new DataInputStream(s.getInputStream());
    dout = new DataOutputStream(s.getOutputStream());
    String Recieved_string;
    while((Recieved_string = din.readUTF()) != null ){
      messages_screen.setText( messages_screen.getText() + "\nserver : " +  Recieved_string);
    }
  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == send_button){
      try{
        messages_screen.setText( messages_screen.getText() + "\n" +  message_field.getText());
        dout.writeUTF(message_field.getText());
        message_field.setText("");
      }catch(Exception ex){
        ex.printStackTrace();
      }
      
    }
  }
  public static void main(String[]args){
    try{
      new client();
    }catch(Exception e){
      e.printStackTrace();
    }
  }    
}
