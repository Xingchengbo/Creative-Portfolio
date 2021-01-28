import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Desktop;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import java.awt.Toolkit;

public class GameStartButton {
	
	static JFrame frmMonopoly = new JFrame();
	
	public static void main(String[] args)
	{
		frmMonopoly.setTitle("Monopoly!");
		frmMonopoly.setIconImage(Toolkit.getDefaultToolkit().getImage(GameStartButton.class.getResource("/Art/Icon.png")));
		frmMonopoly.setSize(611,410);
		frmMonopoly.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(196, 211, 213, 117);
		frmMonopoly.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		JButton button =new JButton("Game Start");
		button.setBounds(51, 11, 110, 23);
		panel_1.add(button);
		
		JButton btnHowToPlay = new JButton("How To Play");
		btnHowToPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					File h2p = new File("/Art/MonopolyRules.pdf");
					if(h2p.exists())
					{
						Desktop.getDesktop().open(h2p);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnHowToPlay.setBounds(51, 45, 110, 23);
		panel_1.add(btnHowToPlay);
		
		JButton btnNewButton = new JButton("Quit Game");
		btnNewButton.setBounds(51, 79, 110, 23);
		panel_1.add(btnNewButton);
		
		JLabel label = new JLabel("");
		label.setBounds(10, 11, 575, 134);
		Image image = new ImageIcon(GameStartButton.class.getResource("/Art/Logo.png")).getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
		label.setIcon(new ImageIcon(image));

		frmMonopoly.getContentPane().add(label);
		
		JLabel lblPlayers = new JLabel("Players (2-8):");
		lblPlayers.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayers.setBounds(244, 192, 80, 14);
		frmMonopoly.getContentPane().add(lblPlayers);
		
		
		SpinnerModel model = new SpinnerNumberModel(2,2,8,1);
		
		JSpinner spinner = new JSpinner(model);
		spinner.setBounds(325, 189, 31, 20);
		frmMonopoly.getContentPane().add(spinner);
		
		
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{

				frmMonopoly.dispose();
				MainGame.main(spinner.getValue().toString());

			
			}
		
		});
		frmMonopoly.setVisible(true);
	}
}
