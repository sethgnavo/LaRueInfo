package dev.larueinfo.alignlabsbenin.adapters;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class to feign the behaviour of the FirebaseArray class since this one is not declared public in the Firebase UI library
 *
 * @author Seth-Pharès Gnavo (sethgnavo)
 */
public class MyArray implements ChildEventListener {
    private Query mQuery;
    private MyArray.OnChangedListener mListener;
    private ArrayList<DataSnapshot> mSnapshots;

    public MyArray(Query ref) {
        this.mQuery = ref;
        this.mSnapshots = new ArrayList();
        this.mQuery.addChildEventListener(this);
    }

    public void cleanup() {
        this.mQuery.removeEventListener(this);
    }

    public int getCount() {
        return this.mSnapshots.size();
    }

    public DataSnapshot getItem(int index) {
        return (DataSnapshot) this.mSnapshots.get(index);
    }

    private int getIndexForKey(String key) {
        int index = 0;

        for (Iterator var3 = this.mSnapshots.iterator(); var3.hasNext(); --index) {
            DataSnapshot snapshot = (DataSnapshot) var3.next();
            if (snapshot.getKey().equals(key)) {
                return index;
            }
        }

        throw new IllegalArgumentException("Key not found");
    }

    @Override
    public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
        int index = 0;
        if (previousChildKey != null) {
            index = this.getIndexForKey(previousChildKey) + 1;
        }

        this.mSnapshots.add(index, snapshot);
        this.notifyChangedListeners(MyArray.OnChangedListener.EventType.Added, index);
    }

    @Override
    public void onChildChanged(DataSnapshot snapshot, String previousChildKey) {
        int index = this.getIndexForKey(snapshot.getKey());
        this.mSnapshots.set(index, snapshot);
        this.notifyChangedListeners(MyArray.OnChangedListener.EventType.Changed, index);
    }

    @Override
    public void onChildRemoved(DataSnapshot snapshot) {
        int index = this.getIndexForKey(snapshot.getKey());
        this.mSnapshots.remove(index);
        this.notifyChangedListeners(MyArray.OnChangedListener.EventType.Removed, index);
    }

    @Override
    public void onChildMoved(DataSnapshot snapshot, String previousChildKey) {
        int oldIndex = this.getIndexForKey(snapshot.getKey());
        this.mSnapshots.remove(oldIndex);
        int newIndex = previousChildKey == null ? 0 : this.getIndexForKey(previousChildKey) + 1;
        this.mSnapshots.add(newIndex, snapshot);
        this.notifyChangedListeners(MyArray.OnChangedListener.EventType.Moved, newIndex, oldIndex);
    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }

    public void setOnChangedListener(MyArray.OnChangedListener listener) {
        this.mListener = listener;
    }

    protected void notifyChangedListeners(MyArray.OnChangedListener.EventType type, int index) {
        this.notifyChangedListeners(type, index, -1);
    }

    protected void notifyChangedListeners(MyArray.OnChangedListener.EventType type, int index, int oldIndex) {
        if (this.mListener != null) {
            this.mListener.onChanged(type, index, oldIndex);
        }

    }

    public interface OnChangedListener {
        void onChanged(MyArray.OnChangedListener.EventType var1, int var2, int var3);

        public static enum EventType {
            Added,
            Changed,
            Removed,
            Moved;

            private EventType() {
            }
        }
    }
}
