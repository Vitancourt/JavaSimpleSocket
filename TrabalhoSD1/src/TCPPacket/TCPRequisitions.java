/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPPacket;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author sgt
 */
public class TCPRequisitions {

    ArrayList list;
    
    public TCPRequisitions() {
        list = new ArrayList();
    }
    
    public void addReq(String palavra){
        list.add(palavra);
    }
    
    public String buscaReq(String palavra){
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {  
            String c = (String) iterator.next();  
            String[] cSplit = c.split("#");
            if(cSplit[4].equals(palavra)){
                return c;
            }
            System.out.println (c);  
         }
        return null;
    }
    
    
    
}
