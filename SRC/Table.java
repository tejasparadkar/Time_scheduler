package org.betaiotazeta.autoshiftplanner;

/**
 *
 * @author betaiotazeta
 */

/**
 * The table is the abstraction that is obtained by grouping all the cells that 
 * represent all the half hours of work available explicitly for each employee. 
 * Each cell will subsequently be used as the basis for displaying a rectangle 
 * of the color of the employee to which it belongs. In case of absence of the 
 * employee the cell will be neutral in color. The table can be modified in
 * graphical mode during program execution. The program checks the validity of
 * changes made in real time.
 */
public class Table {
    
    public Table(int rows, int columns, Business business) {
               
        this.rows = rows;
        this.columns = columns;
        int numberOfEmployees = business.getNumberOfEmployees();
        double startTime = business.getStartTime();        
        byte idEmployee = 0;
        short idPeriod = 0;
        int dayOfWeek = -1; // counting of the days will start at 0
        int startingMinuteOfDay;
                
        // creates an array of Cell objects initialized to null
        arrayOfCells = new Cell[rows][columns];

        // fill the array with objects of type Cell
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // (byte idEmployee, short idPeriod, int startingMinuteOfDay, int dayOfWeek, boolean worked, boolean mandatory, boolean forbidden)
                arrayOfCells[i][j] = new Cell((byte) 0, (short) 0, 0, 0, false, false, false);
            }
        }

        // assign the correct dayOfWeek and startingMinuteOfDay to the cells
        for (int i = 0; i < rows; i++) {
            startingMinuteOfDay = (int) startTime * 60; // *60 in minutes;
            if (i % numberOfEmployees == 0) {
                dayOfWeek++;
            }            
            for (int j = 0; j < columns; j++) {
                arrayOfCells[i][j].setStartingMinuteOfDay(startingMinuteOfDay);
                arrayOfCells[i][j].setDayOfWeek(dayOfWeek);
                startingMinuteOfDay = startingMinuteOfDay + 30; // 30 for half hours
            }
        }
        
        // assign the correct idEmployee to the cells
        for (int i = 0; i < rows; i++) {
            if (idEmployee == numberOfEmployees) {
                idEmployee = 0;
            }
            idEmployee++;
            for (int j = 0; j < columns; j++) {
                arrayOfCells[i][j].setIdEmployee(idEmployee);
            }
        }

        // assign the correct idPeriod to the cells
        int k = 0;
        int l = 0;
        for (int i = 0; i < rows; i++) {
            k++;
            idPeriod = (short) (l * columns);
            if (k == numberOfEmployees) {
                k = 0;
                l++; 
            }                        
            for (int j = 0; j < columns; j++) {
                idPeriod++;
                arrayOfCells[i][j].setIdPeriod(idPeriod);
            }
        } 
    }

    public Cell getCell(int row, int column) {
        return arrayOfCells[row][column];
    }
    
    public void setCell(int row, int column, Cell cell) {
        arrayOfCells[row][column] = cell;
    }    

    public int getNumberOfRows() {
        return rows;
    }

    public int getnumberOfColumns() {
        return columns;
    }
           
    // Print the table schema on terminal. Used for testing.
    public void printIdPeriod() {
        System.out.println("IdPeriod: ");
        short idPeriod;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                idPeriod = (arrayOfCells[i][j].getIdPeriod());
                System.out.print(idPeriod + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printIdEmployee() {
        System.out.println("IdEmployee: ");        
        byte idEmployee;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                idEmployee = (arrayOfCells[i][j].getIdEmployee());
                System.out.print(idEmployee + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printWorked() {
        System.out.println("Worked cells: ");
        boolean worked;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                worked = (arrayOfCells[i][j].isWorked());
                System.out.print(worked + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printTime() {
        System.out.println("Time stored in cells: ");
        int dayOfWeek;
        int startingMinuteOfDay;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                dayOfWeek = (arrayOfCells[i][j].getDayOfWeek());
                startingMinuteOfDay = (arrayOfCells[i][j].getStartingMinuteOfDay());                
                System.out.print("day: " + dayOfWeek + ", min: " + startingMinuteOfDay + " | ");
            }
            System.out.println();
        }
        System.out.println();
    }
       
    // The number (quantity) of the rows in the table.     
    private int rows;

    // The number (quantity) of the columns in the table.
    private int columns;

    // The array contains objects of the cell type and has meaning [row] [column].
    // The row and column count starts at 0.
    private Cell[][] arrayOfCells;
}