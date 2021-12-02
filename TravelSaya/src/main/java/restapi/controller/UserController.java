package main.java.restapi.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import main.java.backend.model.User;
import main.java.restapi.rabbitmq.RestApiReceive;
import main.java.restapi.rabbitmq.RestApiSend;
import main.java.restapi.util.CustomErrorType;
import main.java.restapi.util.MessageType;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.reflect.Type;
import java.util.ArrayList;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class UserController {

	private static List<User> userList;

	// private final String HEADER = "Authorization";
	// private final String PREFIX = "Bearer ";
	// private final String SECRET = "mySecretKey";

	@Autowired
	RestApiSend restApiSend;

	@Autowired
	RestApiReceive restApiReceive;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	//public User login(@RequestBody JSONObject jobj) throws Exception {
	public ResponseEntity<?> loginValidation(@RequestBody JSONObject jobj) throws Exception {

		//SENDING MSG to RabbitMq...........................
		restApiSend.sendToDB(jobj.toString(),"loginValidation");

		//RECEIVING MSG from RabbitMq.......................
		String msg = restApiReceive.receiveFromDB("loginValidationFromDB");
		User user = null;
		try{
            if (msg.equals("Username is not found or/and Password is invalid")){
                return new ResponseEntity<>(new CustomErrorType("Unable to Login. Username is not found or/and Password is invalid"),
                        HttpStatus.NOT_FOUND);
            }else{
                user = new Gson().fromJson(msg, User.class);
                String token = getJWTToken(user.getUsername()+"/"+user.getName());
                // user.setToken(token);
                // user.setLoginStatus("Successful");

            }
        }catch (Exception e){
            return new ResponseEntity<>(new MessageType("Please Try Again, request is under process"),HttpStatus.NOT_FOUND);
        }


		return (new ResponseEntity<>(user, HttpStatus.CREATED));
	}


	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public ResponseEntity<?> requestUsers() throws Exception {

		//SENDING MSG to RabbitMq...........................
		restApiSend.sendToDB("getUsers","usersRequest");

		//RECEIVING MSG from RabbitMq.......................
		String msg = restApiReceive.receiveFromDB("usersRequestFromDB");
		System.out.println("get all users: "+msg);
		try{
			if(msg.equals("No User Found")){
				return new ResponseEntity<>(new CustomErrorType("No User Found"),
						HttpStatus.NOT_FOUND);
			}
			Type listType = new TypeToken<ArrayList<User>>(){}.getType();
			userList = new Gson().fromJson(msg, listType);
			
	
		}catch (Exception e){
			return new ResponseEntity<>(new MessageType("Please Try Again, request is under process"),HttpStatus.NOT_FOUND);
		}

		return (new ResponseEntity<>(userList, HttpStatus.OK));
	}

	

	@RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody JSONObject jobj) throws Exception {

        //SENDING MSG to RabbitMq...........................
        restApiSend.sendToDB(jobj.toString(),"registerUser");

        //RECEIVING MSG from RabbitMq.......................
        String msg = restApiReceive.receiveFromDB("registerUserFromDB");

        if(msg.equals("Username is already exist")){
            return new ResponseEntity<>(new CustomErrorType("Unable to register. Username is already exist"),
                    HttpStatus.NOT_FOUND);
        }

        return (new ResponseEntity<>(new MessageType("Register is successful"), HttpStatus.CREATED));
    }

	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public ResponseEntity<?> updateUser(@RequestBody JSONObject jobj) throws Exception {

        //SENDING MSG to RabbitMq...........................
        restApiSend.sendToDB(jobj.toString(),"updateUser");

        //RECEIVING MSG from RabbitMq.......................
        restApiReceive.receiveFromDB("updateUserFromDB");

        return (new ResponseEntity<>(new MessageType("Update is successful"), HttpStatus.CREATED));
    }

	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public ResponseEntity<?> deleteUser(@RequestBody JSONObject jobj) throws Exception {

        //SENDING MSG to RabbitMq...........................
        restApiSend.sendToDB(jobj.toString(),"deleteUser");

        //RECEIVING MSG from RabbitMq.......................
        restApiReceive.receiveFromDB("deleteUserFromDB");

        return (new ResponseEntity<>(new MessageType("Delete is successful"), HttpStatus.CREATED));
    }


	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 7000000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
}
