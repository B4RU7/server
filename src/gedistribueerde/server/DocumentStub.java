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
    }

    @Override
    public String getText() {
        MethodCallMessage message = new MethodCallMessage(messageManager.getMyAddress(), "getText");
        messageManager.send(message, networkAddress);
        MethodCallMessage reply = messageManager.wReceive();
        if (!reply.getMethodName().equals("getTextReply"))
            throw new RuntimeException("Reply must be getTextReply! Not " + reply.getMethodName());

        return reply.getParameter("textString");
    }

    @Override
    public void setText(String text) {
        MethodCallMessage message = new MethodCallMessage(messageManager.getMyAddress(), "setText");
        message.setParameter("text", text);
        messageManager.send(message, networkAddress);
        MethodCallMessage reply = messageManager.wReceive();
        if (!reply.getMethodName().equals("setTextReply"))
            throw new RuntimeException("Reply must be setTextReply! Not " + reply.getMethodName());
    }

    @Override
    public void append(char c) {
        MethodCallMessage message = new MethodCallMessage(messageManager.getMyAddress(), "append");
        message.setParameter("c",String.valueOf(c));
        messageManager.send(message,networkAddress);
        MethodCallMessage reply = messageManager.wReceive();
        if (!reply.getMethodName().equals("appendReply"))
            throw new RuntimeException("Reply must be appendReply! Not " + reply.getMethodName());
    }

    @Override
    public void setChar(int position, char c) {
        MethodCallMessage message = new MethodCallMessage(messageManager.getMyAddress(), "setChar");
        message.setParameter("position", String.valueOf(position));
        message.setParameter("c",String.valueOf(c));
        messageManager.send(message,networkAddress);
        MethodCallMessage reply = messageManager.wReceive();
        if (!reply.getMethodName().equals("setCharReply"))
            throw new RuntimeException("Reply must be appendReply! Not " + reply.getMethodName());
    }
}
