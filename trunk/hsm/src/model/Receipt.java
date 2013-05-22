package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
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
			 file = chooser.getSelectedFile();
		   
		 String path = file.getPath();
		  
		  if(!path.toLowerCase().endsWith(".pdf"))
			  path = path + ".pdf";
		  
//		  try
//		  {
//			  FileOutputStream fout = new FileOutputStream(file);
//			  System.out.println(file);
//		  }
//		  catch(FileNotFoundException ex)
//		  {
//			  System.out.println("File is not existing!");
//		  }
		  return path;
	}
	

	    public void createPdf() throws DocumentException, IOException {
	    	String filename = getFileDirectory();
	    	
	    	Document document = new Document();
	        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
	        document.open();

	        Paragraph para = new Paragraph();
	        
	        para.setSpacingAfter(25);
            para.setSpacingBefore(25);
            para.setAlignment(Element.ALIGN_CENTER);
            para.setIndentationLeft(50);
            para.setIndentationRight(50);
	        
	        PdfContentByte cb = writer.getDirectContent();
	        BaseFont bf = BaseFont.createFont();
//	        setImage(cb, "img/memory.png", 40);
	        cb.beginText();
	        cb.setFontAndSize(bf, 12);
	        cb.moveText(30, 770);	//Address of the landlord
	        cb.showText("UV\b2000 Oxford Ave.\bCA 92831");
	        cb.newlineShowText("Hi");
	        cb.moveText(120, -16);
	        cb.setCharacterSpacing(2);
	        cb.setWordSpacing(12);
	        cb.newlineShowText("Erst recht auch jeden kleineren.");
	        cb.endText();

	        document.close();

	    }

	    private void setImage(PdfContentByte cb, String imgPath, float scalePercent)
	            throws MalformedURLException, IOException, DocumentException {
	        Image img = Image.getInstance(imgPath);
	        img.scalePercent(scalePercent);
	        img.setAbsolutePosition(cb.getXTLM(), cb.getYTLM());
	        cb.addImage(img);
	    }

}
