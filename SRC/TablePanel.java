package org.betaiotazeta.autoshiftplanner;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JOptionPane;

/**
 *
 * @author betaiotazeta
 */
public class TablePanel extends javax.swing.JPanel {

    /**
    * Constructor without parameters
    * NetBeans GUI builder requires a constructor without parameters
    * Used to avoid Exceptions "bug" in NetBeans GUI builder
    */
    public TablePanel() {
    }
    
    public TablePanel(AspApp aspApp) {

        this.aspApp = aspApp;      
        
        // Used to prevent drawing in the NetBeans graphic editor.
        allowDraw = true;
        
        initComponents();
        
        tableGraphic = new TableGraphic(this.aspApp, this);
       
        /* Works from a method
        AspApp aspApp = (AspApp) SwingUtilities.getAncestorOfClass(AspApp.class, this);      
        AspApp aspApp = (AspApp) SwingUtilities.getWindowAncestor(this);
        AspApp aspApp = (AspApp) this.getTopLevelAncestor();
        */        
    }
    
    // This can be eliminated as it passes through the constructor.
    public AspApp getAspApp() {
        return aspApp;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tablePanelMouseDragged(evt);
            }
        });
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                tablePanelMouseWheelMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePanelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tablePanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePanelMouseClicked

        Table table = aspApp.getTable();

        int nR = table.getNumberOfRows();
        int nC = table.getnumberOfColumns();

        int rectangleLength = tableGraphic.getRectangleLength();
        int rectangleHeight = tableGraphic.getRectangleHeight();

        int xLeftStart = TableGraphic.XLEFTSTART;
        int yTopStart = TableGraphic.YTOPSTART;

        this.requestFocusInWindow(); // necessary?

        int x = evt.getX();
        int y = evt.getY();

        x = x - xLeftStart;
        y = y - yTopStart;

        // x gives the column, y gives the line
        // row and column values starting from 0
        // We have to use Math.floorDiv because we have problems with negative
        // values close to 0. Eg .: (y / rectangleHeight) => (-4 / 20) = 0 instead of -0.2
        int row = Math.floorDiv(y, rectangleHeight);
        int column = Math.floorDiv(x, rectangleLength);

        // check if the click was done on the table or not
        // attention: values of nR and nC start at 0
        boolean flag1 = ((row >= 0) && (row < nR));
        boolean flag2 = ((column >= 0) && (column < nC));

        boolean go = (flag1 && flag2);

        if (go) {

            boolean worked = table.getCell(row, column).isWorked();
            boolean forbidden = table.getCell(row, column).isForbidden();
            boolean mandatory = table.getCell(row, column).isMandatory();

            if (aspApp.getWorked_jRadioButton().isSelected() && !forbidden) {
                byte idEmployee = table.getCell(row, column).getIdEmployee();
                // Counting idEmployee starts at 1. Position in arrayList starts at 0. Subtract 1.
                Employee employee = aspApp.getStaff().get(idEmployee - 1);

                if (worked == false) {
                    table.getCell(row, column).setWorked(true);
                    employee.setHoursWorked(employee.getHoursWorked() + 0.5);
                    aspApp.updateLabelHoursWorked();
                } else {
                    table.getCell(row, column).setWorked(false);
                    employee.setHoursWorked(employee.getHoursWorked() - 0.5);
                    aspApp.updateLabelHoursWorked();
                }
                repaint();

            } else if (aspApp.getForbidden_jRadioButton().isSelected()
                    && !worked && !mandatory) {
                if (forbidden == false) {
                    table.getCell(row, column).setForbidden(true);
                } else {
                    table.getCell(row, column).setForbidden(false);
                }
                repaint();

            } else if (aspApp.getMandatory_jRadioButton().isSelected() && !forbidden) {
                if (mandatory == false) {
                    table.getCell(row, column).setMandatory(true);
                } else {
                    table.getCell(row, column).setMandatory(false);
                }
                repaint();

            } else {
                String message = "Woops... a conflict occurred! Please, check Editing mode.";
                JOptionPane.showMessageDialog(aspApp, message, "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_tablePanelMouseClicked

    private void tablePanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePanelMouseDragged

        Table table = aspApp.getTable();
                        
        int nR = table.getNumberOfRows();
        int nC = table.getnumberOfColumns();
        
        int rectangleLength = tableGraphic.getRectangleLength();
        int rectangleHeight = tableGraphic.getRectangleHeight();
        
        int xLeftStart = TableGraphic.XLEFTSTART;
        int yTopStart = TableGraphic.YTOPSTART;
              
        this.requestFocusInWindow(); // necessary?

        int x = evt.getX();
        int y = evt.getY();
                      
        x = x - xLeftStart;
        y = y - yTopStart;
        
        // x gives the column, y gives the line
        // row and column values starting from 0
        
        // We have to use Math.floorDiv because we have problems with negative
        // values close to 0. Eg .: (y / rectangleHeight) => (-4 / 20) = 0 instead of -0.2
        int row =  Math.floorDiv(y, rectangleHeight);
        int column = Math.floorDiv(x, rectangleLength);

        // check if the click was done on the table or not
        // attention: values of nR and nC start at 0
        boolean flag1 = ( (row >= 0) && (row < nR) );
        boolean flag2 = ( (column >= 0) && (column < nC) ); 
        
        boolean go = ( flag1 && flag2 );
        
        if (go) {
            if (!(rowOrigin == row) || !(columnOrigin == column)) {

                boolean worked = table.getCell(row, column).isWorked();
                boolean forbidden = table.getCell(row, column).isForbidden();
                boolean mandatory = table.getCell(row, column).isMandatory();

                if (aspApp.getWorked_jRadioButton().isSelected() && !forbidden) {
                    byte idEmployee = table.getCell(row, column).getIdEmployee();
                    // Counting idEmployee starts at 1. Position in arrayList starts at 0. Subtract 1.
                    Employee employee = aspApp.getStaff().get(idEmployee - 1);

                    if (worked == false) {
                        table.getCell(row, column).setWorked(true);
                        employee.setHoursWorked(employee.getHoursWorked() + 0.5);
                        aspApp.updateLabelHoursWorked();
                    } else {
                        table.getCell(row, column).setWorked(false);
                        employee.setHoursWorked(employee.getHoursWorked() - 0.5);
                        aspApp.updateLabelHoursWorked();
                    }
                    repaint();

                } else if (aspApp.getForbidden_jRadioButton().isSelected()
                        && !worked && !mandatory) {
                    if (forbidden == false) {
                        table.getCell(row, column).setForbidden(true);
                    } else {
                        table.getCell(row, column).setForbidden(false);
                    }
                    repaint();

                } else if (aspApp.getMandatory_jRadioButton().isSelected() && !forbidden) {
                    if (mandatory == false) {
                        table.getCell(row, column).setMandatory(true);
                    } else {
                        table.getCell(row, column).setMandatory(false);
                    }
                    repaint();

                } else {
                    String message = "Woops... a conflict occurred! Please, check Editing mode.";
                    JOptionPane.showMessageDialog(aspApp, message, "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
            rowOrigin = row;
            columnOrigin = column;         
        }
    }//GEN-LAST:event_tablePanelMouseDragged

    private void tablePanelMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_tablePanelMouseWheelMoved

        Table table = aspApp.getTable();
                                
        int nR = table.getNumberOfRows();
        int nC = table.getnumberOfColumns();

        int rectangleLength = tableGraphic.getRectangleLength();
        int rectangleHeight = tableGraphic.getRectangleHeight();

        int xLeftStart = TableGraphic.XLEFTSTART;
        int yTopStart = TableGraphic.YTOPSTART;

        this.requestFocusInWindow(); // necessary?

        int x = evt.getX();
        int y = evt.getY();
        int z = evt.getWheelRotation();

        x = x - xLeftStart;
        y = y - yTopStart;

        // x gives the column, y gives the line
        // row and column values starting from 0
        
        // We have to use Math.floorDiv because we have problems with negative
        // values close to 0. Eg .: (y / rectangleHeight) => (-4 / 20) = 0 instead of -0.2
        int row = Math.floorDiv(y, rectangleHeight);
        int column = Math.floorDiv(x, rectangleLength);

        // check if the click was done on the table or not
        // attention: values of nR and nC start at 0
        boolean flag1 = ((row >= 0) && (row < nR));
        boolean flag2 = ((column >= 0) && (column < nC));

        boolean go = (flag1 && flag2);

        if (go) {

            boolean worked = table.getCell(row, column).isWorked();
            boolean forbidden = table.getCell(row, column).isForbidden();
            boolean mandatory = table.getCell(row, column).isMandatory();

            // Updating, cell WORKED or not
            if (aspApp.getWorked_jRadioButton().isSelected() && !forbidden) {

                byte idEmployee = table.getCell(row, column).getIdEmployee();
                // Counting idEmployee starts at 1. Position in arrayList starts at 0. Subtract 1.
                Employee employee = aspApp.getStaff().get(idEmployee - 1);

                if (worked == true) {
                    if ((rowOrigin == row) && (columnOrigin == column)) {
                        if (valueWheel == z) {
                            columnWheel = columnWheel - z;
                            if ((columnWheel >= 0) && (columnWheel < nC)) {
                                if (!table.getCell(rowWheel, columnWheel).isWorked()
                                        && !table.getCell(rowWheel, columnWheel).isForbidden()) {
                                    table.getCell(rowWheel, columnWheel).setWorked(true);
                                    employee.setHoursWorked(employee.getHoursWorked() + 0.5);
                                    aspApp.updateLabelHoursWorked();
                                    repaint();
                                }
                            } else {
                                columnWheel = columnWheel + z;
                            }
                        } else {
                            if (table.getCell(rowWheel, columnWheel).isWorked()
                                    && !table.getCell(rowWheel, columnWheel).isForbidden()) {
                                table.getCell(rowWheel, columnWheel).setWorked(false);
                                employee.setHoursWorked(employee.getHoursWorked() - 0.5);
                                aspApp.updateLabelHoursWorked();
                                repaint();
                            }

                            if (((columnWheel - z) >= 0) && ((columnWheel - z) < nC)) {
                                columnWheel = columnWheel - z;
                            }
                        }
                    } else {
                        rowOrigin = row;
                        columnOrigin = column;
                        rowWheel = row;
                        columnWheel = column;
                        valueWheel = z;
                    }
                } else {
                    if ((rowOrigin == row) && (columnOrigin == column)) {
                        if (valueWheel == z) {
                            columnWheel = columnWheel - z;
                            if ((columnWheel >= 0) && (columnWheel < nC)) {
                                if (table.getCell(rowWheel, columnWheel).isWorked()
                                        && !table.getCell(rowWheel, columnWheel).isForbidden()) {
                                    table.getCell(rowWheel, columnWheel).setWorked(false);
                                    employee.setHoursWorked(employee.getHoursWorked() - 0.5);
                                    aspApp.updateLabelHoursWorked();
                                    repaint();
                                }
                            } else {
                                columnWheel = columnWheel + z;
                            }
                        } else {
                            if (table.getCell(rowWheel, columnWheel).isWorked()
                                    && !table.getCell(rowWheel, columnWheel).isForbidden()) {
                                table.getCell(rowWheel, columnWheel).setWorked(false);
                                employee.setHoursWorked(employee.getHoursWorked() - 0.5);
                                aspApp.updateLabelHoursWorked();
                                repaint();
                            }

                            if (((columnWheel - z) >= 0) && ((columnWheel - z) < nC)) {
                                columnWheel = columnWheel - z;
                            }
                        }
                    } else {
                        rowOrigin = row;
                        columnOrigin = column;
                        rowWheel = row;
                        columnWheel = column;
                        valueWheel = z;
                    }
                }

            // Updating, cell FORBIDDEN or not
            } else if (aspApp.getForbidden_jRadioButton().isSelected()
                    && !worked && !mandatory) {
                if (forbidden == true) {
                    if ((rowOrigin == row) && (columnOrigin == column)) {
                        if (valueWheel == z) {
                            columnWheel = columnWheel - z;
                            if ((columnWheel >= 0) && (columnWheel < nC)) {
                                if (!table.getCell(rowWheel, columnWheel).isForbidden()
                                        && !table.getCell(rowWheel, columnWheel).isWorked()
                                        && !table.getCell(rowWheel, columnWheel).isMandatory()) {
                                    table.getCell(rowWheel, columnWheel).setForbidden(true);
                                    repaint();
                                }
                            } else {
                                columnWheel = columnWheel + z;
                            }
                        } else {
                            if (table.getCell(rowWheel, columnWheel).isForbidden()
                                    && !table.getCell(rowWheel, columnWheel).isWorked()
                                    && !table.getCell(rowWheel, columnWheel).isMandatory()) {
                                table.getCell(rowWheel, columnWheel).setForbidden(false);
                                repaint();
                            }

                            if (((columnWheel - z) >= 0) && ((columnWheel - z) < nC)) {
                                columnWheel = columnWheel - z;
                            }
                        }
                    } else {
                        rowOrigin = row;
                        columnOrigin = column;
                        rowWheel = row;
                        columnWheel = column;
                        valueWheel = z;
                    }
                } else {
                    if ((rowOrigin == row) && (columnOrigin == column)) {
                        if (valueWheel == z) {
                            columnWheel = columnWheel - z;
                            if ((columnWheel >= 0) && (columnWheel < nC)) {
                                if (table.getCell(rowWheel, columnWheel).isForbidden()
                                        && !table.getCell(rowWheel, columnWheel).isWorked()
                                        && !table.getCell(rowWheel, columnWheel).isMandatory()) {
                                    table.getCell(rowWheel, columnWheel).setForbidden(false);
                                    repaint();
                                }
                            } else {
                                columnWheel = columnWheel + z;
                            }
                        } else {
                            if (table.getCell(rowWheel, columnWheel).isForbidden()
                                    && !table.getCell(rowWheel, columnWheel).isWorked()
                                    && !table.getCell(rowWheel, columnWheel).isMandatory()) {
                                table.getCell(rowWheel, columnWheel).setForbidden(false);
                                repaint();
                            }

                            if (((columnWheel - z) >= 0) && ((columnWheel - z) < nC)) {
                                columnWheel = columnWheel - z;
                            }
                        }
                    } else {
                        rowOrigin = row;
                        columnOrigin = column;
                        rowWheel = row;
                        columnWheel = column;
                        valueWheel = z;
                    }
                }

            // Updating, cell MANDATORY or not
            } else if (aspApp.getMandatory_jRadioButton().isSelected() && !forbidden) {
                if (mandatory == true) {
                    if ((rowOrigin == row) && (columnOrigin == column)) {
                        if (valueWheel == z) {
                            columnWheel = columnWheel - z;
                            if ((columnWheel >= 0) && (columnWheel < nC)) {
                                if (!table.getCell(rowWheel, columnWheel).isMandatory()
                                        && !table.getCell(rowWheel, columnWheel).isForbidden()) {
                                    table.getCell(rowWheel, columnWheel).setMandatory(true);
                                    repaint();
                                }
                            } else {
                                columnWheel = columnWheel + z;
                            }
                        } else {
                            if (table.getCell(rowWheel, columnWheel).isMandatory()
                                    && !table.getCell(rowWheel, columnWheel).isForbidden()) {
                                table.getCell(rowWheel, columnWheel).setMandatory(false);
                                repaint();
                            }

                            if (((columnWheel - z) >= 0) && ((columnWheel - z) < nC)) {
                                columnWheel = columnWheel - z;
                            }
                        }
                    } else {
                        rowOrigin = row;
                        columnOrigin = column;
                        rowWheel = row;
                        columnWheel = column;
                        valueWheel = z;
                    }
                } else {
                    if ((rowOrigin == row) && (columnOrigin == column)) {
                        if (valueWheel == z) {
                            columnWheel = columnWheel - z;
                            if ((columnWheel >= 0) && (columnWheel < nC)) {
                                if (table.getCell(rowWheel, columnWheel).isMandatory()
                                        && !table.getCell(rowWheel, columnWheel).isForbidden()) {
                                    table.getCell(rowWheel, columnWheel).setMandatory(false);
                                    repaint();
                                }
                            } else {
                                columnWheel = columnWheel + z;
                            }
                        } else {
                            if (table.getCell(rowWheel, columnWheel).isMandatory()
                                    && !table.getCell(rowWheel, columnWheel).isForbidden()) {
                                table.getCell(rowWheel, columnWheel).setMandatory(false);
                                repaint();
                            }

                            if (((columnWheel - z) >= 0) && ((columnWheel - z) < nC)) {
                                columnWheel = columnWheel - z;
                            }
                        }
                    } else {
                        rowOrigin = row;
                        columnOrigin = column;
                        rowWheel = row;
                        columnWheel = column;
                        valueWheel = z;
                    }
                }
            }
        }
    }//GEN-LAST:event_tablePanelMouseWheelMoved
    
    @Override
    public void paintComponent(Graphics g) {
        if (allowDraw) {
            // it is used to draw a vanilla background
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;

            tableGraphic.drawTable(g2);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
   
    // Manual variables declaration:
    private AspApp aspApp;  
    private TableGraphic tableGraphic;
    private boolean allowDraw = false;
    
    // save previous values of the event e
    private int rowWheel;
    private int columnWheel;
    private int rowOrigin;
    private int columnOrigin;
    private int valueWheel;     
}