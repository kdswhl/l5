package managers;

import models.Ticket;
import models.Venue;

import java.time.LocalDate;
import java.util.*;

public class ManagerVenue {
    private Integer currentId = 1;
    private Map<Integer, Venue> venue = new HashMap<>();
    private ArrayDeque<String> logStack = new ArrayDeque<String>();

    private ArrayList<Venue> collection = new ArrayList<Venue>();
    private ArrayList<Venue> collectionDie = new ArrayList<Venue>();
    private LocalDate lastInitTime;
    private LocalDate lastSaveTime;
    private final DumpManager dumpManager;

    public ManagerVenue(DumpManager dumpManager){
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }

    public LocalDate getLastInitTime(){return lastInitTime;}
    public LocalDate getLastSaveTime(){return lastSaveTime;}
    public ArrayList<Venue> getCollection() {return collection;}

    public Integer getFreeId(){
        currentId = 1;
        while (true)
            if (ID().contains(currentId)) {
                currentId += 1;
            } else{break;}
        return currentId;
    }

    protected LinkedList<Integer> ID() {
        var ll = new LinkedList<Integer>();
        for (var e : this.collection) {
            ll.add(e.getId());
        }
        return ll;
    }
}

