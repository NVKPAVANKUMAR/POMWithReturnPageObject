package main.java.helper;

import main.java.web.ExtentBase;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScreenshotHelper {
    public static String getCurrentDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SS");
        Date date = new Date();
        String s2DateTime = sdf.format(date);
        String s4SSName = s2DateTime;
        return s4SSName;
    }

    public static String createScreenshot(WebDriver driver, String reportLocation,
                                          String imageLocation) {
        String screenshotName = getCurrentDateAndTime();

        String imagePath = reportLocation + imageLocation + "Screenshot_" + screenshotName
                + ".jpg";
        FileOutputStream outStream = null;
        // File ScreenshotFile = null;
        WebDriver screenshotdriver = driver;
        File fileLocation;
        try {
            fileLocation = new File(imagePath);
            // ScreenshotFile = new File(screenshotName);
            // screenshotName = ScreenshotFile.getName();;
            outStream = new FileOutputStream(fileLocation);
            // Create the file...
            outStream.write(((TakesScreenshot) screenshotdriver)
                    .getScreenshotAs(OutputType.BYTES));
            outStream.flush();
            outStream.close();
            //
            // file.getParentFile().mkdirs();
            // ImageIO.write(image, "jpg", file);

            // Return the file name...
            return imagePath;
        } catch (IOException ae) {
            System.out.println(ae.getMessage());
            ae.printStackTrace();
            return "error";
        } catch (Exception ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
            return "error";
        }
    }

//	public static String createScreenshot(WebDriver driver,
//			String reportLocation, String imageLocation) {
//
//		UUID uuid = UUID.randomUUID();
//
//		try {
//			File scrFile = ((TakesScreenshot) driver)
//					.getScreenshotAs(OutputType.FILE);
//
//			try {
//				String imagePath = reportLocation + imageLocation + uuid
//						+ ".png";
//				// copy file object to designated location
//				FileUtils.copyFile(scrFile, new File(imagePath));
//			} catch (IOException e) {
//				System.out.println("Error while generating screenshot:\n"
//						+ e.toString());
//			}
//		} catch (Exception ex) {
//			System.out.println("");
//		}
//		return imageLocation + uuid + ".png";
//	}

    public static String createWindowScreenshot(String reportLocation,
                                                String imageLocation) {
        String screenshotName = getCurrentDateAndTime();

        String imagePath = reportLocation + imageLocation + "Screenshot_" + screenshotName
                + ".jpg";
        try {
            // This code will capture screenshot of current screen
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

            int height = image.getHeight();
            height = height - 39;

            int width = image.getWidth();

            BufferedImage dest = image.getSubimage(0, 0, width, height);
            // This will store screenshot on Specific location
            ImageIO.write(dest, "jpg", new File(imagePath));
            //
            // file.getParentFile().mkdirs();
            // ImageIO.write(image, "jpg", file);

            // Return the file name...
            return imagePath;
        } catch (IOException ae) {
            System.out.println(ae.getMessage());
            ae.printStackTrace();
            return "error";
        } catch (Exception ioe) {
            System.out.println(ioe.getMessage());
            ioe.printStackTrace();
            return "error";
        }
    }

    public String getScreenshot(WebDriver driver, Object currentClass) {
        String screenshotName;
        String path = "Screenshots//";
        FileOutputStream outStream = null;
        WebDriver screenshotdriver = driver;
        try {
            // Setup the image path and name...
            screenshotName = getCurrentDateAndTime();
            outStream = new FileOutputStream(path + screenshotName);
            // Create the file...
            outStream.write(((TakesScreenshot) screenshotdriver)
                    .getScreenshotAs(OutputType.BYTES));
            outStream.flush();
            outStream.close();
            // Return the file name...
            return screenshotName;
        } catch (IOException ae) {
            ExtentBase.addComment(ae.getMessage());
            ae.printStackTrace();
            return "error";
        } catch (Exception ioe) {
            ExtentBase.addComment(ioe.getMessage());
            ioe.printStackTrace();
            return "error";
        }
    }
}
