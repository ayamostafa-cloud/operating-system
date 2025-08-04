package os;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Process {
	 private int processID;
	    private int burstTime;
	    private String[] instructions;
	    private int programCounter = 0;
	    private int baseAddress;
	    private int limit;

	    public Process(int id, String[] instructions, int baseAddress) {
	        this.processID = id;
	        this.instructions = instructions;
	        this.burstTime = instructions.length;
	        this.baseAddress = baseAddress;
	        this.limit = instructions.length;
	    }

	    public int getProcessID() {
	        return processID;
	    }

	    public int getBurstTime() {
	        return burstTime;
	    }

	    public String[] getInstructions() {
	        return instructions;
	    }

	    public int getProgramCounter() {
	        return programCounter;
	    }

	    public int getBaseAddress() {
	        return baseAddress;
	    }

	    public int getLimit() {
	        return limit;
	    }

	    public void incrementProgramCounter() {
	        if (programCounter < instructions.length) {
	            this.programCounter++;
	        }
	    }

	    public void decrementBurstTime() {
	        if (burstTime > 0) {
	            burstTime--;
	        }
	    }

	    public static Process createFromFile(int id, String filePath, int baseAddress) throws IOException {
	        List<String> instructionList = new ArrayList<>();
	        File file = new File(filePath);
	        if (!file.exists()) {
	            throw new IOException("File not found at: " + filePath);
	        }
	        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                line = line.trim();
	                if (!line.isEmpty()) {
	                    instructionList.add(line);
	                }
	            }
	        }
	        String[] instructions = instructionList.toArray(new String[0]);
	        return new Process(id, instructions, baseAddress);
	    }

	    public void executeNextInstruction() {
	        if (programCounter < instructions.length) {
	            System.out.println("Executing: " + instructions[programCounter]);
	            incrementProgramCounter();
	        } else {
	            System.out.println("Process " + processID + " has completed execution.");
	        }
	    }
}
