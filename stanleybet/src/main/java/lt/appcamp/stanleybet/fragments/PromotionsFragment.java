package lt.appcamp.stanleybet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import lt.appcamp.stanleybet.R;
import lt.appcamp.stanleybet.activities.PromotionsActivity;

/**
 * Created by cauac on 05/03/14.
 */
public class PromotionsFragment extends Fragment {
    public static final String ARG_OBJECT = "sb_promotion";
    private List<String> mPromotionsStringList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout rootView;

        if (getArguments().getInt(PromotionsFragment.ARG_OBJECT) == 1) {
            rootView = (LinearLayout) inflater.inflate(R.layout.fragment_promotions_list, container, false);

            ListView promotionsList = (ListView) rootView.findViewById(R.id.promotions_list);
            mPromotionsStringList = new ArrayList<String>();
            mPromotionsStringList.add("First");
            mPromotionsStringList.add("Second");
            mPromotionsStringList.add("Third");
            mPromotionsStringList.add("Fourth");
            mPromotionsStringList.add("Fifth");
            promotionsList.setAdapter(new PromotionsAdapter(getActivity().getBaseContext(), mPromotionsStringList));
            promotionsList.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ((PromotionsActivity) getActivity()).onClickPromotion(view);
                }
            });
        }
        else
            rootView = (LinearLayout) inflater.inflate(R.layout.fragment_promotions_detail, container, false);

        return rootView;
    }

    private class PromotionsAdapter extends ArrayAdapter<String> {

        public PromotionsAdapter(Context context, List<String> promotionsStringList) {
            super(context, R.layout.promotions_list_item, R.id.txt_promotions_item, promotionsStringList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View v = (LinearLayout) inflater.inflate(R.layout.promotions_list_item, parent, false);
//            ImageView menuItemImage = (ImageView) v.findViewById(R.id.img_menu_item);
//            if (position == 0) { // || position == 8) {
//                menuItemImage.setVisibility(View.VISIBLE);
//                menuItemImage.setBackgroundResource(R.drawable.user_anonymous);
//                ((TextView) v.findViewById(R.id.txt_menu_item)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//            }
////            else {
////                menuItemImage.setBackgroundResource(mMenuItemImg[position]);
////            }
//            ((TextView) v.findViewById(R.id.txt_menu_item)).setText(mMenuItems[position]);
//            AppFont.setCustomFont(v, getAssets(), "light");
//
//            //if (mSelectedMenuPos == position) {
//            //v.setBackgroundResource(R.color.semi_transparent_black_25);
//            //v.findViewById(R.id.img_menu_item_selected).setBackgroundResource(R.drawable.menu_item_elipse_selected);
//            //}
//
//            if (position == 8) {
//                v.setBackgroundResource(R.drawable.sb_list_bottom);
//                //v.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            }

            return v;
        }
    }
}

