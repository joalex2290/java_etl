/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author John A. Munoz
 */
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;

public class ETL extends JPanel
        implements ActionListener {

    JButton cargar, ejecutar;
    JTextArea log;
    JFileChooser fichero;

    public ETL() {
        super(new BorderLayout());

        //Crea el log
        log = new JTextArea(5, 20);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);
        log.append("KDD ETL ....\nCargue archivos con extension .xlsx o .xls.\n");
        JScrollPane logScrollPane = new JScrollPane(log);

        //Crea el file chooser
        fichero = new JFileChooser();
        fichero.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        cargar = new JButton("Cargar archivos...",
                createImageIcon("images/Open.gif"));
        cargar.addActionListener(this);

        ejecutar = new JButton("Ejecutar...");
        ejecutar.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cargar);
        buttonPanel.add(ejecutar);

        //Agrega el log y los botones al panel.
        add(logScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Handle open button action.
        if (e.getSource() == cargar) {
            int returnVal = fichero.showOpenDialog(ETL.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fichero.getSelectedFile();
                //This is where a real application would open the file.
                
                String fileName = file.getAbsolutePath();
                log.append("Abriendo: " + fileName + "\n");
                
            } else {
                log.append("Comando cancelado por el usuario." + "\n");
            }
            log.setCaretPosition(log.getDocument().getLength());

        }
        
        //Handle execute button action.
        if (e.getSource() == ejecutar) {
            
            File file = fichero.getSelectedFile();
            String folderPath = file.getAbsolutePath();
            
            boolean isFolder = true;
            if(file.getName().contains(".xlsx")) isFolder = false;
            
            LOGIC.Extractor lector = new LOGIC.Extractor (folderPath,isFolder,log);
            //lector.readBooks();
        }
    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = ETL.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("No se pudo encontrar el fichero: " + path);
            return null;
        }
    }

    /**
     * Crea la GUI y la mostramos.
     */
    private static void createAndShowGUI() {
        //Crea y configura la ventana principal.
        JFrame frame = new JFrame("KDD ETL 2016");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Agrega contenido a la ventana principal.
        frame.add(new ETL());

        //Muestra la ventana principal.
        frame.pack();
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
