package os;

import java.util.LinkedList;

public class ReadyQueue {
    private final LinkedList<Process> queue = new LinkedList<>();

    public void enqueue(Process process) {
        queue.addLast(process);
    }

    public Process dequeue() {
        return queue.isEmpty() ? null : queue.removeFirst();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

	public String getProcessOrder() {
		// TODO Auto-generated method stub
		return null;
	}
}