package main.java.backend.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import main.java.backend.model.User;
import main.java.backend.service.CustomerRepositoryImpl;
import main.java.backend.service.LoginValidator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DBReceive_User {

//    @Autowired
//    CustomerRepository customerRepository;

    public static DBSend DBSend = new DBSend();

    public void getUsers() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare("usersRequest", true, false, false, null);
        System.out.println(" [*] Waiting for messages from rest api");
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                
                CustomerRepositoryImpl customer = new CustomerRepositoryImpl();
                List<User> userList = customer.getAll(); //Already JSON
                System.out.println("Cek : "+userList);
                DBSend.sendToApi(new Gson().toJson(userList),"usersRequestFromDB");

            } catch (Exception e) {
                System.out.println("Error in receiver DB : "+e);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume("usersRequest", false, deliverCallback, consumerTag -> {
        });
    }

    public void registerUser() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare("registerUser", true, false, false, null);
        System.out.println(" [*] Waiting for messages from rest api");
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            User user = new Gson().fromJson(message, User.class);
            CustomerRepositoryImpl customer = new CustomerRepositoryImpl();
            User userDB = null;
            try {
                userDB = customer.findUsername(user);
                if(userDB==null){
                    customer.insert(user);
                    DBSend.sendToApi("Register is successful","registerUserFromDB");
                }else{
                    DBSend.sendToApi("Username is already exist","registerUserFromDB");
                }

            } catch (Exception e) {
                System.out.println("Error in receiver DB : "+e);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume("registerUser", false, deliverCallback, consumerTag -> {
        });
    }

    public void loginValidation() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare("loginValidation", true, false, false, null);
        System.out.println(" [*] Waiting for messages from rest api");
        channel.basicQos(1);
        List<String> errorList = new ArrayList<>();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                User user = new Gson().fromJson(message, User.class);
                LoginValidator loginValidator = new LoginValidator();
                CustomerRepositoryImpl customer = new CustomerRepositoryImpl();
                User userDB = customer.findUserLogin(user);
                if(userDB==null){
                    DBSend.sendToApi("Username is not found or/and Password is invalid","loginValidationFromDB");
                }else{
                    DBSend.sendToApi(new Gson().toJson(userDB),"loginValidationFromDB");
                }

            } catch (Exception e) {
                System.out.println("Error in receiver DB : "+e);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume("loginValidation", false, deliverCallback, consumerTag -> {
        });
    }

    public void updateUser() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare("updateUser", true, false, false, null);
        System.out.println(" [*] Waiting for messages from rest api");
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                User user = new Gson().fromJson(message, User.class);
                CustomerRepositoryImpl customer = new CustomerRepositoryImpl();
                customer.updateUser(user);
                DBSend.sendToApi("Update User is successfully","updateUserFromDB");

            } catch (Exception e) {
                System.out.println("Error in receiver DB : "+e);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume("updateUser", false, deliverCallback, consumerTag -> {
        });
    }

    public void deleteUser() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare("deleteUser", true, false, false, null);
        System.out.println(" [*] Waiting for messages from rest api");
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                User user = new Gson().fromJson(message, User.class);
                CustomerRepositoryImpl customer = new CustomerRepositoryImpl();
                customer.deleteUser(user.getId());
                DBSend.sendToApi("Delete User is successfully","deleteUserFromDB");

            } catch (Exception e) {
                System.out.println("Error in receiver DB : "+e);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume("deleteUser", false, deliverCallback, consumerTag -> {
        });
    }

    public void passRecoveryByEmail() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare("passRecoveryByEmail", true, false, false, null);
        System.out.println(" [*] Waiting for messages from rest api");
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                User user = new Gson().fromJson(message, User.class);
                CustomerRepositoryImpl customer = new CustomerRepositoryImpl();
                User userDB = customer.findUsername(user);
                if(userDB==null){
                    DBSend.sendToApi("Username is not found","passRecoveryByEmailFromDB");
                }else{
                    DBSend.sendToApi(new Gson().toJson(userDB),"passRecoveryByEmailFromDB");
                }

            } catch (Exception e) {
                System.out.println("Error in receiver DB : "+e);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume("passRecoveryByEmail", false, deliverCallback, consumerTag -> {
        });
    }


    public void updatePassByEmail() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare("addNewPass", true, false, false, null);
        System.out.println(" [*] Waiting for messages from rest api");
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                User user = new Gson().fromJson(message, User.class);
                CustomerRepositoryImpl customer = new CustomerRepositoryImpl();
                customer.updatePassByEmail(user);
                DBSend.sendToApi("Password is recovered successfully","addNewPassFromDB");

            } catch (Exception e) {
                System.out.println("Error in receiver DB : "+e);
            } finally {
                System.out.println(" [x] Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume("addNewPass", false, deliverCallback, consumerTag -> {
        });
    }
}
