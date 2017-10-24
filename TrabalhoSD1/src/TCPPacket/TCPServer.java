/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPPacket;

import ListProvider.ListaController;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sgt
 */
public class TCPServer implements Runnable{
    
    private  int porta;
    private String ip;
    private ListaController lista;
    private Socket connectionSocket;
    private String clientSentence;
    private String capitalizedSentence;
    private ServerSocket welcomeSocket;
    private BufferedReader inFromClient;
    private boolean valida = true;
    private int flag = -1; 
    
    public TCPServer(int porta, String ip, ListaController lista) throws IOException {
        this.porta = porta;
        this.ip = ip;
        this.lista = lista;           
    }

    
    
    @Override
    public void run() {
        try {
            this.setWelcomeSocket(new ServerSocket(this.getPorta()));             
            while(isValida() == true){
                if(getFlag() == 1){
                    System.out.println("Vou servir"); 
                    this.escuta();
                    setFlag(0);
                }
                sleep(2);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void escuta(){
        try {
            this.setConnectionSocket(getWelcomeSocket().accept());
            setInFromClient(new BufferedReader(new InputStreamReader(getConnectionSocket().getInputStream())));
            DataOutputStream outToClient = new DataOutputStream(getConnectionSocket().getOutputStream());                 
            setClientSentence(getInFromClient().readLine());
            setClientSentence(getClientSentence().trim());
            System.out.println("Recebendo: " + getClientSentence());
            if(!lista.buscaPalavra(clientSentence)){
                getLista().adicionarPalavra(getClientSentence());
            }
            getWelcomeSocket().close();
            getConnectionSocket().close();
        } catch (IOException ex) {
            System.out.println(ex);
        }        
    }   
    
    public void aceita(){
        setFlag(1);
    }
    
    public void rejeita(){
        setFlag(0);
    }

    /**
     * @return the porta
     */
    public int getPorta() {
        return porta;
    }

    /**
     * @param porta the porta to set
     */
    public void setPorta(int porta) {
        this.porta = porta;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the lista
     */
    public ListaController getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(ListaController lista) {
        this.lista = lista;
    }

    /**
     * @return the connectionSocket
     */
    public Socket getConnectionSocket() {
        return connectionSocket;
    }

    /**
     * @param connectionSocket the connectionSocket to set
     */
    public void setConnectionSocket(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
    }

    /**
     * @return the clientSentence
     */
    public String getClientSentence() {
        return clientSentence;
    }

    /**
     * @param clientSentence the clientSentence to set
     */
    public void setClientSentence(String clientSentence) {
        this.clientSentence = clientSentence;
    }

    /**
     * @return the capitalizedSentence
     */
    public String getCapitalizedSentence() {
        return capitalizedSentence;
    }

    /**
     * @param capitalizedSentence the capitalizedSentence to set
     */
    public void setCapitalizedSentence(String capitalizedSentence) {
        this.capitalizedSentence = capitalizedSentence;
    }

    /**
     * @return the welcomeSocket
     */
    public ServerSocket getWelcomeSocket() {
        return welcomeSocket;
    }

    /**
     * @param welcomeSocket the welcomeSocket to set
     */
    public void setWelcomeSocket(ServerSocket welcomeSocket) {
        this.welcomeSocket = welcomeSocket;
    }

    /**
     * @return the inFromClient
     */
    public BufferedReader getInFromClient() {
        return inFromClient;
    }

    /**
     * @param inFromClient the inFromClient to set
     */
    public void setInFromClient(BufferedReader inFromClient) {
        this.inFromClient = inFromClient;
    }

    /**
     * @return the valida
     */
    public boolean isValida() {
        return valida;
    }

    /**
     * @param valida the valida to set
     */
    public void setValida(boolean valida) {
        this.valida = valida;
    }

    /**
     * @return the flag
     */
    public int getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(int flag) {
        this.flag = flag;
    }
}
