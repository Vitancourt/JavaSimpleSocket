/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Configurador;

import Menu.Menu;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author sgt
 */
public final class Configurador {
    private InetAddress group = null;
    private MulticastSocket socket = null;
    private Scanner scan;
    private String ipLocal = null;
    private int porta, portaTCP;
    private String ipgroup = null;
    public Configurador() throws InterruptedException {
        scan = new Scanner(System.in);
        boolean teste;
        while(teste = configura()){
            if(!teste){
                Menu.limpaTela();
                System.out.println("Configuração aceita!");
                System.out.println("Aguarde...");
                sleep(2000);
            }else{
                Menu.limpaTela();
                System.out.println("Configuração rejeitada!");
                System.out.println("Aguarde...");
                sleep(2000);
            }           
        }

    }
    
    public boolean configura() throws InterruptedException{
        boolean validIp, validPort = false;
        Menu.limpaTela();
        //Chama Objeto que retorna o ip da placa de rede
        System.out.println("Digite o nome da sua placa de rede: ");
        String pRedeName = getScan().nextLine();
        PlacaDeRede pdr = new PlacaDeRede(pRedeName);
        setIpLocal(pdr.getIp()); 
        
        if(getIpLocal() == null){
            Menu.limpaTela();
            System.out.println("Não foi possível identificar sua placa de rede.");
            System.out.println("Aguarde...");
            sleep(2000);
            Menu.limpaTela();
            System.out.println("Digite seu IP de rede manualmente: ");
            setIpLocal(getScan().nextLine());
        }
        
        try{
            System.out.println("Digite o IP do grupo (exemplo 239.0.0.1): ");
            setIpgroup(getScan().nextLine());
            setGroup(InetAddress.getByName(getIpgroup()));
            System.out.println("Configurando...");
            if(getGroup().isMulticastAddress()){
                validIp = false;
            }else{                
                System.out.println("Endereço de multicast inválido");
                System.out.println("Aguarde...");
                validIp = true;
                sleep(2000);
            }
            
            sleep(2000);
        }catch(UnknownHostException ex){
            System.out.println("IP de grupo desconhecido!");
            validIp = true;
            sleep(2000);
        }
        if(validIp != true){
            try{
                System.out.println("Digite a porta para escuta multicast: ");
                setPorta(Integer.parseInt(getScan().nextLine()));
                setSocket(new MulticastSocket(getPorta())); 
                System.out.println("Configurando...");
                validPort = false;
                sleep(2000);
            }catch(IOException ex){
                System.out.println("Porta inválida!");
                validPort = true;
                sleep(2000);
            }              
        }
        System.out.println("Digite a porta do servidor TCP: ");
        setPortaTCP(Integer.parseInt(getScan().nextLine()));
        return validIp && validPort;
        
    } 
    
    public MulticastSocket novoSocket(){
        try {
            MulticastSocket nsocket;
            nsocket = new MulticastSocket(getPorta());
            nsocket.joinGroup(this.getGroup());
            return nsocket;
        } catch (IOException ex) {
            System.out.println("Erro ao conectar Multicast #Configurador.java");
        }
        return null;
    }

    /**
     * @return the group
     */
    public InetAddress getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(InetAddress group) {
        this.group = group;
    }

    /**
     * @return the socket
     */
    public MulticastSocket getSocket() {
        return socket;
    }

    /**
     * @param socket the socket to set
     */
    public void setSocket(MulticastSocket socket) {
        this.socket = socket;
    }

    /**
     * @return the scan
     */
    public Scanner getScan() {
        return scan;
    }

    /**
     * @param scan the scan to set
     */
    public void setScan(Scanner scan) {
        this.scan = scan;
    }

    /**
     * @return the ipLocal
     */
    public String getIpLocal() {
        return ipLocal;
    }

    /**
     * @param ipLocal the ipLocal to set
     */
    public void setIpLocal(String ipLocal) {
        this.ipLocal = ipLocal;
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
     * @return the ipgroup
     */
    public String getIpgroup() {
        return ipgroup;
    }

    /**
     * @param ipgroup the ipgroup to set
     */
    public void setIpgroup(String ipgroup) {
        this.ipgroup = ipgroup;
    }

    /**
     * @return the portaTCP
     */
    public int getPortaTCP() {
        return portaTCP;
    }

    /**
     * @param portaTCP the portaTCP to set
     */
    public void setPortaTCP(int portaTCP) {
        this.portaTCP = portaTCP;
    }
}
