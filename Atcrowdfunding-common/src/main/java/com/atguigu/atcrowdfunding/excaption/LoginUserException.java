package com.atguigu.atcrowdfunding.excaption;

public class LoginUserException extends RuntimeException{
    public LoginUserException(String message) {
        super(message);
    }
}
