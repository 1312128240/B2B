package car.myrecyclerviewadapter;

import android.content.Context;
import android.view.LayoutInflater;


import java.util.List;

import car.myrecyclerviewadapter.base.ItemViewDelegate;
import car.myrecyclerviewadapter.base.ViewHolder;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>
{
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    //增加
    public void add(List<T> datas, boolean isClean){
        if(isClean){
            mDatas.clear();
        }
        mDatas.addAll(datas);

        notifyDataSetChanged();
    }
    //删除
    public void del(List<T> datas, int position){
        datas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0,datas.size());
    }
    public CommonAdapter(final Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }


            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);

}
