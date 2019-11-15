import Warehouse.MessageWarehouse;
import Warehouse.UserWarehouse;

import java.net.*;
import java.io.*;

public class Server {
    private static int DEFAULT_PORT=8081;
    private static UserWarehouse userWarehouse;
    private static MessageWarehouse messageWarehouse;

    public static void main(String[] args) {
        int port = DEFAULT_PORT;

        ServerSocket servidor = null;

        try	{
            servidor = new ServerSocket(port);
        } catch (Exception e) {
            System.err.println("erro ao criar socket servidor...");
            e.printStackTrace();
            System.exit(-1);
        }

        userWarehouse = new UserWarehouse();
        messageWarehouse = new MessageWarehouse();

        System.out.println("Servidor a' espera de ligacoes no porto " + port);

        while(true) {
            try {
                Socket ligacao = servidor.accept();

                RqHandler t = new RqHandler(ligacao, userWarehouse, messageWarehouse);
                t.start();

            } catch (IOException e) {
                System.out.println("Erro na execucao do servidor: "+e);
                System.exit(1);
            }
        }
    }
}
