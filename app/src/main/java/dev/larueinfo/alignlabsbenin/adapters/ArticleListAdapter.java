package dev.larueinfo.alignlabsbenin.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

/**
 * @author Seth-Pharès Gnavo (sethgnavo)
 */
public abstract class ArticleListAdapter<T> extends BaseAdapter {
    private final Class<T> mModelClass;
    protected int mLayout;
    protected Activity mActivity;
    MyArray mSnapshots;

    public ArticleListAdapter(Activity activity, Class<T> modelClass, int modelLayout, Query ref) {
        this.mModelClass = modelClass;
        this.mLayout = modelLayout;
        this.mActivity = activity;
        this.mSnapshots = new MyArray(ref);
        this.mSnapshots.setOnChangedListener(new MyArray.OnChangedListener() {
            public void onChanged(EventType type, int index, int oldIndex) {
                ArticleListAdapter.this.notifyDataSetChanged();
            }
        });

    }

    public ArticleListAdapter(Activity activity, Class<T> modelClass, int modelLayout, Firebase ref) {
        this(activity, modelClass, modelLayout, (Query) ref);
    }

    public void cleanup() {
        this.mSnapshots.cleanup();
    }

    @Override
    public int getCount() {
        return this.mSnapshots.getCount();
    }

    @Override
    public T getItem(int i) {
        return this.mSnapshots.getItem(i).getValue(this.mModelClass);
    }

    public Firebase getRef(int position) {
        return this.mSnapshots.getItem(position).getRef();
    }


    @Override
    public long getItemId(int i) {
        return (long) this.mSnapshots.getItem(i).getKey().hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.mActivity.getLayoutInflater().inflate(this.mLayout, viewGroup, false);
        }

        Object model = this.mSnapshots.getItem(i).getValue(this.mModelClass);
        this.populateView(view, (T) model);
        return view;
    }

    protected abstract void populateView(View var1, T var2);

}
