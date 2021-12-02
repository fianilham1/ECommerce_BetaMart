package main.java.backend;

// import main.java.backend.rabbitmq.DBReceive_Flight;
import main.java.backend.rabbitmq.DBReceive_User;
import main.java.backend.rabbitmq.DBSend;

public class BackEndMain {
    public static DBReceive_User DBReceiveUser = new DBReceive_User();
    public static DBSend DBSend = new DBSend();
    // public static DBReceive_Flight DBReceiveFlight = new DBReceive_Flight();

    public static void main(String[] args) throws Exception{
        //User
        DBReceiveUser.loginValidation();
        DBReceiveUser.registerUser();
        DBReceiveUser.passRecoveryByEmail();
        DBReceiveUser.updatePassByEmail();
        DBReceiveUser.getUsers();
        DBReceiveUser.updateUser();
        DBReceiveUser.deleteUser();

        // //Flight
        // DBReceiveFlight.findFlight();
        // DBReceiveFlight.purchaseFlight();
        // DBReceiveFlight.proceedPay();

        // //Ticket
        // DBReceiveFlight.eTicket();

    }
}
