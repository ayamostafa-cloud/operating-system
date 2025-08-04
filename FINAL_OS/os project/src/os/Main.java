package os;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ReadyQueue readyQueue = new ReadyQueue();
        Memory memory = new Memory();
        Scanner scanner = new Scanner(System.in);

        // Get the number of processes
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        // Get file paths for each process
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter the file path for process " + (i + 1) + ": ");
            String filePath = scanner.nextLine();
            try {
                Process process = Process.createFromFile(i + 1, filePath, i * 10);
                readyQueue.enqueue(process);
            } catch (Exception e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
        }

        // Get the quantum value for Round Robin
        System.out.print("Enter the quantum value for Round Robin scheduling: ");
        int quantum = scanner.nextInt();

        // Create and start the SlaveCore thread
        SlaveCore slaveCore = new SlaveCore(readyQueue, memory, quantum);
        slaveCore.start();

        try {
            slaveCore.join();  // Wait for the thread to finish
        } catch (InterruptedException e) {
            System.err.println("Execution interrupted: " + e.getMessage());
        }

        System.out.println("All processes have been executed.");
    }
}