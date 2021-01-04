package gedistribueerde.server;

import gedistribueerde.Client.Document;
import gedistribueerde.communication.MessageManager;
import gedistribueerde.communication.MethodCallMessage;
import gedistribueerde.communication.NetworkAddress;

public class DocumentStub implements Document {
    private final MessageManager messageManager;
    private final NetworkAddress networkAddress;

    public DocumentStub(NetworkAddress networkAddress) {
        this.messageManager = new MessageManager();
        this.networkAddress = networkAddress;
        System.out.println("The document skeleton address is: " + networkAddress.toString());
        System.out.println("A new DocumentStub was created with address: " + messageManager.getMyAddress().toString());
    }

    @Override
    public String getText() {
        MethodCallMessage message = new MethodCallMessage(messageManager.getMyAddress(), "getText");
        messageManager.send(message, networkAddress);
        MethodCallMessage reply = messageManager.wReceive();

        if (reply.getMethodName().equals("getTextReply")) {
            System.out.println("Received reply with name: " + reply.getMethodName());
        } else {
            System.out.println("Reply with name " + reply.getMethodName() + " is wrong!");
           // throw new RuntimeException("Received reply is not equal to getTextReply! Instead received: " + reply.getMethodName());
        }
        System.out.println(reply.getParameter("textString"));
        return reply.getParameter("textString");

    }

    @Override
    public void setText(String text) {
        MethodCallMessage message = new MethodCallMessage(messageManager.getMyAddress(), "setText");
        message.setParameter("text", text);
        messageManager.send(message, networkAddress);
        MethodCallMessage reply = messageManager.wReceive();

        if (reply.getMethodName().equals("setTextReply")) {
            System.out.println("Received reply with name: " + reply.getMethodName());
        } else {
            System.out.println("Reply with name " + reply.getMethodName() + " is wrong!");
            // throw new RuntimeException("Received reply is not equal to setTextReply! Instead received: " + reply.getMethodName());
        }
    }

    @Override
    public void append(char c) {

    }

    @Override
    public void setChar(int position, char c) {
        MethodCallMessage message = new MethodCallMessage(messageManager.getMyAddress(), "setChar");
        message.setParameter("position", String.valueOf(position));
        message.setParameter("c",String.valueOf(c));
        messageManager.send(message,networkAddress);
        MethodCallMessage reply = messageManager.wReceive();

        if (reply.getMethodName().equals("setCharReply")) {
            System.out.println("Received reply with name: " + reply.getMethodName());
        } else {
            System.out.println("Reply with name " + reply.getMethodName() + " is wrong!");
            // throw new RuntimeException("Received reply is not equal to setTextReply! Instead received: " + reply.getMethodName());
        }
    }
}
