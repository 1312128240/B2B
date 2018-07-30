package car.tzxb.b2b.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class MyLvBaseAdapter<T> extends BaseAdapter {

	List<T> ts;
	Context context;
	LayoutInflater inflater;

	public MyLvBaseAdapter(Context context, List<T> ts) {
		this.ts = ts;
		this.context = context;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		return ts.size();
	}

	@Override
	public T getItem(int position) {
		return ts.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getItemView(position,convertView,parent);
	}
	
	public abstract View getItemView(int position, View convertView, ViewGroup parent);

	public void addAll(List<T> list,boolean isClearDatasource){
		if(isClearDatasource){
			ts.clear();
		}
		ts.addAll(list);
		notifyDataSetChanged();
	}
	
	public List<T> getDatasource(){
		return ts;
	}
	public LayoutInflater getInflater(){
		return inflater;
	}
	public Context getContext(){
		return context;
	}

}
