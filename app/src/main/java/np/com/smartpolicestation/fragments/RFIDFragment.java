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
import np.com.smartpolicestation.adapter.RFIDAdapter;
import np.com.smartpolicestation.listeners.MyChildEventListener;
import np.com.smartpolicestation.models.RFID;

public class RFIDFragment extends Fragment {

    private RFIDAdapter rfidAdapter;
    private RecyclerView recyclerRFID;
    private List<RFID> rfidList = new ArrayList<>();
    private DatabaseReference rfidReference = FirebaseDatabase.getInstance().getReference("rfid");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rfid, container, false);

        recyclerRFID = v.findViewById(R.id.recyclerRFID);
        recyclerRFID.setLayoutManager(new LinearLayoutManager(getActivity()));

        rfidAdapter = new RFIDAdapter(getActivity(), rfidList);
        recyclerRFID.setAdapter(rfidAdapter);

        rfidReference.addChildEventListener(new MyChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                rfidList.add(dataSnapshot.getValue(RFID.class));
                rfidAdapter.notifyDataSetChanged();
            }
        });

        return v;
    }

}
