package practice;

import java.util.*;

public class OneTwentyFour {

	public static void main(String[] args) {
		Scanner input= new Scanner(System.in);
		Random rnd= new Random();
		boolean validInput=false;
		int gridNumber = 0;
		do
		{
			System.out.println("Enter a number between 4-8 to start the game");
			gridNumber=input.nextInt();

			if(gridNumber>=4 && gridNumber<=8) {
				
				validInput=true;
			}	

		}while(validInput==false);

		int[][] grid= new int[gridNumber][gridNumber];

		startGame(grid);
		boolean moved=move(grid);
		if(moved==false)
			diplayResult(grid);
	}

	public static void startGame(int[][] grid){
		insert(grid);
		emptyGrid(grid);
		displayGrid(grid);
	}

	public static void continueGame(int[][] grid){
		displayGrid(grid);
		boolean moved=move(grid);
		if(moved==false)
			diplayResult(grid);
	}

	public static void displayGrid(int[][] grid) {
		for(int i=0; i<grid.length;i++ ) {
			System.out.print("+-----");
			if(i == (grid.length-1))
				System.out.print("+");
		}

		System.out.println();

		for(int i=0; i<grid.length; i++) {
			System.out.print("|");

			for(int j=0; j<grid[i].length; j++) {
				printGridValuesOrSpaces(grid[i][j]);      //prints the digits if > 0 or space if digit = 0

				System.out.print(" |");
			}

			System.out.println();
		
			for(int j=0; j<grid[i].length;j++ ) {
				System.out.print("+-----");
			}

			System.out.println("+");
		}
		System.out.print("\n");
	}
	
	public static void printGridValuesOrSpaces(int value){
		//purpose: to remove the zeros from the grid
		if(value==0) 
			System.out.print("    ");

		else{
			if(value>0){
				String myValue = String.valueOf(value);

				if(myValue.length()<4){

					for(int i=0; i<(4-myValue.length());++i)
						System.out.print(" ");

					System.out.print(value);	
				}
			}
		}
	}
	
	public static void insert(int [][]grid) { // generate the first 1
		Random rnd= new Random();
		int i= rnd.nextInt(grid.length);
		int j= rnd.nextInt(grid[0].length);
		grid[i][j]=1;
		
	}

	public static void emptyGrid(int [][] grid) {
		//purpose: check empty spaces in the grid
		int counter=0;
		int[][]emptyGrid= new int[(grid.length*grid[0].length)][2];
		for(int i=0; i<grid.length; i++) {
			for(int j=0; j<grid.length; j++) {
				if(grid[i][j]==0) {
					emptyGrid[counter][0]=i;
					emptyGrid[counter][1]=j;
					counter++;
				}
			}
		}
		// insert the next 1
		Random rnd= new Random();
		int randomCell=rnd.nextInt(counter);
		int i=emptyGrid[randomCell][0];
		int j=emptyGrid[randomCell][1];
		grid[i][j]=1;                 
	}

	public static boolean move(int [][]grid){// letters for movement of numbers
		Scanner input=new Scanner(System.in);
		boolean validMove;
		
		do { 
			validMove=false;
			System.out.println("Enter the following for movement");
			System.out.print("W - Move Up \nS - Move Down \nA - Move Left \nD - Move Right\nQ - Quit\n\n\n");
			char value= input.next().charAt(0);
			if(value=='W'||value=='w'||value=='A'||value=='a'||
				value=='S'|| value=='s'|| value=='D'||value=='d') {

				validMove = validateMove(value, grid);
				displayGrid(grid);
			}
			else if(value=='Q'||value=='q') {
				validMove=false;
			}
			else{
				System.out.println("Invalid entry!!!\n\n");
				validMove=true;
			}

		}while(validMove==true);

		return validMove;
	}

	public static boolean validateMove(char moveValue, int[][] grid){
		// movement of grid
		boolean moved = false;

		switch(moveValue){
			case 'a':
			case 'A':
				moved = moveLeft(grid);
				break;

			case 'd':
			case 'D':
				moved = moveRight(grid);
				break;

			case 'w':
			case 'W':
				moved = moveUpwards(grid);
				break;

			case 's':
			case 'S':
				moved = moveDownwards(grid);
				break;
		}

		return moved;
	}

	//moveLeft shifts the grid to the left
	public static boolean moveLeft(int[][] grid){ 

		boolean isMovedLeft=false;
		int max = 0;
		for(int i=0; i<grid.length; ++i){

			for(int j=0; j<grid[i].length; ++j){
				if(grid[i][j] == 0)                                  
					continue; 
				if(grid[i][j]>0 && j<=grid[i].length-1){
					for(int k=j; k<grid[i].length-1; ++k){ 
						if(grid[i][k+1] == grid[i][j]){    
							grid[i][j]+=grid[i][k+1];     
							grid[i][k+1]=0;
							if(grid[i][j]>=1024)
								max = grid[i][j];
							isMovedLeft=true;
							break;                         
						}
						if(grid[i][k+1]>0)                 
							break;            
					}
				}
			}

			if(max >= 1024)
				break;

		}

		for(int i=0; i<grid.length; ++i){
			for(int j=0; j<grid[i].length; ++j){
				if(grid[i][j] == 0)
					continue;
				if(grid[i][j]>0 && j==0)
					continue;
				if(grid[i][j]>0 && j>0){
					for(int k=j; k>0; --k){
						if(grid[i][k-1]>0)
							break;
						if(grid[i][k-1]==0){
							grid[i][k-1]=grid[i][k];
							grid[i][k]=0;
							isMovedLeft=true; 
						}
					}
				}
			}
		}
		if(isMovedLeft==true){
			if(max >= 1024)
				isMovedLeft=false;
			else
				insertValueOneMovingLeftOrRightwards(grid, grid.length-1);
		}
		else{
			if(isGridFilled(grid)==false){
				System.out.println("You're stuck, try another move!!!\n\n");
				isMovedLeft=true;
			}
			else
				System.out.println("You're out of moves!!!\n\n");
		}
		return isMovedLeft;
	}
	
	//move grid to the right
	public static boolean moveRight(int[][] grid){

		boolean isMovedRight=false;
		int max = 0;
		for(int i=0; i<grid.length; ++i){

			for(int j=grid[i].length-1; j>=0; --j){
				if(grid[i][j]==0)
					continue;
				if(grid[i][j]>0 && j<=grid[i].length-1){

					for(int k=j; k>0; --k){

						if(grid[i][k-1]==grid[i][j]){
							grid[i][j]+=grid[i][k-1];
							grid[i][k-1]=0;

							if(grid[i][j]>=1024)
								max = grid[i][j];
							isMovedRight=true;
							break;
						}
						if(grid[i][k-1]>0)
							break;

					}
				}
			}
			if(max >= 1024)
				break;
		}

		for(int i=0; i<grid.length; ++i){

			for(int j=grid[i].length-1;j>=0;--j){
				if(grid[i][j]==0)
					continue;
				if(grid[i][j]>0 && j==grid[i].length-1)
					continue;
				if(grid[i][j]>0 && j<grid[i].length-1){

					for(int k=j; k<grid[i].length-1; ++k){
						if(grid[i][k+1]>0)
							break;
						else{
							if(grid[i][k+1]==0){
								grid[i][k+1]=grid[i][k];
								grid[i][k]=0;
								isMovedRight=true;
							}
						}
					}
				}
			}
		}
		if(isMovedRight==true){
			if(max >= 1024)
				isMovedRight=false;
			else
				insertValueOneMovingLeftOrRightwards(grid, 0);
		}
		else{

			if(isGridFilled(grid)==false){
				System.out.println("You're stuck, try another move!!!\n\n");
				isMovedRight=true;
			}
			else
				System.out.println("You're out of moves!!!\n\n");
		}
		return isMovedRight;
	}

	public static boolean moveUpwards(int[][] grid){
		//move grid up
		boolean isMovedUpwards=false;
		int max=0;
		for(int j=0; j<grid[0].length; ++j){
			for(int i=0; i<grid.length; ++i){
				if(grid[i][j]==0)
					continue;
				if(grid[i][j]>0 && i>=0){
					for(int k=i; k<grid.length-1; ++k){
						if(grid[k+1][j]==grid[i][j]){
							grid[i][j]+=grid[k+1][j];
							grid[k+1][j]=0;

							if(grid[i][j]>=1024)
								max = grid[i][j];
							isMovedUpwards=true;
							break;
						}
						if(grid[k+1][j]>0)
							break;
					}
				}
			}
			if(max>=1024)
				break;
		}

		for(int j=0; j<grid[0].length; ++j){

			for(int i=0; i<grid.length; ++i){
				if(grid[i][j]==0)
					continue;
				if(grid[i][j]>0 && i==0)
					continue;
				if(grid[i][j]>0 && i>0){
					for(int k=i; k>0; --k){
						if(grid[k-1][j]>0)
							break;
						else{
							if(grid[k-1][j]==0){
								grid[k-1][j]=grid[k][j];
								grid[k][j]=0;
								isMovedUpwards=true;
							}
						}
					}
				}
			}
		}
		if(isMovedUpwards==true){
			if(max >= 1024)
				isMovedUpwards=false;
			else
				insertValueOneMovingUpOrDownwards(grid, grid.length-1);
		}
		else{
			if(isGridFilled(grid)==false){
				System.out.println("You're stuck, try another move!!!\n\n");
				isMovedUpwards=true;
			}
			else
				System.out.println("You're out of moves!!!\n\n");
		}
				
		return isMovedUpwards;
	}

	public static boolean moveDownwards(int[][] grid){
		//move grid down

		boolean isMovedDownwards=false;
		int max = 0;

		for(int j=0; j<grid[0].length; ++j){
			for(int i=grid.length-1; i>=0; --i){
				if(grid[i][j]==0)
					continue;
				if(grid[i][j]>0 && i<=grid.length-1){
					for(int k=i; k>0; --k){
						if(grid[k-1][j]==0)
							continue;
						if(grid[k-1][j]==grid[i][j]){
							grid[i][j]+=grid[k-1][j];
							grid[k-1][j]=0;

							if(grid[i][j]>=1024)
								max = grid[i][j];
							isMovedDownwards=true;
							break;
						}
						if(grid[k-1][j]>0)
							break;
					}
				}

			}
			if(max>=1024)
				break;
		}

		for(int j=0; j<grid[0].length; ++j){

			for(int i=grid.length-1; i>=0; --i){
				if(grid[i][j]==0)
					continue;
				if(grid[i][j]>0 && i==grid.length-1)
					continue;
				if(grid[i][j]>0 && i<grid.length-1){
					for(int k=i; k<grid.length-1; ++k){
						if(grid[k+1][j]>0)
							break;
						else{
							if(grid[k+1][j]==0){
								grid[k+1][j]=grid[k][j];
								grid[k][j]=0;
								isMovedDownwards=true;
							}
						}
					}
				}
			}
		}

		if(isMovedDownwards==true){
			if(max >= 1024)
				isMovedDownwards=false;
			else
				insertValueOneMovingUpOrDownwards(grid, 0);
		}
		else{
			if(isGridFilled(grid)==false){
				System.out.println("You're stuck, try another move!!!\n\n");
				isMovedDownwards=true;
			}
			else
				System.out.println("You're out of moves!!!\n\n");

		}

		return isMovedDownwards;
	}

	public static void insertValueOneMovingUpOrDownwards(int[][] grid, int rowIndex){

		int spaceCounter = 0;
		int rowSpaceCounter=0;

		Random rnd = new Random();

		for(int j=0; j<grid[0].length-1; ++j){
			if(grid[rowIndex][j]==0)
				++spaceCounter;
		}

		if(spaceCounter >= 1){
			int columnIndex=0;

			if(spaceCounter == 1){
				for(int j=0; j<grid[0].length; ++j){
					if(grid[rowIndex][j]==0){
						columnIndex=j;
						break;
					}
				}
			}

			if(spaceCounter > 1){
				int randomColumn;
				do{
					randomColumn = rnd.nextInt(grid.length);

					if(grid[rowIndex][randomColumn]==0)
						columnIndex=randomColumn;

				}while(grid[rowIndex][randomColumn]!=0);
			}
			if(rowIndex==0){
				for(int i=rowIndex; i<grid.length; ++i){
					if(grid[i][columnIndex]==0)
						++rowSpaceCounter;
					if(grid[i][columnIndex]>0)
						break;
				}
				if(rowSpaceCounter==1)
					grid[rowIndex][columnIndex]=1;
				else{
					int randomRowSpace=rnd.nextInt(rowSpaceCounter);
					//System.out.println("RANDOM rowSpaceCounter = " + randomRowSpace);
					grid[randomRowSpace][columnIndex]=1;
				}
			}
			else{
				for(int i=rowIndex; i>=0; --i){
					if(grid[i][columnIndex]==0)
						++rowSpaceCounter;
					if(grid[i][columnIndex]>0)
						break;
				}

				if(rowSpaceCounter==1)
					grid[rowIndex][columnIndex]=1;	
				else{
					int randomRowSpace=rowIndex - rnd.nextInt(rowSpaceCounter);
					grid[randomRowSpace][columnIndex]=1;
				}
			}
		}
	}
	
	public static void insertValueOneMovingLeftOrRightwards(int[][] grid, int columnIndex){
		int spaceCounter = 0;
		int columnSpaceCounter=0;

		Random rnd = new Random();

		for(int i=0; i<grid.length; ++i){
			if(grid[i][columnIndex]==0)
				++spaceCounter;
		}

		if(spaceCounter >= 1){
			int rowIndex=0;

			if(spaceCounter == 1){
				for(int i=0; i<grid.length; ++i){
					if(grid[i][columnIndex]==0){
						rowIndex=i;
						break;
					}
				}
			}

			if(spaceCounter > 1){
				int randomRow=0;
				do{
					randomRow = rnd.nextInt(grid.length);

					if(grid[randomRow][columnIndex]==0)
						rowIndex=randomRow;

				}while(grid[randomRow][columnIndex]!=0);
			}

			if(columnIndex==0){
				for(int j=columnIndex; j<grid[0].length; ++j){
					if(grid[rowIndex][j]==0)
						++columnSpaceCounter;
					if(grid[rowIndex][j]>0)
						break;
				}
				if(columnSpaceCounter==1)
					grid[rowIndex][columnIndex]=1;

				else{
					int randomColumnSpace=rnd.nextInt(columnSpaceCounter);
					grid[rowIndex][randomColumnSpace]=1;
				}
			}
			else{
				for(int j=columnIndex; j>=0; --j){
					if(grid[rowIndex][j]==0)
						++columnSpaceCounter;
					if(grid[rowIndex][j]>0)
						break;
				}
				if(columnSpaceCounter==1)
					grid[rowIndex][columnIndex]=1;

				else{
					int randomColumnSpace=columnIndex - rnd.nextInt(columnSpaceCounter);
					grid[rowIndex][randomColumnSpace]=1;
				}
			}
		}
	}

	public static boolean isGridFilled(int[][] grid){
		//check for spaces in the grid
		boolean isFilled = true;
		for(int i=0; i<grid.length; ++i){
			for(int j=0; j<grid[i].length; ++j){
				if(grid[i][j]==0){
					isFilled=false;
					break;
				}

			}
			if(isFilled==false)
				break;
		}

		return isFilled;
	}

	public static int diplayResult(int[][] grid){
		int max=0;
			
		for(int i=0; i<grid.length; ++i){
			for(int j=0; j<grid[i].length; ++j){
				if(grid[i][j]>max)
					max=grid[i][j];
			}
			if(max>=1024)
				break;
		}

		if(max>=1024){
			System.out.println("*** Your score is "+ max +" ***");
			System.out.println("CONGRATULATIONS!!! *** |||(You WON!!!)||| ***");
			char answer;
			Scanner input= new Scanner(System.in);
			do{
				System.out.println("Do you wish to continue?\nY - yes\nN - no");
				answer=input.next().charAt(0);

				if(answer!='Y'&&answer!='y'&&answer!='N'&&answer!='n')
					System.out.println("Invalid entry!!!\n");

			}while(answer!='Y'&&answer!='y'&&answer!='N'&&answer!='n');

			if(answer=='Y'||answer=='y')
				continueGame(grid);
			else{
				if(max>1024)
					System.out.println("*** Your new High Score is "+ max +" ***");

				System.out.println("Have a nice day!!!");
			}
		}
		else{
			System.out.println("GAME OVER\n\n");
			System.out.println("*** Your score is " + max +" ***");
			System.out.println("SORRY!!! *** |||(You LOST!!!)||| ***");
		}
		return max;
	}

}
