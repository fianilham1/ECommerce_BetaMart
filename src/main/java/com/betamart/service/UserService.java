package com.betamart.service;

import java.io.IOException;

public interface UserService {

    String forgotPassword(String username)throws IOException;

    String resetPassword(String token, String password) throws IOException;

}
