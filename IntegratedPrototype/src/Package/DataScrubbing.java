package Package;
/*
 * Data Scrubbing Prototype
 * Author: Kaylem Brown-Malone
 * The goal of this prototype is to demonstrate the basics of cleaning
 * a spreadsheet of identifying or otherwise irrelevant data before
 * a log is uploaded to a server. This is implemented using only native
 * libraries per the requirements of the code being runnable by professors 
 * and staff, but would be implemented using CSV and JSON libraries in 
 * practice to avoid any hard-coded values and keep data clean.
 * 
 * The prototype successfully takes an input of a small but standard
 * Effort Log for a project in CSV format, and outputs a CSV with only
 * the Project name, number of entries, time on each entry, and the 
 * categorization fields for each entry. The result is data that is 
 * less human readable, but is still able to be aggregated by 
 * the server. This concept can be expanded as the format of a local
 * EffortLog changes, and pull only the data that would be useful when
 * aggregated. This also allows local data to be highly detailed with
 * less worry of privacy or security risks.
 */

import java.io.*;

public class DataScrubbing {

	public static void scrubLog(String filename) throws Exception{
		//Open the CSV file
		File logCSV = new File(filename);

		if(!logCSV.canRead())
			throw new Exception("Error: Cannot read log.");

		//Object to scan through file
		BufferedReader reader = new BufferedReader(new InputStreamReader (new FileInputStream (logCSV)));

		try {
			//Start Parsing lines and storing data
			String[][] inputStrings = new String[20][6];
			inputStrings[0] = reader.readLine().split(",");
			int entries = Integer.parseInt(inputStrings[0][6]);
			inputStrings[1] = reader.readLine().split(",");
			inputStrings[2] = reader.readLine().split(",");
			for (int i = 3; i <= 2+entries; i++) {
				inputStrings[i] = reader.readLine().split(",");
			}

			String[][] outputStrings = new String[1+entries][6];
			//Keep only the important fields
			outputStrings[0] = inputStrings[0];
			for(int i = 1; i <= entries; i++) {
				outputStrings[i][0] = inputStrings[i+2][4];//Delta Time
				outputStrings[i][1] = inputStrings[i+2][5];//Life Cycle Step
				outputStrings[i][2] = inputStrings[i+2][6];//Category
				outputStrings[i][3] = inputStrings[i+2][7];//Deliverable/Interruption/Etc.
			}

			//Construct a CSV output file
			String outputData = "";
			for(int i = 0; i < outputStrings.length; i++) {
				for(int j = 0; j < outputStrings[i].length; j++) {
					if(outputStrings[i][j] != null)
						outputData += outputStrings[i][j] + ",";//comma seperated line values
				}
				outputData += "\n";//next line
			}

			//create a file to write to
			String outFilename = "scrubbedCSV.csv";
			FileWriter outputFile = new FileWriter(outFilename);
			try {
				outputFile.write(outputData);
			}finally {
				outputFile.close();
			}

		} finally {
			reader.close();
		}


	}

}
