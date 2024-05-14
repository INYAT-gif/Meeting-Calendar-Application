package se.inyat.exception;

import se.inyat.util.ConsoleColors;

public class CalendarExceptionHandler extends Throwable {
    public static void handelException(Exception exception) {

        if (exception instanceof AuthenticationFailedException) {//check if exception is AuthenticationFailedException
            System.out.println(ConsoleColors.RED + exception.getMessage() + ConsoleColors.RESET);
        } else if (exception instanceof UserExpiredException) {//check if exception is UserExpiredException
            System.out.println(ConsoleColors.YELLOW + exception.getMessage() + ConsoleColors.RESET);
        } else if (exception instanceof DBConnectionException) {//check if exception is DBConnectionException
            System.out.println(exception.getMessage());
        } else if (exception instanceof MySQLException) {//check if exception is MySQLException
            System.out.println(exception.getMessage());
        } else {
            System.out.println("An unexpected error occurred.");//else sout an unexpected error
            exception.printStackTrace();
        }
    }
}