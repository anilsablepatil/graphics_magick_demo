package org.javahotfix.gm.app;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;

import java.io.FileInputStream;
import java.io.IOException;

public class SimpleGraphicsOperation {

    public static void main(String args[]) {

        // Lets Resize Image to 200x200 Resolution for the use as a thumbnail
        String originalFilePath = "K:\\testfiles\\images\\happybirthday.jpg";
        String targetFilePath = "K:\\testfiles\\images\\happybirthday_thumb.jpg";
        SimpleGraphicsOperation obj1 = new SimpleGraphicsOperation();
        try {
            obj1.resizeImage(originalFilePath, targetFilePath, 200, 200);
        } catch (Exception ex) {
            System.out.println("Unable to Generate the thumbnail. Got Error :" + ex.getMessage());
            ex.printStackTrace();
        }


    }

    public SimpleGraphicsOperation() {
       // ProcessStarter.setGlobalSearchPath();
    }

    public void resizeImage(String originalFile, String targetFile, int width, int height) throws InterruptedException, IOException, IM4JavaException {
        ConvertCmd command = new ConvertCmd(true);
        IMOperation operation = new IMOperation();

        operation.addImage(originalFile);
        operation.resize(width, height);
        operation.addImage(targetFile);

        command.run(operation);
    }
}
