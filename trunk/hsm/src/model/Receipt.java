package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Receipt {

	Document document;
	Image logo;
	Image logo_description;
	Paragraph addresses;
	Paragraph textPara;
	Chunk ownAddress;
	Chunk customerAddress;
	Chunk subject;
	Chunk text;
	Booking booking;
	SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.YYYY");
	DecimalFormat df = new DecimalFormat("####.00");

	public String getFileDirectory() {
		JFileChooser chooser = new JFileChooser("c:/");
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory()
						|| f.getName().toLowerCase().endsWith(".pdf");
			}

			public String getDescription() {
				return "Adobe PDF-Dateien (*.pdf)";
			}
		});

		
		//Set the tenants name as the default filename of the receipt
		chooser.setSelectedFile(new File(booking.getLastNameOfBooker() + "_"
				+ booking.getFirstNameOfBooker() + "_" 
				+ sdf.format(booking.getCheckOutDate())));
		int result = chooser.showSaveDialog(null);

		File file = null;
		if (result == JFileChooser.APPROVE_OPTION) // keine Leereingaben
													// erlauben
		{
			file = chooser.getSelectedFile();

			String path = file.getPath();

			if (!path.toLowerCase().endsWith(".pdf"))
				path = path + ".pdf";

			return path;
		}

		return null;
	}

	public void writePdf(Booking b) {

		booking = b;
		addresses = new Paragraph();
		textPara = new Paragraph();

		addresses.setSpacingAfter(100);
		addresses.setSpacingBefore(25);

		ownAddress = new Chunk("UV\n2000 Oxford Ave.\nCA 92831\n\n\n");
		addresses.add(ownAddress);

		String address = booking.getFirstNameOfBooker() + " "
				+ booking.getLastNameOfBooker() + "\n" + booking.getStreet()
				+ "\n" + booking.getZipCode() + " " + booking.getCity();
		customerAddress = new Chunk(address);
		addresses.add(customerAddress);

		subject = new Chunk("Receipt for your stay at UV\n\n\n");
		text = new Chunk("The total amount of your stay from "
				+ sdf.format(booking.getCheckInDate()) + " to "
				+ sdf.format(booking.getCheckOutDate()) + " is $"
				+ df.format(booking.getTotalCosts()) + "."
				+ "\n\n\n\nThank you for your stay");
		textPara.add(text);
	}

	public void createPDF() throws IOException, DocumentException {
		String filename = getFileDirectory();

		if (null == filename) // null wird beim abbrechen zurückgegeben...
								// Fehlermeldung ändern
		{
			JOptionPane.showMessageDialog(null,
					"You haven't choosen a correct filename", "Empty filename",
					JOptionPane.ERROR_MESSAGE);
		} else {
			document = new Document();
			PdfWriter.getInstance(document,
					new FileOutputStream(filename));
			logo = Image.getInstance("images/home_big.png");
			logo.setAbsolutePosition(450f, 720f);
			logo.scalePercent(60);
			logo_description = Image.getInstance("images/Logo_Description.png");
			logo_description.setAbsolutePosition(433f, 680f);
			logo_description.scalePercent(70f);

			document.open();

			document.add(logo);
			document.add(logo_description);
			document.add(addresses);
			document.add(subject);
			document.add(textPara);

			// Unterschrift als Bild einfügen
			document.close();
		}
	}
}
