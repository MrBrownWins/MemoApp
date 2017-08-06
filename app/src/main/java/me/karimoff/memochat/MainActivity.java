package me.karimoff.memochat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.karimoff.memochat.memo.MemoListFragment;

//MainActivity is to control all fragments and activities
public class MainActivity extends AppCompatActivity {

    ProfileFragment profileFragment = new ProfileFragment();
    ChatListFragment chatListFragment = new ChatListFragment();
    MemoListFragment memoListFragment = new MemoListFragment();

    @BindView(R.id.navigation) BottomNavigationView navigationView; //Binding with Butterknife

    //navigation menu properties. When item in navigation is selected go to proper activity
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile: //if profile is selected go to profile activity
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, profileFragment).commit();
                    return true;
                case R.id.navigation_friends: //if friends is selected go to friends list activity
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, chatListFragment).commit();
                    return true;
                case R.id.navigation_memos: //if memo is selected go to memo activity
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, memoListFragment).commit();
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //navigation item selected listener
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.content, profileFragment).commit();

    }

}
