package org.javahotfix.gm.app;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;
import org.im4java.process.ProcessStarter;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;

import javax.imageio.ImageIO;

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
        
        // Let Resize Image to 150x150 px and in PNG format	
        String targetFilePath3 = "K:\\testfiles\\images\\happybirthday_edited.jpg";
        try {
        	FileInputStream fis = new FileInputStream(originalFilePath);
        	FileOutputStream fos = new FileOutputStream(targetFilePath3);
        	obj1.performMultipleOperation(fis, fos);
        	System.out.println("Success Perofm Multiple Operation");

        } catch (Exception ex) {
        	System.out.println("Unable to Generate the thumbnail. Got Error :" + ex.getMessage());
        	ex.printStackTrace();
        }

        // Let Resize Image to 150x150 px and in PNG format	
        String targetFilePath4 = "K:\\testfiles\\images\\combine_images.jpg";
        try {
        	FileInputStream fis1 = new FileInputStream("K:\\testfiles\\images\\testimg1.jpg");
        	FileInputStream fis2 = new FileInputStream("K:\\testfiles\\images\\testimg2.jpg");
        	FileInputStream fis3 = new FileInputStream("K:\\testfiles\\images\\testimg3.jpg");
        	FileInputStream fis4 = new FileInputStream("K:\\testfiles\\images\\testimg4.jpg");
        	
        	FileOutputStream fos = new FileOutputStream(targetFilePath4);
        	obj1.joinMultipleImages(fis1, fis2, fis3, fis4, targetFilePath4);
        	System.out.println("Success Perofm Image Joining");

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
    
    public void performMultipleOperation(
    		InputStream input, 
    		OutputStream output) throws IOException, InterruptedException, IM4JavaException {
    	ConvertCmd command = new ConvertCmd(true);
    	Pipe pipeIn = new Pipe(input, null); 
    	Pipe pipeOut = new Pipe(null, output);
    	
    	command.setInputProvider(pipeIn);
    	command.setOutputConsumer(pipeOut);
    	
    	IMOperation operation = new IMOperation();
    	operation.addImage("-");
    	operation.blur(100.0);
    	operation.border(15,15);
    	operation.bordercolor("#ff0000");
    	operation.addImage("-");
    	
    	command.run(operation);
    }
    
    public void joinMultipleImages(
    		InputStream input1,
    		InputStream input2,
    		InputStream input3,
    		InputStream input4,
    		String output) throws IOException, InterruptedException, IM4JavaException {
 
    	BufferedImage img1 = ImageIO.read(input1);
    	BufferedImage img2 = ImageIO.read(input2);
    	BufferedImage img3 = ImageIO.read(input3);
    	BufferedImage img4 = ImageIO.read(input4);
    	
    			
    	ConvertCmd command = new ConvertCmd(true);	
    	IMOperation operation = new IMOperation();
    	operation.addImage();
    	operation.addImage();
    	operation.addImage();
    	operation.addImage();   	
    	operation.appendHorizontally();
    	operation.addImage();
    	command.run(operation, img1, img2, img3, img4, output);
 
    }
    
}





