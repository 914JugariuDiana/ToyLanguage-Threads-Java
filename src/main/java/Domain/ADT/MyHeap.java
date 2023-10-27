package Domain.ADT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MyHeap<K, V> implements MyIHeap<K, V>{
    HashMap<K, V> map;
    static int a = 0;

    public MyHeap(){
        map = new HashMap<K, V>();
        a = 0;
    }

    @Override
    public V lookup(K key) {
        return map.get(key);
    }

    @Override
    public void put(K k, V v) {
        map.put(k, v);
    }

    @Override
    public boolean isVarDef(K key) {
        return map.containsKey(key);
    }

    @Override
    public void update(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void delete(K key) {
        map.remove(key);
    }

    @Override
    public Set<K> getKeys() {
        return map.keySet();
    }

    static public int getNewAddr() {
        a++;
        return a;
    }

    static public void clearAddr(){
        a = 0;
    }

    public String toString() {
        StringBuilder stream = new StringBuilder();
        for (K i: getKeys())
            stream.append("    " + i.toString() + "-->" + lookup(i).toString() + "\n");
        return stream.toString();
    }
    @Override
    public void setContent(Map<K, V> m){
        map = (HashMap<K, V>) m;
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }
}
