/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListProvider;

import Menu.Menu;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author sgt
 */
public class ListaController {

    private ArrayList lista;

    /**
     * Recebe a lista para controle
     */
    public ListaController() {
        this.lista = new ArrayList();
    }
    
    public void adicionarPalavra(String palavra){
        this.lista.add(palavra);
    }

    /**
     * @param palavra
     * Recebe uma palavra para verificar se contém na lista
     * @return the lista
     * Retorna o boleano
     */    
    public boolean buscaPalavra(String palavra){
        return this.lista.contains(palavra);
    }
    
    /**
     *
     * @throws InterruptedException
     */
    public void adicionarPalavra() throws InterruptedException{
        Menu.limpaTela();
        Scanner scan = new Scanner(System.in);
        String palavra = "1";
        while(!palavra.equals("0")){
            System.out.println("Digite uma palavra para inserir na lista: (0 - SAIR) ");
            palavra = scan.nextLine();
            if(!palavra.equals("0")){
                this.adicionarPalavra(palavra);
            }
        }  
    }
    
    public void imprimeLista() throws InterruptedException{
        Menu.limpaTela();
        Scanner scan = new Scanner(System.in);
        System.out.println("Tamanho da lista: "+lista.size());
        Iterator it = lista.iterator();
        while(it.hasNext()){
            System.out.print(it.next());
            System.out.print(" - ");        
        }
        System.out.println("\n Pressione uma tecla para continuar.");
        scan.nextLine();
        System.out.println("Aguarde...");
        sleep(2000);
    }


    
    /**
     * @return the lista
     */
    public ArrayList getLista() {
        return lista;
    }

    /**
     * @param lista the lista to set
     */
    public void setLista(ArrayList lista) {
        this.lista = lista;
    }
    
    
}
