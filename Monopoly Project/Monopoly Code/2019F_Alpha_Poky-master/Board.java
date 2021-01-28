public class Board {
	
        int currentTurn = 0;
      	Space[] spaces = new Space[41];
      	Deck commChest;
      	Deck chance;
      	
        //String[] names = new String[] { };
        /** A Monopoly Board. Only one should exist at any time; The Turn class handles gameplay (when a Board is passed in)*/
        public Board() 
        {
        	//52 px between spaces either direction (except for corners)

        	//TODO add actions to all these spaces (Phase 2)
        	
        	//Go doesn't need an action, because AdvanceToAction has a built-in check for passing (or landing on) Go
        	spaces[0] = new Space(576, 576, 0); //Go
        	spaces[0].setAction(new DummyAction());
        	
        	spaces[1] = new Space(518, 576, 1); //Old Kent Road
        	spaces[1].setRent(2, 10, 30, 90, 160, 250);
        	spaces[1].setCost(60);
        	spaces[1].setAction(new PurchaseAction(spaces[1]));
        	
        	spaces[2] = new Space(466, 576, 2); //Comm Chest 1
        	
        	spaces[3] = new Space(412, 576, 3); //White Chapel Road
        	spaces[3].setRent(4, 20, 60, 180, 320, 450);
        	spaces[3].setCost(60);
        	spaces[3].setAction(new PurchaseAction(spaces[3]));
        	
        	spaces[4] = new Space(360, 576, 4); //Income Tax
        	//TODO Give player choice of 200$ or 10%
        	spaces[4].setAction(new BankPaymentAction(-200));
        	
        	spaces[5] = new Space(308, 576, 5); //Kings Cross Station
        	spaces[5].setAction(new RailroadPurchaseAction(spaces[5]));
        	spaces[5].setCost(200);
        	
        	spaces[6] = new Space(256, 576, 6); //The Angel, Islington
        	spaces[6].setRent(6, 30, 90, 270, 400, 550);
        	spaces[6].setCost(100);
        	spaces[6].setAction(new PurchaseAction(spaces[6]));
        	
        	spaces[7] = new Space(204, 576, 7); //Chance 1

        	
        	spaces[8] = new Space(152, 576, 8); //Euston Road
        	spaces[8].setRent(6, 30, 90, 270, 400, 550);
        	spaces[8].setCost(100);
        	spaces[8].setAction(new PurchaseAction(spaces[8]));
        	
        	spaces[9] = new Space(100, 576, 9); //Pentonville Road
        	spaces[9].setRent(8, 40, 100, 300, 450, 600);
        	spaces[9].setCost(120);
        	spaces[9].setAction(new PurchaseAction(spaces[9]));
        	
        	spaces[10] = new Space(15, 576, 10); //Jail (Just Visiting)
        	spaces[10].setAction(new DummyAction());
        	
        	spaces[11] = new Space(15, 523, 11); //Pall Mall
        	spaces[11].setRent(10, 50, 150, 450, 625, 750);
        	spaces[11].setCost(140);
        	spaces[11].setAction(new PurchaseAction(spaces[11]));
        	
        	spaces[12] = new Space(15, 470, 12); //Electric Co
        	//TODO Utilities
        	spaces[12].setAction(new DummyAction());
        	
        	spaces[13] = new Space(15, 417, 13); //Whitehall
        	spaces[13].setRent(10, 50, 150, 450, 625, 750);
        	spaces[13].setCost(140);
        	spaces[13].setAction(new PurchaseAction(spaces[13]));
        	
        	spaces[14] = new Space(15, 364, 14); //Northumberland Ave
        	spaces[14].setRent(12, 60, 180, 500, 700, 900);
        	spaces[14].setCost(160);
        	spaces[14].setAction(new PurchaseAction(spaces[14]));
        	
        	spaces[15] = new Space(15, 311, 15); //Marylebone Station
        	spaces[15].setCost(200);
        	spaces[15].setAction(new RailroadPurchaseAction(spaces[15]));
        	
        	spaces[16] = new Space(15, 258, 16); //Bow Street
        	spaces[16].setRent(14, 70, 200, 550, 750, 950);
        	spaces[16].setCost(180);
        	spaces[16].setAction(new PurchaseAction(spaces[16]));
        	
        	spaces[17] = new Space(15, 205, 17); //Comm Chest 2

        	
        	spaces[18] = new Space(15, 152, 18); //Marlborough St
        	spaces[18].setRent(14, 70, 200, 550, 750, 950);
        	spaces[18].setCost(180);
        	spaces[18].setAction(new PurchaseAction(spaces[18]));
        	
        	spaces[19] = new Space(15, 99, 19); //Vine St
        	spaces[19].setRent(16, 80, 220, 600, 800, 1000);
        	spaces[19].setCost(200);
        	spaces[19].setAction(new PurchaseAction(spaces[19]));
        	
        	spaces[20] = new Space(15, 15, 20); //Free Parking
        	spaces[20].setAction(new DummyAction());
        	
        	spaces[21] = new Space(100, 15, 21); //Strand
        	spaces[21].setRent(2, 10, 30, 90, 160, 250);
        	spaces[21].setCost(220);
        	spaces[21].setAction(new PurchaseAction(spaces[21]));
        	
        	spaces[22] = new Space(152, 15, 22); //Chance 2
        	
        	spaces[23] = new Space(204, 15, 23); //Fleet St
        	spaces[23].setRent(2, 10, 30, 90, 160, 250);
        	spaces[23].setCost(220);
        	spaces[23].setAction(new PurchaseAction(spaces[23]));
        	
        	spaces[24] = new Space(256, 15, 24); //Trafalgar Square
        	spaces[24].setRent(2, 10, 30, 90, 160, 250);
        	spaces[24].setCost(240);
        	spaces[24].setAction(new PurchaseAction(spaces[24]));
        	
        	spaces[25] = new Space(308, 15, 25); //Fenchurch St Station
        	spaces[25].setCost(200);
        	spaces[25].setAction(new RailroadPurchaseAction(spaces[25]));
        	
        	spaces[26] = new Space(360, 15, 26); //Leicaster Square
        	spaces[26].setRent(2, 10, 30, 90, 160, 250);
        	spaces[26].setCost(260);
        	spaces[26].setAction(new PurchaseAction(spaces[26]));
        	
        	spaces[27] = new Space(412, 15, 27); //Coventry Street
        	spaces[27].setRent(2, 10, 30, 90, 160, 250);
        	spaces[27].setCost(260);
        	spaces[27].setAction(new PurchaseAction(spaces[27]));
        	
        	spaces[28] = new Space(464, 15, 28); //Water Works
        	spaces[28].setAction(new DummyAction());
        	
        	spaces[29] = new Space(516, 15, 29); //Piccadilly
        	spaces[29].setRent(2, 10, 30, 90, 160, 250);
        	spaces[29].setCost(280);
        	spaces[29].setAction(new PurchaseAction(spaces[29]));
        	
        	spaces[30] = new Space(576, 15, 30); //GOTO Jail
        	//Action defined below (once Jail Space exists)
        	
        	spaces[31] = new Space(576, 99, 31); //Regent St
        	spaces[31].setRent(2, 10, 30, 90, 160, 250);
        	spaces[31].setCost(300);
        	spaces[31].setAction(new PurchaseAction(spaces[31]));
        	
        	spaces[32] = new Space(576, 152, 32); //Oxford St
        	spaces[32].setRent(2, 10, 30, 90, 160, 250);
        	spaces[32].setCost(300);
        	spaces[32].setAction(new PurchaseAction(spaces[32]));
        	
        	spaces[33] = new Space(576, 205, 33); //Comm Chest 3
        	
        	spaces[34] = new Space(576, 258, 34); //Bond St
        	spaces[34].setRent(2, 10, 30, 90, 160, 250);
        	spaces[34].setCost(320);
        	spaces[34].setAction(new PurchaseAction(spaces[34]));
        	
        	spaces[35] = new Space(576, 311, 35); //Liverpool Street Station
        	spaces[35].setCost(350);
        	spaces[35].setAction(new RailroadPurchaseAction(spaces[35]));
        	
        	spaces[36] = new Space(576, 364, 36); //Chance 3
        	
        	spaces[37] = new Space(576, 417, 37); //Park Lane
        	spaces[37].setRent(2, 10, 30, 90, 160, 250);
        	spaces[37].setCost(400);
        	spaces[37].setAction(new PurchaseAction(spaces[37]));
        	
        	spaces[38] = new Space(576, 470, 38); //Super Tax
        	spaces[38].setAction(new BankPaymentAction(-100));
        	
        	spaces[39] = new Space(576, 523, 39); //Mayfair
        	spaces[39].setRent(2, 10, 30, 90, 160, 250);
        	spaces[39].setCost(60);
        	spaces[39].setAction(new PurchaseAction(spaces[39]));
        	
        	spaces[40] = new Space(16,576, 40); //Jail (Inside)
        	spaces[40].setAction(new DummyAction());
        	
        	/*
        	//TODO remove this (after setting up actions above)
        	for(int i = 0; i < spaces.length; i++)
        	{
        		spaces[i].setAction(new BankPaymentAction(10));
        	}
        	*/
        	
        	//The Go To Space needs the Jail space passed in, but we instantiate that space at the end. Set the action once the space exists
        	spaces[30].setAction(new GoToAction(spaces[40]));
        	
          	commChest = new Deck(1, spaces);
          	chance = new Deck(0, spaces);
          	
          	//Instantiate All card-draw actions after the decks exist
        	spaces[2].setAction(new DrawCardAction(commChest));
        	spaces[7].setAction(new DrawCardAction(chance));
        	spaces[17].setAction(new DrawCardAction(commChest));
        	spaces[22].setAction(new DrawCardAction(chance));
        	spaces[33].setAction(new DrawCardAction(commChest));
        	spaces[36].setAction(new DrawCardAction(chance));
        	
        }    
        
        public static String getPos() {
    
        	//Your Current Position: 
        	return null;
        }

		public Space getSpace(int curSpace) {
			return spaces[curSpace];
		}
        
        
        //public int getTotalSquare() {
	
	      //}
}