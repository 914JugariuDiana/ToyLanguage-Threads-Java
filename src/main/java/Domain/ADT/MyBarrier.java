package Domain.ADT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyBarrier<K, V> implements MyIDict<K, V>{

    Map<K, V> barrier;

    public MyBarrier(){
        barrier = new HashMap<K, V>();
    }

    @Override
    public synchronized V lookup(K key) {
        return barrier.get(key);
    }

    @Override
    public synchronized void put(K k, V v) {
        barrier.put(k, v);
    }

    @Override
    public synchronized boolean isVarDef(K key) {
        return false;
    }

    @Override
    public synchronized void update(K key, V value) {

    }

    @Override
    public synchronized void delete(K key) {

    }

    @Override
    public synchronized Set<K> getKeys() {
        return null;
    }

    @Override
    public synchronized Map<K, V> getContent() {
        return null;
    }
}
