package br.com.erline.portfolio.domain.exception; 

public class DomainException extends RuntimeException { 
    public DomainException(String message) { 
        super(message); 
    } 
}