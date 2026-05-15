package com.arqui.seedair.exceptions;

public class ResourceNotFoundException extends RuntimeException{

        public ResourceNotFoundException(String message){
            super(message);
        }

}
