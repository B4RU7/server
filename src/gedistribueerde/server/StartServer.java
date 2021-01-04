package gedistribueerde.server;

import gedistribueerde.communication.NetworkAddress;

public class StartServer {
    public static void main(String[] args) {
        Server server = new ServerImpl();
        ServerSkeleton serverSkeleton = new ServerSkeleton(server);
        NetworkAddress networkAddress = serverSkeleton.getAddress();
        System.out.println("networkAddress = " + networkAddress);
        serverSkeleton.run();
    }
}
