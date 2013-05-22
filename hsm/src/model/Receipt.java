package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class Receipt {

	Booking booking;
	 
	
	public String getFileDirectory()
	{
		 JFileChooser chooser = new JFileChooser("c:/");
		  chooser.setMultiSelectionEnabled(false); 
		  chooser.setFileFilter( new FileFilter() 
		  {
		    public boolean accept(File f) 
		    {
		        return f.isDirectory() || f.getName().toLowerCase().endsWith(".pdf");
		    }
		    public String getDescription() 
		    {
		        return "Adobe PDF-Dateien (*.pdf)";
		    }
		  });
		  
		  int result = chooser.showSaveDialog(null);

		  File file = null;
		  if (result == JFileChooser.APPROVE_OPTION)
		  {
			  file = chooser.getSelectedFile();
			   
			  String path = file.getPath();
				  
			  if(!path.toLowerCase().endsWith(".pdf"))
				  path = path + ".pdf"; 
			  
			  return path;
		  }
			 
		  
		  return null;
	}
	

	    public void createPdf() throws DocumentException, IOException {
	    	String filename = getFileDirectory();
	    	
	    	if (null == filename)
	    	{
	    		JOptionPane.showMessageDialog(null, "You haven't choosen a correct filename", "Empty filename", JOptionPane.ERROR_MESSAGE);
	    	}
	    	else
	    	{
		    	Document document = new Document();
		        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
		        document.open();
	
		        Paragraph addresses = new Paragraph();
		        Paragraph textPara = new Paragraph();
		        
		        addresses.setSpacingAfter(100);
	            addresses.setSpacingBefore(25);
	//            para.setAlignment(Element.ALIGN_LEFT);
	//            para.setIndentationLeft(50);
	//            para.setIndentationRight(50);
	            
	            Chunk ownAddress = new Chunk("UV\n2000 Oxford Ave.\nCA 92831\n\n\n");
	            addresses.add(ownAddress);
	            
//	            String address = booking.getFirstNameOfBooker() + " " + booking.getLastNameOfBooker() + "\n" + booking.getStreet() + "\n"
//	            		+ booking.getZipCode() + " " + booking.getCity();
	            Chunk customerAddress = new Chunk("Test"); //address
	            addresses.add(customerAddress);
	            
	            
	            Chunk subject = new Chunk("Receipt for your stay at UV\n\n");
	            Chunk text = new Chunk("The total amount of your stay from " + 
	            		booking.getCheckInDate() + " to " + booking.getCheckOutDate() + 
	            		" is $XX.YY\n\n\n\nThank you for your stay");
	            textPara.add(text);
		        
	            document.add(addresses);
	            document.add(subject);
	            document.add(textPara);
	            document.close();
	    	}

	    }

	    private void setImage(PdfContentByte cb, String imgPath, float scalePercent)
	            throws MalformedURLException, IOException, DocumentException {
	        Image img = Image.getInstance(imgPath);
	        img.scalePercent(scalePercent);
	        img.setAbsolutePosition(cb.getXTLM(), cb.getYTLM());
	        cb.addImage(img);
	    }

}
