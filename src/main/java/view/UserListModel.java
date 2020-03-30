package view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UserListModel extends AbstractListModel {
    private List<Integer> friends;

    public UserListModel() {
        friends=new ArrayList<Integer>();
    }

    public int getSize() {
        return friends.size();
    }

    public Object getElementAt(int index) {
        return friends.get(index);
    }

    public void friendLoggedIn(int id){
        friends.add(id);
        fireContentsChanged(this,friends.size()-1,friends.size());
    }

    public void friendLoggedOut(String id){
        friends.remove(id);
        fireContentsChanged(this,0, friends.size());
    }
}
