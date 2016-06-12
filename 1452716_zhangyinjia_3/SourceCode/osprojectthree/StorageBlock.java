/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osprojectthree;

/**
 *
 * @author deadoggy
 */
public class StorageBlock {
    
    public StorageBlock()
    {
        State = false;
        Index = -1;
        Text = new StringBuilder();
    }
    
    public boolean getState()
    {
        return State;
    }
    
    public void changeState()
    {
        if(true == State)
        {
            Text.delete(0, Text.length());
            FileObj = null;
        }
        State = !State;
    }
    
    public int getIndex()
    {
        return Index;        
    }
    
    public boolean setIndex(int index)
    {
        if(-1 == Index)
        {
            Index = index;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public FCB getFileObj()
    {
        return FileObj;
    }
    
    public void setFileObj(FCB file)
    {
        FileObj = file;
    }
    
    public String write(String text)
    {
        State = true;
        int end;
        char[] temp = new char[1];
        StringBuilder textBuilder = new StringBuilder(text);
        if(text.length() > 100)
        {
            end = 100;
        }
        else
        {
            end = text.length();
        }
        Text.delete(0, Text.length());
        for(int i =0; i < end; i++)
        {
            textBuilder.getChars(i, i+1, temp, 0);
            Text.append(temp[0]);
        }
        
        textBuilder.delete(0, end);
        return textBuilder.toString();
    }
    
    public String read()
    {
        return Text.toString();
    }
    
    public static int Size = 16;
    private boolean State;
    private int Index;
    private FCB FileObj;
    private StringBuilder Text;
}
