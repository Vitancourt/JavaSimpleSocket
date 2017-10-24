/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiCast;

import Configurador.Configurador;
import ListProvider.ListaController;
import TCPPacket.TCPClient;
import TCPPacket.TCPRequisitions;
import TCPPacket.TCPServer;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MulticastListener implements Runnable{

    private final MulticastSocket msocket;
    public ListaController lista;
    MulticastRequisitions mr;
    Configurador conf;
    private final TCPServer ts;

    /**
    * @author Maikel
     * @param lista
     * @param mr
     * @param conf
     * @param ts
     * @throws java.io.IOException
    */ 
    public MulticastListener(ListaController lista, MulticastRequisitions mr, Configurador conf, TCPServer ts) throws IOException {
        this.msocket = conf.novoSocket();
        this.lista = lista;
        this.mr = mr;
        this.conf = conf;
        this.ts = ts;
    }
 
    @Override
    public final void run() {
        try {
            byte[] listener = new byte[50]; 
            DatagramPacket receivePacket = new DatagramPacket(listener, listener.length);
            System.out.println("#Serviço de escuta rodando...");
            msocket.receive(receivePacket);
            msocket.disconnect();
            Thread mCast = new Thread(new MulticastListener(lista, mr, conf, ts));
            mCast.start();
            String mensagem = new String(receivePacket.getData());
            mensagem = mensagem.trim();
            String[] divisao;
            divisao = mensagem.split("#");
            switch (divisao[0]) {
                case "busca":                    
                    /*
                    *Efetua a busca
                    *Se encontar envia pacote multicast
                    */
                    //Verifica se não é sua própria requisição
                    if(!mr.checkReq(divisao[4])){
                        System.out.println("#Requisição recebida = ("+mensagem+")");
                        System.out.println("#Buscando... Nro: "+divisao[4]);
                        mr.armazenarAns(divisao[4]);
                        sleep(2000);
                        int nroConsulta = Integer.parseInt(divisao[4]);
                        if(lista.buscaPalavra(divisao[1])){
                            System.out.println("#Lista contém. Nro: "+divisao[4]);
                            String palavra = divisao[2];
                            Thread sender = new Thread(new MulticastSender(lista, 1, nroConsulta, palavra, mr, conf));
                            sender.start();                            
                            System.out.println("Ligando TCPClient...");
                            sleep(2000);
                            TCPClient tc = new TCPClient(Integer.parseInt(divisao[3]), conf.getIpLocal(), divisao[1], lista);
                            Thread thdc = new Thread(tc);
                            thdc.start();
                        }else{
                            System.out.println("#Lista não contém. Nro: "+divisao[4]);
                            Thread sender = new Thread(new MulticastSender(lista, 2, nroConsulta, "", mr, conf));
                            sender.start();
                        }
                    }
                    break;
                case "respostaBusca":
                    if(!mr.checkAns(divisao[2])){
                        System.out.println("#Resposta recebida = ("+mensagem+")");
                        //chama thread de tratamento de resposta 
                        if("yes".equals(divisao[1])){
                            System.out.println("#Respondendo busca... Nro: "+divisao[2]);
                            mr.armazenarAns(divisao[2]);   
                            System.out.println("Ligando TCP Server");
                            ts.aceita();                                 
                        }else if("no".equals(divisao[1])){
                            System.out.println("#Respondendo busca... Nro: "+divisao[2]);
                            mr.armazenarAns(divisao[2]);
                            sleep(2000);
                        }
                    }
                    break;
                default:
                    System.out.println("ERRO - REQUISIÇÃO NEGADA (#1 Fora do padrão)");
                    sleep(1000);
                    break;
            }
        } catch (IOException ex) {
            System.out.println("Erro na criação da thread MulticastListener");
        } catch (NullPointerException ex) {
            System.out.println("Porta ou IP inválidos para criação.");
        } catch (InterruptedException ex) {
            Logger.getLogger(MulticastListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

