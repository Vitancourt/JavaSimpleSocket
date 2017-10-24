/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiCast;

import java.util.ArrayList;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

/**
 *
 * @author sgt
 */
public class MulticastRequisitions {
    private ArrayList requisitions;
    private ArrayList answers;

    public MulticastRequisitions() {
        this.requisitions = new ArrayList();
        this.answers = new ArrayList();
    }
    
    public void armazenarReq(String req){
        this.getRequisitions().add(req);
    }
    
    public void armazenarAns(String ans){
        this.getAnswers().add(ans);
    }
    
    public boolean checkReq(String req){
        return getRequisitions().contains(req);
    }
    
    public boolean checkAns(String ans){
        return getAnswers().contains(ans);
    }

    /**
     * @return the requisitions
     */
    public ArrayList getRequisitions() {
        return requisitions;
    }

    /**
     * @param requisitions the requisitions to set
     */
    public void setRequisitions(ArrayList requisitions) {
        this.requisitions = requisitions;
    }

    /**
     * @return the answers
     */
    public ArrayList getAnswers() {
        return answers;
    }

    /**
     * @param answers the answers to set
     */
    public void setAnswers(ArrayList answers) {
        this.answers = answers;
    }


    
}