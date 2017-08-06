package me.karimoff.memochat.memo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.karimoff.memochat.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemoListFragment extends Fragment {

    @BindView(R.id.memosRecyclerView) RecyclerView recyclerView;
    private MemoAdapter memoAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Unbinder unbinder;

    public MemoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memo_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        List<Memo> items = new ArrayList<>();

        memoAdapter = new MemoAdapter(items);

        recyclerView.setAdapter(memoAdapter);


        memoAdapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_person_white),
                "Sam Smith", new Date()) ;
        memoAdapter.addItem(ContextCompat.getDrawable(getActivity(), R.drawable.ic_person_white),
                "Bryan Adams", new Date()) ;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
