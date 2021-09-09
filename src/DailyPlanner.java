import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class DailyPlanner {
    public void Start(){
        consolListener();
    }
    private void consolListener(){
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> planner = new ArrayList<>();
        boolean continueWork = true;
        printCommands();
        while(continueWork){
            String text = scanner.nextLine();
            switch(parseComand(text)){
                case ("EXIT"): continueWork = false; break;
                case ("ADD"): planner = addTask(text , planner); break;
                case ("EDIT"): planner = editTask(text , planner); break;
                case ("DELETE"): planner = deleteTask(text , planner);break;
                case ("LIST"): printTask(planner); break;
                default: printCommands();
            }
        }
        scanner.close();
    }

    private ArrayList<String> addTask(String text, ArrayList<String> planner) {
        ArrayList<String> answer = planner;
        String task = delPrefix(text, parseComand(text));
        if(taskNumberIsChecked(text)){
            int number = getTaskNumber(text);
            if(number < 0 || number > planner.size() -1){
                System.out.printf("Колличество задачи не превышает %d \n", planner.size());
                return planner;
            }
            answer.add(number, delPrefix(task, Integer.toString(number + 1)));
        }
        else {
            answer.add(task);
        }
        return answer;
    }

    private ArrayList<String> editTask(String text, ArrayList<String> planner) {
        ArrayList<String> answer = planner;
        String task = delPrefix(text, parseComand(text));
        if(taskNumberIsChecked(text)){
            int number = getTaskNumber(text);
            if(number < 0 || number > planner.size() -1){
                System.out.printf("Задачи под номером %d не существует\n", number);
                return planner;
            }
            answer.remove(number);
            answer.add(number, delPrefix(task, Integer.toString(number + 1)));
        }
        return answer;
    }

    private ArrayList<String> deleteTask(String text, ArrayList<String> planner) {
        ArrayList<String> answer = planner;
        if(taskNumberIsChecked(text)){
            int number = getTaskNumber(text);
            if(number < 0 || number > planner.size() - 1){
                System.out.printf("Задачи под номером %d не существует\n", number);
                return planner;
            }
            answer.remove(number);
        }
        else {
            answer.remove(planner.size() - 1);
        }
        return answer;
    }

    private void printTask(ArrayList<String> planner) {
        for(int i = 0; i < planner.size(); i++){
            System.out.println(i + 1 + " " + planner.get(i));
        }
    }

    private String delPrefix(String text, String prefix){
        return text.replaceFirst(prefix, " ").trim();
    }

    private void printCommands() {
        System.out.println("EXIT — выход из программы");
        System.out.println("LIST — выводит дела с их порядковыми номерами");
        System.out.println("ADD — добавляет дело в конец списка или дело на определённое место, " +
                "сдвигая остальные дела вперёд, если указать номер");
        System.out.println("EDIT — заменяет дело с указанным номером");
        System.out.println("DELETE — удаляет");
    }

    private String parseComand(String text){
        Pattern pattern = Pattern.compile("^[A-Z]+");
        Matcher matcher = pattern.matcher(text);
        if(matcher.find())
            return matcher.group();
        return text;
    }

    private int getTaskNumber(String text){
        String task = delPrefix(text,parseComand(text));
        Pattern numberPattern = Pattern.compile("\\d+");
        Matcher numberMatcher = numberPattern.matcher(task);
        int n = 0;
        if(numberMatcher.find()) {
            String s = numberMatcher.group();
            n = Integer.parseInt(s) - 1;
        }
        return n;
    }

    private boolean taskNumberIsChecked(String text){
        Pattern pattern = Pattern.compile("^[A-Z]+ \\d+");
        Matcher matcher = pattern.matcher(text);
        if(matcher.find())
            return true;
        return false;
    }
}
