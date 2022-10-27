import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        //Read C file from file address
        System.out.println("Please input the path of the code file");
        //String fileName = "D:\\Code\\software_engineering_lab1\\src\\sample.c";
        String fileName = scanner.nextLine();

        //Completion Level: You can enter 4
        System.out.println("Please input the completion level (from low to high as 1, 2, 3, 4 )");
        //int level = 4;
        int level = scanner.nextInt();

        //Read the file
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String Cfile = "";
        String line = bufferedReader.readLine();
        while (line != null) {
            Cfile += line;
            line = bufferedReader.readLine();
        }

        //Judge requirements
        switch (level) {
            case 1:
                findKey(Cfile);
                break;
            case 2:
                findKey(Cfile);
                findSwitchAndCase(Cfile);
                break;
            case 3:
                findKey(Cfile);
                findSwitchAndCase(Cfile);
                processElse(Cfile,3);
                break;
            case 4:
                findKey(Cfile);
                findSwitchAndCase(Cfile);
                processElse(Cfile,4);
                break;
        }

    }

    public static void findKey(String s) {
        String keywords = "char、short、int、long、signed、unsigned、float、double、struct、union、enum、void、"
                + "for、do、while、break、continue、if、else、goto、switch、case、default、return、"
                + "auto、extern、register、static、typedef、const、sizeof、volatile";
        Pattern p1 = Pattern.compile("、");
        String[] cKey = p1.split(keywords);

        int totalNum = 0;
        for (String oneKey : cKey) {
            Pattern p = Pattern.compile(oneKey + "[^a-z]");
            Matcher matcher = p.matcher(s);
            while (matcher.find()) {
                totalNum++;
            }
        }
        System.out.println("total num: " + totalNum);
    }

    public static void findSwitchAndCase(String s) {
        Pattern p1 = Pattern.compile("switch");
        String[] switchs = p1.split(s);
        int switchNum = switchs.length - 1;
        System.out.println("switch num: " + switchNum);

        System.out.print("case num:");
        for (int i = 1; i < switchs.length; i++) {
            Pattern p2 = Pattern.compile("case");
            String[] cases = p2.split(switchs[i]);
            int caseNum = cases.length - 1;
            System.out.print(" " + caseNum);
        }
        System.out.println("");
    }

    public static void processElse(String code,int level) {
        Pattern p = Pattern.compile("if|else if|else");
        Matcher matcher = p.matcher(code);
        Stack<String> stack = new Stack();
        int ifElse = 0;
        int ifElseIf = 0;
        boolean checkElseIf = false;
        while (matcher.find()) {
            String string = code.substring(matcher.start(), matcher.end());
            if (string.equals("if")) {
                stack.push(string);
            }
            else if (string.equals("else if")){
                stack.push(string);
            }
            else{  // string == "else"
                while (!stack.isEmpty()){
                    String s= stack.pop();
                    if(s.equals("else if")){
                        checkElseIf = true;
                    }else{ // s == "if"
                        if(checkElseIf == true){
                            ifElseIf++;
                            checkElseIf = false;
                        }else{
                            ifElse++;
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("if-else num: " + ifElse);
        if(level == 4){
            System.out.println("if-elseif-else num: " + ifElseIf);
        }
    }
}

