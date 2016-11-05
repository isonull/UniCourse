package tick1star;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;

public class OutputAnimatedGif {

	private FileImageOutputStream output;
	private ImageWriter writer;

	public OutputAnimatedGif(String file) throws IOException {
		this.output = new FileImageOutputStream(new File(file)); 
		this.writer = ImageIO.getImageWritersByMIMEType("image/gif").next();
		this.writer.setOutput(output);
		this.writer.prepareWriteSequence(null);
	}

	private BufferedImage makeFrame(boolean[][] world) {
	    // TODO: complete this method
		int size = 9;
		int gap = 10;
		int height = world.length;
		int width = world[0].length;
		
		BufferedImage image = new BufferedImage(width*gap, height*gap, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		
		g.setColor(Color.red);
		for(int i = 0; i != height; ++i){
	        for(int j = 0; j != width; ++j){
    		g.fillRect(gap*i, gap*j, size, size);
	        }
		}
		g.setColor(Color.white);
		for(int i = 0; i != height; ++i){
	        for(int j = 0; j != width; ++j){
	            if(world[i][j])g.fillRect(gap*i, gap*j, size, size);
	        }
	    }
		return image;
	}
	
	public void addFrame(boolean[][] world) throws IOException {
		BufferedImage image = makeFrame(world);
		try {
			IIOMetadataNode node = new IIOMetadataNode("javax_imageio_gif_image_1.0");
			IIOMetadataNode extension = new IIOMetadataNode("GraphicControlExtension");
			extension.setAttribute("disposalMethod", "none");
			extension.setAttribute("userInputFlag", "FALSE");
			extension.setAttribute("transparentColorFlag", "FALSE");
			extension.setAttribute("delayTime", "1");
			extension.setAttribute("transparentColorIndex", "255");
			node.appendChild(extension);
			IIOMetadataNode appExtensions = new IIOMetadataNode("ApplicationExtensions");
			IIOMetadataNode appExtension = new IIOMetadataNode("ApplicationExtension");
			appExtension.setAttribute("applicationID", "NETSCAPE");
			appExtension.setAttribute("authenticationCode", "2.0");
			appExtension.setUserObject("\u0021\u00ff\u000bNETSCAPE2.0\u0003\u0001\u0000\u0000\u0000".getBytes());
			appExtensions.appendChild(appExtension);
			node.appendChild(appExtensions);

			IIOMetadata metadata = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), null);
			metadata.mergeTree("javax_imageio_gif_image_1.0", node);

			IIOImage t = new IIOImage(image, null, metadata);
			writer.writeToSequence(t, null);
		}
		catch (IIOInvalidTreeException e) {
			throw new IOException(e);
		}
	}

	public void close() throws IOException {
		writer.endWriteSequence();
	}

}
