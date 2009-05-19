package com.prettypetty.dao.exceptions;

@SuppressWarnings("serial")
public class NotExistException extends Exception {
	public NotExistException(){
        super();
    }
    
    public  NotExistException(String type, Exception e){
        super(type+" matching the name doesnt exists." + e.getMessage());
    }
    
    public NotExistException(String type, String message){
        super(type+" matching \" "+message+" \" doesnt exists.");
    }

    public NotExistException(Throwable cause) {
        super(cause);
    }
}
