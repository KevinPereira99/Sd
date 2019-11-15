import Responses.MsgResponse;
import Responses.UserResponse;
import Warehouse.Message;
import Warehouse.MessageWarehouse;
import Warehouse.User;
import Warehouse.UserWarehouse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.*;
import java.io.*;

public class RqHandler extends Thread {
    private Socket ligacao;
    private BufferedReader in;
    private PrintWriter out;
    private ObjectMapper objectMapper;
    private UserWarehouse userWarehouse;
    private MessageWarehouse messageWarehouse;

    public RqHandler(Socket ligacao, UserWarehouse userWarehouse, MessageWarehouse messageWarehouse) {
        this.ligacao = ligacao;
        this.objectMapper = new ObjectMapper();
        this.userWarehouse = userWarehouse;
        this.messageWarehouse = messageWarehouse;

        try
        {
            this.in = new BufferedReader (new InputStreamReader(ligacao.getInputStream()));

            this.out = new PrintWriter(ligacao.getOutputStream());
        } catch (IOException e) {
            System.out.println("Erro na execucao do servidor: " + e);
            System.exit(1);
        }
    }

    public void run() {
        try {
            JsonNode node;
            String methodPath;
            String res = null;
            String body;
            MsgResponse msgResponse;
            User user;

            if((methodPath = in.readLine()) != null) {
                switch(methodPath) {

                    case "POST /user HTTP/1.1":
                        for(int i = 0; i < 11; i++) {
                            in.readLine();
                        }

                        String userBody = in.readLine();
                        body = "{" + userBody.trim() + "}";
                        node = objectMapper.readTree(body);
                        String userNickname = node.get("nickname").asText();

                        user = userWarehouse.addUser(userNickname);
                        UserResponse userResponse = new UserResponse(user.getId());

                        res = objectMapper.writeValueAsString(userResponse);

                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-type: application/json");
                        out.println("Content-Length: " + res.length());
                        out.write("\r\n");
                        out.println(res);
                        out.flush();
                        break;
                    case "POST /message HTTP/1.1":
                        for(int i = 0; i < 11; i++) {
                            in.readLine();
                        }

                        String userIdBody = in.readLine();
                        String msgBody = in.readLine();
                        body = "{" + userIdBody.trim() + msgBody.trim() + "}";
                        node = objectMapper.readTree(body);
                        int idUserMsg = node.get("id").asInt();
                        String msg = node.get("msg").asText();

                        user = userWarehouse.getUser(idUserMsg);
                        Message message = messageWarehouse.addMessage(user, msg);

                        msgResponse = new MsgResponse(userWarehouse.getUsersList(), messageWarehouse.getMessagesList());
                        res = objectMapper.writeValueAsString(msgResponse);

                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-type: application/json");
                        out.println("Content-Length: " + res.length());
                        out.write("\r\n");
                        out.println(res);
                        out.flush();
                        break;
                    case "GET /message HTTP/1.1":
                        msgResponse = new MsgResponse(userWarehouse.getUsersList(), messageWarehouse.getMessagesList());
                        res = objectMapper.writeValueAsString(msgResponse);

                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-type: application/json");
                        out.println("Content-Length: " + res.length());
                        out.write("\r\n");
                        out.println(res);
                        out.flush();
                        break;
                    default:
                        out.println("HTTP/1.1 404 Not Found");
                        break;
                }

                in.close();
                out.close();
                ligacao.close();

            }

        } catch (IOException e) {
            System.out.println("Erro na execucao do servidor: " + e);
            System.exit(1);
        }
    }
}