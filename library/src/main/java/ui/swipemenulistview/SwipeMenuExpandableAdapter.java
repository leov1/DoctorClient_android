package ui.swipemenulistview;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

import ui.swipemenulistview.SwipeMenuView.OnSwipeItemClickListener;

/**
 * 
 * @author baoyz
 * @date 2014-8-24
 * 
 */
public class SwipeMenuExpandableAdapter extends BaseExpandableListAdapter implements OnSwipeItemClickListener {

    private ExpandableListAdapter mAdapter;
    private Context mContext;
    private SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener;

    public SwipeMenuExpandableAdapter(Context context, ExpandableListAdapter adapter) {
        mAdapter = adapter;
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return mAdapter.getGroupCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mAdapter.getChildrenCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mAdapter.getGroup(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mAdapter.getChildId(groupPosition, childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mAdapter.getGroupId(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mAdapter.getChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return mAdapter.hasStableIds();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
      SwipeMenuLayout layout = null;
        if (convertView == null) {
            View contentView = mAdapter.getGroupView(groupPosition, isExpanded, convertView, parent);
            SwipeMenu menu = new SwipeMenu(mContext);
            menu.setViewType(getGroupType(groupPosition));
            createMenu(menu);
            SwipeMenuExpandableListView listView = (SwipeMenuExpandableListView) parent;
            SwipeMenuView menuView = new SwipeMenuView(menu, listView);
            menuView.setOnSwipeItemClickListener(this);

            layout = new SwipeMenuLayout(contentView, menuView,
                    listView.getCloseInterpolator(),
                    listView.getOpenInterpolator());
            layout.setPosition(groupPosition);
            if (contentView.getTag()!=null){
                convertView.setTag(contentView.getTag());
            }
        } else {
            layout = (SwipeMenuLayout) convertView;
            layout.closeMenu();
            layout.setPosition(groupPosition);
            View view = mAdapter.getGroupView(groupPosition, isExpanded, layout.getContentView(), parent);
        }
        if (mAdapter instanceof BaseSwipListAdapter) {
            boolean swipEnable = (((BaseSwipListAdapter) mAdapter).getSwipEnableByPosition(groupPosition));
            layout.setSwipEnable(swipEnable);
        }
        return layout;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView=mAdapter.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
        Log.e("wangxu",convertView.getTag().toString());
        return convertView ;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return mAdapter.isChildSelectable(groupPosition, childPosition);
    }

    public void createMenu(SwipeMenu menu) {
        // Test Code
        SwipeMenuItem item = new SwipeMenuItem(mContext);
        item.setTitle("Item 1");
        item.setBackground(new ColorDrawable(Color.GRAY));
        item.setWidth(300);
        menu.addMenuItem(item);

        item = new SwipeMenuItem(mContext);
        item.setTitle("Item 2");
        item.setBackground(new ColorDrawable(Color.RED));
        item.setWidth(300);
        menu.addMenuItem(item);
    }

    @Override
    public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
        if (onMenuItemClickListener != null) {
            onMenuItemClickListener.onMenuItemClick(view.getPosition(), menu,
                    index);
        }
    }

    public void setOnSwipeItemClickListener(
            SwipeMenuListView.OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

}
