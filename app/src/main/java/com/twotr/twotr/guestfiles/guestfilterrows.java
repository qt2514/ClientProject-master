package com.twotr.twotr.guestfiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vignesh2514 on 28/2/18...
 */

public class guestfilterrows {
    public static GuestData getguest(){

        List<GuestFilters> mList = new ArrayList<>();
        mList.add(new GuestFilters("oneonone",1.0f,200,"High School","Student"));
        mList.add(new GuestFilters("group",3.0f,150,"Middle School","Tutor"));
        mList.add(new GuestFilters("oneonone",2.0f,120,"Elementary","Student"));
        mList.add(new GuestFilters("group",4.5f,10,"College","Student"));
        mList.add(new GuestFilters("oneonone",1.0f,1,"University","Student"));

        return new GuestData(mList);

    }
}
