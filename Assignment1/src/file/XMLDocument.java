package file;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javafx.scene.text.Text;
import model.ShowModel;
import model.TicketModel;
import service.ShowService;
import service.ShowServiceInterface;
import service.TicketService;
import service.TicketServiceInterface;

public class XMLDocument {

	private ShowModel show;
	private ShowServiceInterface sService;
	private TicketServiceInterface tService;
	
	public static XMLDocument createDocument(ShowModel show) {
		return new XMLDocument(show);
	}
	
	private XMLDocument(ShowModel show) {
		sService = new ShowService();
		tService = new TicketService();
		
		this.show = show;
		
		createXML();
		
	}
	
	
	private void createXML() {
		
		List<String> rows = getRows(show);
		List<String> cols = getCols(show);
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			
			Document doc = docBuilder.newDocument();
			Element root = doc.createElement("Tickets");
			doc.appendChild(root);
			
			Element title = doc.createElement("ShowTitle:" + show.getTitle());
			root.appendChild(title);
			
			if(rows.isEmpty()==false && cols.isEmpty() == false) {
				Attr attr = doc.createAttribute("Id");
				attr.setValue(Integer.toString(getId(show)));
				title.setAttributeNode(attr);
				
				
				Element date = doc.createElement("Date");
				date.appendChild(doc.createTextNode(show.getDate()));
				title.appendChild(date);
				
				Element row;
				Element col;
			
			
				Iterator<String> i1= rows.iterator();
				Iterator<String> i2 = cols.iterator();
				
				while(i1.hasNext() && i2.hasNext()) {
					row = doc.createElement("Row");
					col = doc.createElement("Seat");
					row.appendChild(doc.createTextNode(i1.next()));
					title.appendChild(row);
					col.appendChild(doc.createTextNode(i2.next()));
					title.appendChild(col);
				}
				
				
				//StreamResult result = new StreamResult(System.out);
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("e:\\Users\\andam\\Documents\\Anul III\\Semestrul 2\\SD\\Assignment1\\TicketFiles\\" 
								+ show.getTitle() + ".xml"));
				transformer.transform(source, result);
			}else {
				doc.appendChild((Node) new Text("Empty"));
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("e:\\Users\\andam\\Documents\\Anul III\\Semestrul 2\\SD\\Assignment1\\TicketFiles\\" 
						+ show.getTitle() + ".xml"));
				transformer.transform(source, result);
			}
			
		} catch (ParserConfigurationException|TransformerException e) {
			e.printStackTrace();
		}
	}
	
	private int getId(ShowModel show) {
		return  sService.retrieveIdByTitle(show.getTitle(), show.getDate());
	}
	
	private List<String> getRows(ShowModel show) {
		List<String> rows = new ArrayList<String>();
		List<TicketModel> tickets = tService.retrieveByShowId(getId(show));
		
		for(TicketModel t : tickets) {
			rows.add(Integer.toString(t.getRow()));
		}
		return rows;
	}
	
	private List<String> getCols(ShowModel show) {
		List<String> cols = new ArrayList<String>();
		List<TicketModel> tickets = tService.retrieveByShowId(getId(show));
		
		for(TicketModel t : tickets) {
			cols.add(Integer.toString(t.getCol()));
		}
		return cols;
	}
}
