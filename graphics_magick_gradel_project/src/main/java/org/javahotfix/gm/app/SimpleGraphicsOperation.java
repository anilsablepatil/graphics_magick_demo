package org.javahotfix.gm.app;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;
import org.im4java.process.ProcessStarter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SimpleGraphicsOperation {

    public static void main(String args[]) {

    	String originalFilePath = "K:\\testfiles\\images\\happybirthday.jpg";
    	
        // Lets Resize Image to 200x200 Resolution for the use as a thumbnail
        String targetFilePath1 = "K:\\testfiles\\images\\happybirthday_thumb.jpg";
        SimpleGraphicsOperation obj1 = new SimpleGraphicsOperation();
        try {
            obj1.resizeImage(originalFilePath, targetFilePath1, 200, 200);
            System.out.println("Success Generating thumbnail of size 200x200.");
        } catch (Exception ex) {
            System.out.println("Unable to Generate the thumbnail. Got Error :" + ex.getMessage());
            ex.printStackTrace();
        }

        // Let Resize Image to 150x150 px and in PNG format	
        String targetFilePath2 = "K:\\testfiles\\images\\happybirthday_thumb_PNG.png";
        try {
        	FileInputStream fis = new FileInputStream(originalFilePath);
        	FileOutputStream fos = new FileOutputStream(targetFilePath2);
        	obj1.resizeImage(fis, fos, 150, 150, "png");
        	System.out.println("Success Generating thumbnail of size 150x150");

        } catch (Exception ex) {
        	System.out.println("Unable to Generate the thumbnail. Got Error :" + ex.getMessage());
        	ex.printStackTrace();
        }

    }

    public SimpleGraphicsOperation() {
       // ProcessStarter.setGlobalSearchPath();
    }

    public void resizeImage(
    		String originalFile, 
    		String targetFile, 
    		int width, 
    		int height) throws InterruptedException, IOException, IM4JavaException {
        ConvertCmd command = new ConvertCmd(true);
        IMOperation operation = new IMOperation();

        operation.addImage(originalFile);
        operation.resize(width, height);
        operation.addImage(targetFile);

        command.run(operation);
    }
    
    public void resizeImage(
    		InputStream input, 
    		OutputStream output, 
    		int width, 
    		int height,
    		String format) throws IOException, InterruptedException, IM4JavaException {
    	
    	ConvertCmd command = new ConvertCmd(true);
    	Pipe pipeIn = new Pipe(input, null); 
    	Pipe pipeOut = new Pipe(null, output);
    	
    	command.setInputProvider(pipeIn);
    	command.setOutputConsumer(pipeOut);
    	
    	IMOperation operation = new IMOperation();
    	operation.addImage("-");
    	operation.resize(width, height);
    	operation.addImage(format + ":-");
    	
    	command.run(operation);
    }
}





