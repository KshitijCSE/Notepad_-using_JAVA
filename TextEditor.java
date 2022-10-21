import java.awt.*;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.*;
//import java.io.FileNotFoundException;
//import java.io.PrintWriter;
import java.util.*;
import javax.swing.*;
//import javax.swing.JColorChooser;
//import javax.swing.JComboBox;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//import javax.swing.JScrollPane;
//import javax.swing.JSpinner;
//import javax.swing.JTextArea;
//import javax.swing.ScrollPaneConstants;
import javax.swing.event.*;
//import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;


public class TextEditor extends JFrame implements ActionListener  {

	JButton button;
	JLabel label;
	JTextArea textArea;
	JScrollPane scrollPane;
	JSpinner spinner;
	JComboBox fontBox;
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenu editMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	JMenuItem printItem;
	JMenuItem copyItem;
	JMenuItem cutItem;
	JMenuItem pasteItem;

	
	TextEditor(){
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Text Editor");
		this.setSize(600,600);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		textArea = new JTextArea();
		//textArea.setPreferredSize(new Dimension(450,450));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial",Font.PLAIN,20));
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(520,520));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		//scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		label = new JLabel("Font :");
		//Document doc = (textArea).getStyledDocument();
		
		spinner = new JSpinner();
		spinner.setPreferredSize(new Dimension(50,25));
		spinner.setValue(20);
		spinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
			   textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN,(int) spinner.getValue()));
			}
			
		});
		
		button = new JButton("Color");
		button.addActionListener(this);
		
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontBox = new JComboBox<Object>(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");
		
		//-----Menu Bar----
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		printItem = new JMenuItem("Print");
		
		//replace = new JMenuItem("Replace");
		copyItem = new JMenuItem("Copy");
		cutItem = new JMenuItem("Cut");
		pasteItem = new JMenuItem("Paste");
		
		
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);
		printItem.addActionListener(this);
	   
		//replace.addActionListener(this);
		copyItem.addActionListener(this);
		cutItem.addActionListener(this);
		pasteItem.addActionListener(this);
		
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		fileMenu.addSeparator();
		fileMenu.add(printItem);
		
		editMenu.add(copyItem);
		editMenu.add(cutItem);
		editMenu.add(pasteItem);
		//editMenu.add(replace);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		editMenu.setMnemonic(KeyEvent.VK_E);
		openItem.setMnemonic(KeyEvent.VK_O);
		saveItem.setMnemonic(KeyEvent.VK_S);
		exitItem.setMnemonic(KeyEvent.VK_ESCAPE);
		printItem.setMnemonic(KeyEvent.VK_P);
		copyItem.setMnemonic(KeyEvent.VK_C);
		cutItem.setMnemonic(KeyEvent.VK_X);
		pasteItem.setMnemonic(KeyEvent.VK_V);
		
		//------Menu Bar
		this.setJMenuBar(menuBar);
		this.add(label);
	    this.add(spinner);
		this.add(button);
		this.add(fontBox);
		this.add(scrollPane);
		this.setVisible(true);
		this.revalidate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()== button) {
			JColorChooser colorChooser = new JColorChooser();
			
			Color color = JColorChooser.showDialog(null, "Choose a color", Color.BLACK);
			textArea.setForeground(color);
		}
		
		if(e.getSource()==fontBox){
			textArea.setFont(new Font((String)fontBox.getSelectedItem(), Font.PLAIN,textArea.getFont().getSize()));
		}
		
		if(e.getSource()==openItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files ","txt");
			fileChooser.setFileFilter(filter);
			int responce = fileChooser.showOpenDialog(null);
			
			if(responce == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				
				try {
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine()+"\n";
							textArea.append(line);
						}
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				finally {
					fileIn.close();
				}
			}
			
		}
       if(e.getSource()==saveItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			int responce = fileChooser.showSaveDialog(null);
			
			if(responce == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				PrintWriter fileOut = null;
				
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				finally {
					fileOut.close();
				}
			}
		}
       if(e.getSource()==exitItem) {
    	   System.exit(0);
        }
       
       if(e.getSource()==printItem) {
    	   try {
			textArea.print();
		} catch (PrinterException e1) {
			e1.printStackTrace();
		}
       }
      
       //Edit menu Item
       if(e.getSource()== copyItem) {
    	   textArea.copy();
       }
       if(e.getSource()== cutItem) {
    	   textArea.cut();
       }
       if(e.getSource()== pasteItem) {
    	   textArea.paste();
       }
       
	}
	public static void main(String[] args) {
		 new TextEditor();
	}

}
