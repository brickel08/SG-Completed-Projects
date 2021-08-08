/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.guessthenumber.service;

/**
 *
 * @author benrickel
 */
public class InvalidRoundIdException extends Exception {
    
    public InvalidRoundIdException (String message) {
        super(message);
    }
    
    public InvalidRoundIdException (String message, Throwable cause) {
        super(message, cause);
    }
    
}
