package io.aos.groovy.binding

import groovy.lang.Binding;
import groovy.lang.Script;

public class BindingScript1 {
    
    public static void main(String[] args) {
        
        // lets pass in some variables
        Binding binding = new Binding();
        binding.setVariable("cheese", "Cheddar")
        binding.setVariable("args", args)
        
        Script foo = new Callee(binding);
        
        foo.run();

     }

}
