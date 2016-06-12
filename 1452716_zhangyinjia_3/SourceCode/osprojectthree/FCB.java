/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osprojectthree;
import java.util.*;
import java.io.*;
/**
 *
 * @author deadoggy
 */
public class FCB {
    
    enum FT{Folder, Text} ;
    
    FCB(int ID, String filename, int parent)
    {
        FileID = ID;
        FileName = filename; 
        FileSize = 0;
        Parent = parent;
        SubFiles = new ArrayList<Integer>();
        StorageBlockTable = new ArrayList<Integer>();
    }
    
    public int getFileID()
    {
        return FileID;
    }
    
    public String getFileName()
    {
        return FileName;
    }
    
    public void setFileName(String filename)
    {
        FileName = filename;
    }
    
    public int getFileType()
    {
        if(FT.Text == FileType)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
    
    public void setFileType(int type)
    {
        if(0 == type)
        {
            FileType = FT.Text;
        }
        else
        {
            FileType = FT.Folder;
        }
    }
    
    public int getFileSize()
    {
        return FileSize;
    }
    
    public int getParent()
    {
        return Parent;
    }
    
    public ArrayList<Integer> getStorageBlockTable()
    {
        return StorageBlockTable;
    }
    
    public ArrayList<Integer> getSubFiles()
    {
        return SubFiles;
    }
    
    public boolean addSubFile(FCB subfile)
    {
        if(FileType.Text == FileType)
        {
            return false;
        }
        SubFiles.add(subfile.FileID);
        addSize(subfile.getFileSize());
        return true;
    }
    
    public void addSize(int size)
    {
        FileSize += size;
        if(!(0 == Parent && 0 ==  FileID))
        {
            FileManagement.getFCB(Parent).addSize(size);
        }  
    }
    public void setSize(int size)
    {
        FileSize = size;
    }
    
    public void clear(StorageBlock[] SBT, ArrayList<FCB> FCBT, boolean[] ST)
    {
        if(FT.Text == FileType)
        {
            int end = StorageBlockTable.size();
            for(int i=0; i<end; i++)
            {
                SBT[StorageBlockTable.get(i)].changeState();//delete every block
                File file = new File("disk/StorageBlocks/"+StorageBlockTable.get(i)+".bin");
                if(true == file.exists())
                {
                     file.delete();                                     
                }
                ST[StorageBlockTable.get(i)] = false;
            }
        }
        else
        {
            int end = SubFiles.size();
            for(int i=0; i<end; i++)
            {
                FileManagement.getFCB(SubFiles.get(i)).clear(SBT, FCBT,ST);
                FCBT.remove(FileManagement.getFCB(SubFiles.get(i)));
            }
            SubFiles.clear();
            StorageBlockTable.clear();
        }
        
        addSize(-FileSize);
        FileSize = 0;
        
    }
    
    private int            FileID;
    private FT             FileType;
    private String         FileName;
    private int            FileSize;
    private int            Parent;
    private ArrayList<Integer> SubFiles;
    private ArrayList<Integer> StorageBlockTable;
}
