/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Menu;

import java.util.Scanner;

/**
 *
 * @author sgt
 */
public final class Menu {
    int options, optionsLista;
    Scanner scan;

    public Menu() {

    }
    
    public void recriaScan(){
        scan = new Scanner(System.in);
        options = -1;
        optionsLista = -1;
    }
    
    
    
    public int rodaMenu(){
        this.recriaScan();
        limpaTela();
        System.out.println("-------------------");
        System.out.println("1 - Configure serviço");
        System.out.println("2 - Configurar lista");
        System.out.println("3 - Iniciar serviço de escuta multicast");
        System.out.println("4 - Iniciar uma busca multicast");
        System.out.println("5 - Encerrar aplicação");
        options = Integer.parseInt(scan.nextLine());
        return options;
    }
    
    public int rodaMenuLista(){
        this.recriaScan();
        limpaTela();
        System.out.println("-------------------");
        System.out.println("1 - Adicionar palavra");
        System.out.println("2 - Listar palavras");
        System.out.println("0 - Voltar");
        optionsLista = Integer.parseInt(scan.nextLine());
        return optionsLista;       
    }
    
    /**
     * Da \n para limpar tela
     */
    public final static void limpaTela(){
        for(int i = 0; i < 3; i ++){
            System.out.println("\n");
        }
    }
    
}
