/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiCast;

import Configurador.Configurador;
import ListProvider.ListaController;
import TCPPacket.TCPServer;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sgt
 */
public class MulticastSender implements Runnable{

    private final MulticastSocket msocket;
    String ip, resposta, palavra;
    int porta, op, nroConsulta;
    Configurador conf;
    ListaController lista;
    /**
     *
     * @param lista
     * @param op
     * @param nroConsulta
     * @param palavra
     * @param mr
     * @param conf
     * @throws java.lang.InterruptedException
     * @throws java.io.IOException
     */
    public MulticastSender(ListaController lista, int op , int nroConsulta, String palavra, MulticastRequisitions mr, Configurador conf) throws InterruptedException, IOException {
        this.msocket = conf.novoSocket();
        this.op = op;
        this.nroConsulta = nroConsulta;
        this.palavra = palavra;
        this.conf = conf;
        this.lista = lista;
        this.ip = conf.getIpLocal();
        this.porta = conf.getPorta();
        //Tratamento de opções
        switch (op) {
            case 1:
                resposta = "respostaBusca#yes#"+Integer.toString(nroConsulta);
                System.out.println("#Enviando resposta. Nro: "+Integer.toString(nroConsulta));
                break;
            case 2:
                resposta = "respostaBusca#no#"+nroConsulta;
                System.out.println("#Enviando resposta. Nro: "+Integer.toString(nroConsulta));
                break;
            case 3:
                resposta = "busca#"+palavra+"#"+ip+"#"+conf.getPortaTCP()+"#"+Integer.toString(nroConsulta);
                mr.armazenarReq(Integer.toString(nroConsulta));
                System.out.println("#Enviando requisição. Nro: "+Integer.toString(nroConsulta));
                break;
            default:
                System.out.println("Operação inválida");
                sleep(2000);
                break;
        }
    }
    
    @Override
    public void run() {
        String answer = resposta;
        DatagramPacket sendPacket = new DatagramPacket(answer.getBytes(), answer.length(),conf.getGroup(), conf.getPorta());        
        try {
            msocket.send(sendPacket);
            sleep(2000);
        } catch (IOException ex) {
            System.out.println("#Erro ao enviar pacote MulticastSender");
        } catch (InterruptedException ex) {
            Logger.getLogger(MulticastSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
