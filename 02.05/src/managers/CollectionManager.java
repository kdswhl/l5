package managers;

import models.*;

import java.time.LocalDate;
import java.util.*;


/**
 * Класс для управления коллекцией билетов (Ticket).
 * Обеспечивает операции добавления, удаления, обновления, сортировки, а также взаимодействие с хранилищем.
 */
public class CollectionManager {
    private Integer currentId = 1;
    private Map<Integer, Ticket> ticket = new HashMap<>();
    private ArrayDeque<String> logStack = new ArrayDeque<String>();

    private ArrayList<Ticket> collection = new ArrayList<Ticket>();
    private ArrayList<Ticket> collectionDie = new ArrayList<Ticket>();
    private LocalDate lastInitTime;
    private LocalDate lastSaveTime;
    private final DumpManager dumpManager;


    /**
     * Конструктор менеджера коллекции.
     * @param dumpManager менеджер для сериализации и десериализации данных.
     */
    public CollectionManager(DumpManager dumpManager){
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }

    public LocalDate getLastInitTime(){return lastInitTime;}
    public LocalDate getLastSaveTime(){return lastSaveTime;}
    public ArrayList<Ticket> getCollection() {return collection;}
    /**
     * Получить билет по ID.
     * @param id идентификатор билета.
     * @return найденный билет или null.
     */
    public Ticket byId(Integer id){
        return ticket.get(id);
    }

    public boolean isContain(Ticket e){ return e == null||byId(e.getId())!= null;}


    public Ticket byDieId(long id) { try{for (var e:collectionDie)if (e.getId()==id)return e;return null;} catch (IndexOutOfBoundsException e) { return null; } }


    /**
     * Обновляет порядок коллекции, сортируя ее.
     */
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
        for (var i : collection) {
            info.append(i+"\n\n");
        }
        return info.toString().trim();
    }

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


    public Integer getFreeIdVenue(){
        currentId = 1;
        while (true)
            if (IDVenue().contains(currentId)) {
                currentId += 1;
            } else{break;}
        return currentId;
    }

    protected LinkedList<Integer> IDVenue() {
        var ll = new LinkedList<Integer>();
        for (var e : this.collection) {
            ll.add(e.getVenue().getId());
        }
        return ll;
    }
}

