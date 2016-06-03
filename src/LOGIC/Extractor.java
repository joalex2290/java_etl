/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LOGIC;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTextArea;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
;
/**
 *
 * @author John A. Munoz
 */
public class Extractor {
    
    private ArrayList<XSSFWorkbook> books;
    private Transformer transformer;
    private JTextArea log;
    
    //folderPath indica la ruta del archivo o directorio que se ha seleccionado
    //isFolder indica true si el parametro folderPath es un folder, false si es un archivo de excel
    public Extractor(String folderPath, boolean isFolder, JTextArea log)
    {
        this.transformer = new Transformer(log);
        this.books = new ArrayList();
        this.log = log;
        
        if(isFolder)
        {
            getFiles(folderPath);
        }
        else
        {
            try
            {
                File excelFile = new File(folderPath);
                XSSFWorkbook book = new XSSFWorkbook(excelFile);
                book.getProperties().getCoreProperties().setTitle(excelFile.getName());
                readBook(book);
                //this.books.add(book);
            }
            catch(Exception e)
            {
                System.out.println("Exception " + e.getMessage());
            }
        }
    }
    
    //Lee todos los archivos excel que ha seleccionado el usuario
    public void readBooks()
    {
        for (int i=0;i<books.size();i++)
        {
            readBook(books.get(i));
        }
    }
    
    private void getFiles(String folderPath)
    {
        try
        {            
            File folder = new File(folderPath);
            
            //Create filter to read just excel files
            FilenameFilter fileNameFilter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if(name.lastIndexOf('.')>0)
                    {
                        //get last index for '.' char
                        int lastIndex = name.lastIndexOf('.');
                        // get extension
                        String str = name.substring(lastIndex);

                        // match path name extension
                        if(str.equals(".xlsx"))
                        {
                            return true;
                        }
                    }
                    return false;
                }
            };
            
            File[] files = folder.listFiles(fileNameFilter);
            
            XSSFWorkbook book;
            
            for(int i=0;i<files.length;i++)
            {
                book = new XSSFWorkbook(files[i]);
                System.out.println("Leyendo" + files[i].getName());
                book.getProperties().getCoreProperties().setTitle(files[i].getName());
                readBook(book);
                //this.books.add(book);
            }
        }
        catch(Exception e)
        {
            System.out.println("Exception " + e.getMessage());
        }
    }
    
    /*
    Lee todas las hojas de un libro excel.
    Recorre hoja por hoja.
    */
    private void readBook(XSSFWorkbook book){

        String bookName = book.getProperties().getCoreProperties().getTitle();
        
        //Procesar cada una de las hojas del libro
        for (int i=0; i<book.getNumberOfSheets(); i++)
        {
            
            //Obtener la hoja actual
            XSSFSheet sheet = book.getSheetAt(i);
            
            //Obtener el nombre de la hoja actual
            readSheet(sheet, bookName);
            
            
        }
    }
    
    /*
    Recorre cada celda para obtener los valores del libro.
    Recorre cada fila, y luego cada celda de la fila.
    */
    private void readSheet(XSSFSheet sheet, String bookName)
    {
        
        
        String origen = "";
        String destino = "";
        String sheetName = sheet.getSheetName();
        
        ArrayList<String> nombreDestinos = new ArrayList();
        
        Iterator<Row> rowIterator = sheet.iterator();
        
        //Leer la primera fila de la hoja para obtener los encabezados de las celdas.
        //Obtener los nombres de las rutas
        
        Row firstRow = rowIterator.next();
        Iterator<Cell> cellIterator = firstRow.cellIterator();
        Cell cell;
        
        //Recorrer todas las celdas de la primera fila para obtener nombre de rutas destino
        while(cellIterator.hasNext())
        {
            cell = cellIterator.next();
            String nombreRuta = cell.getStringCellValue();
            nombreDestinos.add(nombreRuta);            
        }
        
        //Recorrer todas las filas de la hoja, a partir de la segunda fila
        Row row;
        while (rowIterator.hasNext())
        {
            row = rowIterator.next();
            cellIterator = row.cellIterator();
            int cant_celdas = 0;
            while(cellIterator.hasNext())
            {
                
                cell = cellIterator.next();
                if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
                {
                    destino = nombreDestinos.get(cant_celdas);
                    double valorCapturado = cell.getNumericCellValue();
                    
                    if(valorCapturado!= 0.0){
                        transformer.crearParada(destino);
                        transformer.crearDemanda(bookName, sheetName, origen, destino, valorCapturado);
                    }
                    
                }
                else if(cell.getCellType()==Cell.CELL_TYPE_STRING)
                {
                    if(cant_celdas==0)
                    {
                        origen = cell.getStringCellValue();
                        transformer.crearParada(origen);
                    }
                }
                cant_celdas++;
            }
        }
    }
    
}