package example;

import example.command.Command;
import example.command.Commands;

import java.util.Scanner;

/**
 * Simple repl, type command described in {@link example.command.Commands} with ';' at the end,
 * or type 'exit;' to close repl
 * @see example.command.Commands Commands
 */
public class DbRepl {
    public void run() {
        showInstruction();
        StringBuilder commandBuilder;
        Scanner scan = new Scanner(System.in);
        String line;
        while (true) {
            commandBuilder = new StringBuilder();
            while (true) {
                System.out.print("> ");
                line = scan.nextLine();
                if (line.endsWith(";")) {
                    commandBuilder.append(line, 0, line.length() - 1);
                    break;
                }
                commandBuilder.append(line).append(" ");
            }
            String commandStr = commandBuilder.toString().trim();
            if (commandStr.equals("exit")) {
                break;
            }
            Command command = Commands.get(commandStr);
            command.execute();
        }

    }

    private void showInstruction() {
        System.out.println("Welcome to database tool");
        System.out.println("Available commands:");
        System.out.println("\tinit; - initialize database");
        System.out.println("\tlog <names of tables separated with space>; - generate log from specified tables");
        System.out.println("\texit; - stop program");
        System.out.println("\totherwise execute entered string as SQL query");
        System.out.println("Each command must end up with ';'");
    }
}
