package AgileCinemas.InputMethods;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;
public class TimerInput
{
    private String userInput = "";

    TimerTask task = new TimerTask()
    {
        public void run()
        {
            if( userInput.equals("") )
            {
                System.out.println( "you input nothing. exit..." );
                System.exit( 0 );
            }
        }
    };

    public String getInput() throws Exception
    {
        Timer timer = new Timer();
        timer.schedule( task, 5*1000 );
        System.out.println( "Input a string within 5 seconds: " );
        Scanner in = new Scanner(System.in);
        userInput = in.nextLine();
        timer.cancel();
        System.out.println( "you have entered: "+ userInput );
        return userInput;
    }
}