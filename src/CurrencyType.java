/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bartosz
 */
public enum CurrencyType {
    USD("USD"),
    EUR("EUR"),
    CHF("CHF"),
    GBP("GBP");
    
    private String displayName;
    
    CurrencyType(String displayName){
        this.displayName = displayName;
    }
    
    public String getDisplayName(){
        return displayName;
    }
    @Override
    public String toString(){
        return displayName;
    }
                   
}

