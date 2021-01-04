package gedistribueerde.server;

import gedistribueerde.Client.Document;
import gedistribueerde.communication.MessageManager;
import gedistribueerde.communication.MethodCallMessage;
import gedistribueerde.communication.NetworkAddress;

public class ServerSkeleton {
    private final Server server;
    private final MessageManager messageManager;

    public ServerSkeleton(Server server) {
        this.server = server;
        messageManager = new MessageManager();
    }

    public NetworkAddress getAddress() {
        return messageManager.getMyAddress();
    }

    public void run() {
        System.out.println("Server is running on address " + messageManager.getMyAddress().toString());
        while (true) {
            MethodCallMessage request = messageManager.wReceive();
            handleRequest(request);
        }
    }

    private void handleRequest(MethodCallMessage request){
        System.out.println("Request received with name: " + request.getMethodName());
        String documentSkeletonAddress;
        String documentSkeletonIp;
        int documentSkeletonPort;
        switch (request.getMethodName()){
            case "log" :
                server.log(new DocumentImpl(request.getParameter("documentText")));
                messageManager.send(new MethodCallMessage(messageManager.getMyAddress(), "logReply"),request.getOriginator());
                break;
            case "create" :
                String createText = request.getParameter("s");
                Document createdDocument = server.create(createText);
                MethodCallMessage createReply = new MethodCallMessage(messageManager.getMyAddress(), "createReply");
                createReply.setParameter("s",createdDocument.getText());
                messageManager.send(createReply,request.getOriginator());
                break;
            case "toUpper" :
                documentSkeletonAddress = request.getParameter("documentSkeletonAddress");
                documentSkeletonIp = documentSkeletonAddress.split(":")[0];
                documentSkeletonPort = Integer.parseInt(documentSkeletonAddress.split(":")[1]);

                Document toUpperDocument = new DocumentStub(new NetworkAddress(documentSkeletonIp,documentSkeletonPort));
                server.toUpper(toUpperDocument);
                messageManager.send(new MethodCallMessage(messageManager.getMyAddress(),"toUpperReply"),request.getOriginator());
                break;
            case "toLower" :
                documentSkeletonAddress = request.getParameter("documentSkeletonAddress");
                documentSkeletonIp = documentSkeletonAddress.split(":")[0];
                documentSkeletonPort = Integer.parseInt(documentSkeletonAddress.split(":")[1]);

                Document toLowerDocument = new DocumentStub(new NetworkAddress(documentSkeletonIp,documentSkeletonPort));
                server.toLower(toLowerDocument);
                messageManager.send(new MethodCallMessage(messageManager.getMyAddress(),"toLowerReply"),request.getOriginator());
                break;
            case "append" :
                documentSkeletonAddress = request.getParameter("documentSkeletonAddress");
                documentSkeletonIp = documentSkeletonAddress.split(":")[0];
                documentSkeletonPort = Integer.parseInt(documentSkeletonAddress.split(":")[1]);


        }
    }


}
