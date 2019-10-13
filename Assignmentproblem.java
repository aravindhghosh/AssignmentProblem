import java.util.*;
public class Assignmentproblem{
	public static void main(String []args){
		new Assignment();
	}
}
class Assignment{
	int [][]a;
	int [][]temp;
	int []flag;
	int tempmin, row, column;
	int []marked_row;
	int []marked_column;
	Assignment(){
		a = new int[20][20];
		temp = new int[20][20];
		tempmin = 0;
		getelements();
		balance();
		flag = new int[row];
		calculate_row();
		System.out.println("AFTER SUBTRACTED IN EACH ROW");
		display();
		calculate_column();
		System.out.println("AFTER SUBTRACTED IN EACH COLUMN");
		display();
		marked_column = new int[column];
		marked_row = new int[row];
		solve_assignment();
		assignment_cost();
	}
	void getelements(){
		Scanner input = new Scanner(System.in);
		int i, j;
		System.out.print("\nEnter the No. of Rows: ");
		row = input.nextInt();
		System.out.print("\nEnter the No. of Columns: ");
		column = input.nextInt();
		System.out.print("\nEnter the elements: ");
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				System.out.print("\na["+i+"]["+j+"] = ");
				a[i][j] = input.nextInt();
				temp[i][j] = a[i][j];
			}
		}
		display();
		input.close();
	}
	void balance(){
		int i, j;
		if(row<column){
			System.out.println("Row is less than column....");
			row++;
			for(i=row-1;i<row;i++){
				for(j=0;j<column;j++){
					a[i][j] = 0;
					temp[i][j] = 0;
				}
			}
			display();
		}else if(column<row){
			column++;
			System.out.println("Column is less than row....");
			for(i=0;i<row;i++){
				for(j=column-1;j<column;j++){
					a[i][j] = 0;
					temp[i][j] = 0;
				}
			}
			display();
		}else{
			System.out.println("**************ROWS AND COLUMNS ARE EQUAL**************");
		}
	}
	void display(){
		int i, j;
		System.out.println();
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				System.out.print(temp[i][j] +"\t");
			}
			System.out.println();
		}
	}
	int find_min_row(int i){
		int j;
		int min = Integer.MAX_VALUE;
		for(j=0;j<column;j++){
			if(temp[i][j]<min){
				min = temp[i][j];
			}
		}
		return min;
	}
	void calculate_row(){
		int i, j, min;
		for(i=0;i<row;i++){
			min = find_min_row(i);
			for(j=0;j<column;j++){
				temp[i][j] = temp[i][j] - min;
			}
		}
	}
	int find_min_column(int i){
		int j;
		int min = Integer.MAX_VALUE;
		for(j=0;j<row;j++){
			if(temp[j][i]<min){
				min = temp[j][i];
			}
		}
		return min;
	}
	void calculate_column(){
		int i, j, min;
		for(i=0;i<column;i++){
			min = find_min_column(i);
			for(j=0;j<row;j++){
				temp[j][i] = temp[j][i] - min;
			}
		}
	}
	void solve_assignment(){
		int f = 0, i, j;
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				if(temp[i][j] == 0 && i != j){
					flag[i]++;
				}
			}
		}//end for
		for(i=0;i<column;i++){
			for(j=0;j<row;j++){
				if(flag[j] == 1){
					solve(j, i);
				}
			}
		}//end for
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				if(temp[i][j]==0){
					f++;
				}
			}
		}
		if(f==row){
			System.out.println("COMPLETED ASSIGNEMENT");
			display();
		}else{
			display();
			System.out.println("NOT FEASIBLE IN THIS MATRIX");
			mark_row_column();
			solve_assignment();
		}
	}
	void solve(int i,int j){
		int k,m;
		for(m=0;m<column;m++){
			if(temp[i][m]==0 && j!=m){
				temp[i][m]=-1;
			}
		}
		for(k=0;k<row;k++){
			if(temp[k][j]==0 && k!=i){
				temp[k][j]=-1;
			}
		}
	}
	void mark_row_column(){
		mark_unassigned_row();
		mark_zero_column();
		mark_assigned_row();
		int m_min = find_min_unmarked();
		System.out.println("AFTER MARKING ROWS AND COLUMNS");
		display_marked();
		System.out.println("minimum: "+m_min+"\nSUM OF THE ELEMENTS WITH MINIMUM \n");
		sum_elements(m_min);
		display();
		System.out.println("\n**********************************************");
	}
	void display_marked(){
		int i, j;
		System.out.println();
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				System.out.print(temp[i][j]+"\t");
			}
			System.out.print(marked_row[i]+"");
			System.out.println();
		}
		System.out.println();
		for(i=0;i<column;i++){
			System.out.print(marked_column[i]+"\t");
		}
		System.out.println("\n**********************************************");
		System.out.println();
	}
	void mark_unassigned_row(){
		int i, j;
		int flag = 0;
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				if(temp[i][j]==0){
					flag = 1;
				}
			}
			if(flag!=1){
				marked_row[i] = 1;
			}
			flag = 0;			
		}
	}//end_function
	void mark_zero_column(){
		int i, j;
		for(i=0;i<row;i++){
			if(marked_row[i]==1){
				for(j=0;j<column;j++){
					if(temp[i][j]==-1){
						marked_column[j] = 1;
					}//end_if
				}//end_j
			}//end_if
		}//end_i
	}//end_function
	void mark_assigned_row(){
		int i, j;
		for(i=0;i<column;i++){
			if(marked_column[i]==1){
				for(j=0;j<row;j++){
					if(temp[j][i]==0){
						marked_row[j] = 1;
					}//end_if
				}//end_j
			}//end_if
		}//end_i
	}//end_function
	int find_min_unmarked(){
		int min = Integer.MAX_VALUE;
		int i, j;
		for(i=0;i<row;i++){
			if(marked_row[i]==1){
				for(j=0;j<column;j++){
					if(marked_column[j]!=1){
						if(temp[i][j]<min && temp[i][j]!=-1){
							min = temp[i][j];
						}
					}//end_if
				}//end_j
			}//end_if
		}//end_i
		return min;
	}//end_function
	void sum_elements(int min){
		int i, j;
		for(i=0;i<row;i++){
			if(marked_row[i]==1){
				for(j=0;j<column;j++){
					if(marked_column[j]!=1){
						if(temp[i][j]!=-1){
							temp[i][j] = temp[i][j] - min;
						}//end_if
					}//end_if
				}//end_j
			}//end_if
		}//end_i
		for(i=0;i<row;i++){
			if(marked_row[i]!=1){
				for(j=0;j<column;j++){
					if(marked_column[j]==1){
						temp[i][j] = temp[i][j] + min;
					}//end_if
				}//end_j
			}//end_if
		}//end_i
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				if(temp[i][j]==-1){
					temp[i][j] = 0;
				}//end if
			}// end j
		}//end i
	}//end_function
	void assignment_cost(){
		int []ac = new int[row];
		int ass_cost = 0;
		int i, j;
		System.out.println("***********************************************************************");
		System.out.println("\nPATH");
		for(i=0;i<row;i++){
			for(j=0;j<column;j++){
				if(temp[i][j]==0){
					System.out.println((i+1)+"->"+(j+1));
					ac[i] = a[i][j];
				}//if
			}//for j
		}//for i
		System.out.println("");
		System.out.print("ASSIGNMENT COST = "+ ac[0] +" ");
		for(i=1;i<row;i++){
			System.out.print("+ "+ ac[i] +" ");
		}
		for(i=0;i<row;i++){
			ass_cost = ass_cost + ac[i];
		}
		System.out.print("= "+ ass_cost);
		System.out.print("");
	}
}