/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Configurador;

import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Classe para consultar o IP a partir do nome da placa de rede
 * @author Maikel Vitancourt
 */
public final class PlacaDeRede {
    private String ip;
    private final String nomePlacaDeRede;

    /**
     *
     * @param nomePlacaDeRede
     * Recebe o nome da placa de rede para consultar o IP
     */
    public PlacaDeRede(String nomePlacaDeRede) {
        this.nomePlacaDeRede = nomePlacaDeRede;
        this.setIp(this.consultarIP(nomePlacaDeRede));
        //System.out.println(this.getIp());
    }
    
    private String consultarIP(String nomePlacaDeRede){
        Enumeration nis = null;
        String retorno = null;
        try {
                nis = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
                System.out.println("#Erro ao consultar IP.");
                System.out.println("Aguarde...");
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("#Erro no sleep classe PlacaDeRede");
            }
        }
        while (nis.hasMoreElements()) {  
            NetworkInterface ni = (NetworkInterface) nis.nextElement();  
            Enumeration ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {  
                InetAddress ia = (InetAddress) ias.nextElement();
                if (ni.getName().equals(nomePlacaDeRede))
                    retorno = ia.getHostAddress();   
            }  
        }
        return retorno;
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
}
