package np.com.smartpolicestation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import np.com.smartpolicestation.R;
import np.com.smartpolicestation.adapter.EqAdapter;
import np.com.smartpolicestation.listeners.MyChildEventListener;
import np.com.smartpolicestation.models.EQ;

public class EQFragment extends Fragment {

    private EqAdapter eqAdapter;
    private RecyclerView recyclerEQ;
    private List<EQ> eqList = new ArrayList<>();
    private DatabaseReference eqReference = FirebaseDatabase.getInstance().getReference("eq");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_eq, container, false);

        recyclerEQ = v.findViewById(R.id.recyclerEQ);
        recyclerEQ.setLayoutManager(new LinearLayoutManager(getActivity()));

        eqAdapter = new EqAdapter(getActivity(), eqList);
        recyclerEQ.setAdapter(eqAdapter);

        eqReference.addChildEventListener(new MyChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                eqList.add(dataSnapshot.getValue(EQ.class));
                eqAdapter.notifyDataSetChanged();
            }
        });
        return v;
    }
}