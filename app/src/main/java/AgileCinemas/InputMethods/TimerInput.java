package AgileCinemas.InputMethods;
import AgileCinemas.Crud;
import AgileCinemas.CustomerInterface;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;
public class TimerInput
{
    private String userInput = "";
    private CustomerInterface CI;
    TimerTask task = new TimerTask()
    {
        public void run()
        {
            if( userInput.equals("") )
            {
                if (CI.getCustomer() != null){
                    Crud.addTransaction(CI.getCustomer().getUsername(), 0, "failed", 0, 0, 0, 0, null,1, null, "time out" );
                }
                else{
                    Crud.addTransaction("anonymous", 0, "failed", 0, 0, 0, 0, null,1, null, "time out" );
                }
                System.out.println( "Time out. exit..." );
                System.exit( 0 );
            }
        }
    };

    public TimerInput(CustomerInterface CI){
        this.CI = CI;
    }

    public String getInput(Scanner scan) throws Exception
    {
        Timer timer = new Timer();
        timer.schedule( task, 120*1000 );
//        System.out.println( "Input a string within 5 seconds: " );
//        Scanner in = new Scanner(System.in);
        userInput = scan.nextLine();
        timer.cancel();
//        System.out.println( "you have entered: "+ userInput );
        return userInput;
    }

    public String get_input_mask(Scanner scan, String input){
        Timer timer = new Timer();
        timer.schedule( task, 120*1000 );
//        System.out.println( "Input a string within 5 seconds: " );
//        Scanner in = new Scanner(System.in);
        userInput = PasswordField.readPassword(input);
        timer.cancel();
//        System.out.println( "you have entered: "+ userInput );
        return userInput;
    }
}