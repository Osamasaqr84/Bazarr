package com.bazar.bazars;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bazar.bazars.Items.Tab_one_items;
import com.bazar.bazars.Items.Tab_two_item;

import java.util.ArrayList;

/**
 * Created by AG on 3/7/2017.
 */

public class Tab_Two extends Fragment {

    ArrayList<String> mylist = new ArrayList<String>();
    public  static ArrayList<Tab_two_item> SUBsub_arrayList;

    public TabLayout tabLayout;
    public ViewPager mViewPager;



    public static Tab_Two newInstance(int position, String catid) {

        Log.i("sssssssss", String.valueOf(position));
        Tab_Two fragment = new Tab_Two();
        Bundle args = new Bundle();
        args.putInt("key", position);
        args.putString("keystring", catid);
        fragment.setArguments(args);

        return fragment;
    }

    public Tab_Two() {



    }

    @Override
    public void onResume() {
        super.onResume();

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        if (tab.getPosition() != 0) {
                            MainActivity   app = new MainActivity();

                            app.hideTabLayout();
                            Bundle bundle = getArguments();

                            int myInt = bundle.getInt("key");
                            Log.i("ssssaawww", String.valueOf(myInt));


                        }else {

                            MainActivity   app = new MainActivity();

                            app.showTabLayout();
                        }

                        Log.i("numTab", String.valueOf(tab.getPosition()));

                    }
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);


                    }


                });

    }
    public  void showTabLayout() {
        tabLayout.setVisibility(View.VISIBLE);

    }

    public  void hideTabLayout() {
        tabLayout.setVisibility(View.GONE);

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); Bundle bundle = getArguments();
        int myInt = bundle.getInt("key");
        Log.i("ssssaawww", String.valueOf(myInt));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        int myInt = bundle.getInt("key");


        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.container_main);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(),MainActivity.Cat_arrayList);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs2);


        tabLayout.setVisibility(View.GONE);

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        if (tab.getPosition() != 0) {

                            Bundle bundle = getArguments();

                            int myInt = bundle.getInt("key");
                        }else {


                        }

                        Log.i("numTab", String.valueOf(tab.getPosition()));

                    }
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                });
        if (myInt != 0 ){
            tabLayout.setVisibility(View.VISIBLE);


        }

        return view;

    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Tab_one_items> chalet_list;
        private Context context;
        private ArrayList<Tab_one_items> arraylist;
        private LayoutInflater mInflater;
        public  ArrayList<Tab_two_item> SUB_arrayList;

        private ViewPager mViewPager;
        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Tab_one_items> Cat_items_result) {
            super(fm);
            Bundle bundle = getArguments();
            int myInt = bundle.getInt("key");

            if (MainActivity.Cat_arrayList != null) {

                    SUB_arrayList = new ArrayList<Tab_two_item>();
                    SUB_arrayList.addAll(MainActivity.Cat_arrayList.get(myInt).getSubCategories());
                    SUB_arrayList.add(0, new Tab_two_item("0", "الكل",  null));
                    Log.i("SUBsub_arrayListakey١", String.valueOf(myInt));
                                            SUBsub_arrayList = new ArrayList<Tab_two_item>();

                    SUBsub_arrayList.addAll(SUB_arrayList);
                    Log.i("SUBsub_arrayList", String.valueOf(SUBsub_arrayList));
//
//                    if (SUB_arrayList.get(myInt).getSubCategoriess() != null) {
//                        if (myInt == 1 || myInt == 2) {
//                            SUBsub_arrayList = new ArrayList<Tab_two_item>();
//                            for (int photosIndex = 0; photosIndex < SUB_arrayList.size(); photosIndex++) {
//                                String names = SUB_arrayList.get(photosIndex).getName();
//                                SUBsub_arrayList.add(SUB_arrayList.get(photosIndex));
//
//                            }
//                        }
//                    }else {
//                        SUBsub_arrayList = new ArrayList<Tab_two_item>();
//                        SUBsub_arrayList.add(new Tab_two_item("تم","تم",null));
//
//
//                    }


            }
        }



        @Override
        public Fragment getItem(int position) {
            Bundle bundle = getArguments();
            int myInt = bundle.getInt("key");
            String catid = bundle.getString("keystring");
            Log.i("catid", catid);
            Log.i("posi", String.valueOf(myInt));
            if(SUB_arrayList != null){
                if(SUB_arrayList.get(position).getSubCategoriess()!=null){
                    if(SUB_arrayList.get(position).getSubCategoriess().size()>0){
                        return Tab_three.newInstance(position, myInt);
                    } else {
                        return Container_Fragment2.newInstance(Integer.parseInt(SUB_arrayList.get(position).getId()),"2");
                    }
                } else {
                    return Container_Fragment2.newInstance(Integer.parseInt(SUB_arrayList.get(position).getId()),"2");
                }
            } else {
                return Container_Fragment2.newInstance(Integer.parseInt(SUB_arrayList.get(position).getId()),"2");
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.

            Bundle bundle = getArguments();
            if (SUB_arrayList != null){

                return SUB_arrayList.size();


            }
            else {
                return  0;

            }
        }

        @Override
        public CharSequence getPageTitle(int position) {

            Bundle bundle = getArguments();
            if (SUB_arrayList != null) {
                int myInt = bundle.getInt("key");
                return SUB_arrayList.get(position).getName();

            }

            else {
                return  null;
            }





        }

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////ر




    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
