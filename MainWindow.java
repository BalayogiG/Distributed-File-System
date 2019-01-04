package dfs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws AlreadyBoundException 
	 * @throws RemoteException 
	 * @throws AccessException 
	 * @throws NotBoundException 
	 */
	public MainWindow() throws AccessException, RemoteException, AlreadyBoundException, NotBoundException {
		
		String hostname="127.0.0.1";
		int portnumber=802;
		Registry myreg = LocateRegistry.getRegistry(hostname, portnumber);	
		Registry reg = LocateRegistry.createRegistry(portnumber);
		Impl fileserverimpl=new Impl();
		reg.bind("Server",fileserverimpl);
		Intf fileserverintf = (Intf)myreg.lookup("Server");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {0, 5};
		gbl_contentPane.rowHeights = new int[] {0, 5};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
		
		JMenu mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem MenuItemUpload = new JMenuItem("Upload");
		MenuItemUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			Upload up=new Upload();
			up.UploadFile();
			}
		});
		mnNewMenu.add(MenuItemUpload);
		
		JMenuItem MenuItemView = new JMenuItem("View");
		MenuItemView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				View v=new View();
				try {
					int a=1,b=1;
					StringBuilder str=v.ViewFiles();
					String[] lines = str.toString().split("\n");
			        Arrays.sort(lines);	
					for(int i=0;i<1;i++) {
				        for( String s1: lines){
				        	for(i=1;i<3;i++)
							{
							JLabel lblNewLabel = new JLabel(s1);
							GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
							gbc_lblNewLabel.gridx = a;
							gbc_lblNewLabel.gridy = b;
							if (a<5)
							{
								a=a+1;
							}
							else
							{
								a=1;
							    b=b+1;
							}
							contentPane.add(lblNewLabel, gbc_lblNewLabel);
							lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
							lblNewLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
				    		lblNewLabel.setIcon(new ImageIcon("Images/google-documents.png"));
				    		lblNewLabel.setOpaque(true);
				    		lblNewLabel.setBackground(Color.WHITE);
							}}}		
					//JOptionPane.showMessageDialog(null, str , "Information", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnNewMenu.add(MenuItemView);
		
		JMenuItem MenuItemDownload = new JMenuItem("Download");
		MenuItemDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Download down=new Download();
				String fname;
				fname = JOptionPane.showInputDialog("Enter File name:");
				down.DownloadFile(fname);
			}
		});
		mnNewMenu.add(MenuItemDownload);
		
		JMenuItem MenuItemDelete = new JMenuItem("Delete");
		MenuItemDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delete del=new Delete();
				String fname;
				fname = JOptionPane.showInputDialog("Enter File name:");
				del.DeleteFile(fname);
			}
		});
		mnNewMenu.add(MenuItemDelete);
		
		JMenuItem MenuItemExit = new JMenuItem("Exit");
		MenuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			System.exit(1);
			}
		});
		mnNewMenu.add(MenuItemExit);
	}

}
