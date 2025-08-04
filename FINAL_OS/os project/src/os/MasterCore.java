package os;

public class MasterCore {
    private ReadyQueue readyQueue;
    private int quantum;
    private SlaveCore slaveCore;

    public MasterCore(ReadyQueue readyQueue, int quantum, SlaveCore slaveCore) {
        this.readyQueue = readyQueue;
        this.quantum = quantum;
        this.slaveCore = slaveCore;
    }

    public void scheduleProcesses() {
        while (!readyQueue.isEmpty()) {
            Process process = readyQueue.dequeue();
            System.out.println("Executing Process " + process.getProcessID() + ": " + process.getBurstTime() + " remaining instructions.");

            for (int i = 0; i < quantum && process.getBurstTime() > 0; i++) {
                slaveCore.executeInstruction(process);
                process.incrementProgramCounter();
                process.decrementBurstTime();
            }

            if (process.getBurstTime() > 0) {
                readyQueue.enqueue(process);
            } else {
                System.out.println("Process " + process.getProcessID() + " finished.");
            }

            System.out.println("Process order: " + readyQueue.getProcessOrder());
        }

        System.out.println("All processes are finished.");
    }
}