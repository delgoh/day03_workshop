package sg.edu.nus.iss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        String filePath = args[0];

        File newPath = new File(filePath);
        if (newPath.exists()) {
            System.out.println("Directory already exists.");
        } else {
            newPath.mkdir();
        }

        System.out.println("Welcome to your shopping cart");
        System.out.print("What would you like to do? ");

        List<String> cartItems = new ArrayList<String>();
        String username = "";
        
        Console cons = System.console();
        String input = cons.readLine();

        while (!input.equals("quit")) {
            // String[] inputWords = input.split("[,\\s]+");
            
            if (input.startsWith("login")) {
                Scanner scan = new Scanner(input.substring(6));
                
                while (scan.hasNext()) {
                    username = scan.next();
                }

                File newFile = new File(filePath + File.separator + username + ".db");
                if (newFile.exists()) {
                    System.out.println("Welcome back, " + username + ".");
                    System.out.print("What would you like to do? ");
                } else {
                    newFile.createNewFile();
                    System.out.println("New user (" + username + ") registered.");
                }
            }

            if (input.equals("users")) {
                File currentPath = new File(filePath);
                String[] filesList = currentPath.list();
                System.out.println("List of files and directories: ");
                for (String file : filesList) {
                    System.out.println(file);
                }
            }

            if (input.startsWith("add")) {
                input = input.replace(',', ' ');
                Scanner scan = new Scanner(input.substring(4));

                FileWriter fw = new FileWriter(new File(filePath + File.separator + username + ".db"), true);
                PrintWriter pw = new PrintWriter(fw);

                String currentScanStr = "";
                while (scan.hasNext()) {
                    currentScanStr = scan.next();

                    pw.append(currentScanStr + "\n");
                }

                pw.flush();
                pw.close();
                fw.close();
            }

            if (input.equals("list")) {
                File dbFile = new File(filePath + File.separator + username + ".db");
                BufferedReader br = new BufferedReader(new FileReader(dbFile));

                cartItems = new ArrayList<String>();
                String lineData = "";
                while ((lineData = br.readLine()) != null) {
                    System.out.println(lineData);
                    cartItems.add(lineData);
                }

                br.close();
            }

            if (input.equals("save")) {
                if (username.equals("")) {
                    System.out.println("Please login first. Type \"login <username>\".");
                    continue;
                }

                // File newFile = new File(filePath)
            }

            if (input.startsWith("delete")) {
                String[] strArr = input.split(" ");
                int itemVal = Integer.parseInt(strArr[1]);

                if (itemVal > cartItems.size() || itemVal < 1) {
                    System.out.println("Item index out of range");
                } else {
                    cartItems.remove(itemVal - 1);
                    FileWriter fw = new FileWriter(new File(filePath + File.separator + username + ".db"), false);
                    BufferedWriter bw = new BufferedWriter(fw);
                    for (String cartItem : cartItems) {
                        bw.append(cartItem);
                        bw.newLine();
                    }

                    bw.flush();
                    bw.close();
                    fw.close();
                }
            }
            
            input = cons.readLine();
        }

    }
}
