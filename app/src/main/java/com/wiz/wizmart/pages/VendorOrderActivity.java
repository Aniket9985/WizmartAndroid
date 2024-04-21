package com.wiz.wizmart.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;


import com.wiz.wizmart.R;
import com.wiz.wizmart.pages.fragments.MonthalyFragment;
import com.wiz.wizmart.pages.fragments.OneDayFragment;
import com.google.android.material.tabs.TabLayout;

public class VendorOrderActivity extends AppCompatActivity {
    private ViewPager2 pager;
    private TabLayout tabLayout;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_vendor_order );
        tabLayout=findViewById ( R.id.tabLyt );
        pager=findViewById ( R.id.viewPagerLyt );
        orderAdapter=new OrderAdapter ( getSupportFragmentManager (),getLifecycle () );
        tabLayout.addTab (tabLayout.newTab ().setText ( "One Day" ) );
        tabLayout.addTab (tabLayout.newTab ().setText ( "Monthly" ) );
        pager.setAdapter ( orderAdapter );
        orderAdapter.notifyDataSetChanged ();
        pager.registerOnPageChangeCallback ( new ViewPager2.OnPageChangeCallback () {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab ( tabLayout.getTabAt ( position ) );

            }
        } );
        tabLayout.addOnTabSelectedListener ( new TabLayout.OnTabSelectedListener () {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem ( tab.getPosition () );

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                pager.setCurrentItem ( tab.getPosition () );

            }
        } );
    }

    public class OrderAdapter extends FragmentStateAdapter
    {

        public OrderAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super ( fragmentManager, lifecycle );
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position)
            {
                case 0:
                    OneDayFragment supplierName=new OneDayFragment ();
                    return  supplierName;
                case 1:
                    MonthalyFragment customerNameFagment=new MonthalyFragment ();
                    return customerNameFagment;
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return tabLayout.getTabCount ();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity ( new Intent (VendorOrderActivity.this,VendorLoginActivity.class) );
        super.onBackPressed ();
    }
}