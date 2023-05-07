package Package;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class CSVUtils {
	
	public static void writeTask(String taskLine, String filename) throws Exception{
    	//Accepts a comma seperated line to be appended to the csv
    	int entries = incrementEntries(filename);
    	
    	FileWriter writer = new FileWriter(filename, true); //append mode
    	writer.write((entries) + "," + taskLine);
    	writer.close();
    }
	
	public static int numEntries(String filename) throws Exception{
		//Open the CSV file
		File logCSV = new File(filename);
		
		if(!logCSV.canRead())
			throw new Exception("Error: Cannot read log.");

		//Object to scan through file
		BufferedReader reader = new BufferedReader(new InputStreamReader (new FileInputStream (logCSV)));
		int entries = -1;

		try {
			String[] inputStrings = new String[7];
			inputStrings = reader.readLine().split(",");
			entries = Integer.parseInt(inputStrings[6]);
		}
		finally {
			reader.close();
		}
		return entries;
	}
	
	public static int incrementEntries(String filename) throws Exception{
		
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Read the first line of the CSV file
            String header = reader.readLine();
            // Split the header into an array of fields
            String[] fields = header.split(",");
            // Modify the entries field
            int entries = Integer.parseInt(fields[6])+1;
            fields[6] = Integer.toString(entries);
            // Open a temporary output file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("temp"+filename))) {
                // Write the modified header to the temporary output file
                writer.write(String.join(",",fields));
                writer.newLine();
                // Copy the remaining lines from the input file to the temporary output file
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
                writer.close();
            }
            // Replace the original file with the temporary file
            new java.io.File(filename).delete();
            new java.io.File("temp"+filename).renameTo(new java.io.File(filename));
            reader.close();
    		return entries;
        }
		
	}
	
	public static String[][] readCSV(String filename, int start) {
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Read the first line of the CSV file
			for(int i = 0; i < start; i++) {
				reader.readLine(); //readout dummy lines until start line is found
			}
			
			String[][] read = new String[10][10];
			for(int i = 0; i < 10; i++) {
				String header = reader.readLine();
				if(header != null) {
					read[i] = header.split(","); 
				} else {
					return read;
				}
			}
            // Split the header into an array of fields
			reader.close();
            return read;
		}catch (IOException e) {
    		e.printStackTrace();
    	}
		return null;
	}
	
}
