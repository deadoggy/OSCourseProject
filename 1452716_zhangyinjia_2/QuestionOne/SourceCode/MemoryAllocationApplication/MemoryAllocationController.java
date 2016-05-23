/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MemoryAllocationApplication;

/**
 *
 * @author deadoggy
 */
public class MemoryAllocationController {

    // Link node to manage the memory block
    private final class MemoryBlock {

        public int beginPoint;
        public int endPoint;
        public int size;
        MemoryBlock next;
        MemoryBlock pre;

        public MemoryBlock(int beg, int end) {
            next = null;
            pre = null;
            beginPoint = beg;
            endPoint = end;
            if (beg >= 0 && end >= 0) {
                size = end - beg + 1;
            } else {
                size = -1;
            }
        }
    }

    // Two links to manage the memory
    private final MemoryBlock busyLink;
    private final MemoryBlock freeLink;

    // construct function
    public MemoryAllocationController() {
        // each link has an empty head node
        freeLink = new MemoryBlock(-1, -1);
        freeLink.next = new MemoryBlock(0, 639);
        freeLink.next.pre = freeLink;
        busyLink = new MemoryBlock(-1, -1);
    }

    // supply a function to outside to allocate memory
    public boolean allocateMemory(int method, int size, MemoryArrangementUnit ref) {
        if (0 == method) {
            return privateAllocateMemoryByFF(size, ref);
        } else {
            return privateAllocateMemoryByBF(size, ref);
        }
    }

    // supply a function to outside to free memory
    public boolean freeMemory(int begin, int end) {
        return privateFreeMemory(begin, end);
    }

    // real private function to allocate memory by FIFO
    private boolean privateAllocateMemoryByFF(int size, MemoryArrangementUnit ref) {
        MemoryBlock freeLinkRef = freeLink;
        MemoryBlock busyLinkRef = busyLink;
        // move to the first memory unit that meet the request
        while (null != freeLinkRef && size > freeLinkRef.size) {
            freeLinkRef = freeLinkRef.next;
        }
        if (null == freeLinkRef) //if there is no unit can meet the requirement
        {
            return false;
        }
        if (size == freeLinkRef.size) // if the size of the memory unit just equals to required size
        {
            ref.begin = freeLinkRef.beginPoint;
            ref.end = freeLinkRef.endPoint;
            //delete the memory unit from the freeLink
            freeLinkRef.pre.next = freeLinkRef.next;
            if (null != freeLinkRef.next) {
                freeLinkRef.next.pre = freeLinkRef.pre;
            }
            //add freeLinkRef to busyLink directly
            while (null != busyLinkRef.next && busyLinkRef.next.beginPoint < freeLinkRef.beginPoint) //find the pre node
            {
                busyLinkRef = busyLinkRef.next;
            }
            //insert
            if (null != busyLinkRef.next) {
                busyLinkRef.next.pre = freeLinkRef;
            }
            freeLinkRef.next = busyLinkRef.next;
            busyLinkRef.next = freeLinkRef;
            freeLinkRef.pre = busyLinkRef;
            return true;
        } else {
            ref.begin = freeLinkRef.beginPoint;
            ref.end = ref.begin + size - 1;
            //reduce the memory unit of the freeLinkR          
            freeLinkRef.beginPoint = ref.end + 1;
            freeLinkRef.size = freeLinkRef.endPoint - freeLinkRef.beginPoint + 1;
            // new a memory block and then add it to busy link
            MemoryBlock tempRef = new MemoryBlock(ref.begin, ref.end);
            while (null != busyLinkRef.next && busyLinkRef.next.beginPoint < tempRef.beginPoint) //find the pre node
            {
                busyLinkRef = busyLinkRef.next;
            }
            //insert
            if (null != busyLinkRef.next) {
                busyLinkRef.next.pre = tempRef;
            }
            tempRef.next = busyLinkRef.next;
            busyLinkRef.next = tempRef;
            tempRef.pre = busyLinkRef;
            return true;
        }
    }

    // real private function to allocate memory by LRU
    private boolean privateAllocateMemoryByBF(int size, MemoryArrangementUnit ref) {
        //   In order to support two different algriothm to be used by turn, I 
        //organize the freeLink by their address, so if I want to allocate by LRU,
        //I must use a alternative object ref "locationRef" to find the smallest 
        //memory who meet the requirement 
        MemoryBlock freeLinkRef = freeLink;
        MemoryBlock busyLinkRef = busyLink;
        MemoryBlock locationRef = null;
        boolean CanAllocate = false;
        //find the smallest memory unit
        while (null != freeLinkRef) {
            if (size <= freeLinkRef.size) {
                if (false == CanAllocate) {
                    CanAllocate = true;
                    locationRef = freeLinkRef;
                }
                if (locationRef.size >= freeLinkRef.size) {
                    locationRef = freeLinkRef;
                }

            }
            freeLinkRef = freeLinkRef.next;
        }
        //if no unit can meet requirement
        if (false == CanAllocate) {
            return false;
        }
        if (size == locationRef.size) // if the size of the memory unit just equals to required size
        {
            ref.begin = locationRef.beginPoint;
            ref.end = locationRef.endPoint;
            // delete the memory unit from freeLink
            locationRef.pre.next = locationRef.next;
            if (null != locationRef.next) {
                locationRef.next.pre = locationRef.pre;
            }
            //then add it to busyLink
            while (null != busyLinkRef.next && busyLinkRef.next.beginPoint < locationRef.beginPoint) // find the pre node of the location
            {
                busyLinkRef = busyLinkRef.next;
            }
            // insert 
            if (null != busyLinkRef.next) {
                busyLinkRef.next.pre = locationRef;
            }
            locationRef.next = busyLinkRef.next;
            busyLinkRef.next = locationRef;
            locationRef.pre = busyLinkRef;
            return true;
        } else {
            ref.begin = locationRef.beginPoint;
            ref.end = ref.begin + size - 1;
            //reduce the size of the locationRef
            locationRef.beginPoint = ref.end + 1;
            locationRef.size = locationRef.endPoint - locationRef.beginPoint + 1;
            //new a memory block and add it to busy link
            MemoryBlock tempRef = new MemoryBlock(ref.begin, ref.end);
            while (null != busyLinkRef.next && busyLinkRef.next.beginPoint < tempRef.beginPoint) //find the pre node
            {
                busyLinkRef = busyLinkRef.next;
            }
            //insert
            if (null != busyLinkRef.next) {
                busyLinkRef.next.pre = tempRef;
            }
            tempRef.next = busyLinkRef.next;
            busyLinkRef.next = tempRef;
            tempRef.pre = busyLinkRef;
            return true;
        }
    }

    // real private function to free memeory
    private boolean privateFreeMemory(int begin, int end) {
        MemoryBlock locationRef = busyLink;
        MemoryBlock freeLinkRef = freeLink;
        while (null != locationRef
                && locationRef.beginPoint != begin && locationRef.endPoint != end) {
            locationRef = locationRef.next;
        }
        //there is no busy unit meeting the requirement
        if (null == locationRef) {
            return false;
        }
        //delete it from busyLink
        locationRef.pre.next = locationRef.next;
        if (null != locationRef.next) {
            locationRef.next.pre = locationRef.pre;
        }

        //insert it to freeLink
        while (null != freeLinkRef.next && freeLinkRef.next.beginPoint < locationRef.beginPoint) {
            freeLinkRef = freeLinkRef.next;
        }
        if (null != freeLinkRef.next && freeLinkRef.next.beginPoint - 1 == locationRef.endPoint
                && freeLinkRef.endPoint != -1 && freeLinkRef.endPoint + 1 == locationRef.beginPoint) //can be combined with both sides
        {
            freeLinkRef.endPoint = freeLinkRef.next.endPoint;
            freeLinkRef.size = freeLinkRef.endPoint - freeLinkRef.beginPoint + 1;
            //delete the middle one          
            if (null != freeLinkRef.next.next) {
                freeLinkRef.next.next.pre = freeLinkRef;
            }
            freeLinkRef.next = freeLinkRef.next.next;
            return true;
        }

        if (freeLinkRef.endPoint != -1 && freeLinkRef.endPoint + 1 == locationRef.beginPoint) //can be combined with pre one
        {
            freeLinkRef.endPoint = locationRef.endPoint;
            freeLinkRef.size = freeLinkRef.endPoint - freeLinkRef.beginPoint + 1;
            return true;
        }

        if (null != freeLinkRef.next && freeLinkRef.next.beginPoint - 1 == locationRef.endPoint) //can be combined with later one
        {
            freeLinkRef.next.beginPoint = locationRef.beginPoint;
            freeLinkRef.next.size = freeLinkRef.next.endPoint - freeLinkRef.next.beginPoint + 1;
            return true;
        }

        //can not be combined with both sides
        if (null != freeLinkRef.next) {
            freeLinkRef.next.pre = locationRef;
        }
        locationRef.next = freeLinkRef.next;
        freeLinkRef.next = locationRef;
        locationRef.pre = freeLinkRef;
        return true;
    }
}
