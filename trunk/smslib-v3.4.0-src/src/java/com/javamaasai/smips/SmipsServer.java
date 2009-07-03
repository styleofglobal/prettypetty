package com.javamaasai.smips;

import java.util.List;

import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Library;
import org.smslib.OutboundMessage;
import org.smslib.Phonebook;
import org.smslib.Service;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.AGateway.Protocols;
import org.smslib.Message.MessageTypes;
import org.smslib.modem.SerialModemGateway;

import examples.modem.SendMessage.OutboundNotification;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class SmipsServer extends JFrame implements ActionListener {
	Service srv;
	// Define a list which will hold the phonebook entries.
	Phonebook phonebook;

	// Define a list which will hold the read messages.
	List<InboundMessage> msgList;

	// Create the notification callback method for inbound & status report
	// messages.
	InboundNotification inboundNotification ;

	// Create the notification callback method for inbound voice calls.
	CallNotification callNotification ;

	// Create the notification callback method for gateway statuses.
	GatewayStatusNotification statusNotification;
	
	// Create the Gateway representing the serial GSM modem.
	SerialModemGateway gateway;
	
	//define outbound message
	OutboundMessage msg;
	
	//define outbound notification
	OutboundNotification outboundNotification;
	
	JDesktopPane desktop;
	public SmipsServer(){
		super("SMIP Server");
		
		//Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 20;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - 222, screenSize.height - 152);

        //Set up the GUI.
        desktop = new JDesktopPane(); //a specialized layered pane
        setContentPane(desktop);
        setJMenuBar(createMenuBar());
        //Make dragging a little faster but perhaps uglier.
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        
    	inboundNotification = new InboundNotification();
    	callNotification = new CallNotification();
    	statusNotification = new GatewayStatusNotification();
    	outboundNotification = new OutboundNotification();
	}
	
	public void startService() throws Exception {
		try {
			System.out
					.println("Example: Read messages from a serial gsm modem.");
			System.out.println(Library.getLibraryDescription());
			System.out.println("Version: " + Library.getLibraryVersion());

			// Create new Service object - the parent of all and the main
			// interface
			// to you.
			this.srv = new Service();

			// Create the Gateway representing the serial GSM modem.
			this.gateway = new SerialModemGateway("Safaricom",
					"COM5", 115600, "Huawei", "E220");

			// Set the modem protocol to PDU (alternative is TEXT). PDU is the
			// default, anyway...
			this.gateway.setProtocol(Protocols.PDU);

			// Do we want the Gateway to be used for Inbound messages?
			this.gateway.setInbound(true);

			// Do we want the Gateway to be used for Outbound messages?
			this.gateway.setOutbound(true);

			// Let SMSLib know which is the SIM PIN.
			// gateway.setSimPin("0000");

			this.gateway.setSmscNumber("+254722500029");
			
			// Set up the notification methods.
			this.srv.setInboundNotification(inboundNotification);
			this.srv.setCallNotification(callNotification);
			this.srv.setGatewayStatusNotification(statusNotification);
			this.srv.setOutboundNotification(outboundNotification);

			// Add the Gateway to the Service object.
			this.srv.addGateway(gateway);

			// Similarly, you may define as many Gateway objects, representing
			// various GSM modems, add them in the Service object and control
			// all of them.

			// Start! (i.e. connect to all defined Gateways)
			this.srv.startService();

			// Printout some general information about the modem.
			System.out.println();
			System.out.println("Modem Information:");
			System.out.println("  Manufacturer: " + gateway.getManufacturer());
			System.out.println("  Model: " + gateway.getModel());
			System.out.println("  Serial No: " + gateway.getSerialNo());
			System.out.println("  SIM IMSI: " + gateway.getImsi());
			System.out.println("  Signal Level: " + gateway.getSignalLevel()
					+ "%");
			System.out.println("  Battery Level: " + gateway.getBatteryLevel()
					+ "%");
			System.out.println("  SMsCenter number: " + gateway.getSmscNumber()
					+ "%");
			System.out.println();
			
			showMessage("SMIP service started");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void sendMessage(){
		try{
			String number = JOptionPane.showInputDialog("Enter valid Number");
			String message = JOptionPane.showInputDialog("Enter Message");
			msg = new OutboundMessage(number, message);
			srv.sendMessage(msg);
			showMessage(msg.toString());
		}catch(Exception e){
			
		}
	}
	public void stopService(){
		try {
			srv.stopService();
			showMessage("SMIP service stopped");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	public class InboundNotification implements IInboundMessageNotification {
		public void process(String gatewayId, MessageTypes msgType,
				InboundMessage msg) {
			if (msgType == MessageTypes.INBOUND)
				System.out
						.println(">>> New Inbound message detected from Gateway: "
								+ gatewayId);
			else if (msgType == MessageTypes.STATUSREPORT)
				System.out
						.println(">>> New Inbound Status Report message detected from Gateway: "
								+ gatewayId);
			showMessage(msg.toString());
			try {
				// Uncomment following line if you wish to delete the message
				// upon arrival.
				SmipsServer.this.srv.deleteMessage(msg);
			} catch (Exception e) {
				System.out.println("Oops!!! Something gone bad...");
				e.printStackTrace();
			}
		}
	}
	
	public class OutboundNotification implements IOutboundMessageNotification
	{
		public void process(String gatewayId, OutboundMessage msg)
		{
			System.out.println("Outbound handler called from Gateway: " + gatewayId);
			showMessage(msg.toString());
		}
	}
	
	public class CallNotification implements ICallNotification {
		public void process(String gatewayId, String callerId) {
			System.out.println(">>> New call detected from Gateway: "
					+ gatewayId + " : " + callerId);
		}
	}

	public class GatewayStatusNotification implements
			IGatewayStatusNotification {
		public void process(String gatewayId, GatewayStatuses oldStatus,
				GatewayStatuses newStatus) {
			System.out.println(">>> Gateway Status change for " + gatewayId
					+ ", OLD: " + oldStatus + " -> NEW: " + newStatus);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ("Quit".equals(e.getActionCommand()))
        { 
        	//quit
            quit();
        }else if ("startservice".equals(e.getActionCommand()))
        { 
        	//Start modem service
        	try{
        	startService();
        	}catch (Exception ex){}
        }else if ("stopservice".equals(e.getActionCommand()))
        { 
        	//stop modem service
        	stopService();
        }else if ("sendmessage".equals(e.getActionCommand()))
        { 
        	//send Message
        	sendMessage();
        }

	}
	protected void quit() 
    {
    	JOptionPane.showMessageDialog(null,"Thank You!");
        System.exit(0);
        
    }
	
	/**
	 * Display Given Message
	 * 
	 * @param message
	 */
	public void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	protected JMenuBar createMenuBar() 
    {
        JMenuBar menuBar = new JMenuBar();

        //Set up the Game menu.
        JMenu menu = new JMenu("Open");
        menu.setMnemonic(KeyEvent.VK_G);
        menuBar.add(menu);
        
		//Set up the Help menu.
        JMenu menu1 = new JMenu("Help");
        menu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(menu1);
        
        //Set up the first menu item for Game menu.
        JMenuItem menuItem = new JMenuItem("StartService");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("startservice");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //Set up the first menu item for Game menu.
        menuItem = new JMenuItem("StopService");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("stopservice");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
      //Set up the first menu item for Game menu.
        menuItem = new JMenuItem("SendMessage");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("sendmessage");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //Set up the second menu item for Game menu.
        menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("Quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);
		
        return menuBar;
    }
	
	static void createAndShowGUI() 
    {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
        //Create and set up the window.
        SmipsServer frame = new SmipsServer();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Display the window.
        frame.setVisible(true);
        JOptionPane.showMessageDialog(null, "Welcome to S.M.I.P Server Application");
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SmipsServer server= new SmipsServer();
		server.createAndShowGUI();
	}

}
