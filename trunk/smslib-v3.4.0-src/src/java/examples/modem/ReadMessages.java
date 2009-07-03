// ReadMessages.java - Sample application.
//
// This application shows you the basic procedure needed for reading
// SMS messages from your GSM modem, in synchronous mode.
//
// Operation description:
// The application setup the necessary objects and connects to the phone.
// As a first step, it reads all messages found in the phone.
// Then, it goes to sleep, allowing the asynchronous callback handlers to
// be called. Furthermore, for callback demonstration purposes, it responds
// to each received message with a "Got It!" reply.
//
// Tasks:
// 1) Setup Service object.
// 2) Setup one or more Gateway objects.
// 3) Attach Gateway objects to Service object.
// 4) Setup callback notifications.
// 5) Run

package examples.modem;

import java.util.ArrayList;
import java.util.List;

import org.smslib.ICallNotification;
import org.smslib.IGatewayStatusNotification;
import org.smslib.IInboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Library;
import org.smslib.OutboundMessage;
import org.smslib.Phonebook;
import org.smslib.Service;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.AGateway.Protocols;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Message.MessageTypes;
import org.smslib.modem.SerialModemGateway;

public class ReadMessages
{
	Service srv;

	public void doIt() throws Exception
	{
		// Define a list which will hold the phonebook entries.
		Phonebook phonebook;

		// Define a list which will hold the read messages.
		List<InboundMessage> msgList;

		// Create the notification callback method for inbound & status report
		// messages.
		InboundNotification inboundNotification = new InboundNotification();

		// Create the notification callback method for inbound voice calls.
		CallNotification callNotification = new CallNotification();

		//Create the notification callback method for gateway statuses.
		GatewayStatusNotification statusNotification = new GatewayStatusNotification();

		try
		{
			System.out.println("Example: Read messages from a serial gsm modem.");
			System.out.println(Library.getLibraryDescription());
			System.out.println("Version: " + Library.getLibraryVersion());

			// Create new Service object - the parent of all and the main interface
			// to you.
			this.srv = new Service();

			// Create the Gateway representing the serial GSM modem.
			SerialModemGateway gateway = new SerialModemGateway("Safaricom", "COM5", 9600, "Huawei", "E220");

			// Set the modem protocol to PDU (alternative is TEXT). PDU is the default, anyway...
			gateway.setProtocol(Protocols.PDU);
			
			// Do we want the Gateway to be used for Inbound messages?
			gateway.setInbound(true);

			// Do we want the Gateway to be used for Outbound messages?
			gateway.setOutbound(true);

			// Let SMSLib know which is the SIM PIN.
			//gateway.setSimPin("0000");

			// Set up the notification methods.
			this.srv.setInboundNotification(inboundNotification);
			this.srv.setCallNotification(callNotification);
			this.srv.setGatewayStatusNotification(statusNotification);

			// Add the Gateway to the Service object.
			this.srv.addGateway(gateway);

			// Similarly, you may define as many Gateway objects, representing
			// various GSM modems, add them in the Service object and control all of them.

			// Start! (i.e. connect to all defined Gateways)
			this.srv.startService();

			// Printout some general information about the modem.
			System.out.println();
			System.out.println("Modem Information:");
			System.out.println("  Manufacturer: " + gateway.getManufacturer());
			System.out.println("  Model: " + gateway.getModel());
			System.out.println("  Serial No: " + gateway.getSerialNo());
			System.out.println("  SIM IMSI: " + gateway.getImsi());
			System.out.println("  Signal Level: " + gateway.getSignalLevel() + "%");
			System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
			System.out.println("  SMsCenter number: " + gateway.getSmscNumber() + "%");
			System.out.println();

			// Read Messages. The reading is done via the Service object and
			// affects all Gateway objects defined. This can also be more directed to a specific
			// Gateway - look the JavaDocs for information on the Service method calls.
			msgList = new ArrayList<InboundMessage>();
			this.srv.readMessages(msgList, MessageClasses.ALL);
			for (InboundMessage msg : msgList)
				System.out.println(msg);

			// Read Phonebook.
			//phonebook = new Phonebook();
			//System.out.println("TOTAL PHONEBOOK ENTRIES = " + srv.readPhonebook(phonebook, gateway.getGatewayId()));
			// Print out all contacts retrieved.
			//for (Contact entry : phonebook.getContacts())
			//	System.out.println(entry);
			// Print out contact stored on the SIM card.
			//for (Contact entry : phonebook.getContacts(Contact.ContactLocation.SIM_ENTRIES))
			//	System.out.println(entry);

			// Sleep now. Emulate real world situation and give a chance to the notifications
			// methods to be called in the event of message or voice call reception.
			
			System.out.println("Now Sleeping - Press <enter> to fill up queue manager.");
			System.in.read(); System.in.read();
			srv.queueMessage(new OutboundMessage("1", "1"));

			while (true)
			{
			System.out.println("Now Sleeping - Hit <enter> to stop service.");
			System.in.read(); System.in.read();
			srv.stopService();
			System.out.println("Now Sleeping - Hit <enter> to restart service.");
			System.in.read(); System.in.read();
			srv.startService();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.srv.stopService();
		}
	}

	public class InboundNotification implements IInboundMessageNotification
	{
		public void process(String gatewayId, MessageTypes msgType, InboundMessage msg)
		{
			if (msgType == MessageTypes.INBOUND) System.out.println(">>> New Inbound message detected from Gateway: " + gatewayId);
			else if (msgType == MessageTypes.STATUSREPORT) System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gatewayId);
			System.out.println(msg);
			try
			{
				// Uncomment following line if you wish to delete the message upon arrival.
				// ReadMessages.this.srv.deleteMessage(msg);
			}
			catch (Exception e)
			{
				System.out.println("Oops!!! Something gone bad...");
				e.printStackTrace();
			}
		}
	}

	public class CallNotification implements ICallNotification
	{
		public void process(String gatewayId, String callerId)
		{
			System.out.println(">>> New call detected from Gateway: " + gatewayId + " : " + callerId);
		}
	}

	public class GatewayStatusNotification implements IGatewayStatusNotification
	{
		public void process(String gatewayId, GatewayStatuses oldStatus, GatewayStatuses newStatus)
		{
			System.out.println(">>> Gateway Status change for " + gatewayId + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
		}
	}

	public static void main(String args[])
	{
		ReadMessages app = new ReadMessages();
		try
		{
			app.doIt();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
