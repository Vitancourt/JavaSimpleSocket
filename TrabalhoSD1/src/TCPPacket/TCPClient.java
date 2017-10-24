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
import java.net.Socket;

/**
 *
 * @author sgt
 */
public class TCPClient implements Runnable{

    private int porta;
    private String ip;
    private String palavra;
    private final ListaController lista;

    public TCPClient(int porta, String ip, String palavra, ListaController lista) {
        this.porta = porta;
        this.ip = ip;
        this.palavra = palavra;
        this.lista = lista;
    }

    
    
    @Override
    public void run() {
     
        try {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            try (Socket clientSocket = new Socket(this.ip, this.porta)) {
                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                if(lista.buscaPalavra(palavra)){
                    outToServer.writeBytes(palavra);
                    System.out.println("Enviando para " + this.ip+ " - "+this.porta+" - "+palavra);
                    
                }else{
                    System.out.println("Erro TCPCliente palavra.");
                } 
                clientSocket.close();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }   
}
