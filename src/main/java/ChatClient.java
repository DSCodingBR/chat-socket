import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
/**
 *
 * @author Diego Silva
 */
public class ChatClient implements Runnable {
    private static final String SERVER_ADDRESS = "192.168.56.1";
    private ClientSocket clientSocket;
    private Scanner scanner;
    
    public ChatClient(){
        scanner = new Scanner (System.in);
    }
    
    public void start() throws IOException{
        try{
        clientSocket = new ClientSocket(new Socket(SERVER_ADDRESS, ChatServer.PORT));
        System.out.println(" Client conectado ao server em " + SERVER_ADDRESS + " : " + ChatServer.PORT);
        new Thread (this).start();
        messageLoop();
        }finally{
            clientSocket.close();
        }
    }
    private void messageLoop() throws IOException{
        String msg;
        do{
            System.out.println(" Digite uma Mensagem (ou sair para finalizar): ");
            msg = scanner.nextLine();
            clientSocket.sendMsg(msg);
        }while(!msg.equalsIgnoreCase("sair"));
    
    }
    
    @Override
    public void run(){
        String msg;
        while((msg = clientSocket.getMessage()) != null){
        System.out.println(" Msg recebida do servidor " + msg);
        }
    }
    
    public static void main(String[] args) {
       
        try {
            ChatClient client = new ChatClient();
            client.start();
        } catch (IOException ex) {
            System.out.println(" ERRO ao inicar o Client:" + ex.getMessage());
        }
        
        System.out.println(" Cliente finalizado!");
    }
    
}
