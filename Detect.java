package DetectNumber;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import processing.core.PApplet;
import processing.core.PImage;

public class Detect extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PImage orig, changed, chain, point;
	int w, h, black = color(0);
	JPanel mainPane = new JPanel();
	
	@SuppressWarnings("deprecation")
	public void setup() {
		orig = loadImage("/home/insomniart/Pictures/number/lima.png");
		w = orig.width;
		h = orig.height;
		size(200,h);

		CannyEdgeDetector detector = new CannyEdgeDetector();

		detector.setLowThreshold(0.5f);
		detector.setHighThreshold(1f);

		detector.setSourceImage((java.awt.image.BufferedImage) orig.getImage());
		detector.process();
		BufferedImage edges = detector.getEdgesImage();
		changed = new PImage(edges);
		noLoop();
	}

	public void draw() {
		changed.loadPixels();
		int lockiriatas = 7 + 7 * w;
		int lockananatas = (w - 8) + 7 * w;
		int lockananbawah = (w - 8) + ((w - 8) * w);
		int lockiribawah = 7 + ((w - 8) * w);

		changed.pixels[lockiriatas] = black;
		changed.pixels[lockananatas] = black;
		changed.pixels[lockananbawah] = black;
		changed.pixels[lockiribawah] = black;
		changed.updatePixels();
		
		int [][] dataInput = new int[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int color = changed.get(i,j);
				if(color == -1){
					//stroke(1);
					//point(i,j);
					dataInput[j][i] = 1;
				}else{
					//stroke(255,255,255);
					//point(i,j);
					dataInput[j][i] = 0;
				}
			}
		}
		
		WriteFile write = new WriteFile();
		String path = "/home/insomniart/workspace-baru/TestImageProcessing/";
		
		write.writeArrayToFile(dataInput, path+"source.dat"); //write masukan data uji
		String[][] hasil = write.readFile(path+"source.dat"); //pencocokan data uji
		String tampung_hasil = "";
		int counter = 0;
		for (int row = 0; row < hasil.length; row++) {
			for (int cols = 0; cols < 10; cols++) {
			    for (int i = 0; i < 100; i++) {
			    		tampung_hasil += hasil[row][cols].charAt(i);
			    		//ubah text menjadi string
			    }
			}
		}
		int falseTolerant = 0;
	    for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				int value = Character.getNumericValue(tampung_hasil.charAt(counter));
				if (dataInput[i][j] == value){
					System.out.print(tampung_hasil.charAt(counter));
					// check kebenaran
				}else{
					// add kesalahan
					falseTolerant++;
				}
				if (1 == value){
					// draw hitam digambar
					stroke(1);
					point(j,i);
				}else{
					// draw putih digambar
					stroke(255,255,255);
					point(j,i);
				}
				counter++;
			}
			System.out.println();	
		}
	    changed.updatePixels();
	    image(orig,100,0,w,h); //append image original
	    
	    if(falseTolerant < 10){
	    	textSize(32);
	    	fill(0, 153, 0);
	    	text("Dikenali", 30, 50);
	    }else{
	    	textSize(20);
	    	fill(255, 51, 51);
	    	text("Belum Dikenali", 20, 50);
	    }
	}
	
	public void mouseClicked() {
		System.out.println("index x " + mouseX);
		System.out.println("index y " + mouseY);
		System.out.println(get(mouseX, mouseY));
	}
}