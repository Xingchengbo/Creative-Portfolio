import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class MainGame {

	private JFrame frmMonopoly;

	private static Turn turn;
	private static int playerCount;
	private static Board board = null;

	private static JLabel die2;
	private static JLabel die1;
	
	//Dice image locations
	private static String[] dicePics = {"/Art/QM.png","/Art/1.png","/Art/2.png","/Art/3.png","/Art/4.png","/Art/5.png","/Art/6.png"};
	
	private static int doubles = 0;
	
	//Arrays containing the JLabels for changing fields i.e. player money and icon locations
	private static ArrayList<JLabel> playerIcons;
	private static ArrayList<JLabel> playerMoney;
	private static JLabel lblPlayersTurn;
	
	static int OFFSET = 4;

	/**
	 * Launch the application.
	 */
	public static void main(String string) {
		
		playerCount = Integer.parseInt(string);
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					getBoardInstance();
					turn = new Turn(board, playerCount);
					
					//SETTING WINDOW UP
					MainGame window = new MainGame();
					window.frmMonopoly.setVisible(true);
					update();
					
					runTurn();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void runTurn()
	{
		doubles = turn.takeTurn(doubles);
		update();
		turn.doAction();
		update();
		if(doubles == 0)
		{
			turn.nextPlayer();
		}

	}
	
	private static void playerTurnUpdate() {
		
		lblPlayersTurn.setText("Player " + (turn.currentPlayer.getID()+1) + "'s Turn");
		
	}

	/**
	 * Create the application.
	 */
	public MainGame() {
		initialize();
	}
	
	/** Singleton Design Pattern*/
	//Probably in wrong spot (move to Board class itself)
	public static Board getBoardInstance()
	{
		if (board==null)
		{
			board = new Board();
		}
		
		return board;
	}
	
/**
 * Update the frame after a turn
 * */
 
	private static void update()
	{
		playerIcons.get(turn.currentPlayer.getID()).setLocation(board.getSpace(turn.currentPlayer.getSpace()).x + (OFFSET*turn.currentPlayer.getID()), board.getSpace(turn.currentPlayer.getSpace()).y + OFFSET*turn.currentPlayer.getID());
		die1.setIcon(new ImageIcon(MainGame.class.getResource(dicePics[turn.roll1])));
		die2.setIcon(new ImageIcon(MainGame.class.getResource(dicePics[turn.roll2])));
		
		for(int i = 0; i < turn.players.length; i++)
		{
			playerMoney.get(i).setText("Current Funds: " + turn.getMoney(i));
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		
		playerMoney = new ArrayList<JLabel>();
		
		frmMonopoly = new JFrame();
		frmMonopoly.setTitle("Monopoly!");
		frmMonopoly.setIconImage(Toolkit.getDefaultToolkit().getImage(MainGame.class.getResource("/Art/Icon.png")));
		frmMonopoly.setBounds(100, 100, 1084, 704);
		frmMonopoly.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMonopoly.getContentPane().setLayout(null);
		
		JLabel P1Icon = new JLabel("");
		P1Icon.setBounds(board.getSpace(0).x + OFFSET*0, board.getSpace(0).y + OFFSET*0, 32, 32);
		frmMonopoly.getContentPane().add(P1Icon);
		P1Icon.setIcon(new ImageIcon(MainGame.class.getResource("/Art/Hat.png")));
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setBounds(10, 11, 644, 644);
		Image newImage = new ImageIcon(MainGame.class.getResource("/Art/MonopolyBoard.jpg")).getImage().getScaledInstance(644, 644, Image.SCALE_SMOOTH);
		
		JLabel P2Icon = new JLabel("");
		P2Icon.setBounds(board.getSpace(0).x + OFFSET*1, board.getSpace(0).y + OFFSET*1, 32, 32);
		P2Icon.setIcon(new ImageIcon(MainGame.class.getResource("/Art/Car.png")));
		frmMonopoly.getContentPane().add(P2Icon);
		
		JLabel P3Icon = new JLabel("");
		P3Icon.setBounds(board.getSpace(0).x + OFFSET*2, board.getSpace(0).y + OFFSET*2, 32, 32);
		P3Icon.setIcon(new ImageIcon(MainGame.class.getResource("/Art/Thimble.png")));
		frmMonopoly.getContentPane().add(P3Icon);
		
		JLabel P4Icon = new JLabel("");
		P4Icon.setBounds(board.getSpace(0).x + OFFSET*3, board.getSpace(0).y + OFFSET*3, 32, 32);
		P4Icon.setIcon(new ImageIcon(MainGame.class.getResource("/Art/Scottie.png")));
		frmMonopoly.getContentPane().add(P4Icon);
		
		JLabel P5Icon = new JLabel("");
		P5Icon.setBounds(board.getSpace(0).x + OFFSET*4, board.getSpace(0).y + OFFSET*4, 32, 32);
		P5Icon.setIcon(new ImageIcon(MainGame.class.getResource("/Art/Ship.png")));
		frmMonopoly.getContentPane().add(P5Icon);
		
		JLabel P6Icon = new JLabel("");
		P6Icon.setBounds(board.getSpace(0).x + OFFSET*5, board.getSpace(0).y + OFFSET*5, 32, 32);
		P6Icon.setIcon(new ImageIcon(MainGame.class.getResource("/Art/Shoe.png")));
		frmMonopoly.getContentPane().add(P6Icon);
		
		JLabel P7Icon = new JLabel("");
		P7Icon.setBounds(board.getSpace(0).x + OFFSET*6, board.getSpace(0).y + OFFSET*6, 32, 32);
		P7Icon.setIcon(new ImageIcon(MainGame.class.getResource("/Art/Wheelbarrow.png")));
		frmMonopoly.getContentPane().add(P7Icon);
		
		JLabel P8Icon = new JLabel("");
		P8Icon.setBounds(board.getSpace(0).x + OFFSET*7, board.getSpace(0).y + OFFSET*7, 32, 32);
		P8Icon.setIcon(new ImageIcon(MainGame.class.getResource("/Art/Iron.png")));
		frmMonopoly.getContentPane().add(P8Icon);
		lblNewLabel.setIcon(new ImageIcon (newImage));

		
		frmMonopoly.getContentPane().add(lblNewLabel);
		
		lblPlayersTurn = new JLabel("Player 1's Turn");
		lblPlayersTurn.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayersTurn.setBounds(817, 47, 97, 14);
		frmMonopoly.getContentPane().add(lblPlayersTurn);
		
		JPanel panel = new JPanel();
		panel.setBounds(671, 95, 177, 103);
		frmMonopoly.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblPlayer = new JLabel("Player 1");
		lblPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer.setBounds(56, 5, 60, 14);
		panel.add(lblPlayer);
		
		JLabel P1_Money = new JLabel("Current Funds: 1500");
		P1_Money.setBounds(10, 30, 157, 14);
		panel.add(P1_Money);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(881, 95, 177, 103);
		frmMonopoly.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblPlayer_1 = new JLabel("Player 2");
		lblPlayer_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayer_1.setBounds(69, 5, 60, 14);
		panel_1.add(lblPlayer_1);
		
		JLabel P2_Money = new JLabel("Current Funds: 1500");
		P2_Money.setBounds(10, 30, 157, 14);
		panel_1.add(P2_Money);
		
		
		playerMoney.add(P1_Money);
		playerMoney.add(P2_Money);
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(881, 246, 177, 103);
		frmMonopoly.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(671, 246, 177, 103);
		frmMonopoly.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		if(playerCount >= 3)
		{
			JLabel lblPlayer_2 = new JLabel("Player 3");
			lblPlayer_2.setBounds(69, 5, 60, 14);
			panel_3.add(lblPlayer_2);
			
			JLabel P3_Money = new JLabel("Current Funds: 1500");
			P3_Money.setBounds(10, 30, 157, 14);
			panel_3.add(P3_Money);
			
			playerMoney.add(P3_Money);
		}

		if(playerCount >= 4)
		{
			JLabel lblPlayer_3 = new JLabel("Player 4");
			lblPlayer_3.setBounds(69, 5, 60, 14);
			panel_2.add(lblPlayer_3);
			
			JLabel P4_Money = new JLabel("Current Funds: 1500");
			P4_Money.setBounds(10, 30, 157, 14);
			panel_2.add(P4_Money);
			
			playerMoney.add(P4_Money);
		}
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(671, 393, 177, 103);
		frmMonopoly.getContentPane().add(panel_4);
		panel_4.setLayout(null);
		
		
		if(playerCount >= 5)
		{
			JLabel lblPlayer_4 = new JLabel("Player 5");
			lblPlayer_4.setBounds(69, 5, 60, 14);
			panel_4.add(lblPlayer_4);
			
			JLabel P5_Money = new JLabel("Current Funds: 1500");
			P5_Money.setBounds(10, 30, 157, 14);
			panel_4.add(P5_Money);
			
			playerMoney.add(P5_Money);
		}
		
		
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(881, 393, 177, 103);
		frmMonopoly.getContentPane().add(panel_5);
		panel_5.setLayout(null);
		
		
		if(playerCount >= 6)
		{
			JLabel lblPlayer_5 = new JLabel("Player 6");
			lblPlayer_5.setBounds(69, 5, 60, 14);
			panel_5.add(lblPlayer_5);
			
			JLabel P6_Money = new JLabel("Current Funds: 1500");
			P6_Money.setBounds(10, 30, 157, 14);
			panel_5.add(P6_Money);
			
			playerMoney.add(P6_Money);
		}
		
		
		JPanel panel_6 = new JPanel();
		panel_6.setBounds(671, 536, 177, 103);
		frmMonopoly.getContentPane().add(panel_6);
		panel_6.setLayout(null);
		
		
		if(playerCount >= 7)
		{
			JLabel lblPlayer_6 = new JLabel("Player 7");
			lblPlayer_6.setBounds(69, 5, 60, 14);
			panel_6.add(lblPlayer_6);
			
			JLabel P7_Money = new JLabel("Current Funds: 1500");
			P7_Money.setBounds(10, 30, 157, 14);
			panel_6.add(P7_Money);
			
			playerMoney.add(P7_Money);
		}
		

		JPanel panel_7 = new JPanel();
		panel_7.setBounds(881, 536, 177, 103);
		frmMonopoly.getContentPane().add(panel_7);
		panel_7.setLayout(null);
		
		if(playerCount == 8)
		{
			JLabel lblPlayer_7 = new JLabel("Player 8");
			lblPlayer_7.setBounds(69, 5, 60, 14);
			panel_7.add(lblPlayer_7);
			
			JLabel P8_Money = new JLabel("Current Funds: 1500");
			P8_Money.setBounds(10, 30, 157, 14);
			panel_7.add(P8_Money);
			
			playerMoney.add(P8_Money);
		}
		

		
		die1 = new JLabel("");
		die1.setBounds(970, 34, 32, 32);
		die1.setIcon(new ImageIcon(MainGame.class.getResource("/Art/QM.png")));
		frmMonopoly.getContentPane().add(die1);
		
		die2 = new JLabel("");
		die2.setBounds(1012, 34, 32, 32);
		die2.setIcon(new ImageIcon(MainGame.class.getResource("/Art/QM.png")));
		frmMonopoly.getContentPane().add(die2);
		
		JButton btnEndTurn = new JButton("End Turn");
		btnEndTurn.setBounds(687, 43, 84, 23);
		btnEndTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				playerTurnUpdate();
				runTurn();
			}
		});
		frmMonopoly.getContentPane().add(btnEndTurn);
		
		playerIcons = new ArrayList<JLabel>();
		playerIcons.add(P1Icon);
		playerIcons.add(P2Icon);
		playerIcons.add(P3Icon);
		playerIcons.add(P4Icon);
		playerIcons.add(P5Icon);
		playerIcons.add(P6Icon);
		playerIcons.add(P7Icon);
		playerIcons.add(P8Icon);
		









	}
}
