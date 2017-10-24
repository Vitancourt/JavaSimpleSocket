/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhosd1;

import Configurador.Configurador;
import ListProvider.ListaController;
import Menu.Menu;
import MultiCast.MulticastListener;
import MultiCast.MulticastRequisitions;
import MultiCast.MulticastSender;
import TCPPacket.TCPClient;
import TCPPacket.TCPRequisitions;
import TCPPacket.TCPServer;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author sgt
 */
public class main {
    
    static Menu menu;
    
    public static void main(String[] args) throws InterruptedException, SocketException, IOException{
        //Var para retorno do menu
        int valorMenu = -1, valorMenuLista = -1;
        //Objeto menu
        menu = new Menu();
        //Objeto configurador
        Configurador conf = null;
        //Objeto lista;
        ListaController lista;
        lista = new ListaController();
        //Objeto requisições respotas
        MulticastRequisitions mr;
        mr = new MulticastRequisitions();
        //Variáveis threads
        Thread listener, sender;
        //Socket mc listener
        MulticastSocket socket = null;
        
        TCPServer ts = null;
        
        //Loop do menu
        while(valorMenu == -1){
            valorMenu = menu.rodaMenu();  
            switch (valorMenu){
                case 1:
                    Menu.limpaTela();
                    //Chama o objeto responsável pela configuração 
                    conf = new Configurador();
                    valorMenu = -1;
                    break;
                case 2:
                    Menu.limpaTela();
                    //Chama menu da lista
                    while(valorMenuLista == -1){
                        valorMenuLista = menu.rodaMenuLista();
                        //Chama o objeto responsável pela lista                        
                        switch (valorMenuLista){
                            case 1:
                                lista.adicionarPalavra();
                                valorMenuLista = -1;
                                break;
                            case 2:
                                lista.imprimeLista();
                                valorMenuLista = -1;
                                break;
                            case 0:
                                valorMenuLista = 0;
                                break;
                            default:
                                System.out.println("Opção inválida.");
                                System.out.println("Aguarde...");
                                valorMenuLista = -1;
                                sleep(2000);
                                break;
                        }
                    }
                    valorMenu = -1;
                    valorMenuLista = -1;
                    break;
                case 3:
                    //Se o serviço já foi configurado inicia thread lisetener
                    if(conf != null){  
                        Menu.limpaTela();
                        if(socket == null){
                            
                            ts = new TCPServer(conf.getPortaTCP(), conf.getIpLocal(), lista);
                            Thread thds = new Thread(ts);
                            thds.start();
                            
                            InetAddress grupo = conf.getGroup();
                            socket = conf.getSocket(); 
                            listener = new Thread(new MulticastListener(lista, mr, conf, ts));
                            listener.start();
                            System.out.println("Iniciando serviço de escuta...");
                            System.out.println("Aguarde...");
                            sleep(2000);
                        }else{
                            System.out.println("O serviço já está rodando.");
                            System.out.println("Aguarde...");
                            sleep(2000);
                        }

                    }else{
                        Menu.limpaTela();
                        System.out.println("O serviço ainda não configurado.");
                        System.out.println("Aguarde...");
                        sleep(2000);
                    }
                    valorMenu = -1;
                    break;
                case 4:
                    //Chama inicia a thread sender
                    System.out.println("Digite a palavra que deseja buscar na rede");
                    Scanner scan = new Scanner(System.in);
                    String busca = scan.nextLine();
                    
                    //Gerador de número aleatório
                    Random randomGenerator = new Random();
                    int randomInt = randomGenerator.nextInt(99999);
                    
                    sender = new Thread(new MulticastSender(lista, 3, randomInt, busca, mr, conf));
                    sender.start();
                    valorMenu = -1;
                    break;
                case 5:
                    Menu.limpaTela();
                    System.out.println("Encerrando aplicação...");
                    sleep(2000);
                    System.exit(0);
                    break;
                default:
                    Menu.limpaTela();
                    System.out.println("Opção inválida!");
                    System.out.println("Aguarde...");
                    valorMenu = -1;
                    sleep(2000);
                    break;
          }
        }
    }  
}
