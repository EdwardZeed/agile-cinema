package AgileCinemas.InputMethods;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;
public class TimerInput
{
    private String userInput = "";
    private Scanner in = new Scanner(System.in);
    TimerTask task = new TimerTask()
    {
        public void run()
        {
            if( userInput.equals("") )
            {
                System.out.println( "Time out. exit..." );
                System.exit( 0 );
            }
        }
    };

    public String getInput() throws Exception
    {
        Timer timer = new Timer();
        timer.schedule( task, 120*1000 );
//        System.out.println( "Input a string within 5 seconds: " );
//        Scanner in = new Scanner(System.in);
        userInput = in.nextLine();
        timer.cancel();
//        System.out.println( "you have entered: "+ userInput );
        return userInput;
    }
}