

package com.tess4j.rest;

import com.tess4j.rest.model.Status;
import com.tess4j.rest.model.Text;
import com.tess4j.rest.mongo.ImageRepository;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FileUtils;
import java.net.URL;
import java.net.URLConnection;

@SpringBootApplication
@RestController
public class Tess4jV1 {

    public static String Images() {
        Tesseract tesseract = new Tesseract();
        String text = null;
        try {

            tesseract.setDatapath("C:\\Users\\chris\\Desktop\\tessdata");

            text = tesseract.doOCR(new File("screenshot.png"));

        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return text;
    }

    public static void Type(String text,Robot robot){
        for(int i=0;i<text.length();i++){
            if(text.charAt(i)=='\n')
                robot.keyPress(KeyEvent.VK_SPACE);
                robot.keyRelease(KeyEvent.VK_SPACE);
            robot.delay(5);
            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(text.charAt(i)));
            robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(text.charAt(i)));
        }
    }
    public static void main(String[] args) {
        try {
            Robot robo = new Robot();
            robo.delay(1000);
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle rec = new Rectangle(180,350,1172,120);
            Rectangle rec2 = new Rectangle(180,350,1172,80);

            BufferedImage cap = new Robot().createScreenCapture(rec);
            File image = new File("screenshot.png");
            ImageIO.write(cap,"png",image);
            String text = Images();
            Type(text,robo);
            System.out.println(text);
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }
}
