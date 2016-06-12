/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package osprojectthree;

import java.util.*;
import java.lang.*;
import java.io.*;

/**
 *
 * @author deadoggy
 */
public class FileManagement {

    public FileManagement() {
        FCBTable = new ArrayList<FCB>();
        // FCB T = new FCB(0,"root",0);
        //FCBTable.add(T);

        StorageTable = new StorageBlock[BlockSize];
        StorageState = new boolean[BlockSize];
        SBSize = BlockSize;
        for (int i = 0; i < BlockSize; i++) {
            StorageTable[i] = new StorageBlock();
            StorageTable[i].setIndex(i);
            StorageState[i] = false;
        }
        FCBSize = 0;
    }

    public void format(int fileid) //格式化
    {
        FCB fcb = FileManagement.getFCB(fileid);
        FCBSize -= fcb.getSubFiles().size();
        fcb.clear(StorageTable, FCBTable, StorageState);

    }

    public int getFCBSize() {
        return FCBSize;
    }

    public void createFolder(String filename, int fileid) {
        FCB NewFile = new FCB(fileid, filename, CurrentFolder.getFileID());
        NewFile.setFileType(1);
        FCBTable.add(NewFile);
        CurrentFolder.addSubFile(NewFile);
        FCBSize++;
    }

    public void createFile(String filename, int fileid) {
        FCB NewFile = new FCB(fileid, filename, CurrentFolder.getFileID());
        NewFile.setFileType(0);
        FCBTable.add(NewFile);
        CurrentFolder.addSubFile(NewFile);
        FCBSize++;
    }

    public void deleteFile(int fileid) {
        FCB file = FileManagement.getFCB(fileid);
        ArrayList<Integer> SubFiles = CurrentFolder.getSubFiles();
        for (int i = 0; i < SubFiles.size(); i++) {
            if (file.getFileID() == SubFiles.get(i).intValue()) {
                SubFiles.remove(i);
                break;
            }
        }
        file.clear(StorageTable, FCBTable, StorageState);
        FCBTable.remove(file);// remove from global index
        FCBSize--;
    }

    public String readFile(int fileid) {
        FCB file = FileManagement.getFCB(fileid);
        StringBuilder text = new StringBuilder();
        ArrayList<Integer> SBT = file.getStorageBlockTable();
        int end = SBT.size();
        for (int i = 0; i < end; i++) {
            text.append(StorageTable[SBT.get(i)].read());
        }
        return text.toString();
    }

    public int getFreeBlock() {
        for (int i = 0; i < BlockSize; i++) {
            if (false == StorageState[i]) {
                return i;
            }
        }
        return -1;
    }

    public boolean writeFile(FCB file, String text) {
        int i;
        ArrayList<Integer> sbt = file.getStorageBlockTable();
        int end_sb = sbt.size();
        if(0==text.length())
        {
            i = -1;
        }
        else
        {
            for (i = 0; i < end_sb; i++) {
            text = StorageTable[sbt.get(i)].write(text);
            if (0 == text.length()) {
                break;
            }
            }
        }
        
        if (i == end_sb) {
            int block = getFreeBlock();
            int externsize = 0;
            while (-1 != block) {
                file.getStorageBlockTable().add(block);
                text = StorageTable[block].write(text);
                StorageTable[block].setFileObj(file);
                StorageState[block] = true;
                externsize++;
                if (0 == text.length()) {
                    file.addSize(externsize);
                    return true;
                }
                block = getFreeBlock();
            }
            return false;
        } else {
            int less = i + 1 - end_sb;
            if (0 == less) {
                return true;
            }
            for (int j = 1; j <= -less; j++) {
                StorageTable[sbt.get(1 + i)].changeState();
                StorageState[sbt.get(1 + i)] = false;
                sbt.remove(i + 1);
            }
            file.addSize(less);
            return true;
        }
    }

    public void load() {
        //load storage block
        for (int i = 0; i < BlockSize; i++) {
            File file = new File("disk/StorageBlocks/" + i + ".bin");
            if (true == file.exists()) {
                try {
                    StringBuilder sb = new StringBuilder();
                    StorageState[i] = true;
                    Reader reader = new InputStreamReader(new FileInputStream(file));
                    int tempchar;
                    while (-1 != (tempchar = reader.read())) {
                        sb.append((char) tempchar);
                    }
                    StorageTable[i].write(sb.toString());
                    reader.close();
                } catch (Exception e) {
                    return;
                }
            }

        }
        //load fcb
        load_fcb();
    }

    public void store() {

        //Write fcb
        try {
            File file = new File("disk/FCBFile/FCB.bin");
            if (false == file.exists()) {
                boolean res = file.mkdir();
            }
            Writer fw = new OutputStreamWriter(new FileOutputStream(file));
            int end = FCBTable.size();

            for (int i = 0; i < end; i++) {
                FCB fcb = FCBTable.get(i);

                //File name
                fw.write(fcb.getFileName() + "\n");
                //ID
                Integer temp = new Integer(fcb.getFileID());
                fw.write(temp.toString() + '\n');
                //file parent
                int pare = fcb.getParent();
                if (-1 != pare) {
                    temp = new Integer(pare);
                    fw.write(temp.toString() + '\n');
                } else {
                    fw.write("-1\n");
                }
                //file type
                if (0 == fcb.getFileType()) {
                    fw.write("text" + '\n');
                } else {
                    fw.write("folder" + '\n');
                }
                //file size
                temp = new Integer(fcb.getFileSize());
                fw.write(temp.toString() + '\n');

                //subfiles
                ArrayList<Integer> subfiles = fcb.getSubFiles();
                if (null != subfiles) {
                    int end_sub = subfiles.size();
                    temp = new Integer(fcb.getSubFiles().size());
                    fw.write(temp.toString() + '\n');
                    for (int j = 0; j < end_sub; j++) {
                        temp = new Integer(subfiles.get(j));
                        fw.write(temp.toString() + '\n');
                    }
                }

                //storage block
                ArrayList<Integer> storage = fcb.getStorageBlockTable();
                if (null != storage) {
                    int end_sb = storage.size();
                    temp = new Integer(fcb.getStorageBlockTable().size());
                    fw.write(temp.toString() + '\n');
                    for (int j = 0; j < end_sb; j++) {
                        temp = new Integer(StorageTable[storage.get(j)].getIndex());
                        fw.write(temp.toString() + '\n');

                    }
                }
            }
            fw.close();
        } catch (Exception e) {
            return;
        }
        //write storage block
        //first delete all existed file
        //then write
        for (int i = 0; i < BlockSize; i++) {
            File file = new File("disk/StorageBlocks/", Integer.toString(i) + ".bin");
            if (true == file.exists()) {
                file.delete();
            }
            if (true == StorageState[i]) {
                try {
                    file.createNewFile();
                    Writer fw = new OutputStreamWriter(new FileOutputStream(file));
                    fw.write(StorageTable[i].read());
                    fw.close();
                } catch (Exception e) {
                    return;
                }
            }
        }

    }

    public static FCB getFCB(Integer ID) {
        for (int i = 0; i < FCBTable.size(); i++) {
            if (ID == FCBTable.get(i).getFileID()) {
                return FCBTable.get(i);
            }
        }
        return null;
    }

    public FCB getCurrentFolder() {
        return CurrentFolder;
    }

    public void setCurrentFolder(FCB fcb) {
        CurrentFolder = fcb;
    }

    private void load_fcb() {
        FCB ret = null;
        File file = new File("disk/FCBFile/FCB.bin");
        try {
            Reader reader = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(reader);
            //get fcb name
            String temp = null;
            int fcbsize = 0;
            while (null != (temp = br.readLine())) {
                fcbsize++;
                int id = Integer.parseInt(br.readLine());
                int pare = Integer.parseInt(br.readLine());
                //initialize a fcb
                FCB fcb = new FCB(id, temp, pare);
                //get type
                int type;
                String ex = br.readLine();
                if (ex.equalsIgnoreCase("text")) {
                    type = 0;
                } else {
                    type = 1;
                }
                fcb.setFileType(type);
                //get size
                int size = Integer.parseInt(br.readLine());
                fcb.setSize(size);
                //get subfiles
                int lines = Integer.parseInt(br.readLine());

                for (int i = 0; i < lines; i++) {
                    fcb.getSubFiles().add(Integer.parseInt(br.readLine()));
                }
                //get storage blocks
                lines = Integer.parseInt(br.readLine());
                for (int i = 0; i < lines; i++) {
                    fcb.getStorageBlockTable().add(Integer.parseInt(br.readLine()));
                }
                FCBTable.add(fcb);
                temp = null;
            }
            FCBSize = fcbsize;
            reader.close();
        } catch (Exception e) {
            return;
        }

    }

    private static final int BlockSize = 1024;
    private FCB CurrentFolder;
    private static ArrayList<FCB> FCBTable;
    private StorageBlock[] StorageTable;
    private boolean[] StorageState;
    private int FCBSize;
    private static int SBSize;
}
