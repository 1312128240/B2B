package car.tzxb.b2b.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import car.myrecyclerviewadapter.CommonAdapter;
import car.myrecyclerviewadapter.base.ViewHolder;
import car.tzxb.b2b.Bean.OrderBeans.OrderHeader;
import car.tzxb.b2b.Bean.OrderBeans.OrderItem;
import car.tzxb.b2b.Bean.ShopCarBean;
import car.tzxb.b2b.MyApp;
import car.tzxb.b2b.R;

/**
 * Created by Administrator on 2017/3/26.
 * 购物车适配器
 */

public class ShopcatAdapter extends BaseExpandableListAdapter {
    private List<OrderHeader> groups;
    //这个String对应着StoreInfo的Id，也就是店铺的Id
    private Map<String, List<OrderItem>> childrens;
    private Context mcontext;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private GroupEditorListener groupEditorListener;
    private int count = 0;
    private boolean flag = true; //组的编辑按钮是否可见，true可见，false不可见




    public ShopcatAdapter(List<OrderHeader> groups, Map<String, List<OrderItem>> childrens, Context mcontext) {
        this.groups = groups;
        this.childrens = childrens;
        this.mcontext = mcontext;
    }


    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupId = groups.get(groupPosition).getId();
        return childrens.get(groupId).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<OrderItem> childs = childrens.get(groups.get(groupPosition).getId());
        return childs.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.order_header_item, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.storeCheckBox.setVisibility(View.VISIBLE);
        groupViewHolder.ll_header_parent.setBackgroundColor(Color.parseColor("#f5f5f5"));
        final OrderHeader group = (OrderHeader) getGroup(groupPosition);
        groupViewHolder.storeName.setText(group.getName());
        //订单优惠
        String special_promotion = group.getSpecial_promotion();
        if ("".equals(special_promotion)) {
            groupViewHolder.tv_ms.setVisibility(View.GONE);
        } else {
            groupViewHolder.tv_ms.setVisibility(View.VISIBLE);
            groupViewHolder.tv_ms.setText(special_promotion);
        }


        groupViewHolder.storeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group.setChoosed(((CheckBox) v).isChecked());
                checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());
            }
        });
        groupViewHolder.storeCheckBox.setChecked(group.isChoosed());

        /**【文字指的是组的按钮的文字】
         * 当我们按下ActionBar的 "编辑"按钮， 应该把所有组的文字显示"编辑",并且设置按钮为不可见
         * 当我们完成编辑后，再把组的编辑按钮设置为可见
         * 不懂，请自己操作淘宝，观察一遍
         */
        if (group.isActionBarEditor()) {
            group.setEditor(false);
            groupViewHolder.storeEdit.setVisibility(View.GONE);
            flag = false;
        } else {
            flag = true;
            groupViewHolder.storeEdit.setVisibility(View.VISIBLE);
        }

        /**
         * 思路:当我们按下组的"编辑"按钮后，组处于编辑状态，文字显示"完成"
         * 当我们点击“完成”按钮后，文字显示"编辑“,组处于未编辑状态
         */
        if (group.isEditor()) {
            groupViewHolder.storeEdit.setText("完成");
        } else {
            groupViewHolder.storeEdit.setText("编辑");
        }

        groupViewHolder.storeEdit.setOnClickListener(new GroupViewClick(group, groupPosition, groupViewHolder.storeEdit));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = View.inflate(mcontext, R.layout.item_shopcat_product, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        /**
         * 根据组的编辑按钮的可见与不可见，去判断是组对下辖的子元素编辑  还是ActionBar对组的下瞎元素的编辑
         * 如果组的编辑按钮可见，那么肯定是组对自己下辖元素的编辑
         * 如果组的编辑按钮不可见，那么肯定是ActionBar对组下辖元素的编辑
         */

        if (flag) {
            if (groups.get(groupPosition).isEditor()) {
                childViewHolder.editGoodsData.setVisibility(View.VISIBLE);
                childViewHolder.delGoods.setVisibility(View.VISIBLE);
                childViewHolder.goodsData.setVisibility(View.GONE);
            } else {
                childViewHolder.delGoods.setVisibility(View.VISIBLE);
                childViewHolder.goodsData.setVisibility(View.VISIBLE);
                childViewHolder.editGoodsData.setVisibility(View.GONE);
            }
        } else {

            if (groups.get(groupPosition).isActionBarEditor()) {
                childViewHolder.delGoods.setVisibility(View.GONE);
                childViewHolder.editGoodsData.setVisibility(View.VISIBLE);
                childViewHolder.goodsData.setVisibility(View.GONE);
            } else {
                childViewHolder.delGoods.setVisibility(View.VISIBLE);
                childViewHolder.goodsData.setVisibility(View.VISIBLE);
                childViewHolder.editGoodsData.setVisibility(View.GONE);
            }
        }

        final OrderItem child = (OrderItem) getChild(groupPosition, childPosition);
        if (child != null) {
            childViewHolder.goodsPrice.setText("￥" + child.getPrice() + "");
            childViewHolder.goodsNum.setText(String.valueOf(child.getCount()));
            childViewHolder.goodsName.setText(child.getName());
            Glide.with(MyApp.getContext()).load(child.getImageUrl()).into(childViewHolder.goodsImage);
            childViewHolder.goodsBuyNum.setText("x" + child.getCount() + "");
            childViewHolder.singleCheckBox.setChecked(child.isChoosed());
            //优惠信息
            childViewHolder.tv_discounts_hint.setText(child.getMotion_type());
            childViewHolder.tv_discounts_hint_content.setText(child.getChild_title());
            List<ShopCarBean.DataBean.DataChildBean.GiftBean> giftBeanList = child.getGift();
            if ("0".equals(child.getMotion_id())) {
                childViewHolder.rl_discoutns_parent.setVisibility(View.GONE);
            } else if (giftBeanList == null || giftBeanList.size() == 0) {
                childViewHolder.ll_discounts.setVisibility(View.GONE);
            } else {
                childViewHolder.ll_discounts.setVisibility(View.VISIBLE);
                initDiscouns(giftBeanList, childViewHolder.recy_discounts);
            }


            childViewHolder.tv_modify_discounts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.modify_discounts(child.getShop_id(), child.getAid());
                }
            });

            childViewHolder.singleCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    child.setChoosed(((CheckBox) v).isChecked());
                    childViewHolder.singleCheckBox.setChecked(((CheckBox) v).isChecked());
                    checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());
                }
            });
            childViewHolder.increaseGoodsNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doIncrease(groupPosition, childPosition, childViewHolder.goodsNum, childViewHolder.singleCheckBox.isChecked());
                }
            });
            childViewHolder.reduceGoodsNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.doDecrease(groupPosition, childPosition, childViewHolder.goodsNum, childViewHolder.singleCheckBox.isChecked());
                }
            });
            childViewHolder.goodsNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //showDialog(groupPosition,childPosition,childViewHolder.goodsNum,childViewHolder.singleCheckBox.isChecked(),child);
                }
            });
            childViewHolder.delGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifyCountInterface.childDelete(groupPosition, childPosition, child.getAid());
                }
            });

        }
        return convertView;
    }

    private void initDiscouns(List<ShopCarBean.DataBean.DataChildBean.GiftBean> giftBeanList, RecyclerView recy) {
        recy.setLayoutManager(new LinearLayoutManager(MyApp.getContext()));
        CommonAdapter<ShopCarBean.DataBean.DataChildBean.GiftBean> adapter = new CommonAdapter<ShopCarBean.DataBean.DataChildBean.GiftBean>(MyApp.getContext(), R.layout.my_gold_sign_item, giftBeanList) {
            @Override
            protected void convert(ViewHolder holder, ShopCarBean.DataBean.DataChildBean.GiftBean giftBean, int position) {
                holder.getView(R.id.ll_gold_sign).setVisibility(View.GONE);
                holder.getView(R.id.ll_dicounts).setVisibility(View.VISIBLE);
                //名字
                holder.setText(R.id.tv_discounts_content, giftBean.getZp_title());
                //数量
                holder.setText(R.id.tv_discounts_num, "x" + giftBean.getZp_numbers());
            }
        };
        recy.setAdapter(adapter);
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    public GroupEditorListener getGroupEditorListener() {
        return groupEditorListener;
    }

    public void setGroupEditorListener(GroupEditorListener groupEditorListener) {
        this.groupEditorListener = groupEditorListener;
    }

    public CheckInterface getCheckInterface() {
        return checkInterface;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public ModifyCountInterface getModifyCountInterface() {
        return modifyCountInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }


    static class GroupViewHolder {
        @BindView(R.id.cb_item)
        CheckBox storeCheckBox;
        @BindView(R.id.tv_order_status_shop_name)
        TextView storeName;
        @BindView(R.id.tv_order_status_describe)
        TextView storeEdit;
        @BindView(R.id.order_header_parent)
        LinearLayout ll_header_parent;
        @BindView(R.id.tv_special_promotion)
        TextView tv_ms;

        public GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 店铺的复选框
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素的位置
         * @param isChecked     组元素的选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变触发的事件
         *
         * @param groupPosition 组元素的位置
         * @param childPosition 子元素的位置
         * @param isChecked     子元素的选中与否
         */
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }


    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素的位置
         * @param childPosition 子元素的位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        void doUpdate(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删除子Item
         *
         * @param groupPosition
         * @param childPosition
         */
        void childDelete(int groupPosition, int childPosition, String aid);
        //修改优惠

        void modify_discounts(String shopId, String aid);
    }

    /**
     * 监听编辑状态
     */
    public interface GroupEditorListener {
        void groupEditor(int groupPosition);
    }

    /**
     * 使某个小组处于编辑状态
     */
    private class GroupViewClick implements View.OnClickListener {
        private OrderHeader group;
        private int groupPosition;
        private TextView editor;

        public GroupViewClick(OrderHeader group, int groupPosition, TextView editor) {
            this.group = group;
            this.groupPosition = groupPosition;
            this.editor = editor;
        }

        @Override
        public void onClick(View v) {
            if (editor.getId() == v.getId()) {
                groupEditorListener.groupEditor(groupPosition);
                if (group.isEditor()) {
                    group.setEditor(false);
                } else {
                    group.setEditor(true);
                }
                notifyDataSetChanged();
            }
        }
    }


    static class ChildViewHolder {
        @BindView(R.id.single_checkBox)
        CheckBox singleCheckBox;
        @BindView(R.id.goods_image)
        ImageView goodsImage;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.goods_buyNum)
        TextView goodsBuyNum;
        @BindView(R.id.goods_data)
        LinearLayout goodsData;
        @BindView(R.id.iv_subtract)
        ImageView reduceGoodsNum;
        @BindView(R.id.iv_plus)
        ImageView increaseGoodsNum;
        @BindView(R.id.tv_show_number)
        TextView goodsNum;
        @BindView(R.id.iv_del_item)
        ImageView delGoods;
        @BindView(R.id.ll_more_or_less)
        LinearLayout editGoodsData;
        @BindView(R.id.ll_shopingcar_discounts)
        LinearLayout ll_discounts;
        @BindView(R.id.recy_discounts)
        RecyclerView recy_discounts;
        @BindView(R.id.tv_shoppingcar_discounts_hint)
        TextView tv_discounts_hint;
        @BindView(R.id.rl_shoppingcar_discounts_layout)
        RelativeLayout rl_discoutns_parent;
        @BindView(R.id.tv_shoppingcar_discounts_hint_content)
        TextView tv_discounts_hint_content;
        @BindView(R.id.tv_modify_disconts)
        TextView tv_modify_discounts;

        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
