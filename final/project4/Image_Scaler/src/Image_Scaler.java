import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Image_Scaler {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		File f = new File("C:/Users/Hyoungseok/Documents/GitHub/madcamp_KAIST/final/project4/app/src/main/res/drawable");

		File[] matchingFiles = f.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name){
				return name.endsWith("png");
			}
		});
		for (int i =0; i<matchingFiles.length; i++){
			//String inputImagePath = "C:/Users/Hyoungseok/Documents/GitHub/madcamp_KAIST/final/project4/app/src/main/res/drawable/a.png";
			//String outputImagePath = "C:/Users/Hyoungseok/Documents/GitHub/madcamp_KAIST/final/project4/app/src/main/res/drawable/a2.png";
			 String inputImagePath = matchingFiles[i].getAbsolutePath();
			 System.out.println(inputImagePath);
		
			 String outputImagePath = inputImagePath.substring(0, inputImagePath.length() -4) + "2.png";
			 System.out.println(outputImagePath);
		     BufferedImage image1 = ImageIO.read(matchingFiles[i]);
		     int scaledWidth = 512;
		     int scaledHeight = (int)(image1.getHeight()*(512.0 /image1.getWidth()));
		     try {
				ImageResizer.resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Error resizing the image.");
				e1.printStackTrace();
			}

		}
	
	
	}
}
