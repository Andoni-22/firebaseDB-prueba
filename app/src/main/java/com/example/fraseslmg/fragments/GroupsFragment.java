package com.example.fraseslmg.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fraseslmg.R;
import com.example.fraseslmg.adapters.GroupsAdapter;
import com.example.fraseslmg.objetos.Groups;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsFragment extends Fragment {

    private View root;
    ListView listViewGroups;
    List<Groups> list;


    public GroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_groups, container, false);

        listViewGroups = root.findViewById(R.id.ListViewGroup);

        GroupsAdapter adapter = new GroupsAdapter(getContext(), GetData());
        listViewGroups.setAdapter(adapter);
        listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Groups g = list.get(position);
            }
        });

        return root;
    }

    private List<Groups> GetData() {
        list = new ArrayList<>();
        list.add(new Groups("La Mosca Gau", "La mejor cuadrilla", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("Tu Madre", "La gorda", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("Pap puto negro", "Jajajaj ya tu sabe", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("Delirius papu", "papuuuuuu", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("KLK", "kukuxclan", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("motherfocker", "la madre de la foca", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("Pap puto negro", "Jajajaj ya tu sabe", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("Delirius papu", "papuuuuuu", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("KLK", "kukuxclan", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("motherfocker", "la madre de la foca", R.drawable.ic_child_care_black_24dp));
        return list;
    }

}
