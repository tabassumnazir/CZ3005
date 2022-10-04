import java.util.Scanner;

public class main {

    public static void main(String[] args){
        Utility.convertData();
        Utility runTask = null;
        Scanner sc = new Scanner(System.in);
        int choice = 0, startNode = 0, endNode = 0;
        while (choice != 4) {
            System.out.print(
                "\n----Start----\n" +
                "1)   Task 1\n"  +
                "2)   Task 2\n"  +
                "3)   Task 3\n"  +
                "4)   Exit\n"    +
                "Choose task to run: "
            );
            choice = Integer.parseInt(sc.nextLine());
            if(choice == 1){
                runTask = new task1();
            }
            else if(choice == 2){
                System.out.print("Enter budget: ");
                Utility.energyBudget = Integer.parseInt(sc.nextLine());
                runTask = new Task2();
            }
            else if(choice == 3){
                System.out.print("Enter budget: ");
                Utility.energyBudget = Integer.parseInt(sc.nextLine());
                runTask = new Task3();
            }
            else if(choice == 4){
                sc.close();
                return;
            }
            else{
                System.out.print("Enter option within 1-4");
                continue;
            }
            System.out.print("Enter start node: ");
            startNode = Integer.parseInt(sc.nextLine());
            System.out.print("Enter end node: ");
            endNode = Integer.parseInt(sc.nextLine());
            runTask.run(startNode, endNode);
        }

        sc.close();
    }
}
