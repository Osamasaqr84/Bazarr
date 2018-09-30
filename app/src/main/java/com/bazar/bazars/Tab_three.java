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
import com.bazar.bazars.Items.Tab_three_item;
import com.bazar.bazars.Items.Tab_two_item;

import java.util.ArrayList;

import static com.bazar.bazars.Tab_Two.SUBsub_arrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_three extends Fragment {
    public TabLayout tabLayout;
    public ViewPager mViewPager;
    public static Tab_three newInstance(int position,int myint) {
        Tab_three fragment = new Tab_three();
        Bundle args = new Bundle();
        args.putInt("key3", position);
        args.putInt("key", myint);

        fragment.setArguments(args);
        return fragment;
    }

    public Tab_three() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                            TabLayout vp=(TabLayout) getActivity().findViewById(R.id.tabs2);

                          vp.setVisibility(View.GONE);
                            Bundle bundle = getArguments();




                        }else {

                            TabLayout vp=(TabLayout) getActivity().findViewById(R.id.tabs2);

                            vp.setVisibility(View.VISIBLE);
                        }


                    }
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);


                    }


                });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.container_maintab3);
    SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
       mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs3);


        Bundle bundle = getArguments();

        int myInt = bundle.getInt("key3");
        if (myInt != 0) {
            tabLayout.setVisibility(View.VISIBLE);


        }
        return view;


    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Tab_one_items> chalet_list;
        private Context context;
        private ArrayList<Tab_one_items> arraylist;
        private LayoutInflater mInflater;
        private ViewPager mViewPager;
        public ArrayList<Tab_three_item> SUB_arrayListt;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);


            if (MainActivity.SUB_arrayList != null) {

                Bundle bundle = getArguments();
               SUB_arrayListt = new ArrayList<Tab_three_item>();

                int myInt = bundle.getInt("key3");
                int myInt2 = bundle.getInt("key");
                Log.e("ArrayList",MainActivity.Cat_arrayList.get(myInt2).getSubCategories().get(myInt-1).getName() );
                ArrayList<Tab_two_item> sss =    Tab_Two.SUBsub_arrayList;
                                        SUB_arrayListt.add(0, new Tab_three_item("0", "الكل"));

                SUB_arrayListt.addAll(MainActivity.Cat_arrayList.get(myInt2).getSubCategories().get(myInt-1).getSubCategoriess());
//                if (myInt != 0) {
//                    if ((Tab_Two.SUBsub_arrayList.size() != 0)) {
//                       if (Tab_Two.SUBsub_arrayList.get(myInt).getSubCategoriess().size() != 0) {
//                            SUB_arrayListt.add(0, new Tab_three_item("0", "الكل"));
//
//                            for (int photosIndex = 0; photosIndex < Tab_Two.SUBsub_arrayList.get(myInt).getSubCategoriess().size(); photosIndex++) {
//
//
//                                String names =Tab_Two.SUBsub_arrayList.get(myInt).getSubCategoriess().get(photosIndex).getName();
//
//                                SUB_arrayListt.add(Tab_Two.SUBsub_arrayList.get(myInt).getSubCategoriess().get(photosIndex));
//
//                            }
//                        } else {
//
//                        }
//                    }
//                }


            }
        }


        @Override
        public Fragment getItem(int position) {

            Bundle bundle = getArguments();

            int myInt = bundle.getInt("key3");
            int myInt2 = bundle.getInt("key");
            //            Log.i("id", String.valueOf(args.getInt("key5")));
            String catid = bundle.getString("key3string");
            Log.i("posi", String.valueOf(myInt));
            if (position == 0) {
                return Container_Fragment3.newInstance(Integer.parseInt(MainActivity.Cat_arrayList.get(myInt2).getSubCategories().get(myInt-1).getId()),"2");
            }else {
                return Container_Fragment4.newInstance(Integer.parseInt(SUB_arrayListt.get(position).getId()),"3");
            }

        }

        @Override
        public int getCount() {
            // Show 4 total pages.

            if (SUBsub_arrayList != null) {

                Bundle bundle = getArguments();  int myInt = bundle.getInt("key3");
                int myInt2 = bundle.getInt("key");

                    return SUB_arrayListt.size();


            }


            else {
                return  0;

            }
        }

        @Override
        public CharSequence getPageTitle(int position) {

            Bundle bundle = getArguments();
            if (SUBsub_arrayList != null) {
                int myInt = bundle.getInt("key3");
                int myInt2 = bundle.getInt("key");


                    return SUB_arrayListt.get(position).getName();
                }


            else {
                return  null;
            }





        }

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////ر




    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
