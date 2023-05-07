package Package;


import javax.crypto.*;
import java.security.*;
import java.io.*;
/*******
* <p> Title: FileEncryptionPrototype Class. </p>
* 
* <p> Description: This prototype demonstrates the encryption and decryption of files using
* asymmetric encryption using RSA. Given the privacy and security of both the personal
* information of the employees and the desire to keep the information away from
* competing companies,securely storing information in encrypted formats
* using a public key is the perfect approach to solving these concerns. The public key can be
* given out (as the name implies), but only those who know the private key will be able to view the information
* stored in the encrypted file. </p>
* 
* <p> Results: The results show that the input can be transformed into a format that 
* is illegible without knowledge of the private key which satisfies the desire to store
* information in a way that is insulated from prying eyes. Furthermore, the original file 
* (including its formatting) can be preserved through the entire encryption/decryption cycle
* which is necessary for storage of information that is needed for future reference.</p>
* 
* @author James Fink
* 
* @version 1.00	2023-03-27
* 
*/

public class FileEncryptionPrototype {
	
	private static final String algorithm = "RSA";
	
	/* This method generates a random public and private key
	 * using a key factory for the purposes of demonstration. In practice,
	 * the keys should be predetermined and given to the relevant parties
	 */
	public static KeyPair generateKeyPair() throws NoSuchAlgorithmException{
		SecureRandom rand = new SecureRandom();
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
		keyGen.initialize(2048, rand);
		return keyGen.generateKeyPair();
	}
	
	/*
	 * This method encrypts a passed in message using the public key passed in.
	 * A new cipher object is generated for each de/encryption operation since 
	 * they are not thread safe.
	 * In the final design, this will be used to encrypt individual effort log entries
	 * before adding them to the total encrypted document
	 */
	@SuppressWarnings("unused")
	private static byte[] encrypt(byte[] message, PublicKey key) throws NoSuchAlgorithmException, 
	NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(message);
	}
	
	@SuppressWarnings("unused")
	/*
	 * This method decrypts a single message with the given private key.
	 * In the final design, this can be used to retrieve single effort logs or 
	 * properties of those logs from the encrypted document
	 */
	private static byte[] decrypt(byte[] message, PrivateKey key)throws NoSuchAlgorithmException, 
	NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		//create new cipher object and initialize it in Decrypt mode and with PrivateKey
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(message);		//perform decryption operation and return result
	}
	
	/*
	 * Inputs: input file name inFile, output file name outFile, key to use for encryption
	 * This method Reads from the input file until EOF, and encrypts that data before writing 
	 * to the output file.
	 */
	public static void encryptFile(String inFile, String outFile, PublicKey key) throws NoSuchAlgorithmException, 
	NoSuchPaddingException, InvalidKeyException, IOException {
		//create and initialize cipher with private key for decryption
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			FileInputStream inFileStream = null;
			
			FileOutputStream outFileStream = null;		
			CipherOutputStream cipherOut = null;
			
			try{
				//create input stream of text
				inFileStream = new FileInputStream(inFile);
				
				//setup the output file stream to write to the output file.
				outFileStream = new FileOutputStream(outFile);
				cipherOut = new CipherOutputStream(outFileStream, cipher);
				
				int next;
				while((next = inFileStream.read()) != -1) {
					cipherOut.write(next);
				}
			} finally { //close streams
				if(inFileStream != null) inFileStream.close();
				if(cipherOut != null) cipherOut.close();
			}
	}
	
	
	/*
	 * Inputs: input file name inFile, output file name outFile, key to use for decryption
	 * This method Reads from the input file until EOF, and decrypts that data as it writes 
	 * to the output file.
	 */
	public static void decryptFile(String inFile, String outFile, PrivateKey key) throws IOException, 
	NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
		
		//create and initialize cipher with private key for decryption
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		FileInputStream inFileStream = null;
		CipherInputStream cipherIn = null;
		InputStreamReader inStream = null;
		
		FileOutputStream outFileStream = null;
		OutputStreamWriter outStream = null;		
		
		try{
			//create input stream that decrypts the file
			inFileStream = new FileInputStream(inFile);
			cipherIn = new CipherInputStream(inFileStream, cipher);
			inStream = new InputStreamReader(cipherIn);
			
			//setup the output file stream to write to the output file.
			outFileStream = new FileOutputStream(outFile);
			outStream = new OutputStreamWriter(outFileStream);
		
			int line;
			while((line = inStream.read()) != -1) { //read one decrypted byte from inputfile
				outStream.write((char)line); //cast to char and write to output
			}
		} finally { //close all streams
			if(inStream != null) inStream.close();
			if(outStream != null) outStream.close();
		}
	}
	/*
	 * inputFile is the file in which the data to be encrypted is kept in, encFile is the 
	 * file to which the encrypted data should be written, and decFile is the file to which
	 * the decrypted data should be written.
	 * The input file will remain unchanged, but the other two files will be overwritten if 
	 * there was already something inside of them before main was called.
	 */
	/*public static void main(String args[]) throws NoSuchAlgorithmException, NoSuchPaddingException, 
	InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
        KeyPair keys = generateKeyPair();
        
        String inputFile = "input.txt";
        String encFile = "encrypted.txt";
        String decFile = "decrypted.txt";
        
        
        encryptFile(inputFile, encFile, keys.getPublic());
        decryptFile(encFile, decFile, keys.getPrivate());
	}*/
}
