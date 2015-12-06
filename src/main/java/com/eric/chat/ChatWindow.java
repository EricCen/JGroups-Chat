package com.eric.chat;

import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ETIACEN on 12/5/2015.
 */
public class ChatWindow extends Frame implements ActionListener{

    private Label userLabel;
    private TextField userField;
    private TextArea input;
    private Button sendButton;
    private TextArea messagePanel;
    private Button exitButton;
    private final Font default_font= new Font("Helvetica", Font.PLAIN, 12);
    private Channel channel;
    private final static String JGROUP_PROP = "udp.xml";
    private MessageReceiver messageReceiver;
    private StringBuffer message = new StringBuffer();
    private WindowEventHandler windowEventHandler;



    public ChatWindow() throws Exception {
        initWindow();
    }

    private void initWindow(){
        userLabel = new Label("User");
        userField = new TextField();
        input = new TextArea();
        sendButton = new Button("Send");
        messagePanel = new TextArea();
        exitButton = new Button("Exit");

        setLayout(null);
        setSize(600,600);
        setFont(default_font);
        setBackground(Color.ORANGE);
        userLabel.setBounds(new Rectangle(10, 30, 30, 30));
        userField.setBounds(new Rectangle(50, 30, 50, 30));
        input.setBounds(new Rectangle(10,70,200,50));
        sendButton.setBounds(new Rectangle(220,70,40,40));
        messagePanel.setBounds(new Rectangle(10,150,500,400));
        messagePanel.setBackground(Color.CYAN);

        sendButton.addActionListener(this);

        windowEventHandler = new WindowEventHandler();

        add(userLabel);
        add(userField);
        add(input);
        add(sendButton);
        add(messagePanel);
        setVisible(true);
        addWindowListener(windowEventHandler);
    }

    public void start() throws Exception {
        channel = new JChannel(JGROUP_PROP);
        messageReceiver = new MessageReceiver();
        channel.setReceiver(messageReceiver);
        channel.connect("ChatCluster");
    }


    public void actionPerformed(ActionEvent e){
        User user = new User(userField.getText());
        Message message = new Message(input.getText(),user);
        org.jgroups.Message jGroupsMessage = new org.jgroups.Message(null,null,message);
        try {
            channel.send(jGroupsMessage);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    public class MessageReceiver extends ReceiverAdapter{
        public void receive(org.jgroups.Message msg){
            String messageToShow = ((Message)msg.getObject()).getUser().getName() +
                    " says: " + ((Message)msg.getObject()).getSaying();
            message.append(messageToShow).append("\n");
            messagePanel.setText(message.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        ChatWindow chatWindow = new ChatWindow();
        chatWindow.start();
    }
}
