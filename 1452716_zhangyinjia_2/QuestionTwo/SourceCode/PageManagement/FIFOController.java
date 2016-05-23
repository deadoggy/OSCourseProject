/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PageManagement;

/**
 *
 * @author deadoggy
 */
public class FIFOController implements Controller
{
    public FIFOController(int paraInstructionSize, int paraPageSize, int paraIPP)
    {
        InstructionSize = paraInstructionSize;
        PageSize = paraPageSize;
        InstructionsPerPage = paraIPP;
        InstructionTable = new boolean[InstructionSize];
        PageTable = new int[PageSize];
        for(int j = 0; j < InstructionSize; j++)
        {
            InstructionTable[j] = false;
        }
        for (int i = 0; i < PageSize; i++) {
            PageTable[i] = -1;
        }
    }
    
    @Override
    public double runInstructions()
    {
        return privateRunInstructions();
    }
    
    private double privateRunInstructions()
    {
        //record how many times of lacking page
        int LackTimes = 0;
        //record total times of visiting
        int VisitTimes = 0;
        //mean current ins to exe
        int curIns = (int)(Math.random() * (InstructionSize-1));
        //current block on page table to replace
        int curBlock = 0;
        //how many ins left without visiting
        int remainIns = InstructionSize;
        
        //ever loop means run a ins
        while(VisitTimes < InstructionSize)
        {
            //get current instruction
            curIns = getNextIns(VisitTimes, curIns);
            //if instruction has been visited, then need to get another again
            while (true == InstructionTable[curIns]) {
                curIns = getNextIns(VisitTimes, curIns);
            }
            InstructionTable[curIns] = true;
            if(true != checkExistInPageTable(curIns))
                // lack pages
            {
                //add 1 to Lack time
                LackTimes++;
                //replace the block
                PageTable[curBlock] = curIns / InstructionsPerPage;
                //set next block to replace
                curBlock = (curBlock + 1)%PageSize;
            }
            VisitTimes++;
        }
        return (double)(LackTimes)/(double)(VisitTimes);
    }
    
        private int getNextIns(int VisitTimes, int CurIns) {
        int ret = 0;
        int half = InstructionSize / 2;
        //if at the first to run ins
        if (-1 == CurIns) {
            return 0;
        }
        if (1 == VisitTimes % 2) {
            return CurIns + 1;
        }
        
        if( (double)(InstructionSize-VisitTimes)/(double)InstructionSize >= 0.04 )
        {
            ret = (int)((InstructionSize - 1) * Math.random());
            if(1 == ret%2)
                ret--;
        }
        else
            //when most part of instructions has been visited, it will cost a very long time to
            //find a non-true instruction,so in this case, I will not use random, but find a false 
            //instruction one by one
        {
            for(int i = 0; i < InstructionSize; i++)
            {
                if(false == InstructionTable[i])
                {
                    ret = i;
                    break;
                }
            }
        }   
        return ret;
    }
    private boolean checkExistInPageTable(int InsIndex)
    {
        for(int i = 0; i < PageSize; i++)
        {
            if( PageTable[i] == InsIndex/InstructionsPerPage)
                return true;
        }
        return false;
    }
    private boolean [] InstructionTable;
    private int [] PageTable;
    private int InstructionSize;
    private int PageSize;
    private int InstructionsPerPage;
}
