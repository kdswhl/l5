package managers;

import models.*;

import java.time.LocalDate;
import java.util.*;

public class CollectionManager {
    private Integer currentId = 1;
    private Map<Integer, Ticket> ticket = new HashMap<>();
    private ArrayDeque<String> logStack = new ArrayDeque<String>();

    private ArrayList<Ticket> collection = new ArrayList<Ticket>();
    private ArrayList<Ticket> collectionDie = new ArrayList<Ticket>();
    private LocalDate lastInitTime;
    private LocalDate lastSaveTime;
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager){
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }

    public LocalDate getLastInitTime(){return lastInitTime;}
    public LocalDate getLastSaveTime(){return lastSaveTime;}
    public ArrayList<Ticket> getCollection() {return collection;}

    public Ticket byId(Integer id){
        return ticket.get(id);
    }

    public boolean isContain(Ticket e){ return e == null||byId(e.getId())!= null;}


    public Ticket byDieId(long id) { try{for (var e:collectionDie)if (e.getId()==id)return e;return null;} catch (IndexOutOfBoundsException e) { return null; } }



    public void update() {
        Collections.sort(collection);
    }

    public boolean add(int id) {
        Ticket ret = byDieId(id);
        if (ret == null) return false;
        ticket.put(ret.getId(), ret);
        collection.add(ret);
        collectionDie.remove(ret);
        update();
        return true;
    }

    public boolean add(Ticket e){
        if (isContain(e)){
            return false;
        }
        ticket.put(e.getId(),e);
        collection.add(e);
        update();
        return true;
    }

    public boolean swap(Integer id, Integer repId) {
        var e = byId(id);
        var re = byId(repId);
        if (e == null) return false;
        if (re == null) return false;
        var ind = collection.indexOf(e);
        var rind = collection.indexOf(re);
        if (ind < 0) return false;
        if (rind < 0) return false;

        e.setId(repId);
        re.setId(id);

        ticket.put(e.getId(), e);
        ticket.put(re.getId(), re);

        // addLog("swap " + id + " " + repId , false);
        // replacement
        update();
        return true;
    }

    public boolean update(Ticket a) {
        if (!isContain(a)) return false;
        collection.remove(byId(a.getId()));
        ticket.put(a.getId(), a);
        collection.add(a);
        update();
        return true;
    }

    /**
     * Удаляет Aboba по ID
     */
    public boolean remove(Integer id) {
        var a = byId(id);
        if (a == null) return false;
        ticket.remove(a.getId());
        collection.remove(a);
        update();
        return true;
    }



    public boolean init() {
        collection.clear();
        ticket.clear();
        dumpManager.readCollection(collection);
        lastInitTime = LocalDate.now();
        for (var e : collection)
            if (byId(e.getId()) != null) {
                collection.clear();
                ticket.clear();
                return false;
            } else {
                if (e.getId()>currentId) currentId = e.getId();
                ticket.put(e.getId(), e);
            }
        update();
        return true;
    }

    public boolean loadCollection() {
        ticket.clear();
        dumpManager.readCollection(collection);
        lastInitTime = LocalDate.now();
        for (var e : collection)
            if (byId(e.getId()) != null) {
                collection.clear();
                return false;
            } else {
                if (e.getId()>currentId) currentId = e.getId();
                ticket.put(e.getId(), e);
            }
        update();
        return true;
    }

    /**
     * Сохраняет коллекцию в файл
     */
    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDate.now();
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (var aboba : collection) {
            info.append(aboba+"\n\n");
        }
        return info.toString().trim();
    }

    public Integer getFreeId(){
        while (byId(currentId) != null || byDieId(currentId) != null)
            if (++currentId < 0)
                currentId = 1;
        return currentId;
    }
}

