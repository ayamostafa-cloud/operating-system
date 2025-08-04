package os;

import java.util.Scanner;

public class SlaveCore extends Thread {
    private final ReadyQueue readyQueue;
    private final Memory memory;
    private final int quantum;

    public SlaveCore(ReadyQueue readyQueue, Memory memory, int quantum) {
        this.readyQueue = readyQueue;
        this.memory = memory;
        this.quantum = quantum;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (!readyQueue.isEmpty()) {
            Process process = readyQueue.dequeue();
            System.out.println("Executing Process " + process.getProcessID() + ": " + process.getBurstTime() + " remaining instructions.");

            int instructionsToExecute = Math.min(quantum, process.getBurstTime());
            for (int i = 0; i < instructionsToExecute; i++) {
                if (process.getProgramCounter() < process.getInstructions().length) {
                    String instruction = process.getInstructions()[process.getProgramCounter()];
                    System.out.println("Executing: " + instruction);

                    // Parse and execute the instruction
                    executeInstruction(instruction, scanner);
                    process.incrementProgramCounter();
                    process.decrementBurstTime();
                }
            }

            // Print process order in the ready queue
            printProcessOrder();

            // Re-enqueue the process if it has remaining burst time
            if (process.getBurstTime() > 0) {
                readyQueue.enqueue(process);
            } else {
                System.out.println("Process " + process.getProcessID() + " finished.");
            }
        }
    }

    private void executeInstruction(String instruction, Scanner scanner) {
        String[] parts = instruction.split(" ");
        String command = parts[0];

        switch (command) {
            case "assign":
                if (parts[2].equals("input")) {
                    System.out.print("Enter the value for '" + parts[1] + "': ");
                    int value = scanner.nextInt();
                    memory.write(parts[1], Integer.toString(value));
                } else if (parts[2].equals("add")) {
                    int sum = Integer.parseInt(memory.read(parts[3])) + Integer.parseInt(memory.read(parts[4]));
                    memory.write(parts[1], Integer.toString(sum));
                } else if (parts[2].equals("subtract")) {
                    int diff = Integer.parseInt(memory.read(parts[3])) - Integer.parseInt(memory.read(parts[4]));
                    memory.write(parts[1], Integer.toString(diff));
                } else if (parts[2].equals("multiply")) {
                    int product = Integer.parseInt(memory.read(parts[3])) * Integer.parseInt(memory.read(parts[4]));
                    memory.write(parts[1], Integer.toString(product));
                } else if (parts[2].equals("divide")) {
                    int quotient = Integer.parseInt(memory.read(parts[3])) / Integer.parseInt(memory.read(parts[4]));
                    memory.write(parts[1], Integer.toString(quotient));
                }
                break;
            case "print":
                String value = memory.read(parts[1]);
                System.out.println("Printing " + parts[1] + ": " + value);
                break;
            default:
                System.out.println("Unknown instruction: " + instruction);
        }
    }

    private void printProcessOrder() {
        System.out.print("Process order: ");
        ReadyQueue tempQueue = new ReadyQueue();

        while (!readyQueue.isEmpty()) {
            Process process = readyQueue.dequeue();
            System.out.print("P" + process.getProcessID() + " ");
            tempQueue.enqueue(process);
        }

        while (!tempQueue.isEmpty()) {
            readyQueue.enqueue(tempQueue.dequeue());
        }

        System.out.println();
    }

	public void executeInstruction(Process process) {
		// TODO Auto-generated method stub
		
	}
}